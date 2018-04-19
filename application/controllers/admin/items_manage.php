<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Items_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->data['lang']=  $this->response->lang;
	    if($this->data['lang'] == 'en'){
			$this -> data['current_lang'] = 'English';
		}else{
		   $this -> data['current_lang'] = 'Arabic';
		}
	}
	
	public function index_get()
	{
		$this -> data['subview'] = 'admin/ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	
	public function load_reported_items_page_get()
	{
		$this -> data['subview'] = 'admin/ads/reported_ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function all_get()
	{
	   $filter_option = array();
	   if($this->input->get('status')){
	   	 $filter_option['status'] = $this->input->get('status');
	   }
	   $ads = $this->ads->get_all_ads_with_details($this->data['lang']);
	   $output = array("aaData" => array());
		foreach ($ads as $row) {
			$recorde = array();
			$recorde[] = $row -> ad_id;
			$recorde[] = $row -> created_at;
			$recorde[] = TAMPLATES::get_tamplate_name($row -> tamplate_id  , $this->data['lang']);
			$recorde[] = $row->category_name;
			$recorde[] = $row -> title;
			if($row -> publish_date != null){
			   $recorde[] = $row -> publish_date;
			}else{
			   $recorde[] = $this->lang->line('not_set'); 
			}
			$recorde[] = $row -> price;
			$recorde[] = $row -> city_name. ' - '.$row->location_name;
			$recorde[] = STATUS::get_name($row -> status  , $this->data['lang']);
			$recorde[] = $row -> tamplate_id;
		//	$recorde[] = $row -> tamplate_id .''.$row->expired_after;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	
	public function get_all_reported_items_get()
	{
		$this->load->model('data_sources/reported_ads');
		$ads = $this->reported_ads-> get_reported_ads();
		$output = array("aaData" => array());
		foreach ($ads as $row) {
			$recorde = array();
			$recorde[] = $row->ad_number;
			$recorde[] = $row->ad_title;
	        $recorde[] = STATUS::get_name($row -> status  , $this->data['lang']);
			$recorde[] = $row->tamplate_id; // for details: 2
		    $recorde[] = $row->reports_count; // for reports: 3
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	
	public function get_item_reports_get()
	{
		$this->load->model('data_sources/reported_ads');
		$reports = $this->reported_ads-> get_ad_reports($this->input->get('ad_id') , $this->data['lang']);
		$output = array("aaData" => array());
		foreach ($reports as $row) {
			$recorde = array();
			$recorde[] = $row->reported_ad_id;
			$recorde[] = $row->created_at;
			if($row->user_name == null){
				$recorde[] = $this->lang->line('anknown');
			}else{
				$recorde[] = $row->user_name;
			}
			$recorde[] = $row->report_msg;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
}