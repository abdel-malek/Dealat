<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Commercial_items_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/commercial_ads');
		$this->data['lang']=  $this->response->lang;
	}
    
	
	public function index_get()
	{
		$this->load->model('data_sources/categories');
		$this->data['main_categories'] = $this->categories->get_main_categories($this->data['lang']);
	    $this -> data['subview'] = 'admin/commercial_ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function get_all_get()
	{
		$items = $this->commercial_ads->get_all($this->data['lang']);
		$output = array("aaData" => array());
		foreach ($items as $row) {
			$recorde = array();
			$recorde[] = $row -> commercial_ad_id;
			$recorde[] = $row -> created_at;
		    if($row->title != null){
			   $recorde[] = $row -> title;
			}else{
			   $recorde[] = $this->lang->line('not_set');
			}
			$recorde[] = $row->category_name;
			$recorde[] = POSITION::get_position_name($row->position, $this->data['lang']);
			if($row->is_main == 1){
				 $recorde[] = $this->lang->line('yes');
			}else{
				 $recorde[] = $this->lang->line('no');
			}
			$recorde[] = $row -> category_id;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
}