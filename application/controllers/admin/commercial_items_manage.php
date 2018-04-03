<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Commercial_items_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/commercial_ads');
		$this->data['lang']=  $this->response->lang;
        if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
    
	
	public function index_get()
	{
		$this->load->model('data_sources/categories');
		$this->data['main_categories'] = $this->categories->get_main_categories($this->data['lang']);
	    $this -> data['subview'] = 'admin/commercial_ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function load_main_manage_page_get()
	{
	    $this -> data['subview'] = 'admin/commercial_ads/main_ads';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function get_without_main_get()
	{
		$items = $this->commercial_ads->get_all($this->data['lang']);
		$output = array("aaData" => array());
		foreach ($items as $row) {
			$recorde = array();
			$recorde[] = $row -> commercial_ad_id;
			$recorde[] = $row -> created_at;
			$recorde[] = $row->category_name;
			$recorde[] = POSITION::get_position_name($row->position, $this->data['lang']);
			$recorde[] = commercila_status_checkbox($row->is_active , $row -> commercial_ad_id , $row->category_id , $row->position);
			$recorde[] = $row -> category_id;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}

   public function get_main_get()
    {
       //dump(commercila_status_checkbox(0));
       $items = $this->commercial_ads->get_by(array('category_id'=>0));
	   $output = array("aaData" => array());
		foreach ($items as $row) {
			$recorde = array();
			$recorde[] = $row -> commercial_ad_id;
			$recorde[] = $row -> created_at;
			$recorde[] = POSITION::get_position_name($row->position, $this->data['lang']);
			$recorde[] = commercila_status_checkbox($row->is_active , $row -> commercial_ad_id , $row->category_id , $row->position);
			$recorde[] = $row->position;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
    }

}