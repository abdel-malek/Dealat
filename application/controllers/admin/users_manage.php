<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Users_manage extends REST_Controller {
	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/users');
		$this->data['lang']=  $this->response->lang;
	}
	
	public function index_get()
	{
  	  if($this->data['lang'] == 'en'){
		$this -> data['current_lang'] = 'English';
	  }else{
	   $this -> data['current_lang'] = 'Arabic';
	  }
	  $this -> data['subview'] = 'admin/users/index';
	  $this -> load -> view('admin/_main_layout', $this -> data);
	}
	
	public function get_all_get()
	{
		$users = $this->users->get_with_ads_info($this->data['lang']);
		$output = array("aaData" => array());
		foreach ($users as $row) {
			$recorde = array();
			$recorde[] = $row -> user_id;
			$recorde[] = $row -> name;
			$recorde[] = $row -> phone;
			if($row -> email != null){
				$recorde[] = $row -> email;
			}else{
				$recorde[] = $this->lang->line('not_set'); 
			}
			$recorde[] = $row -> city_name;
			if($row->ads_num == 0){
				$recorde[] =  $this->lang->line('none');
			}else{
				$recorde[] = $row->ads_num;
			}
		    if($row -> is_active != 1){
				$recorde[] = $this->lang->line('inactive'); 
			}else{
				$recorde[] = $this->lang->line('active'); 
			}
			$output['aaData'][] = $recorde;
		}
		echo json_encode($output);
	}
	
	public function change_language_get()
	{
	  $current_lang = $this->input->get('lang');
	  $this->session->set_userdata(array('language' => $current_lang));
	  redirect('admin/items_manage');
	}
}