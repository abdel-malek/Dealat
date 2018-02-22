<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_control_web extends REST_Controller {

	function __construct() {
		parent::__construct();
	}

	public function index_get() {
	}
	
	public function change_language_get()
	{
	  $current_lang = $this->input->get('language');
	  $this->session->set_userdata(array('language' => $current_lang));
	  redirect('Home_control');
	 // $this->response(array('status' => true, 'data' =>'', 'message' => ''));
	}

}