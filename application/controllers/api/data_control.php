<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Data_control extends REST_Controller {
	function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
	}
	
}
