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
            if($this->input->post('gender')!= null && $this->input->post('gender')  != ''){
                $data['gender'] = $this->input->post('gender');
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
				//redirect('home_control');
			} else {
				 $this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('not_a_user')));
				//$this -> session -> set_flashdata('error', $this -> lang -> line('incorrect_credentials'));
				//redirect('users');
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

} 