<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Qrcode_login extends CI_Controller {
	
	//generate qr code. 
	public function index()
	{
		$this->load->model('data_sources/qr_code_users');
		$generated_code = $this->qr_code_users->get_generated_code();
		$data = array('gen_code' => $generated_code);
		$this->load->library('ciqrcode');
		$qr_image=rand().'.png';
		$params['data'] = $generated_code;
		$params['level'] = 'H';
		$params['size'] = 10;
		$params['savename'] =FCPATH.QR_CODES_PATH.$qr_image;
		$data['QR_path'] = '';
		if($this->ciqrcode->generate($params)){
		   //store the generated code in database
		   $this->qr_code_users->save(array('generated_code' => $generated_code));
		   $data['QR_path'] = QR_CODES_PATH.$qr_image;
		}
		$this->load->view('website/qrcode',$data);
	}
	
}