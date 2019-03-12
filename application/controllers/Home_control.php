<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Home_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->load->model('data_sources/categories');
		$this->data['lang']=  $this->response->lang;
		$this->data['main_categories'] = $this->categories->get_main_categories($this->data['lang']);
	}
	
	public function index_get()
	{
		$this->data['ads'] = $this->ads->get_latest_ads($this->data['lang']);
	    $this -> data['subview'] = 'website/index';
		$this -> load -> view('website/_main_layout', $this -> data);
	}
	
	public function load_ads_by_category_page_get()
	{
		$category_id = $this->input->get('category_id');
		$category_name = $this->input->get('category_name');
		$parent_id = $this->input->get('parent_id');
		$parent_name = $this->input->get('parent_name');
		$this->data['ads'] = $this->ads->get_ads_by_category($category_id , $this->data['lang']);
		$this->data['subcategories'] = $this->categories->get_category_subcategories($category_id , $this->data['lang']);
		$this->data['category_id']= $category_id;
		$this->data['category_name']= $category_name;
		$this->data['parent_id']= $parent_id;
		$this->data['parent_name']= $parent_name;
		$this -> data['subview'] = 'website/category';
		echo $this -> load -> view('website/_main_layout', $this -> data);
	}
	
	public function load_ads_by_category_page_all_get()
	{
		$category_id = $this->input->get('category_id');
		$category_name = $this->input->get('category_name');
		$parent_id = $this->input->get('parent_id');
		$parent_name = $this->input->get('parent_name');
		$this->data['ads'] = $this->ads->get_ads_by_category($category_id , $this->data['lang']);
		$this->data['subcategories'] = $this->categories->get_category_subcategories($category_id , $this->data['lang']);
		$this->data['category_id']= $category_id;
		$this->data['category_name']= $category_name;
		$this->data['parent_id']= $parent_id;
		$this->data['parent_name']= $parent_name;
		$this -> data['subview'] = 'website/category_all';
		echo $this -> load -> view('website/_main_layout', $this -> data);
	}
	
	public function load_subcategories_div_get()
    {
       $category_id = $this->input->get('category_id');
       $category_name = $this->input->get('category_name');
       $this->data['ads'] = $this->ads->get_ads_by_category($category_id , $this->data['lang']);
       $this->data['subcategories'] = $this->categories->get_category_subcategories($category_id , $this->data['lang']);
       $this->data['category_name']= $category_name;
       echo $this -> load -> view('website/category_div', $this -> data);
    }

    public function load_ad_details_get(){
	    $this->data['ad_id'] = $this->input->get('ad_id');
        $this->data['template_id']  = $this->ads->get_ad_template($this->data['ad_id']);
        $this->data['subview'] = 'website/ad_details';
        $this->load->view('website/_main_layout', $this -> data);
    }
}