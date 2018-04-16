<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Search_control extends REST_Controller {
	
	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->data['lang']=  $this->response->lang;
	}
	
	public function index_get()
	{
	   	$query_string = $this->input->get('query');
		$category_id = $this->input->get('category_id');
		$category_name = $this->input->get('category_name');
		$this->data['ads'] = $this->ads->serach_with_filter( $this->data['lang']  , $query_string , $category_id);
		$this->data['category_name'] = $category_name;
	    $this -> data['subview'] = 'website/search';
		$this -> load -> view('website/_main_layout', $this -> data);
	}
	
}