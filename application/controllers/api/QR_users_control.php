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
		$data = array('gen_code' => $generated_code);
		$this->load->library('ciqrcode');
		$qr_image=rand().'.png';
		$params['data'] = $generated_code;
		$params['level'] = 'H';
		$params['size'] = 10;
		$params['savename'] =FCPATH.QR_CODES_PATH.$qr_image;
		if($this->ciqrcode->generate($params)){
		   //store the generated code in database
		   $data['QR_path'] = QR_CODES_PATH.$qr_image;
		   $this->qr_code_users->save(array('generated_code' => $generated_code));
		   $this->response(array('status' => true, 'data' =>  $data, "message" => $this->lang->line('sucess')));
		}else{
		   $this->response(array('status' => false, 'data' =>'', "message" => $this->lang->line('failed')));
		}
	}
	
	
	public function QR_code_scan_post()
	{
		$user_id = $this->current_user->user_id;
		if(!$this->input->post('gen_code')){
			throw new Parent_Exception('You have to provide genrated code');
		}else{
		   $gen_code = $this->input->post('gen_code');
		   $exsist_qr_user = $this->qr_code_users->check_exsistance($gen_code);
		   if($exsist_qr_user){ // check the exsistance of the generated code. 
		   	   $secret_code = $this->qr_code_users->genrate_secret_code();
			   $data = array(
			     'user_id' => $user_id , 
			     'secret_code'=>md5($secret_code)
			   );
			  $this->qr_code_users->save($data ,$exsist_qr_user );
			  $this->response(array('status' => true, 'data' => $secret_code, "message" => $this->lang->line('sucess')));
		   }else{
		   	 $this->response(array('status' => false, 'data' =>'', "message" => $this->lang->line('failed')));
		   }
		}
   }

   public function login_by_qr_code_post()
   {
   	    if($this->session->userdata('IS_LOGGED_IN')!= null && $this->session->userdata('IS_ADMIN') == 1){
		   	 $this->response(array('status' => true, 'data' => array('cms_logged' => 1), "message" => $this->lang->line('sucess')));
		}else{
		    $this -> form_validation -> set_rules('gen_code', 'generated code', 'required');
			$this -> form_validation -> set_rules('secret_code', 'secret code', 'required|max_length[32]');
			if (!$this -> form_validation -> run()) {
			   throw new Validation_Exception(validation_errors());
			}else{
			   $gen_code = $this->input->post('gen_code');
			   $secret_code = $this->input->post('secret_code');
			   $user = $this->qr_code_users->login($gen_code , $secret_code);
			   if($user){
			   	 $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('sucess')));
			   }else{
			   	 $this->response(array('status' => false, 'data' => '', "message" => $this->lang->line('not_a_qr_user')));
			   }
			}	    	
		}
   }
	
	
	
}