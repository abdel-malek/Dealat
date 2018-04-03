<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Categories_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/categories');
		$this->data['lang']=  $this->response->lang;
		if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
	
	public function index_get($value='')
	{
		$this->load->model('data_sources/categories');
		$this->data['main_categories'] = $this->categories->get_main_categories($this->data['lang']);
		$this -> data['subview'] = 'admin/categories/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function add_post()
	{
//      $this -> user_permission -> check_permission(PERMISSION::POST_AD, $this -> permissions, $this -> current_user->user_id);
		$this -> form_validation -> set_rules('en_name', 'english name', 'required');
		$this -> form_validation -> set_rules('ar_name', 'arabic name', 'required');
		$this -> form_validation -> set_rules('parent_id', 'Parent', 'required');
		$this -> form_validation -> set_rules('tamplate_id', 'Template', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
		   $create_result = $this->categories->create_category();
		   if($create_result != false){
		   	 $this->response(array('status' => true, 'data' =>$create_result, 'message' => $this->lang->line('sucess')));
		   }else{
		   	 $this->response(array('status' => false, 'data' =>$create_result, 'message' => $this->lang->line('failed'))); 
		   }
		}
	}
	
	public function get_sub_cats_get()
	{
	   $category_id = $this->input->get('category_id');
	   $sub_sub_cats = $this->categories->get_subcats_with_parents($category_id, $this->data['lang']);
	   $output = array("aaData" => array());
		foreach ($sub_sub_cats as $row) {
			$recorde = array();
			$recorde[] = $row -> category_id;
			$recorde[] = $row -> category_name;
			$recorde[] = $row->parent_name;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}

   
}