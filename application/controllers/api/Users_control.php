<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
		$this->load->model('data_sources/users');
	}

	public function index_get() {
	}
	
	// this regiter method is for mobiles platforms
	public function register_post()
	{
        $this->form_validation->set_rules('phone', 'lang:phone', 'required|numeric|exact_length[9]');
        $this->form_validation->set_rules('name', 'lang:name', 'required');
       // $this->form_validation->set_rules('lang', 'lang:lang', 'required');
        $this->form_validation->set_rules('location_id', 'lang:country', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
        	$data = array(
        	  'phone' => $this->input->post('phone'),
        	  'name' => $this->input->post('name'),
        	  'lang' => $this->input->post('lang') != "" ? strtolower($this->input->post('lang')) : $this->data['lang'],
        	  'location_id' => $this->input->post('location_id')
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
			$user = $this->users->verify($phone , $code);
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
			$saved_token = $this->user_tokens->save($data);
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
	   $user_info = $this->users->get($this->current_user->user_id); 
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
		if(!$this->input->get('chat_session_id')){
			 throw new Parent_Exception('chat_session_id is requierd');
		}else{
			$chat_id = $this->input->get('chat_session_id');
			// user permiisoon check 
			$this->load->model('data_sources/messages');
			$this->load->model('data_sources/chat_sessions');
			$chat_messages = $this->messages->get_by(array('chat_session_id'=>$chat_id));
			if($chat_messages){ // set chat session to seen
			    $changed_session = $this->chat_sessions->change_seen_status($chat_id , $this->current_user->user_id);
			}
			$this->response(array('status' => true, 'data' => $chat_messages, "message" => $this->lang->line('sucess')));
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
			$msg = $this->input->post('msg');
		    $user_id = $this->current_user->user_id;
		    $msg_id = $this->messages->send_msg($ad_id , $msg , $user_id);
			if($msg_id){
				$this->response(array('status' => true, 'data' => $msg_id, "message" => $this->lang->line('sucess')));
			}else{
				$this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('failed')));
			}
		}
	}

}
