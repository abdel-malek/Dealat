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
			$this->load->model('data_sources/commercials_cities');
			$cities = $this->commercials_cities->get_cities($row-> commercial_ad_id , $this->data['lang']);
			$cities_names = $this->_get_cities_names($cities);
			$cities_ids = $this->_get_cities_ids($cities);
			$recorde[] = $cities_names;
			$recorde[] = $row->clicks_num;
			$recorde[] =  ($row->external==1) ? 'Yes' : 'No' ;;
			if(PERMISSION::Check_permission(PERMISSION::SHOW_OTHER_COMMERCIAL , $this->session->userdata('LOGIN_USER_ID_ADMIN')))
			  $recorde[] = commercila_status_checkbox($row->is_active , $row -> commercial_ad_id , $row->category_id , $row->position , $cities_ids);
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
			$this->load->model('data_sources/commercials_cities');
    	    $cities = $this->commercials_cities->get_cities($row-> commercial_ad_id , $this->data['lang']);
			$cities_names = $this->_get_cities_names($cities);
			$cities_ids = $this->_get_cities_ids($cities);
			$recorde[] = $cities_names;
		    if(PERMISSION::Check_permission(PERMISSION::SHOW_MAIN_COMMERCIAL , $this->session->userdata('LOGIN_USER_ID_ADMIN')))
			  $recorde[] = commercila_status_checkbox($row->is_active , $row -> commercial_ad_id , $row->category_id , $row->position , $cities_ids);
			else{
			   if($row->is_active == 1){
			   	  $recorde[] = $this->lang->line('shown');
			   }else{
			   	  $recorde[] = $this->lang->line('hidden');
			   }
			}
			$recorde[] = $row->clicks_num;
			$recorde[] =  ($row->external==1) ? 'Yes' : 'No' ;;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
    }


    private function _get_cities_names($cities){
		$cities_names = '';
		foreach ($cities as $key => $city_row) {
		   if($cities_names != ''){
		   	  $cities_names .= ', '.$city_row->city_name;
		   }else{
		   	  $cities_names .= $city_row->city_name;
		   }
		}
		return $cities_names;
    }

    private function _get_cities_ids($cities){
		$cities_ids = '';
		foreach ($cities as $key => $city_row) {
		   if($cities_ids != ''){
		   	  $cities_ids .= '-'.$city_row->city_id;
		   }else{
		   	  $cities_ids .= $city_row->city_id;
		   }
		}
		return $cities_ids;
    }

}
