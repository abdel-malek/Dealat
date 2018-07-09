<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Items_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->data['lang']=  $this->response->lang;
	    if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
	
	public function index_get()
	{
		$this -> data['subview'] = 'admin/ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	
	public function load_reported_items_page_get()
	{
		$this -> data['subview'] = 'admin/ads/reported_ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function all_get()
	{
	   $filter_option = array();
	   if($this->input->get('status')){
	   	 $filter_option['status'] = $this->input->get('status');
	   }
	   $ads = $this->ads->get_all_ads_with_details($this->data['lang']);
	   $output = array("aaData" => array());
		foreach ($ads as $row) {
			$recorde = array();
			$recorde[] = $row -> ad_id;
			$recorde[] = $row -> created_at;
			$recorde[] = TAMPLATES::get_tamplate_name($row -> tamplate_id  , $this->data['lang']);
			$recorde[] = $row->category_name;
			$recorde[] = $row -> title;
			if($row -> publish_date != null){
			   $recorde[] = $row -> publish_date;
			}else{
			   $recorde[] = $this->lang->line('not_set'); 
			}
			$recorde[] = $row -> price;
			$recorde[] = $row -> city_name. ' - '.$row->location_name;
			$recorde[] = STATUS::get_name($row -> status  , $this->data['lang']);
			$recorde[] = EDIT_STATUS::get_edit_status_name($row->edit_status , $this->data['lang']);
			$recorde[] = $row -> tamplate_id;
		//	$recorde[] = $row -> tamplate_id .''.$row->expired_after;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	
	public function get_all_reported_items_get()
	{
		$this->load->model('data_sources/reported_ads');
		$ads = $this->reported_ads-> get_reported_ads();
		//dump($ads);
		$output = array("aaData" => array());
		foreach ($ads as $row) {
			$recorde = array();
			$recorde[] = $row->ad_number;
			$recorde[] = $row->ad_title;
	        $recorde[] = STATUS::get_name($row -> status  , $this->data['lang']);
			$recorde[] = $row->tamplate_id; // for details: 2
		    $recorde[] = $row->reports_count; // for reports: 3
		    $recorde[] = $row->report_seen;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	
	public function get_item_reports_get()
	{
		$this->load->model('data_sources/reported_ads');
		$reports = $this->reported_ads-> get_ad_reports($this->input->get('ad_id') , $this->data['lang']);
		$output = array("aaData" => array());
		foreach ($reports as $row) {
			$recorde = array();
			$recorde[] = $row->reported_ad_id;
			$recorde[] = $row->created_at;
			if($row->user_name == null){
				$recorde[] = $this->lang->line('anknown');
			}else{
				$recorde[] = $row->user_name;
			}
			$recorde[] = $row->report_msg;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	
	public function set_reports_to_seen_post()
	{
	   $this->load->model('data_sources/reported_ads');
	   $this->reported_ads->set_to_seen();
	   $this -> response(array('status' => true, 'data' =>'', 'message' => $this->lang->line('sucess')));
	}
	
	
   public function get_not_seen_reported_get()
    {
      $this->load->model('data_sources/reported_ads');
	  $ads = $this->reported_ads->get_not_seen();
	  // set to seen 
	  $this->reported_ads->set_to_seen();
	  $this -> response(array('status' => true, 'data' =>$ads, 'message' => $this->lang->line('sucess')));
    }
	
	public function send_email_post()
	{
		$res = $this->ads->send_pending_email();
		if($res){
			 $this -> response(array('status' => true, 'data' =>'', 'message' => $this->lang->line('sucess')));
		}else{
			 $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));
		}
	}
	
    public function action_post()
	{
		$this->load->model('notification');
		$this->load->model('data_sources/admin_actions_log');
		$this->load->helper('notification_messages_helper');
		if(!$this->input->post('ad_id')){
		  	throw Parent_Exception('you have to provide an id');
		}
		else if(!$this->input->post('action')){
			throw Parent_Exception('you have to provide an action');
		}else{
			$ad_id = $this->input->post('ad_id');
			$action = $this->input->post('action');
			//$ad_info = $this->ads->get($ad_id);
			$data = array('user_seen' => 0);
			if($this->input->post('ad_contact_phone')){
				$data['ad_contact_phone'] = $this->input->post('ad_contact_phone');
			}
			if($action == 'accept'){
				if(!$this->input->post('publish_date')){
				  throw new Parent_Exception('you have to provide a publish date');	
				}else{
				   $ad_info = $this->ads->get_info($ad_id , $this->data['lang']);
				   $data['is_featured'] = $this->input->post('is_featured');
				   $data['status'] = STATUS::ACCEPTED;
				   $current_info = $this->ads->get($ad_id);
				   if(!isset($ad_info->publish_date)){
				   	 $data['publish_date'] = $this->input->post('publish_date');
				   }else{
				   	  if($ad_info->expired_after <= 0){ //if the ad is expired
				   	  	  $data['publish_date'] = $this->input->post('publish_date');
				   	  }
				   }
				   $ad_id = $this->ads->save($data , $ad_id);
				   $this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::ACCEPTED_MSG);
				   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ACCEPT_AD , $ad_id);
				}
			}else if($action == 'reject'){
			    $note = $this->input->post('reject_note');
				$data['status'] = STATUS::REJECTED ;
				$data['reject_note'] = $note;
			    $this->ads->save($data , $ad_id);
				$ad_info = $this->ads->get_info($ad_id ,  $this->data['lang']);
				$this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::REJECTED_MSG);
				$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::REJECT_AD , $ad_id);
			}else if($action == 'hide'){
				$data['status'] = STATUS::HIDDEN;
			    $this->ads->save($data , $ad_id);
				$ad_info = $this->ads->get_info($ad_id ,  $this->data['lang']);
				$this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::HIDE_MSG);
			    $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::HIDE_AD , $ad_id);
			}else if($action == 'show'){
				$data['status'] = STATUS::ACCEPTED ;
			    $this->ads->save($data , $ad_id);
				$ad_info = $this->ads->get_info($ad_id ,  $this->data['lang']);
			    $this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::SHOW_MSG);
		        $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::SHOW_AD , $ad_id);
			}else if($action == 'delete'){
		    	$template_id = $this->input->post('template_id');
				$result = $this->ads->delete_an_ad($ad_id , $template_id);
				if(!$result){
					 throw new Parent_Exception('Some thing wrong'); 	 
				}
			    $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_AD , $ad_id);
			}else{
			   throw new Parent_Exception('No Such action'); 	
			}
		  $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));
		}
    }


  public function edit_item_post()
   {
      if(!$this->input->post('ad_id')){
      	 throw new Parent_Exception('ad id is required');
      }else{
      	   $this->load->model('data_sources/admin_actions_log');
	       $ad_id = $this->input->post('ad_id');
		   $ad_info = $this->ads->get($ad_id);
		   $category_id  = $ad_info->category_id;
		   $edit_result = $this->ads->edit($ad_id , $category_id , 1 );
		   if($edit_result){
		   	  // add to log
		   	  $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_AD , $ad_id);
		   	  $this -> response(array('status' => true, 'data' => $edit_result, 'message' => $this->lang->line('sucess')));
		   }else{
		   	  $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		   }
      }
   }
}