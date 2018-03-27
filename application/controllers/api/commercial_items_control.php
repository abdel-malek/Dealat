<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Commercial_items_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/commercial_ads');
		$this->data['lang']=  $this->response->lang;
	}
	
	public function get_commercial_items_get()
    {
        if($this->input->get('category_id') == null){
            throw new Parent_Exception('category id is required');
        }else{
            $category_id = $this->input->get('category_id');
            $ads = null;
            
            if ($this->input->get('from_web')){ // for web
                $ads = $this->commercial_ads->get_commercial_ads($category_id,$this->data['lang'], 0);
            }else{ // for mobile
                $ads = $this->commercial_ads->get_commercial_ads($category_id,$this->data['lang'], 1);
            }
          $this->response(array('status' => true, 'data' =>$ads, 'message' => ''));
        }
    }
	
	public function get_info_get()
	{
	   if(!$this->input->get('comm_id')){
	   	   throw Parent_Exception('id is requierd');
	   }else{
	   	  $id = $this->input->get('comm_id');
		  $info = $this-> commercial_ads -> get($id);
		  $this->response(array('status' => true, 'data' =>$info, 'message' => ''));
	   }
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
	  if($comm_id == 0){ // add
	     if(!$this->input->post('image')){
	        throw new Parent_Exception($this->lang->line('image_is_requierd'));
	     }else{
	     	$comm_id = $this->commercial_ads->save($data);
	     }
	  }else{ // edit 
	  	 $comm_id = $this->commercial_ads->save($data, $comm_id);
	  }
	  $this -> response(array('status' => true, 'data' => $comm_id, 'message' => $this->lang->line('sucess')));
  }

  public function delete_post()
  {
      if(!$this->input->post('comm_ad_id')){
      	 throw new Parent_Exception('comm_id is requierd');
      }else{
      	 $deleted = $this->commercial_ads->delete($this->input->post('comm_ad_id'));
		 $this -> response(array('status' => true, 'data' => $deleted, 'message' => $this->lang->line('sucess')));
      }
  }
	
	
}