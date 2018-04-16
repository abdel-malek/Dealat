<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_manage extends REST_Controller {
	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/users');
		$this->load->model('data_sources/admins');
		$this->data['lang']=  $this->response->lang;
		 if($this->data['lang'] == 'en'){
		  $this -> data['current_lang'] = 'English';
		 }else{
		   $this -> data['current_lang'] = 'Arabic';
		 }
	}
	
	public function index_get()
	{
	  $this -> data['subview'] = 'admin/users/index';
	  $this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	
	public function load_login_page_get()
	{
	  $this -> load -> view('admin/users/login', $this -> data);
	}
	
	
	public function login_post()
	{
	    $this -> form_validation -> set_rules('admin_username', 'username', 'required');
		$this -> form_validation -> set_rules('admin_password', 'password', 'required|max_length[32]');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$username = $this -> input -> post('admin_username');
			$password = $this -> input -> post('admin_password');
			$user = $this -> admins -> login($username, $password);
			if ($user) {
				// $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('sucess')));	
				redirect('admin/items_manage');
			} else {
			    $this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('not_a_user')));
			   //$this -> session -> set_flashdata('error', $this -> lang -> line('incorrect_credentials'));
			   //redirect('users');
			}
		}
	}
	
	public function logout_get()
	{
	   // Remove user data from session
       $this->session->sess_destroy();
       // Redirect to login page
       redirect('admin/users_manage/load_login_page');
	}
	
	public function get_all_get()
	{
		//dump(user_status_checkbox(0 ,1));
		$users = $this->users->get_with_ads_info($this->data['lang']);
		$output = array("aaData" => array());
		foreach ($users as $row) {
			$recorde = array();
			$recorde[] = $row -> user_id;
			$recorde[] = $row -> name;
			$recorde[] = $row -> phone;
			if($row -> email != null){
				$recorde[] = $row -> email;
			}else{
				$recorde[] = $this->lang->line('not_set'); 
			}
			$recorde[] = $row -> city_name;
			if($row->ads_num == 0){
				$recorde[] =  $this->lang->line('none');
			}else{
				$recorde[] = $row->ads_num;
			}
		    // if($row -> is_active != 1){
				// $recorde[] = $this->lang->line('inactive'); 
			// }else{
				// $recorde[] = $this->lang->line('active'); 
			// }
			$recorde[] = user_status_checkbox($row->is_active , $row->user_id);
			//$recorde[] = '';
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	
	public function change_language_get()
	{
	  $current_lang = $this->input->get('lang');
	  $this->session->set_userdata(array('language' => $current_lang));
	  redirect('admin/items_manage');
	}
	
	public function change_status_post()
	{
		$user_id = $this->input->post('user_id');
		$active_status = $this->input->post('is_active');
		$updated_user_id = $this->users->save(array('is_active'=> !$active_status) , $user_id);
		$this -> response(array('status' => true, 'data' => $updated_user_id, 'message' => $this->lang->line('sucess')));
	}
	
	public function load_notification_page_get()
	{
	   $this -> data['subview'] = 'admin/users/notification';
	   $this -> load -> view('admin/_main_layout', $this -> data);
	}
	
    public function send_notifications_by_city_post()
    {
   	  $this->form_validation->set_rules('city_id', 'city_id', 'required'); 
	  $this->form_validation->set_rules('body', 'body', 'required'); 
	  $this->form_validation->set_rules('title', 'title', 'required');  
	  if (!$this->form_validation->run()) {
	 	 throw new Validation_Exception(validation_errors());
	  }else{
	  	  $city_id = $this->input->post('city_id');
		  $text = $this->input->post('body');
		  $title = $this->input->post('title');
		  $users_ids =  $this->users->get_user_ids_by_city($city_id);
		  if($users_ids){
		  	$this->load->model('notification');
			$this->notification->send_notofication_to_group($users_ids ,$text , null , $title ,  NotificationHelper::PUBLIC_NOTFY);
			$this->response(array('status' => true, 'data' => '', "message" => $this->lang->line('sucess')));
		  }else{
		  	$this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('no_users')));
		  }
	  }
    }
	
   public function send_public_notification_post()
   {
   	  $this->form_validation->set_rules('body', 'body', 'required'); 
	  $this->form_validation->set_rules('title', 'title', 'required');  
	  if (!$this->form_validation->run()) {
	 	 throw new Validation_Exception(validation_errors());
	  }else{
  	 	  $this->load->model('notification');
		  $text = $this->input->post('body');
		  $title = $this->input->post('title');
		  $this->notification->send_public_notification($text , null , $title ,  NotificationHelper::PUBLIC_NOTFY);
		  $this->response(array('status' => true, 'data' => '', "message" => $this->lang->line('sucess')));
	  }
   }
}