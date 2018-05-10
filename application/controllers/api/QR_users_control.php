<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class QR_users_control extends REST_Controller {
	
	public function __construct()
    {
        parent::__construct();
	    $this->load->helper('url');
		$this->load->model('data_sources/qr_code_users');
    }
	
	
	public function generate_QR_code_post()
	{
		$generated_code = $this->qr_code_users->get_generated_code();
		$this->load->library('ciqrcode');
		$qr_image=rand().'.png';
		$params['data'] = $generated_code;
		$params['level'] = 'H';
		$params['size'] = 10;
		$params['savename'] =FCPATH.QR_CODES_PATH.$qr_image;
		if($this->ciqrcode->generate($params)){
		   $this->response(array('status' => true, 'data' => FCPATH.QR_CODES_PATH.$qr_image, "message" => $this->lang->line('sucess')));
		}else{
		   $this->response(array('status' => false, 'data' =>'', "message" => $this->lang->line('failed')));
		}
	}
	
}