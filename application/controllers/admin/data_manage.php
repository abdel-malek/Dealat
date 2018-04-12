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
	
  
  public function get_types_models_get()
	{
	   $this->load->model('data_sources/type_models');
	   $models = $this->type_models->get_all_by_type($this->input->get('type_id'));
	   $output = array("aaData" => array());
	   foreach ($models as $row) {
	   //	dump($row);
			$recorde = array();
			$recorde[] = $row -> type_model_id;
			$recorde[] = $row -> en_name;
			$recorde[] = $row -> ar_name;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	 
   public function add_type_post()
   {
        $this -> form_validation -> set_rules('en_name', 'english name', 'required');
		$this -> form_validation -> set_rules('ar_name', 'arabic name', 'required');
		$this -> form_validation -> set_rules('tamplate_id', 'Template', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$this->load->model('data_sources/types');
			$data = array(
			   'en_name' => $this->input->post('en_name'),
			   'ar_name' => $this->input->post('ar_name'),
			   'tamplate_id' => $this->input->post('tamplate_id')
			);
			$type_id = $this->types->save($data);
			$this->response(array('status' => true, 'data' =>$type_id, 'message' => ''));
		}
   }
   
   public function edit_type_post()
   {
        $this -> form_validation -> set_rules('type_id', 'type id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$this->load->model('data_sources/types');
			$data = array(
			   'en_name' => $this->input->post('en_name'),
			   'ar_name' => $this->input->post('ar_name'),
			);
			$type_id = $this->types->save($data , $this->input->post('type_id'));
			$this->response(array('status' => true, 'data' =>$type_id, 'message' => ''));
		}
   }
   
   public function delete_type_post()
   {
   	    $this -> form_validation -> set_rules('type_id', 'type id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
	    	$this->load->model('data_sources/types');
			$this->types->delete($this->input->post('type_id'));
			$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
		}
   }
   
   public function save_type_model_post()
   {
        $this -> form_validation -> set_rules('en_name', 'en_name', 'required');
	    $this -> form_validation -> set_rules('ar_name', 'ar_name', 'required');
		$this -> form_validation -> set_rules('type_id', 'type id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$this->load->model('data_sources/type_models');
			$data = array(
			   'en_name' => $this->input->post('en_name'),
			   'ar_name' => $this->input->post('ar_name'),
			   'type_id' => $this->input->post('type_id'),
			);
			$type_model_id = $this->input->post('type_model_id');
			if($type_model_id != 0){ // edit
			   $type_model_id = $this->type_models->save($data , $type_model_id);
			}else{ // add
			   $type_model_id = $this->type_models->save($data);	
			}
			$this->response(array('status' => true, 'data' =>$type_model_id, 'message' => ''));
		}
   }

  public function load_educations_page_get()
  {
      $this -> data['subview'] = 'admin/educations/index';
	  $this -> load -> view('admin/_main_layout', $this -> data);
  }

  public function get_all_educations_get()
  {
  	$this->load->model('data_sources/educations');
	$educations = $this->educations->get_all();
    $output = array("aaData" => array());
	foreach ($educations as $row) {
			$recorde = array();
			$recorde[] = $row -> education_id;
			$recorde[] = $row -> en_name;
			$recorde[] = $row -> ar_name;
			$output['aaData'][] = $recorde;
		}
	echo json_encode($output);  
  }

  public function save_education_post()
  {
    $this -> form_validation -> set_rules('en_name', 'en_name', 'required');
    $this -> form_validation -> set_rules('ar_name', 'ar_name', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	} else {
		$this->load->model('data_sources/educations');
		$data = array(
		   'en_name' => $this->input->post('en_name'),
		   'ar_name' => $this->input->post('ar_name'),
		);
		$id = $this->input->post('education_id');
		if($id != 0){ // edit
		   $new_id = $this->educations->save($data , $id);
		}else{ // add
		   $new_id = $this->educations->save($data);	
		}
		$this->response(array('status' => true, 'data' =>$new_id, 'message' => ''));
	}
  }
}