<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Dashboard extends REST_Controller {
	
    function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
		if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
	
	public function index_get()
	{
	   $this -> data['subview'] = 'admin/home_page';
	   $this -> load -> view('admin/_main_layout', $this -> data); 
	}

	public function load_searches_report_get(){
	   $this->load->model('data_sources/No_result_searches');
	   $results = $this->No_result_searches->get_all();
	   $this -> data['subview'] = 'admin/reports/searches';
	   $this -> data['searches'] = $results;
	   $this -> load -> view('admin/_main_layout', $this -> data); 
	}
	
	
}