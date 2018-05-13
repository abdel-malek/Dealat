<?php 

class Qr_code_users extends MY_Model {
	protected $_table_name = 'qr_code_users';
	protected $_primary_key = 'qr_code_user_id';
	protected $_order_by = 'qr_code_user_id DESC';
	protected $_timestamps = TRUE;
	public $rules = array();
	
	public function get_generated_code()
	{
	   $digites = '0123456789';
       $randomString = $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
        ;
        return $randomString;
	}
	
	public function genrate_secret_code()
	{
	   $digites = '0123456789';
       $randomString = $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
        ;
        while ($this->get_by(array('secret_code'=>$randomString))) {
		   $randomString = $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
        ;
		}
       return $randomString;
	}
	
	public function check_exsistance($gen_code , $user_id = null)
	{
	   $this->db->where('generated_code' , $gen_code);
	   if($user_id){
	   	   $this->db->where('user_id' , $user_id);
	   }
	   $q = parent::get(null,true);
	   if($q) {
	   	 return $q->qr_code_user_id;
	   }else{
	   	 return false;
	   }
	}
	
	public function check_authentication($gen_code , $sec_code)
	{
		// check if we passed the allowed period. 
		$this->db->where('(TIMESTAMPDIFF(MINUTE, modified_at, NOW()) < 10)');
		return parent::get_by(array('generated_code' => $gen_code , 'secret_code' => $sec_code) , true);
	}
	
	public function login($gen_code , $sec_code)
	{
		$qr_user_row = $this->check_authentication($gen_code, md5($sec_code));//
		if($qr_user_row){ 
			$this->load->model('data_sources/users');
			$user = $this->users->get_by(array('user_id' => $qr_user_row->user_id, 'is_active' => 1 , 'is_deleted' => 0) , true);
			if($user != null){
				 $newdata = array(
	                'PHP_AUTH_USER' => $user->phone,
	                'PHP_AUTH_USER_ADMIN' => null,
	                'LOGIN_USER_ID' => $user->user_id,
	                'USERNAME' => $user->name,
	                'PHP_AUTH_PW' => md5($sec_code),
	                'IS_LOGGED_IN' => 1,
	                'IS_ADMIN' => 0,
	                'IS_USER' => 1
	            );
	            $this->session->set_userdata($newdata);
				// we must delete the qr row here. 
	            return $user; 
			}else{
			  return false;
			}
		}else{
			return false;
		}
	}
}