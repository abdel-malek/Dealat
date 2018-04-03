<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
		$this->data['city'] = $this->response->city;
		$this->load->model('data_sources/users');
	}

	public function index_get() {
	}
	
	// this regiter method is for mobiles platforms
	public function register_post()
	{
        $this->form_validation->set_rules('phone', 'lang:phone', 'required|numeric|exact_length[9]');
        $this->form_validation->set_rules('name', 'lang:name', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
        	$data = array(
        	  'phone' => $this->input->post('phone'),
        	  'whatsup_number' => $this->input->post('phone'), 
        	  'name' => $this->input->post('name'),
        	  'lang' => $this->input->post('lang') != "" ? strtolower($this->input->post('lang')) : $this->data['lang'],
        	  'city_id' => $this->data['city']
			);
            $user = $this->users->register($data ,ACCOUNT_TYPE::MOBILE);
			if($user != null){
			   $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('sucess')));	
			}else{
			   $this->response(array('status' => false, 'data' => $user, "message" => $this->lang->line('failed')));
			}
        }		
	}

    public function verify_post() {
        $this->form_validation->set_rules('phone', 'Phone', 'required|numeric|exact_length[9]');
        $this->form_validation->set_rules('verification_code', 'Verification code', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $phone = $this->input->post('phone');
            $code = $this->input->post('verification_code');
			$is_multi = $this->input->post('is_multi');
			$user = $this->users->verify($phone , $code , $is_multi);
			if($user){
				$this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('account_confirmed')));
			}else{
				$this->response(array('status' => false, 'data' => $user, "message" => $this->lang->line('failed')));
			}
        }
    }
	
   public function save_user_token_post()
	{
        $this->form_validation->set_rules('token', 'lang:token', 'required');
        $this->form_validation->set_rules('os', 'lang:os', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
        	$this->load->model('data_sources/user_tokens');
        	$data = array(
        	 'token'=>$this->input->post('token'),
        	 'os'=> $this->input->post('os'),
        	 'user_id'=> $this->current_user->user_id
			);
			$check = $this->user_tokens->check($data['user_id']  , $data['token']);
			$saved_token = $this->user_tokens->save($data  , $check);
			if($saved_token){
				$this->response(array('status' => true, 'data' => $saved_token, "message" => $this->lang->line('sucess')));
			}else{
				$this->response(array('status' => false, 'data' => $saved_token, "message" => $this->lang->line('failed')));
			}
		}
	}
	
	public function get_countries_get()
	{
		$this->load->model('data_sources/locations');
		$countries = $this->locations->get_cities($this->data['lang']);
		if($countries){
			$this->response(array('status' => true, 'data' => $countries, "message" => $this->lang->line('sucess')));
		}else{
			$this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('failed')));
		}
	}
	
	public function get_my_favorites_get()
	{
		$this->load->model('data_sources/user_favorite_ads');
		$ads = $this->user_favorite_ads->get_my_favorites($this->current_user->user_id , $this->data['lang']);
		$this->response(array('status' => true, 'data' => $ads, "message" => $this->lang->line('sucess')));
	}
	
	public function get_my_items_get()
	{
	   $this->load->model('data_sources/ads');
	   $ads = $this->ads->get_user_ads($this->current_user->user_id , $this->data['lang']);
	   $this->response(array('status' => true, 'data' => $ads, "message" => $this->lang->line('sucess')));
	}
	
	public function get_my_info_get()
	{
	   $user_info = $this->users->get_user_info($this->data['lang'] , $this->current_user->user_id); 
	   if($user_info){
	   	  $this->response(array('status' => true, 'data' => $user_info, "message" => $this->lang->line('sucess')));
	   }else{
	   	  $this->response(array('status' => false, 'data' => '', "message" => 'No such user!'));
	   }
	}
	
	public function get_my_chat_sessions_get()
	{
		$this->load->model('data_sources/chat_sessions');
		$user_id = $this->current_user->user_id;
		$chat_sessions = $this->chat_sessions->get_user_chat_sessions($user_id);
		$this->response(array('status' => true, 'data' => $chat_sessions, "message" => $this->lang->line('sucess')));
	}
	
	public function get_chat_messages_get()
	{
		$this->load->model('data_sources/messages');
		$this->load->model('data_sources/chat_sessions');
		if($this->input->get('chat_session_id')){
			$chat_id = $this->input->get('chat_session_id');
			$chat_messages = $this->messages->get_by(array('chat_session_id'=>$chat_id));
			if($chat_messages){ // set chat session to seen
			    $changed_session = $this->chat_sessions->change_seen_status($chat_id , $this->current_user->user_id);
			}
			$this->response(array('status' => true, 'data' => $chat_messages, "message" => $this->lang->line('sucess')));
		}else if($this->input->get('ad_id')){
		   $ad_id = $this->input->get('ad_id');
		   $user_id = $this->current_user->user_id;
		   $chat_session_info = $this->chat_sessions->check_by_seller_or_user($ad_id , $user_id);
		  // dump($chat_session_info);
		   if($chat_session_info){
		   	  $chat_id = $chat_session_info->chat_session_id;
			  $chat_messages = $this->messages->get_by(array('chat_session_id'=>$chat_id));
			  if($chat_messages){ // set chat session to seen
				    $changed_session = $this->chat_sessions->change_seen_status($chat_id , $this->current_user->user_id);
			  }
			  $this->response(array('status' => true, 'data' => $chat_messages, "message" => $this->lang->line('sucess')));
		   }else{
		   	  $this->response(array('status' => true, 'data' => array(), "message" => 'No chat session for this user')); 
		   }
		}else{
		  throw new Parent_Exception('chat_session_id or ad_id is requierd');
		}
	}
	
	public function send_msg_post()
	{
	    $this->form_validation->set_rules('msg', 'msg', 'required');  
		$this->form_validation->set_rules('ad_id', 'ad_id', 'required');  
	    if (!$this->form_validation->run()) {
	       throw new Validation_Exception(validation_errors());
		}else{	
			$this->load->model('data_sources/messages');
			$ad_id = $this->input->post('ad_id');
			$chat_session_id = $this->input->post('chat_session_id');
			$msg = $this->input->post('msg');
		    $user_id = $this->current_user->user_id;
		    $msg_id = $this->messages->send_msg($ad_id ,$chat_session_id ,  $msg , $user_id);
			if($msg_id){
				$msg_info = $this->messages->get($msg_id);
				$this->response(array('status' => true, 'data' => $msg_info, "message" => $this->lang->line('sucess')));
			}else{
				$this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('failed')));
			}
		}
	}
	
    public function upload_personal_image_post()
	{
	  $image_name = date('m-d-Y_hia').'-'.'1';
      $image = upload_attachement($this, PERSONAL_IMAGES_PATH , $image_name);
      if (isset($image['image'])) {
          $image_path =  PERSONAL_IMAGES_PATH.$image['image']['upload_data']['file_name'];
          $this -> response(array('status' => true, 'data' => $image_path, 'message' => $this->lang->line('sucess')));
      }else{
           $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
      }
    }
	
	public function edit_user_info_post()
	{
	   	 $user_id = $this->current_user->user_id;
		 $data = array();
		 if($this->input->post('name')){
		 	$data['name'] = $this->input->post('name');
		 }
		 if($this->input->post('email')){
		 	$data['email'] = $this->input->post('email');
		 }
		 if($this->input->post('phone')){
		 	$data['phone'] = $this->input->post('phone');
		 }
		 if($this->input->post('city_id')){
		 	$data['city_id'] = $this->input->post('city_id');
		 }
	     if($this->input->post('image')){
	  	   $data['personal_image'] = $this->input->post('image');
	     }
		 if($this->input->post('gender')){
	  	   $data['gender'] = $this->input->post('gender');
	     }
         if($this->input->post('birthday')){
	  	   $data['birthday'] = $this->input->post('birthday');
	     }
         if($this->input->post('whatsup_number')){
	  	   $data['whatsup_number'] = $this->input->post('whatsup_number');
	     }
		 $image_name = date('m-d-Y_hia').'-'.'1';
	     $image = upload_attachement($this, PERSONAL_IMAGES_PATH , $image_name);
	     if (isset($image['personal_image'])) {
	         $data['personal_image'] =  PERSONAL_IMAGES_PATH.$image['personal_image']['upload_data']['file_name'];
	     }
	    $user_id = $this->users->save($data , $user_id);
	    $user_info = $this->users->get($user_id , true);
	    $this->response(array('status' => true, 'data' => $user_info, "message" => $this->lang->line('sucess')));
	}
	
	public function mark_search_post()
	{
		$this->load->model('data_sources/user_search_bookmarks');
		$user_id = $this->current_user->user_id;
		$data_json = json_encode($this->input->post());
		$data = array(
		  'user_id' => $user_id , 
		  'query' => $data_json
		);
		$bookmark = $this->user_search_bookmarks->save($data); 
		$this->response(array('status' => true, 'data' => $bookmark, "message" => $this->lang->line('sucess')));
	}
	
	public function get_my_bookmarks_get()
	{
		$this->load->model('data_sources/user_search_bookmarks');
		$user_id = $this->current_user->user_id;
		$bookmarks = $this->user_search_bookmarks->get_by(array('user_id' => $user_id , 'deleted' => 0));
		$this->response(array('status' => true, 'data' => $bookmarks, "message" => $this->lang->line('sucess')));
	}
	
	public function delete_bookmark_post()
	{
		if(!$this->input->post('user_bookmark_id')){
		   throw new Parent_Exception('user_bookmark_id is requierd');
		}else{
		    $mark_id = $this->input->post('user_bookmark_id');
			$this->load->model('data_sources/user_search_bookmarks');
			$deletd_mark_id = $this->user_search_bookmarks->save(array('deleted' => 1) , $mark_id);
			$this->response(array('status' => true, 'data' => $deletd_mark_id, "message" => $this->lang->line('sucess')));
		}
	}
	
	public function rate_seller_post()
	{
	   $this->load->model('data_sources/user_ratings');
	   $user_id = $this->current_user->user_id;
	   if(!$this->input->post('seller_id')){
	   	  throw new Parent_Exception('seller_id is requierd');
	   }else{
	   	 $seller_id = $this->input->post('seller_id');
		 if(!$this->input->post('rate')){
		 	throw new Parent_Exception('rate is requierd');
		 }else{
		     $rate = $this->input->post('rate');
			 $data = array(
			  'rated_user_id' => $seller_id , 
			  'rated_by_user_id' => $user_id, 
			  'rate' => $rate
			 );
			 $user_rate_id = $this->user_ratings->save_rate($data);
			 $this->response(array('status' => true, 'data' => $user_rate_id, "message" => $this->lang->line('sucess')));	
		 }
	   }
   }

   public function logout_post()
   {
   	  if(!$this->input->post('token')){
   	  	 throw new Parent_Exception('token is requierd');
   	  }else{
   	  	 $this->load->model('data_sources/user_tokens');
   	     $user_id = $this->current_user->user_id;
		 $token = $this->input->post('token');
		 $deleted = $this->user_tokens->delete_by_token($user_id , $token);
	     if($deleted){
	     	 $this->response(array('status' => true, 'data' => '', "message" => $this->lang->line('sucess')));
	     }else{
	     	 $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed'))); 
	     }
   	  }
   }
}
