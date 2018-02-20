<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Ads_manage extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->data['lang']=  $this->response->lang;
	}
	
	public function index_get()
	{
		$this -> data['subview'] = 'admin/ads/index';
		$this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function all_ads_get()
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
			$recorde[] = TAMPLATES::get_tamplate_name($row -> tamplate_id);
			$recorde[] = $row -> title;
			if($row -> publish_date != null){
				$recorde[] = $row -> publish_date;
			}else{
				$recorde[] = $this->lang->line('not_set_yet'); 
			}
			$recorde[] = $row -> price;
			$recorde[] = $row -> city_name. ' - '.$row->location_name;
			$recorde[] = STATUS::get_name($row -> status);
			$recorde[] = $row -> tamplate_id;
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}	
}