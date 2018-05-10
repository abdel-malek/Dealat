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
		$this->data['main_categories'] = $this->categories->get_main_categories_for_manage($this->data['lang']);
		$this -> data['subview'] = 'admin/categories/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function add_post()
	{
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
	
	
	public function deactivate_cat_post()
	{
	    $this -> form_validation -> set_rules('category_id', 'category_id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$child_ids = $this->categories-> get_nested_ids($this->input->post('category_id'));
			$this->categories->diactivate($child_ids);
			$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
		}
	}
	
	public function activate_cat_post()
	{
		$this -> form_validation -> set_rules('category_id', 'category_id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$child_ids = $this->categories-> get_nested_ids($this->input->post('category_id'));
			$this->categories->activate($child_ids);
			$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
		}
	}

   public function delete_cat_post()
   {
       	$this -> form_validation -> set_rules('category_id', 'category_id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$child_ids = $this->categories-> get_nested_ids($this->input->post('category_id'));
			$this->categories->delete_cats($child_ids);
			$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
		}
   }
	
	
	public function edit_post()
	{
		$this -> form_validation -> set_rules('category_id', 'category_id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		}else{
	       $cat_id = $this->input->post('category_id');
		   $data = array();
		   if($this->input->post('ar_name')){
		   	  $data['ar_name'] = $this->input->post('ar_name');
		   }
           if($this->input->post('en_name')){
		   	  $data['en_name'] = $this->input->post('en_name');
		   }
		   if($this->input->post('hidden_fields')){
		   	if($this->input->post('hidden_fields') == -1 ){
		   	   $data['hidden_fields'] = NULL;
		   	}else{
		   	   $data['hidden_fields'] = $this->input->post('hidden_fields');
		   	}
		   }
		   $edit_cat_id = $this-> categories->save($data ,$cat_id );
		   if($edit_cat_id ){
		   	 $this->response(array('status' => true, 'data' =>$edit_cat_id, 'message' => $this->lang->line('sucess')));
		   }else{
		   	 $this->response(array('status' => false, 'data' =>'', 'message' => $this->lang->line('failed'))); 
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
	
	public function check_child_exsist_get()
	{
		$cat_id = $this->input->get('category_id');
		$check_res = $this->categories->has_child($cat_id);
		echo $check_res;
		//$this->response(array('status' => true, 'data' =>$check_res, 'message' => $this->lang->line('sucess')));
	}
	
	public function check_ad_exsit_get()
	{
		$this->load->model('data_sources/ads');
	    $cat_id  = $this->input->get('category_id');
		$check_result = $this->ads->check_category_ads_existence($cat_id);
		if($check_result){
			echo 1;
		}else{
			echo 0;
		}
	}
	
	public function update_categories_order_post()
	{
		$this -> form_validation -> set_rules('parent_id', 'parent_id', 'required');
		$this -> form_validation -> set_rules('categories_queue', 'categories_queue', 'required');
		if (!$this -> form_validation -> run()) {
		   throw new Validation_Exception(validation_errors());
		}else{
			$parent_id = $this->input->post('parent_id');
			$categories_queue = $this -> input -> post('categories_queue');
			$result = $this->categories->update_queue($parent_id , $categories_queue);
			if($result){
				$this->response(array('status' => true, 'data' =>$result, 'message' => $this->lang->line('sucess'))); 
			}else{
				$this->response(array('status' => false, 'data' =>'', 'message' => $this->lang->line('failed')));
			}
		}
		
	}

   
}