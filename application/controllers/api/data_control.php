<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Data_control extends REST_Controller {
	function __construct() {
		parent::__construct();
		$this->data['lang']=  $this->response->lang;
	}
	
	public function get_type_info_get()
	{
		$this->load->model('data_sources/types');
		$info = $this->types->get($this->input->get('type_id'));
		$info->template_name = TAMPLATES::get_tamplate_name($info->tamplate_id , $this->data['lang']);
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}
	
    public function get_type_model_info_get()
	{
		$this->load->model('data_sources/type_models');
		$info = $this->type_models->get($this->input->get('type_model_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}
	
}
