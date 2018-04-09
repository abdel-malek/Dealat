<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Data_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
		if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
	
	public function load_types_page_get()
	{
      $this -> data['subview'] = 'admin/types/index';
	  $this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function get_all_types_get()
	{
	   $this->load->model('data_sources/types');
	   $types = $this->types->get();
	   $output = array("aaData" => array());
	   foreach ($types as $row) {
			$recorde = array();
			$recorde[] = $row -> type_id;
			$recorde[] = $row -> en_name;
			$recorde[] = $row -> ar_name;
			$recorde[] = TAMPLATES::get_tamplate_name($row -> tamplate_id  , $this->data['lang']);
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
}