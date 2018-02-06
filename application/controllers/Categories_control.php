<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Categories_control extends REST_Controller {
	
    function __construct() {
		parent::__construct();
		$this->load->model('data_sources/categories');
	    $this->data['lang']=  $this->response->lang;
	}
	
	
	public function get_nested_categories_get()
	{
    	$categories  = $this->categories->get_nested($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$categories, 'message' => ''));
	}
	
}