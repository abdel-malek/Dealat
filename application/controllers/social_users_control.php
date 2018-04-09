<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Social_users_control extends CI_Controller {
	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/users');
		$this->load->model('data_sources/social_users');
		$this->load->model('data_sources/ads');
		$this->load->model('data_sources/categories');
      //  $this->load->library('facebook');
		$this->data['lang']=  $this->session->userdata('language');
		$this->data['main_categories'] = $this->categories->get_main_categories($this->data['lang']);
	}
	
	public function facebook_login()
	{
	    $userData = array();
        // Check if user is logged in
        dump($this->facebook->is_authenticated());
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
            dump($userID);
            // Check user data insert or update status
            if(!empty($userID)){
                $this->data['userData'] = $userData;
                $this->session->set_userdata('userData',$userData);
            }else{
               $this->data['userData'] = array();
            }

            // Get logout URL
            $this->data['logoutUrl'] = $this->facebook->logout_url();
        }else{
            $fbuser = '';

            // Get login URL
           // $this->data['authUrl'] =  $this->facebook->login_url();
        }
        // Load login & profile view
        // $this->data['ads'] = $this->ads->get_latest_ads($this->data['lang']);
	    // $this -> data['subview'] = 'website/index';
		// $this -> load -> view('website/_main_layout', $this -> data);
		redirect('home_control');
	}
}