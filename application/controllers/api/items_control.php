<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Items_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->data['lang']=  $this->response->lang;
	}
	
	public function get_latest_items_get()
	{
    	$ads_list  = $this->ads->get_latest_ads($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}
	
	public function get_items_by_main_category_get()
	{
		$main_category_id = $this->input->get('category_id');
		$ads_list = $this->ads->get_ads_by_category($main_category_id , $this->data['lang']);
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}
	
	public function get_pending_items_get()
	{
		$ads_list = $this->ads->get_pending_ads($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}
	
	public function get_pending_count_get()
	{
		$count = $this->ads->get_pending_ads_counts();
		$this->response(array('status' => true, 'data' =>$count, 'message' => ''));
	}
	
	public function get_item_details_get()
    {
        $user_id = null;
        if($this->session->userdata('PHP_AUTH_USER')){
            $user_id = $this->session->userdata('LOGIN_USER_ID');
        }
        else if($this->response->is_auth){
            $user_id = $this->response->is_auth;
        }
        $ad_id = $this->input->get('ad_id');
        $tamplate_id = $this->input->get('template_id');
        $deatils = $this->ads->get_ad_details($ad_id , $this->data['lang'] , $tamplate_id , $user_id);
        $this->response(array('status' => true, 'data' =>$deatils, 'message' => ''));
    }

    public function post_new_item_post()
    {
        $this->load->model('data_sources/categories');
		$this -> form_validation -> set_rules('category_id', 'lang:category_id', 'required');
		$this -> form_validation -> set_rules('city_id', 'lang:city', 'required');
		$this -> form_validation -> set_rules('show_period', 'lang:show_period', 'required');
		$this -> form_validation -> set_rules('price', 'lang:price', 'required');
		$this -> form_validation -> set_rules('title', 'lang:title', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		}else {
		   $basic_data = array(
		     'user_id' => $this->current_user->user_id,
		     'city_id' => $this->input->post('city_id'),
		     'show_period' => $this->input->post('show_period'),
		     'price' => $this->input->post('price'),
		     'title' => $this->input->post('title'),
		     'category_id' => $this->input->post('category_id'),
		     'is_featured' => $this->input->post('is_featured'),
		     'is_negotiable' => $this->input->post('is_negotiable'),
		     'ad_visible_phone' => $this->input->post('ad_visible_phone')
		   );
		   if($this->input->post('main_video') && $this->input->post('main_video')!=''){
		   	  $basic_data['main_video'] = $this->input->post('main_video');
		   }
		   if($this->input->post('location_id') && $this->input->post('location_id')!=''){
		   	  $basic_data['location_id'] = $this->input->post('location_id');
		   }
		   if($this->input->post('description') && $this->input->post('description')!=''){
		   	  $basic_data['description'] = $this->input->post('description');
		   }
		   $main_image = null;
		   if ($this->input->post('main_image') && $this->input->post('main_image') != '') {
			  $main_image = $this->input->post('main_image');
		   }
		   $ads_images_paths = array();
		   if($this->input->post('images')){
		   	   $ads_images_paths = json_decode($this -> input -> post('images'), true);
		   }
		   $category_info = $this->categories->get($this->input->post('category_id'));
		   $tamplate_id = $category_info->tamplate_id;
		   $save_result = $this->ads->create_an_ad($basic_data, $main_image , $ads_images_paths , $tamplate_id);
		   if($save_result != false){
		   	 // send an email 
		   	 $this->ads->send_pending_email($save_result);
		   	 $this -> response(array('status' => true, 'data' => $save_result, 'message' => $this->lang->line('sucess')));
		   }else{
		   	 $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		   }
		}
    }

   public function edit_post()
    {
      if(!$this->input->post('ad_id')){
      	 throw new Parent_Exception('ad id is required');
      }else{
      	$ad_id = $this->input->post('ad_id');
		$ad_info = $this->ads->get($ad_id);
		if($ad_info->user_id != $this->current_user->user_id){
			 throw new Parent_Exception('you do not have the permission to edit this ad');
		}else{
		   $category_id  = $ad_info->category_id;
		   $edit_result = $this->ads->edit($ad_id , $category_id );
		   if($edit_result){
		   	 // send an email
		   	 // $this->ads->send_pending_email($edit_result , true);
		   	  $this -> response(array('status' => true, 'data' => $edit_result, 'message' => $this->lang->line('sucess')));
		   }else{
		   	  $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		   }
		}
      } 
    }

  public function item_images_upload_post()
   {
      $image_name = date('m-d-Y_hia').'-'.$this->current_user->user_id;
      $image = upload_attachement($this, ADS_IMAGES_PATH , $image_name);
      if (isset($image['image'])) {
          $image_path =  ADS_IMAGES_PATH.$image['image']['upload_data']['file_name'];
          $this -> response(array('status' => true, 'data' => $image_path, 'message' => $this->lang->line('sucess')));
      }else{
           $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
      }
   }
	
  public function item_video_upload_post()
   {
   	  $vedio_name = date('m-d-Y_hia').'-'.$this->current_user->user_id;
	  $vedio = upload_attachement($this, ADS_VEDIO_PATH , $vedio_name, true); 
	  if (isset($vedio['video'])) {
          $vedio_path =  ADS_VEDIO_PATH.$vedio['video']['upload_data']['file_name'];
          $this -> response(array('status' => true, 'data' => $vedio_path, 'message' => $this->lang->line('sucess')));
      }else{
          $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
      }
   }
   
  public function delete_vedios_post()
   {
        $videos = $this -> input -> post('videos');
		if(!$videos){
			throw new Parent_Exception('You have to provide videos array');
		}
		$videos_array = json_decode($videos, true);
	    $deleted = $this->ads->delete_videos($videos_array);
		if($deleted){
		   $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));	
		}else{
		   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		}
   }	
	
  public function delete_images_post()
   {
		$images = $this -> input -> post('images');
		if(!$images){
			throw new Parent_Exception('You have to provide images array');
		}
		$images_array = json_decode($images, true);
	    $deleted = $this->ads->delete_images($images_array);
		if($deleted){
		   $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));	
		}else{
		   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		}
   }

	
  public function search_get()
   {
		$query_string = $this->input->get('query');
		$category_id = $this->input->get('category_id');
		$resuts = $this->ads->serach_with_filter( $this->data['lang']  , $query_string , $category_id);
		$this->response(array('status' => true, 'data' =>$resuts, 'message' => $this->lang->line('sucess')));
   }
	
  public function get_bookmark_search_get()
   {
		$this->load->model('data_sources/user_search_bookmarks');
		$bookmark_id = $this->input->get('user_bookmark_id');
		$bookmark_info = $this->user_search_bookmarks->get($bookmark_id);
		$filter_data = $bookmark_info->query;
		$filter_data_array = json_decode($filter_data , true);
		foreach ($filter_data_array as $key => $value) {
			$_GET[$key] = $value;
		}
		$this->search_get();
	}
	
	public function get_data_lists_get()
	{
	     $this->load->model('data_sources/types');
		 $this->load->model('data_sources/educations');
		 $this->load->model('data_sources/schedules');
		 $this->load->model('data_sources/locations');
		 $this->load->model('data_sources/show_periods');
		 $this->load->model('data_sources/certificates');
		 $this->load->model('data_sources/property_states');
		 $locations = $this->locations->get_all($this->data['lang']);
		 //$cities = $this->locations->get_cities($this->data['lang']);
		 $nested_locations = $this->locations->get_cities_with_locations($this->data['lang']);
		 $types = $this->types->get_all_by_tamplate($this->data['lang']);
		 $educations = $this->educations->get_all($this->data['lang']);
		 $schedules = $this->schedules->get_all($this->data['lang']);
		 $show_periods = $this->show_periods->get_all($this->data['lang']);
		 $certificates = $this->certificates->get_all($this->data['lang']);
		 $property_states = $this->property_states->get_all($this->data['lang']);
		 $data = array('location' =>$locations ,'nested_locations'=>$nested_locations, 'types' =>$types , 'educations' =>$educations , 'schedules'=>$schedules , 'show_periods'=>$show_periods , 'certificates'=>$certificates , 'states' => $property_states);
		 $this -> response(array('status' => true, 'data' => $data, 'message' => ''));
	}
	
	public function get_data_get()
	{
		$attrs = TAMPLATES::get_tamplate_attributes_array();
		$status = STATUS::get_list();
		$data = array('attrs'=>$attrs , 'status'=>$status);
		$this -> response(array('status' => true, 'data' => $data, 'message' => ''));
	}
	
	// public function action_post()
	// {
		// $this->load->model('notification');
		// $this->load->model('data_sources/admin_actions_log');
		// $this->load->helper('notification_messages_helper');
		// if(!$this->input->post('ad_id')){
		  	// throw Parent_Exception('you have to provide an id');
		// }
		// else if(!$this->input->post('action')){
			// throw Parent_Exception('you have to provide an action');
		// }else{
			// $ad_id = $this->input->post('ad_id');
			// $action = $this->input->post('action');
			// //$ad_info = $this->ads->get($ad_id);
			// if($action == 'accept'){
				// if(!$this->input->post('publish_date')){
				  // throw new Parent_Exception('you have to provide a publish date');	
				// }else{
				   // $ad_info = $this->ads->get_info($ad_id , $this->data['lang']);
				   // $data = array(
				     // //'publish_date'=>$this->input->post('publish_date'),
				     // 'is_featured' => $this->input->post('is_featured'),
				     // 'status' => STATUS::ACCEPTED ,
				     // 'user_seen' => 0
				   // );
				   // $current_info = $this->ads->get($ad_id);
				   // if(!isset($ad_info->publish_date)){
				   	 // $data['publish_date'] = $this->input->post('publish_date');
				   // }else{
				   	  // if($ad_info->expired_after <= 0){ //if the ad is expired
				   	  	  // $data['publish_date'] = $this->input->post('publish_date');
				   	  // }
				   // }
				   // $ad_id = $this->ads->save($data , $ad_id);
				   // $this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::ACCEPTED_MSG);
				   // $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ACCEPT_AD , $ad_id);
				// }
			// }else if($action == 'reject'){
			    // $note = $this->input->post('reject_note');
			    // $this->ads->save(array('status'=>STATUS::REJECTED , 'reject_note' => $note , 'user_seen' => 0 ) , $ad_id);
				// $ad_info = $this->ads->get_info($ad_id ,  $this->data['lang']);
				// $this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::REJECTED_MSG);
				// $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::REJECT_AD , $ad_id);
			// }else if($action == 'hide'){
			    // $this->ads->save(array('status'=>STATUS::HIDDEN ,'user_seen' => 0) , $ad_id);
				// $ad_info = $this->ads->get_info($ad_id ,  $this->data['lang']);
				// $this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::HIDE_MSG);
			    // $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::HIDE_AD , $ad_id);
			// }else if($action == 'show'){
			    // $this->ads->save(array('status'=>STATUS::ACCEPTED ,'user_seen' => 0) , $ad_id);
				// $ad_info = $this->ads->get_info($ad_id ,  $this->data['lang']);
			    // $this->notification-> send_action_notification($ad_info->user_id , $ad_info , NOTIFICATION_MESSAGES::SHOW_MSG);
		        // $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::SHOW_AD , $ad_id);
			// }else if($action == 'delete'){
		    	// $template_id = $this->input->post('template_id');
				// $result = $this->ads->delete_an_ad($ad_id , $template_id);
				// if(!$result){
					 // throw new Parent_Exception('Some thing wrong'); 	 
				// }
			    // $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_AD , $ad_id);
			// }else{
			   // throw new Parent_Exception('No Such action'); 	
			// }
		  // $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));
		// }
    // }

   public function set_as_favorite_post()
   {
   	 if(!$this->input->post('ad_id')){
   	 	throw new Parent_Exception('ad id is required');
   	 }else{
	   	 $ad_id = $this->input->post('ad_id');
		 $user_id = $this->current_user->user_id;
		 $this->load->model('data_sources/user_favorite_ads');
		 $favorate_id = $this->user_favorite_ads->save(array('ad_id'=>$ad_id , 'user_id'=>$user_id));
		 if($favorate_id){
		 	$this -> response(array('status' => true, 'data' => $favorate_id, 'message' => $this->lang->line('sucess')));
		 }else{
		 	$this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		 }
	 }
   }

  public function remove_from_favorite_post()
   {
   	 if(!$this->input->post('ad_id')){
   	 	throw new Parent_Exception('ad id is required');
   	 }else{
   	 	 $ad_id = $this->input->post('ad_id');
		 $user_id = $this->current_user->user_id;
		 $this->load->model('data_sources/user_favorite_ads');
		 $deleted = $this->user_favorite_ads->delete_by_user($ad_id , $user_id);
		 if($deleted){
		 	$this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));
		 }else{
		 	$this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		 }
   	  }
   }
   
   
  public function test_post()
    {
     $this->load->model('notification');
	 $this->load->helper('notification');
     $this->notification->send_notification(6 , 'Your ad is accepted' , null , 'Ad Accepted' , NotificationHelper::ACTION);
    }
   

  public function change_status_post()
	{
	  if(!($this->input->post('status') == STATUS::HIDDEN || $this->input->post('status') == STATUS::DELETED )){
	  	  throw new Parent_Exception($this->lang->line('change_status_warning'));
	  }else{
	   	 if(!$this->input->post('ad_id')){
	   	 	throw new Parent_Exception('ad_id is requierd');
	   	 }else{
	   	 	$ad_id = $this->ads->save(array('status'=>$this->input->post('status')) , $this->input->post('ad_id'));
	   	 	$this -> response(array('status' => true, 'data' => $ad_id, 'message' => $this->lang->line('sucess')));
	   	 }
 	  }
	}
	
   public function get_report_messages_get()
    {
	    $this->load->model('data_sources/report_messages');
	    $messages = $this->report_messages->get_all($this->data['lang']);
	    $this -> response(array('status' => true, 'data' => $messages, 'message' => $this->lang->line('sucess'))); 
    }
   
  public function report_item_post()
   {
		$this -> form_validation -> set_rules('ad_id', 'ad_id', 'required');
		$this -> form_validation -> set_rules('report_message_id', 'report_message_id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$this->load->model('data_sources/reported_ads');
			$ad_id = $this->input->post('ad_id');
			$message = $this->input->post('report_message_id');
		    $user_id = null;
	        if($this->session->userdata('PHP_AUTH_USER')){
	            $user_id = $this->session->userdata('LOGIN_USER_ID');
	        }
	        else if($this->response->is_auth){
	            $user_id = $this->response->is_auth;
	        }
			$data = array('ad_id' => $ad_id , 'report_message_id' =>$message , 'user_id'=> $user_id ,'report_seen' =>0 );
			$repored_ad_id = $this->reported_ads->save($data);
			if($repored_ad_id){
			   $this->load->model('data_sources/report_messages');
			   $message_info = $this->report_messages->get_info($this->input->post('report_message_id'), $this->data['lang']);
			   $this->reported_ads->send_email($this->input->post('ad_id') , $message_info->msg);
			   $this -> response(array('status' => true, 'data' => $repored_ad_id, 'message' => $this->lang->line('sucess')));	
			}else{
			   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
			}
		}
   	}
}
