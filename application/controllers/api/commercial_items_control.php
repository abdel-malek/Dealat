<?php
defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Commercial_items_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/commercial_ads');
		$this->load->model('data_sources/admin_actions_log');
		$this->data['lang']=  $this->response->lang;
		$this->data['city'] = $this->response->city;
	}

	public function get_commercial_items_get()
    {
        if($this->input->get('category_id') == null){
            throw new Parent_Exception('category id is required');
        }else{
            $category_id = $this->input->get('category_id');
            $ads = null;
            $ads = $this->commercial_ads->get_commercial_ads($category_id,$this->data['lang'],$this->data['city'], $this->input->get('from_web'));
          $this->response(array('status' => true, 'data' =>$ads, 'message' => ''));
        }
    }

public function increment_clicks_get(){
	  $commercial_ad_id = $this->input->get('commercial_ad_id');
	if (	$this->commercial_ads->increment_clicks($commercial_ad_id) ==1) {
		$this->response(array('status' => true, 'data' =>'', 'message' => ''));
	}
	$this->response(array('status' => false, 'data' =>'', 'message' => ''));
}


	public function get_info_get()
	{

	   if(!$this->input->get('comm_id')){
	   	   throw Parent_Exception('id is requierd');
	   }else{
	   	  $id = $this->input->get('comm_id');
		  $info = $this-> commercial_ads -> get($id);
		  $cities = $this->_get_cities_array($id);
		  $data = array('info' => $info , 'cities' => $cities);
		  $this->response(array('status' => true, 'data' => $data, 'message' => ''));
	   }
	}

    private function _get_cities_array($comm_id){
    	$this->load->model('data_sources/commercials_cities');
    	$cities = $this->commercials_cities->get_cities($comm_id , $this->data['lang']);
		$cities_ids = array();
		foreach ($cities as $key => $city_row) {
		   $cities_ids [] = $city_row->city_id;
		}
		return $cities_ids;
    }

   public function item_images_upload_post()
   {
      $image_name = date('m-d-Y_hia').'-'.'1';
      $image = upload_attachement($this, COMMERCIAL_IMAGES_PATH , $image_name);
      if (isset($image['image'])) {
          $image_path =  COMMERCIAL_IMAGES_PATH.$image['image']['upload_data']['file_name'];
          $this -> response(array('status' => true, 'data' => $image_path, 'message' => $this->lang->line('sucess')));
      }else{
          $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
      }
   }

  public function delete_image_post()
	{
		$image = $this -> input -> post('image');
		if(!$image){
			throw new Parent_Exception('You have to provide images');
		}
	    $deleted = $this->commercial_ads->delete_image($image);
		if($deleted){
		   $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));
		}else{
		   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		}
	}

  public function save_post()
  {
  	  $this->load->model('data_sources/commercials_cities');
      $comm_id = $this->input->post('comm_id');
	  $data = array(
	    // 'title' => $this->input->post('title'),
	    // 'description' => $this->input->post('description'),
	    // 'ad_url' => $this->input->post('ad_url'),
	    // 'position' => $this->input->post('position'),
	    // 'is_main' => $this->input->post('is_main'),
	    // 'image' => $this->input->post('image'),
	    // //'category_id' => $this->input->post('category_id'),
	  );
	  if($this->input->post('title')){
	  	 $data['title'] = $this->input->post('title');
	  }
	  if($this->input->post('description')){
	  	 $data['description'] = $this->input->post('description');
	  }
	  if($this->input->post('ad_url')){
	  	 $data['ad_url'] = $this->input->post('ad_url');
	  }
      if($this->input->post('position')){
	  	 $data['position'] = $this->input->post('position');
	  }
	  if($this->input->post('is_main')!= null){
	  	 $data['is_main'] = $this->input->post('is_main');
	  }
      if($this->input->post('image')){
	  	 $data['image'] = $this->input->post('image');
	  }
	  if($this->input->post('category_id')){
	  	 $data['category_id'] = $this->input->post('category_id');
	  }
		if(isset($_POST['external'])){
			$data['external'] = $this->input->post('external');
	 }
	  // var_dump($data['external']);var_dump($this->input->post('external'));die();
	  $cities = $this->input->post('city_id');
	  if($comm_id == 0){ // add
	     if(!$this->input->post('image')){
	        throw new Parent_Exception($this->lang->line('image_is_requierd'));
	     }else{
	     	$comm_id = $this->commercial_ads->save($data);
			$this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::ADD_COMMERCIAL , $comm_id);
	     }
	  }else{ // edit
	  	 $comm_id = $this->commercial_ads->save($data, $comm_id);
		 $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::EDIT_COMMERCIAL , $comm_id);
	  }
	  // save the commercials cities.
	  $this->commercials_cities->save_cities($comm_id , $cities);
	  $this -> response(array('status' => true, 'data' => $comm_id, 'message' => $this->lang->line('sucess')));
  }

  public function delete_post()
  {
      if(!$this->input->post('comm_ad_id')){
      	 throw new Parent_Exception('comm_id is requierd');
      }else{
      	 $deleted = $this->commercial_ads->delete($this->input->post('comm_ad_id'));
		 $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::DELETE_COMMERCIAL , $this->input->post('comm_ad_id'));
		 $this -> response(array('status' => true, 'data' => $deleted, 'message' => $this->lang->line('sucess')));
      }
  }

  public function change_status_post()
  {
  	  // check activation number
  	  $category_id = $this->input->post('category_id');
	  $position = $this->input->post('position');
	  $to_active = $this->input->post('to_active');
	  $city_id = $this->input->post('city_id');
	  $is_ok = true;
	  if($to_active == 1){
	     $is_ok = $this->commercial_ads->check_active_number($category_id , $position ,$city_id);
	  }
	  if(!$is_ok){
	  	  throw new Parent_Exception($this->lang->line('excced_limit'));
	  }else{
	  	  $id = $this->input->post('comm_id');
		  $current_comm = $this->commercial_ads->get($id);
		  $active_status = $current_comm->is_active;
		  $edited_id = $this->commercial_ads->save(array('is_active' => !$active_status),$id);
		  $this->admin_actions_log->add_log($this->current_user->user_id , LOG_ACTIONS::CHANGE_COMMERCIAL_SHOW_STATUS , $edited_id);
		  $this -> response(array('status' => true, 'data' => $edited_id, 'message' => $this->lang->line('sucess')));
	  }
  }


}
