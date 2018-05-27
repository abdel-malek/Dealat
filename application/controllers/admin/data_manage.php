<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Data_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/admin_actions_log');
		$this->data['lang']=  $this->response->lang;
		if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
	
	public function load_types_page_get()
	{
	  $this->load->model('data_sources/categories');
	  $this->data['childs_cats'] = $this->categories->get_childs_only($this->data['lang']);
      $this -> data['subview'] = 'admin/types/index';
	  $this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function get_all_types_get()
	{
	   $this->load->model('data_sources/types');
	   $types = $this->types->get_by(array('is_active' => 1));
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
		$this -> form_validation -> set_rules('category_id', 'categoey id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$this->load->model('data_sources/types');
			$data = array(
			   'en_name' => $this->input->post('en_name'),
			   'ar_name' => $this->input->post('ar_name'),
			   'tamplate_id' => $this->input->post('tamplate_id'),
			   'category_id' => $this->input->post('category_id')
			);
			$type_id = $this->types->save($data);
			$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_BRAND , $type_id);
			$this->response(array('status' => true, 'data' =>$type_id, 'message' => ''));
		}
   }
   
  public function edit_type_post()
   {
        $this -> form_validation -> set_rules('type_id', 'type id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			if(PERMISSION::Check_permission(PERMISSION::UPDATE_DATA , $this->session->userdata('LOGIN_USER_ID_ADMIN'))){
				 $this->response(array('status' => false, 'data' =>'', 'message' => $this->lang->line('no_permission')));
			}else{
				$this->load->model('data_sources/types');
				$data = array(
				   'en_name' => $this->input->post('en_name'),
				   'ar_name' => $this->input->post('ar_name'),
				);
				$type_id = $this->types->save($data , $this->input->post('type_id'));
				$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_BRAND , $type_id);
				$this->response(array('status' => true, 'data' =>$type_id, 'message' => ''));
			}
		}
   }
   
   public function delete_type_post()
   {
   	    $this -> form_validation -> set_rules('type_id', 'type id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
	    	$this->load->model('data_sources/types');
			$type_id = $this->types->save(array('is_active' => 0) ,$this->input->post('type_id'));
			$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_BRAND , $type_id);
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
			   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_BRAND_MODEL , $type_model_id);
			}else{ // add
			   $type_model_id = $this->type_models->save($data);	
			   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_BRAND_MODEL , $type_model_id);
			}
			$this->response(array('status' => true, 'data' =>$type_model_id, 'message' => ''));
		}
   }

  public function delete_type_model_post()
  {
        $this -> form_validation -> set_rules('type_model_id', 'type model id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
	    	$this->load->model('data_sources/type_models');
			$type_model_id = $this->type_models->save(array('is_active' => 0) ,$this->input->post('type_model_id'));
			$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_BRAND_MODEL , $type_model_id);
			$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
			
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
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_EDUCATION , $new_id);
		}else{ // add
		   $new_id = $this->educations->save($data);
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_EDUCATION , $new_id);	
		}
		$this->response(array('status' => true, 'data' =>$new_id, 'message' => ''));
	}
  }
  
 public function delete_education_post()
 {
    $this -> form_validation -> set_rules('education_id', 'education_id', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	} else {
    	$this->load->model('data_sources/educations');
		$id = $this->educations->save(array('is_active' => 0) ,$this->input->post('education_id'));
		$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_EDUCATION , $id);
		$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
	}
 }
 
 
 public function load_schedules_page_get($value='')
 {
	  $this -> data['subview'] = 'admin/schedules/index';
	  $this -> load -> view('admin/_main_layout', $this -> data);
 }
 
 public function get_all_schedules_get()
  {
  	$this->load->model('data_sources/schedules');
	$schedules = $this->schedules->get_by(array('is_active'=>1));
    $output = array("aaData" => array());
	foreach ($schedules as $row) {
			$recorde = array();
			$recorde[] = $row -> schedule_id;
			$recorde[] = $row -> en_name;
			$recorde[] = $row -> ar_name;
			$output['aaData'][] = $recorde;
		}
	echo json_encode($output);  
  }
 
 public function save_schedule_post()
 {
     $this -> form_validation -> set_rules('en_name', 'en_name', 'required');
     $this -> form_validation -> set_rules('ar_name', 'ar_name', 'required');
	 if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	 } else {
		$this->load->model('data_sources/schedules');
		$data = array(
		   'en_name' => $this->input->post('en_name'),
		   'ar_name' => $this->input->post('ar_name'),
		);
		$id = $this->input->post('schedule_id');
		if($id != 0){ // edit
		   $new_id = $this->schedules->save($data , $id);
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_SCHEDUAL , $new_id);
		}else{ // add
		   $new_id = $this->schedules->save($data);	
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_SCHEDUAL , $new_id);
		}
      $this->response(array('status' => true, 'data' =>$new_id, 'message' => ''));
    }
  }
 
 public function delete_schedule_post()
 {
    $this -> form_validation -> set_rules('schedule_id', 'schedule_id', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	} else {
    	$this->load->model('data_sources/schedules');
		$id = $this->schedules->save(array('is_active' => 0) ,$this->input->post('schedule_id'));
		$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_SCHEDUAL , $id);
		$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
	} 
 }
 
 
 public function load_cities_page_get()
 {
    $this -> data['subview'] = 'admin/cities/index';
    $this -> load -> view('admin/_main_layout', $this -> data);
 }
 
 public function get_all_cities_get()
 {
    $this->load->model('data_sources/locations');
	$cities = $this->locations->get_cities();
    $output = array("aaData" => array());
	foreach ($cities as $row) {
			$recorde = array();
			$recorde[] = $row['city_id'];
			$recorde[] = $row['en_name'];
			$recorde[] = $row['ar_name'];
			$output['aaData'][] = $recorde;
		}
	echo json_encode($output);  
 }
 
 public function get_city_areas_get()
 {
    $this->load->model('data_sources/locations');
	$data = $this->locations->get_by_city_for_manage($this->input->get('city_id'));
    $output = array("aaData" => array());
	foreach ($data as $row) {
			$recorde = array();
			$recorde[] = $row -> location_id;
			$recorde[] = $row -> en_name;
			$recorde[] = $row -> ar_name;
			$output['aaData'][] = $recorde;
		}
	echo json_encode($output);  
 }
 
 public function save_city_post()
 {
    $this -> form_validation -> set_rules('en_name', 'en_name', 'required');
    $this -> form_validation -> set_rules('ar_name', 'ar_name', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	} else {
		$this->load->model('data_sources/cities');
		$data = array(
		   'en_name' => $this->input->post('en_name'),
		   'ar_name' => $this->input->post('ar_name'),
		);
		$id = $this->input->post('city_id');
		if($id != 0){ // edit
		   $new_id = $this->cities->save($data , $id);
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_CITY , $new_id);
		}else{ // add
		   $new_id = $this->cities->save($data);	
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_CITY , $new_id);
		}
		$this->response(array('status' => true, 'data' =>$new_id, 'message' => ''));
	}
 }
 
 public function delete_city_post()
 {
    $this -> form_validation -> set_rules('city_id', 'city_id', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	}else {
    	$this->load->model('data_sources/cities');
		$id = $this->cities->save(array('is_active' => 0) ,$this->input->post('city_id'));
		$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_CITY , $id);
		$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
	}  
 }
 
 public function save_location_post()
 {
    $this -> form_validation -> set_rules('en_name', 'en_name', 'required');
    $this -> form_validation -> set_rules('ar_name', 'ar_name', 'required');
	$this -> form_validation -> set_rules('city_id', 'city_id', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	} else {
		$this->load->model('data_sources/locations');
		$data = array(
		   'en_name' => $this->input->post('en_name'),
		   'ar_name' => $this->input->post('ar_name'),
		   'city_id' => $this->input->post('city_id')
		);
		$id = $this->input->post('location_id');
		if($id != 0){ // edit
		   $new_id = $this->locations->save($data , $id);
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_LOCATION , $new_id);
		}else{ // add
		   $new_id = $this->locations->save($data);	
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_LOCATION , $new_id);
		}
		$this->response(array('status' => true, 'data' =>$new_id, 'message' => ''));
	}
 }
 
 public function delete_location_post()
 {
    $this -> form_validation -> set_rules('location_id', 'location_id', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	}else {
    	$this->load->model('data_sources/locations');
		$id = $this->locations->save(array('is_active' => 0) ,$this->input->post('location_id'));
		$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_LOCATION , $id);
		$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
	}  
 }
 
 
 public function load_about_manage_get()
 {
 	$this->load->model('data_sources/about_info');
 	$this->data['about_info'] = $this->about_info->get(null , true , 1);
    $this -> data['subview'] = 'admin/about_manage';
    $this -> load -> view('admin/_main_layout', $this -> data);
 }
 
 public function save_about_post()
 {
    if(!PERMISSION::Check_permission(PERMISSION::UPDATE_ABOUT_INFO , $this->session->userdata('LOGIN_USER_ID_ADMIN'))){
    	$this->response(array('status' => false, 'data' =>'', 'message' => $this->lang->line('no_permission')));
    }else{
    	$this->load->model('data_sources/about_info');
	    $data = array(
	      'ar_about_us' => $this->input->post('ar_about_us'),
	      'en_about_us' => $this->input->post('en_about_us'),
	      'phone' => $this->input->post('phone') ,
	      'email' => $this->input->post('email'),
	      'facebook_link' => $this->input->post('facebook_link'),
	      'youtube_link' => $this->input->post('youtube_link'),
	      'linkedin_link' => $this->input->post('linkedin_link'),
	      'twiter_link' => $this->input->post('twiter_link'),
	      'instagram_link' => $this->input->post('instagram_link'),
	      'ar_terms' => $this->input->post('ar_terms'),
	      'en_terms' => $this->input->post('en_terms'),
		);
	   $saved = $this->about_info->save($data , 1);
	   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_ABOUT_INFO);
	   $this->response(array('status' => true, 'data' =>$saved, 'message' => 'sucess'));
    }
 }
 
 public function load_certificates_page_get()
 {
    $this -> data['subview'] = 'admin/certificates/index';
    $this -> load -> view('admin/_main_layout', $this -> data); 
 }

 public function get_all_certificates_get()
 {
    $this->load->model('data_sources/certificates');
	$data = $this->certificates->get_by(array('is_active'=>1));
    $output = array("aaData" => array());
	foreach ($data as $row) {
			$recorde = array();
			$recorde[] = $row -> certificate_id;
			$recorde[] = $row -> en_name;
			$recorde[] = $row -> ar_name;
			$output['aaData'][] = $recorde;
		}
	echo json_encode($output);  
 }
 
 public function save_certificate_post()
 {
     $this -> form_validation -> set_rules('en_name', 'en_name', 'required');
     $this -> form_validation -> set_rules('ar_name', 'ar_name', 'required');
	 if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	 } else {
		$this->load->model('data_sources/certificates');
		$data = array(
		   'en_name' => $this->input->post('en_name'),
		   'ar_name' => $this->input->post('ar_name'),
		);
		$id = $this->input->post('certificate_id');
		if($id != 0){ // edit
		   $new_id = $this->certificates->save($data , $id);
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_CERTIFICATE , $new_id);
		}else{ // add
		   $new_id = $this->certificates->save($data);	
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_CERTIFICATE , $new_id);
		}
      $this->response(array('status' => true, 'data' =>$new_id, 'message' => ''));
    }
  }
 
 public function delete_certificate_post()
 {
    $this -> form_validation -> set_rules('certificate_id', 'certificate_id', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	} else {
    	$this->load->model('data_sources/certificates');
		$id = $this->certificates->save(array('is_active' => 0) ,$this->input->post('certificate_id'));
		$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_CERTIFICATE , $id);
		$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
	} 
 }
 
 public function load_periods_page_get()
 {
    $this -> data['subview'] = 'admin/show_periods/index';
    $this -> load -> view('admin/_main_layout', $this -> data);  
 }
 
 public function get_show_periods_get()
 {
 	$this->load->model('data_sources/show_periods');
	$data = $this->show_periods->get_by(array('is_active' => 1));
	$output = array("aaData" => array());
	foreach ($data as $row) {
			$recorde = array();
			$recorde[] = $row -> show_period_id;
			$recorde[] = $row -> en_name;
			$recorde[] = $row -> ar_name;
			$recorde[] = $row->days;
			$output['aaData'][] = $recorde;
		}
	echo json_encode($output);
     
 }
 
 public function save_period_post()
 {
    $this -> form_validation -> set_rules('en_name', 'en_name', 'required');
    $this -> form_validation -> set_rules('ar_name', 'ar_name', 'required');
	$this -> form_validation -> set_rules('days', 'days', 'required');
	 if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	 } else {
		$this->load->model('data_sources/show_periods');
		$data = array(
		   'en_name' => $this->input->post('en_name'),
		   'ar_name' => $this->input->post('ar_name'),
		   'days' => $this->input->post('days')
		);
		$id = $this->input->post('period_id');
		if($id != 0){ // edit
		   $new_id = $this->show_periods->save($data , $id);
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_PERIOD , $new_id);
		}else{ // add
		   $new_id = $this->show_periods->save($data);	
		   $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_PERIOD , $new_id);
		}
      $this->response(array('status' => true, 'data' =>$new_id, 'message' => ''));
    } 
 }

 public function delete_period_post()
 {
    $this -> form_validation -> set_rules('period_id', 'period_id', 'required');
	if (!$this -> form_validation -> run()) {
		throw new Validation_Exception(validation_errors());
	} else {
    	$this->load->model('data_sources/show_periods');
		$id = $this->show_periods->save(array('is_active' => 0) ,$this->input->post('period_id'));
		$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_PERIOD , $id);
		$this->response(array('status' => true, 'data' =>"", 'message' => 'sucess'));
	} 
 }
 
 
}
