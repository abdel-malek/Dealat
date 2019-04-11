<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Categories_control extends REST_Controller {

    function __construct() {
		parent::__construct();
		$this->load->model('data_sources/categories');
	    $this->data['lang']=  $this->response->lang;
		$this->data['version'] = $this->response->version;
		$this->data['city'] = $this->response->city;
	}


	public function get_nested_categories_get()
	{
    	$categories  = $this->categories->get_nested($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$categories, 'message' => ''));
	}

	public function get_all_get()
	{
		// get main commercial ads
		$this->load->model('data_sources/commercial_ads');
		$commercials = $this->commercial_ads->get_commercial_ads(0,$this->data['lang'],$this->data['city'], $this->input->get('from_web'));
		// get categories.
		$categories = $this->categories->get_all($this->data['lang']);
		$data  = array('categories' => $categories , 'commercials' => $commercials);
		// for old versions
        if($this->data['version'] == '1.0'){
        	 $data = $categories;
        }
		$this->response(array('status' => true, 'data' =>$data , 'message' => ''));
	}

	public function get_main_categories_get()
	{
		$categories = $this->categories->get_main_categories_for_manage($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$categories, 'message' => ''));
	}

	public function get_subcategories_get()
	{
		$category_id = $this->input->get('category_id');
		$subcategoirs = $this->categories->get_category_subcategories($category_id, $this->data['lang']);
		$this->response(array('status' => true, 'data' =>$subcategoirs, 'message' => ''));
	}

	public function get_info_get()
	{
		if(!$this->input->get('category_id')){
			throw new Parent_Exeption('category id is requierd');
		}else{
			$cat_id = $this->input->get('category_id');
			$info = $this->categories->get($cat_id);
			$this->response(array('status' => true, 'data' =>$info, 'message' => ''));
		}
	}

	public function get_nested_ids_get()
	{
		if(!$this->input->get('category_id')){
			throw new Parent_Exeption('category id is requierd');
		}else{
		   	$child_ids = $this->categories-> get_nested_ids($this->input->get('category_id'));
		    $this->response(array('status' => true, 'data' => $child_ids, 'message' => ''));
		}
	}

	public function get_nested_categories_from_id_get()
	{
			$category_id=$this->input->get('category_id');
		if(! $category_id){
			throw new Parent_Exeption('category id is requierd');
		}else{
				$info = $this->categories->get($category_id);
				// var_dump($info);

			if ($info) {
				if ($info->parent_id==0) {
					$child_ids = $this->categories-> get_nested_info($category_id);
				} else {
					$child_ids = $this->categories-> get_nested_info($info->parent_id);
				}


			}else {
				  $child_ids = $this->categories-> get_nested_info($category_id);
			}

		    $this->response(array('status' => true, 'data' => $child_ids, 'message' => ''));
		}
	}

}
