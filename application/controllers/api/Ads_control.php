<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Ads_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->data['lang']=  $this->response->lang;
	}
	
	public function get_latest_ads_get()
	{
    	$ads_list  = $this->ads->get_latest_ads($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}
	
	public function get_ads_by_main_category_get()
	{
		$main_category_id = $this->input->get('category_id');
		$ads_list = $this->ads->get_ads_by_category($main_category_id , $this->data['lang']);
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}
	
	public function get_ad_details_get()
	{
		$ad_id = $this->input->get('ad_id');
		$tamplate_id = $this->input->get('template_id');
		$deatils = $this->ads->get_ad_details($ad_id , $this->data['lang'] , $tamplate_id);
		$this->response(array('status' => true, 'data' =>$deatils, 'message' => ''));
	}

    public function post_new_ad_post()
    {
        $this->load->model('data_sources/categories');
     // $this -> user_permission -> check_permission(PERMISSION::POST_AD, $this -> permissions, $this -> current_user->user_id);
		$this -> form_validation -> set_rules('category_id', 'Category id', 'required');
		$this -> form_validation -> set_rules('location_id', 'Location id', 'required');
		$this -> form_validation -> set_rules('show_period', 'Show Period', 'required');
		$this -> form_validation -> set_rules('price', 'Price', 'required');
		$this -> form_validation -> set_rules('title', 'Title', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
		   $basic_data = array(
		   //  'user_id' => $this->current_user->user_id,
		     'user_id' => 1,  // temp
		     'location_id' => $this->input->post('location_id'),
		     'show_period' => $this->input->post('show_period'),
		     'price' => $this->input->post('price'),
		     'title' => $this->input->post('title'),
		     'description' => $this->input->post('description'),
		     'category_id' => $this->input->post('category_id'),
		     'is_featured' => $this->input->post('is_featured'),
		     'status' => 2    // temp
		   );
		   $main_image = null;
		   if ($this->input->post('main_image')) {
			  $main_image = $this->input->post('main_image');
		   }
		   $ads_images_paths = array();
		   if($this->input->post('images')){
		   	   $ads_images_paths = $this->input->post('images');
		   }
		   $category_info = $this->categories->get($this->input->post('category_id'));
		   $tamplate_id = $category_info->tamplate_id;
		   $save_result = $this->ads->create_an_ad($basic_data, $main_image , $ads_images_paths , $tamplate_id);
		   if($save_result != false){
		   	 $this -> response(array('status' => true, 'data' => $save_result, 'message' => 'Successfully created'));
		   }else{
		   	 $this -> response(array('status' => false, 'data' => '', 'message' => 'Some thing went wrong'));
		   }
		}
    }

    public function ad_images_upload_post()
    {
   //    $image_name = date('m-d-Y_hia').'-'.$this->current_user->user_id;
       $image_name = date('m-d-Y_hia').'-'.'1';
	   if (isset($image['image'])) {
		   $image_path =  ADS_IMAGES_PATH.$image['image']['upload_data']['file_name'];
		   $this -> response(array('status' => true, 'data' => $image_path, 'message' => 'Successfully Uploaded'));
	   }else{
	   	 $this -> response(array('status' => false, 'data' => '', 'message' => 'Some thing went wrong'));
	   }
    }
	
	public function delete_images_post()
	{
		$images = $this->input_post('images');
		if(!$images){
			throw new Parent_Exception('You have to provide images array');
		}
		$images_array = json_decode($images, true);
	    $deleted = $this->ads->delete_images($images_array);
		if($deleted){
		   $this -> response(array('status' => true, 'data' => '', 'message' => 'Successfully deleted'));	
		}else{
		   $this -> response(array('status' => true, 'data' => '', 'message' => 'Some thing went wrong'));
		}
	}

	
	public function search_get()
	{
		$query_string = $this->input->get('query');
		$category_id = $this->input->get('category_id');
		$resuts = $this->ads->serach_with_filter( $this->data['lang']  , $query_string , $category_id);
		$this->response(array('status' => true, 'data' =>$resuts, 'message' => ''));
	}
	
	public function get_data_lists_get()
	{
	     $this->load->model('data_sources/types');
		 $this->load->model('data_sources/educations');
		 $this->load->model('data_sources/schedules');
		 $this->load->model('data_sources/locations');
		 $locations = $this->locations->get_all($this->data['lang']);
		 //$cities = $this->locations->get_cities($this->data['lang']);
		 $nested_locations = $this->locations->get_cities_with_locations($this->data['lang']);
		 $types = $this->types->get_all_by_tamplate($this->data['lang']);
		 $educations = $this->educations->get_all($this->data['lang']);
		 $schedules = $this->schedules->get_all($this->data['lang']);
		 $data = array('location' =>$locations ,'nested_locations'=>$nested_locations, 'types' =>$types , 'educations' =>$educations , 'schedules'=>$schedules);
		 $this -> response(array('status' => true, 'data' => $data, 'message' => ''));
	}

}
