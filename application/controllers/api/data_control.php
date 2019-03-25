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
		$this->load->model('data_sources/categories');
		$this->load->model('data_sources/types');
		$info = $this->types->get($this->input->get('type_id'));
		$info->template_name = TAMPLATES::get_tamplate_name($info->tamplate_id , $this->data['lang']);
		$name = $this->categories->get_category_name($info->category_id , $this->data['lang']);
		if($name){
		    $info->category_name = $name->parent_name .'-'.$name->category_name;
		}else{
			$info->category_name = null;
		}
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}

    public function get_type_model_info_get()
	{
		$this->load->model('data_sources/type_models');
		$info = $this->type_models->get($this->input->get('type_model_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}


	public function get_education_info_get()
	{
		$this->load->model('data_sources/educations');
		$info = $this->educations->get($this->input->get('education_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}

   public function get_schedule_info_get()
	{
		$this->load->model('data_sources/schedules');
		$info = $this->schedules->get($this->input->get('schedule_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}

   public function get_city_info_get()
	{
	    $this->load->model('data_sources/locations');
		$info = $this->locations->get_city_info($this->input->get('city_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}
	public function get_feature_info_get()
 {
		 $this->load->model('data_sources/features');
	 $info = $this->features->get_feature_info($this->input->get('feature_id'));
	 $this->response(array('status' => true, 'data' =>$info, 'message' => ''));
 }
	public function get_location_info_get()
	{
	    $this->load->model('data_sources/locations');
		$info = $this->locations->get($this->input->get('location_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}

	public function get_about_info_get()
	{
		$this->load->model('data_sources/about_info');
		$info = $this->about_info->get_info($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}

	public function get_certificate_info_get()
	{
		$this->load->model('data_sources/certificates');
		$info = $this->certificates->get($this->input->get('certificate_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}

	public function get_period_info_get()
	{
		$this->load->model('data_sources/show_periods');
		$info = $this->show_periods->get($this->input->get('period_id'));
		$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	}

}
