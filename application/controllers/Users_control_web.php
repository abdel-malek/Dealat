<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control_web extends REST_Controller {

	function __construct() {
		parent::__construct();
		// Load facebook library
		$this->load->model('data_sources/users');
		$this->load->model('data_sources/social_users');
        $this->load->library('facebook');
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

  public function login_with_facebook()
  {
  		$userData = array();
        // Check if user is logged in
        if($this->facebook->is_authenticated()){
            // Get user facebook profile details
            $userProfile = $this->facebook->request('get', '/me?fields=id,first_name,last_name,email,gender,locale,picture');

            // Preparing data for database insertion
            $userData['oauth_provider'] = 'facebook';
            $userData['oauth_uid'] = $userProfile['id'];
            $userData['first_name'] = $userProfile['first_name'];
            $userData['last_name'] = $userProfile['last_name'];
            $userData['email'] = $userProfile['email'];
            $userData['gender'] = $userProfile['gender'];
            $userData['locale'] = $userProfile['locale'];
            $userData['profile_url'] = 'https://www.facebook.com/'.$userProfile['id'];
            $userData['picture_url'] = $userProfile['picture']['data']['url'];

            // Insert or update user data
            $userID = $this->social_users->checkUser($userData);

            // Check user data insert or update status
            if(!empty($userID)){
                $data['userData'] = $userData;
                $this->session->set_userdata('userData',$userData);
            }else{
               $data['userData'] = array();
            }
            // Get logout URL
            $data['logoutUrl'] = $this->facebook->logout_url();
		//	redirect('home_control');
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
				//dump($user);
				//redirect('home_control');
				$this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('sucess')));
			} else {
				dump(false);
				//$this -> session -> set_flashdata('error', $this -> lang -> line('incorrect_credentials'));
				//redirect('users');
			    $this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('failed')));
			}
		}
	}
   
   public function test_post()
   {
             // Preparing data for database insertion
            $userData['oauth_provider'] = 'facebook';
            $userData['oauth_uid'] = 1;
            $userData['first_name'] = 'bla';
            $userData['last_name'] = 'bla';
            $userData['email'] = 'bla';
            $userData['gender'] = 'bla';
            $userData['locale'] = 'bla';
            $userData['profile_url'] = 'bla';
            $userData['picture_url'] = 'bla';

            // Insert or update user data
            $userID = $this->social_users->checkUser($userData);
			dump($userID);
   }
   
   
   	public function logout_post()
	{
	    // Remove local Facebook session
        $this->facebook->destroy_session();

        // Remove user data from session
        $this->session->unset_userdata('userData');

        // Redirect to login page
        redirect('home_control');
		
	}

}