<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control_web extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/users');
		$this->data['lang']=  $this->response->lang;
		$this->data['city'] = $this->response->city;
	}

	public function index_get() {
	}
	
	public function change_language_get()
	{
	  $current_lang = $this->input->get('language');
	  $this->session->set_userdata(array('language' => $current_lang));
	}
	
    public function register_post()
    {
       $this->form_validation->set_rules('phone', 'lang:phone', 'required|exact_length[9]');
       $this->form_validation->set_rules('name', 'lang:name', 'required');
        $this->form_validation->set_rules('password', 'lang:password', 'required');
       $this->form_validation->set_rules('city_id', 'lang:country', 'required');
       if (!$this->form_validation->run()) {
           throw new Validation_Exception(validation_errors());
       } else {
           $data = array(
             'phone' => $this->input->post('phone'),
             'whatsup_number' => $this->input->post('phone'),
             'name' => $this->input->post('name'),
             'email' =>$this->input->post('email'),
             'password' => md5($this->input->post('password')),
             'lang' => $this->input->post('lang') != "" ? strtolower($this->input->post('lang')) : $this->data['lang'],
             'city_id' => $this->input->post('city_id'),
            );
            if($this->input->post('user_gender')!= null && $this->input->post('user_gender')  != ''){
                $data['user_gender'] = $this->input->post('user_gender');
            }
            if($this->input->post('birthday')!= null && $this->input->post('birthday')  != ''){
                $data['birthday'] = $this->input->post('birthday');
            }
            if($this->input->post('personal_image')!= null && $this->input->post('personal_image')  != ''){
                $data['personal_image'] = $this->input->post('personal_image');
            }
           $user = $this->users->register($data ,ACCOUNT_TYPE::WEB);
            if($user != null){
               $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('sucess')));    
            }else{
               $this->response(array('status' => false, 'data' => $user, "message" => $this->lang->line('failed')));
            }
       }        
    }

   public function login_post() {
   	    if($this->session->userdata('IS_LOGGED_IN')!= null && $this->session->userdata('IS_ADMIN') == 1){
   	    	$this->response(array('status' => true, 'data' => array('cms_logged' => 1), "message" => $this->lang->line('sucess')));
   	    }else{
   	        $this -> form_validation -> set_rules('phone', 'lang:phone', 'required');
			$this -> form_validation -> set_rules('password', 'lang:password', 'required|max_length[32]');
			if (!$this -> form_validation -> run()) {
				throw new Validation_Exception(validation_errors());
			} else {
				$phone = $this -> input -> post('phone');
				$password = $this -> input -> post('password');
				$user = $this -> users -> login($phone, $password);
				if ($user) {
					 $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('sucess')));	
				} else {
					 $this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('not_a_user')));
				}
			}
		}
	}
	
	
	public function load_profile_get(){
	    $this -> data['subview'] = 'website/profile';
	    $this -> load -> view('website/_main_layout', $this -> data);
	}
	
	public function logout_get()
    {
       // Remove user data from session
       $this->session->sess_destroy();
       // Redirect to login page
       redirect('home_control');
    }
	
	public function get_notifications_for_me_get()
	{
		$this->load->model('data_sources/public_notifications' );
		$user_id = $this->current_user->user_id;
		$city_id = $this->data['city'];
		$data = $this->public_notifications->get_user_notifications($user_id , $city_id);
		// change seen status
		$this->public_notifications->change_to_seen($user_id);
	    $this->response(array('status' => true, 'data' => $data, "message" => $this->lang->line('sucess')));
	}

    public function get_my_notifications_count_get()
    {
       $this->load->model('data_sources/public_notifications');
	   $user_id = $this->current_user->user_id;  
	   $count = $this->public_notifications->get_not_seen_count($user_id);
	   $this->response(array('status' => true, 'data' => $count, "message" => $this->lang->line('sucess')));
    }
	
	public function get_my_items_unseen_count_get()
	{
	   $this->load->model('data_sources/ads');
	   $user_id = $this->current_user->user_id;  
	   $count = $this->ads->get_user_unseen_count($user_id);
	   $this->response(array('status' => true, 'data' => $count, "message" => $this->lang->line('sucess')));
	}
	
	
	public function get_my_items_get()
	{
		$this->load->model('data_sources/ads');
		$user_id = $this->current_user->user_id;
		$ads = $this->ads->get_user_ads_without_details($user_id);
		$this->response(array('status' => true, 'data' => $ads, "message" => $this->lang->line('sucess')));
	}

} 