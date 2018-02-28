<?php

class Users extends MY_Model {
	protected $_table_name = 'users';
	protected $_primary_key = 'user_id';
	protected $_order_by = 'name';
	public $rules = array();
	

	public function check_authentication($username, $password , $type) {
		if($type == ACCOUNT_TYPE::MOBILE){
	  		return $this -> db -> select("users.*") 
                   -> from('users')
				   -> where('phone', $username) 
				   -> where('server_key', $password) 
				   -> where('is_active', 1)
				   -> get() 
				   -> row();
		}else{
		    return $this -> db -> select("users.*") 
	               -> from('users')
				   -> where('phone', $username) 
				   -> where('password', $password) 
				   -> where('is_active', 1)
				   -> get() 
				   -> row();
	   }
	}
	 
	 
   public function register($data , $account_type = ACCOUNT_TYPE::MOBILE) {
   	    $this->load->model('data_sources/user_activation_codes');
        $user = $this->get_by(array('phone'=>$data['phone']) , true);
		$new_user_id = null;
        if ($user) {
            $user_id  = $user->user_id;
			$code = $this->generate_activation_code();
		    $this->user_activation_codes->add_new_for_user($code , $user_id);
		    if($user->account_type != $account_type && $user->account_type !=  ACCOUNT_TYPE::BOTH){
		    	$data['account_type'] = ACCOUNT_TYPE::BOTH;
		    }
            $new_user_id = $this->save($data , $user_id);
            //$this->send_sms->send_sms($phone, $this->lang->line('verification_sms') . $code);
        } else {
            $code = $this->generate_activation_code();
			$data['account_type'] = $account_type;
			if($account_type == ACCOUNT_TYPE::MOBILE){
			    $server_key = uniqid();
		        while ($this->get_by(array('server_key'=>$server_key))) {
		            $server_key = uniqid();
		        }
				$data['server_key'] =  md5($server_key);
		    }
            $new_user_id = $this->save($data);
			$this->user_activation_codes->add_new_for_user($code , $new_user_id);
            //$this->send_sms->send_sms($phone, $this->lang->line('verification_sms') . $code);
        }
        $user = $this->get($new_user_id);
        return $user;
    }

   public function verify($phone , $code)
   {
   	  $this->load->model('data_sources/user_activation_codes');
   	  // get the user by phone
   	  $user = $this->get_by(array('phone'=>$phone) , true);
	  if($user){
	  	$user_id =  $user->user_id;
	    // checking the user code ,update the state of this code to active code 
	    $activatin_code = $this->user_activation_codes->activate_user_code($user_id , $code);
		if($activatin_code){
		   // update the state of the user to active user 
			$saved_user  = parent::save(array('is_active' => 1) , $user_id);
			 // return the user  
			return parent::get($saved_user , true , 1);
		}else{
			return false;
		}
	  }else{
	  	return false; 
	  }
   }

    function generate_activation_code() {

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
	
	

}
