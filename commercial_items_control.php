<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Commercial_ads_control extends REST_Controller {

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
			if ($this->response->format == "json"){ // for mobile
				$ads = $this->commercial_ads->get_commercial_ads($category_id,$this->data['lang'], 1);
			}else{ // for website
			    $ads = $this->commercial_ads->get_commercial_ads($category_id,$this->data['lang'], 0);
			}
		  $this->response(array('status' => true, 'data' =>$ads, 'message' => ''));
		}
	}
	
	
}