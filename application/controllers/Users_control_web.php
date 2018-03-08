<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control_web extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/users');
	}

	public function index_get() {
	}
	
	public function change_language_get()
	{
	  $current_lang = $this->input->get('language');
	  $this->session->set_userdata(array('language' => $current_lang));
	  redirect('home_control');
	 // $this->response(array('status' => true, 'data' =>'', 'message' => ''));
	}
	
    public function register_post()
	{
        $this->form_validation->set_rules('phone', 'lang:phone', 'required|numeric|exact_length[9]');
        $this->form_validation->set_rules('name', 'lang:name', 'required');
		$this->form_validation->set_rules('password', 'lang:password', 'required');
      //  $this->form_validation->set_rules('lang', 'lang:lang', 'callback_valid_lang');
        $this->form_validation->set_rules('location_id', 'lang:country', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
        	$data = array(
        	  'phone' => $this->input->post('phone'),
        	  'name' => $this->input->post('name'),
        	  'email' =>$this->input->post('email'),
        	  'password' => $this->input->post('password'),
        	  'lang' => $this->input->post('lang') != "" ? strtolower($this->input->post('lang')) : $this->data['lang'],
        	  'location_id' => $this->input->post('location_id')
			);
            $user = $this->users->register($data ,ACCOUNT_TYPE::WEB);
			if($user != null){
			   $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('sucess')));	
			}else{
			   $this->response(array('status' => false, 'data' => $user, "message" => $this->lang->line('failed')));
			}
        }		
	}

   public function login_post() {
		$this -> form_validation -> set_rules('phone', 'lang:phone', 'required');
		$this -> form_validation -> set_rules('password', 'lang:password', 'required|max_length[32]');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$phone = $this -> input -> post('phone');
			$password = $this -> input -> post('password');
			$user = $this -> users -> login($phone, $password);
			if ($user) {
				dump($user);
				//redirect('home_control');
			} else {
				dump(false);
				//$this -> session -> set_flashdata('error', $this -> lang -> line('incorrect_credentials'));
				//redirect('users');
			}
		}
	}

}