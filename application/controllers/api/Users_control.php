<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
	}

	public function index_get() {
	}
	
	// this regiter method is for mobiles platforms
	public function register_post()
	{
        $this->form_validation->set_rules('phone', 'lang:phone', 'required|numeric|exact_length[9]');
        $this->form_validation->set_rules('name', 'lang:name', 'required');
        $this->form_validation->set_rules('lang', 'lang:lang', 'required');
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

}
