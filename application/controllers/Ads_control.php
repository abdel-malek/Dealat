<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Ads_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
	}
	
	
	public function get_latest_ads_get()
	{
    	$ads_list  = $this->ads->get_latest_ads();
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}

}
