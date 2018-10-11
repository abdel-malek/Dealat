<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Commercial_items_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/commercial_ads');
		$this->data['lang']=  $this->response->lang;
        if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
    
	
	public function index_get()
	{
		$this->load->model('data_sources/categories');
		$this->data['main_categories'] = $this->categories->get_main_categories($this->data['lang']);
	    $this -> data['subview'] = 'admin/commercial_ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function load_main_manage_page_get()
	{
	    $this -> data['subview'] = 'admin/commercial_ads/main_ads';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function get_without_main_get()
	{
		$items = $this->commercial_ads->get_all($this->data['lang']);
		$output = array("aaData" => array());
		foreach ($items as $row) {
			$recorde = array();
			$recorde[] = $row -> commercial_ad_id;
			$recorde[] = $row -> created_at;
			$recorde[] = $row->category_name;
			if($row -> title != null){
			   $recorde[] = $row -> title;
			}else{
			   $recorde[] = $this->lang->line('not_set'); 
			}
			$recorde[] = POSITION::get_position_name($row->position, $this->data['lang']);
			$recorde[] = $row->city_name;
			if(PERMISSION::Check_permission(PERMISSION::SHOW_OTHER_COMMERCIAL , $this->session->userdata('LOGIN_USER_ID_ADMIN')))
			  $recorde[] = commercila_status_checkbox($row->is_active , $row -> commercial_ad_id , $row->category_id , $row->position , $row->city_id);
			else{
			   if($row->is_active == 1){
			   	  $recorde[] = $this->lang->line('shown');
			   }else{
			   	  $recorde[] = $this->lang->line('hidden');
			   }
			}
			//$recorde[] = commercila_status_checkbox($row->is_active , $row -> commercial_ad_id , $row->category_id , $row->position);
			$recorde[] = $row -> category_id;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}

   public function get_main_get()
    {
       $items = $this->commercial_ads->get_all_main($this->data['lang']);
	   $output = array("aaData" => array());
		foreach ($items as $row) {
			$recorde = array();
			$recorde[] = $row -> commercial_ad_id;
			$recorde[] = $row -> created_at;
		    if($row -> title != null){
			   $recorde[] = $row -> title;
			}else{
			   $recorde[] = $this->lang->line('not_set'); 
			}
			$recorde[] = POSITION::get_position_name($row->position, $this->data['lang']);
			$recorde[] = $row->city_name;
		    if(PERMISSION::Check_permission(PERMISSION::SHOW_MAIN_COMMERCIAL , $this->session->userdata('LOGIN_USER_ID_ADMIN')))
			  $recorde[] = commercila_status_checkbox($row->is_active , $row -> commercial_ad_id , $row->category_id , $row->position , $row->city_id);
			else{
			   if($row->is_active == 1){
			   	  $recorde[] = $this->lang->line('shown');
			   }else{
			   	  $recorde[] = $this->lang->line('hidden');
			   }
			}
			$recorde[] = $row->position;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
    }

}