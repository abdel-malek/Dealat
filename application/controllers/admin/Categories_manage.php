<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Categories_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/categories');
		$this->data['lang']=  $this->response->lang;
	}
	
	public function index_get($value='')
	{
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
		   	 $this->response(array('status' => true, 'data' =>$create_result, 'message' => 'Successfully created'));
		   }else{
		   	 $this->response(array('status' => false, 'data' =>$create_result, 'message' => 'Something went wrong!')); 
		   }
		}
	}

   
}