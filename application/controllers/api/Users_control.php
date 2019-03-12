<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
		$this->data['city'] = $this->response->city;
		$this->load->model('data_sources/users');
		if($this->response->os  == OS::IOS){
			$this->data['os'] = '_os'; 
		}else{
			$this->data['os']= '';
		}
	}

	public function index_get() {
		//dump($this->data['city']);
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
				$this->response(array('status' => false, 'data' => $user, "message" => $this->lang->line('incorrect_verfication')));
			}
        }
    }
	
   public function save_user_token_post()
	{
        $this->form_validation->set_rules('token', 'lang:token', 'required');
        $this->form_validation->set_rules('os', 'os:os', 'required');
	    $this->form_validation->set_rules('os', 'lang:lang', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
        	$this->load->model('data_sources/user_tokens');
        	$data = array(
        	 'token'=>$this->input->post('token'),
        	 'os'=> $this->input->post('os'),
        	 'user_id'=> $this->current_user->user_id,
        	 'lang' => $this->input->post('lang')
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
		//$method = 'get_cities'.$this->data['os'];
		$countries = $this->locations->get_cities($this->data['lang']);
		if($countries){
			$this->response(array('status' => true, 'data' => $countries, "message" => $this->lang->line('sucess'), 'currency_ar' =>'ل.س' , 'currency_en' => 'S.P'));
		}else{
			$this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('failed'), 'currency_ar' =>'' , 'currency_en' => ''));
		}
	}
	
	public function get_my_favorites_get()
	{
		$this->load->model('data_sources/user_favorite_ads');
		//$method = 'get_my_favorites'.$this->data['os'];
		$ads = $this->user_favorite_ads->get_my_favorites($this->current_user->user_id , $this->data['lang']);
		$this->response(array('status' => true, 'data' => $ads, "message" => $this->lang->line('sucess')));
	}
	
	public function get_my_items_get()
	{
	   $this->load->model('data_sources/ads');
	   //$method = 'get_user_ads'.$this->data['os'];
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
		$this->load->model('data_sources/deleted_chat_sessions');
		if($this->input->get('chat_session_id')){
			$user_id = $this->current_user->user_id;
			$chat_id = $this->input->get('chat_session_id');
			$deleted_check = $this->deleted_chat_sessions->get_by(array('user_id' => $user_id , 'chat_session_id' => $chat_id) , true);
		   	$data = array('chat_session_id' => $chat_id);
		    if($deleted_check){
		   	  	if($deleted_check->is_shown == 1){
		   	  		//$data['DATE_ADD(created_at, INTERVAL +15 SECOND) >='] = $deleted_check->modified_at;
		   	  		$data['created_at >='] = $deleted_check->modified_at;
		   	  	}
		   	}
			$chat_messages = $this->messages->get_by($data);
			if($chat_messages){ // set chat session to seen
			    $changed_session = $this->chat_sessions->change_seen_status($chat_id , $this->current_user->user_id);
			}
			$this->response(array('status' => true, 'data' => $chat_messages, "message" => $this->lang->line('sucess')));
		}else if($this->input->get('ad_id')){
		   $ad_id = $this->input->get('ad_id');
		   $user_id = $this->current_user->user_id;
		   $chat_session_info = $this->chat_sessions->check_by_seller_or_user($ad_id , $user_id);
		   if($chat_session_info){
		   	  $chat_id = $chat_session_info->chat_session_id;
		   	  $deleted_check = $this->deleted_chat_sessions->get_by(array('user_id' => $user_id , 'chat_session_id' => $chat_id) , true);
		   	  $data = array('chat_session_id' => $chat_id);
		   	  if($deleted_check){
		   	  	//if($deleted_check->is_shown == 1){
		   	  		//$data['DATE_ADD(created_at, INTERVAL +15 SECOND) >='] = $deleted_check->modified_at;
		   	  		$data['created_at >='] = $deleted_check->modified_at;
		   	  	//}
		   	  }
			  $chat_messages = $this->messages->get_by($data);
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
				// check if this chat session is deleted for the users.
				$this->load->model('data_sources/deleted_chat_sessions');
				$deleted_chat_sessions = $this->deleted_chat_sessions->get_by(array('chat_session_id' => $msg_info->chat_session_id));
			    if($deleted_chat_sessions){
			    	foreach ($deleted_chat_sessions as $key => $row) {
			    		//update the shown status to shown for the deleted chat sessions if it's not already shown.
			    		if($row->is_shown != 1){
			    			$this->deleted_chat_sessions->save(array('is_shown'=> 1 ,
			    			                                         'modified_at' => $msg_info->modified_at) , $row->deleted_chat_session_id);
			    		}
			    	}
			    } 
				$this->response(array('status' => true, 'data' => $msg_info, "message" => $this->lang->line('sucess')));
			}else{
				$this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('failed')));
			}
		}
	}


   public function delete_chat_post(){
   	    $this->form_validation->set_rules('chat_id', 'Chat ID', 'required');   
	    if (!$this->form_validation->run()) {
	        throw new Validation_Exception(validation_errors());
		}else{	
			$this->load->model('data_sources/deleted_chat_sessions');
			$data = array('chat_session_id' => $this->post('chat_id') , 'user_id' => $this->current_user->user_id);
			$already_deleted = $this->deleted_chat_sessions->get_by($data , true);
			if($already_deleted != null){
				$this->deleted_chat_sessions->delete($already_deleted->deleted_chat_session_id);
			}
		   $res_id = $this->deleted_chat_sessions->save($data);
		   if($res_id){
		   	   $this->response(array('status' => true, 'data' =>$res_id, "message" => 'Deleted successfully' ));
		   }else{
		   	   $this->response(array('status' => false, 'data' =>$res_id, "message" => 'Something went wrong' ));
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
		 if($this->input->post('email') != null){
		   if(trim($this->input->post('email')) == -1){
		   	  $data['email'] = NULL; 
		   }else{
		   	  $data['email'] = $this->input->post('email');
		   }
		 }
		 if($this->input->post('city_id')){
		 	$data['city_id'] = $this->input->post('city_id');
		 }
	     if($this->input->post('image') != null){
	       if(trim($this->input->post('image')) == -1){
		   	  $data['personal_image'] = NULL; 
		   }else{
		   	  $data['personal_image'] = $this->input->post('image');
		   }
	     }
		 if($this->input->post('user_gender')!= null && $this->input->post('user_gender')!= ''){
	  	    if(trim($this->input->post('user_gender')) == -1){
		   	  $data['user_gender'] = NULL; 
		   }else{
		   	  $data['user_gender'] = $this->input->post('user_gender');
		   }
	     }
         if($this->input->post('birthday')!= null && $this->input->post('birthday')!= ''){
	  	   if(trim($this->input->post('birthday')) == -1){
		   	  $data['birthday'] = NULL; 
		   }else{
		   	  $data['birthday'] = $this->input->post('birthday');
		   }
	     }
         if($this->input->post('whatsup_number')!= null){
           if(trim($this->input->post('whatsup_number')) == -1){
		   	  $data['whatsup_number'] = NULL; 
		   }else{
		   	  $data['whatsup_number'] = $this->input->post('whatsup_number');
		   }
	     }
		 if($this->input->post('visible_phone') != null){
		 	 $data ['visible_phone'] = $this->input->post('visible_phone');
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
   
   public function save_search_query_post()
   {
   	 $this->form_validation->set_rules('query', 'query', 'required');  
	 if (!$this->form_validation->run()) {
	 	 throw new Validation_Exception(validation_errors());
	 }else{
	 	 $this->load->model('data_sources/user_search_history');
		 $data = array(
		   'query' => $this->input->post('query'),
		   'number_of_results' => $this->input->post('num_of_result'),
		   'user_id' => $this->current_user->user_id
		 );
		 $search = $this->user_search_history->save($data);
		 $this->response(array('status' => true, 'data' => '', "message" => $this->lang->line('sucess')));
	 }
   }
   
   public function delete_my_account_post()
   {
       $current_user = $this->current_user->user_id;
	   $this->load->model('data_sources/users');
	   $this->load->model('data_sources/user_tokens');
	   $user_id = $this->users->save(array('is_deleted'=>1) , $current_user);
	   // delete tokens 
	   $res = $this->user_tokens->delete_by_user($current_user);
	   if($user_id && $res){
	   	   $this->session->sess_destroy();
	   	   $this->response(array('status' => true, 'data' => $user_id, "message" => $this->lang->line('sucess')));
	   }else{
	   	   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
	   }
   }

   public function update_lang_post()
   {
	   $this->form_validation->set_rules('token', 'token', 'required');  
       $this->form_validation->set_rules('lang', 'lang', 'required');  
	    if (!$this->form_validation->run()) {
	       throw new Validation_Exception(validation_errors());
		}else{
		   $this->load->model('data_sources/user_tokens');
		   $current_user = $this->current_user->user_id;
		   $lang = $this->input->post('lang');
		   $token = $this->input->post('token');
		   $update_result = $this->user_tokens->update_token_lang($token , $current_user , $lang);
		   if($update_result){
	   	      $this->response(array('status' => true, 'data' => $update_result, "message" => $this->lang->line('sucess')));
		   }else{
		   	  $this->response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		   }
		}
   }
   
   public function get_urls_get()
   {
   	   $data = array(
         'site_url' => 'http://dealat.tradinos.com',
         'logo_url' => 'http://dealat.tradinos.com/assets/images/ios_logo.png',
         'currency_ar' =>'' ,
         'currency_en' => ''
	   );
   	   if($this->response->version  == '1.0'){
			$data['site_url'] = 'http://dealat.tradinos.com'; 
	   }else{
			$data['site_url'] = 'http://deal-at.com'; 
	   }
	   $this->response(array('status' => true, 'data' => $data, "message" => $this->lang->line('sucess')));
   }
   
   public function test_get()
   {
       $this->load->model('data_sources/chat_sessions');
	   dump($this->chat_sessions->get_with_users(23));
   }
}
