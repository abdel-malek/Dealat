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
				   -> where('is_deleted' , 0)
				   -> get() 
				   -> row();
		}else{
		    return $this -> db -> select("users.*") 
	               -> from('users')
				   -> where('phone', $username) 
				   -> where('password', $password) 
				   -> where('is_active', 1)
				   -> where('is_deleted' , 0)
				   -> get() 
				   -> row();
	   }
    }

   public function register($data , $account_type = ACCOUNT_TYPE::MOBILE) {
   	    $this->load->model('data_sources/user_activation_codes');
        $user = $this->get_by(array('phone'=>$data['phone'] , 'is_deleted' => 0) , true);
		$new_user_id = null;
		$code = null;
		$user_activation_id = null;
        if ($user) {
            $user_id  = $user->user_id;
			$code = $this->user_activation_codes->generate_activation_code();
		    $user_activation_id = $this->user_activation_codes->add_new_for_user($code , $user_id);
		    if($user->account_type != $account_type && $user->account_type !=  ACCOUNT_TYPE::BOTH){
		    	$data['account_type'] = ACCOUNT_TYPE::BOTH;
		    }
            $new_user_id = $this->save($data , $user_id);			
        } else {
			$data['account_type'] = $account_type;
            $new_user_id = $this->save($data);
		    $code = $this->user_activation_codes->generate_activation_code();
			$user_activation_id = $this->user_activation_codes->add_new_for_user($code , $new_user_id);
        }
	    $this->user_activation_codes->send_code_SMS($data['phone'], $this->lang->line('verification_msg') . $code);
	//	send verification code to email.
		//$to      = 'dealat.co@gmail.com';
		// $to      = 'dealat.co@gmail.com';
        // $subject = 'Message from Dealat';
        // $message = 'Your Verification Code: '.$code;
        // mail($to, $subject, $message,  "From: ola@tradinos.com");
		$user = $this->get($new_user_id);
		if($user){
			return $user;
		}else{
			return false;
		}
   }

   public function login($phone , $password)
   {
        $user = $this->check_authentication($phone, md5($password) , ACCOUNT_TYPE::WEB);
        if ($user != NULL) {
            $newdata = array(
                'PHP_AUTH_USER' => $phone,
                'PHP_AUTH_USER_ADMIN' => null,
                'LOGIN_USER_ID' => $user->user_id,
                'USERNAME' => $user->name,
                'PHP_AUTH_PW' => md5($password),
                'IS_LOGGED_IN' => 1,
                'IS_ADMIN' => 0,
                'IS_USER' => 1
            );
            $this->session->set_userdata($newdata);
            return $user;
        } else {
            return false;
        }
   }

  public function verify($phone , $code , $is_multi = 0)
   {
   	  $this->load->model('data_sources/user_activation_codes');
   	  // get the user by phone
   	  $user = $this->get_by(array('phone'=>$phone , 'is_deleted' => 0) , true);
	  if($user){
	  	$user_id =  $user->user_id;
		$account_type = $user->account_type;
		$data = array();
		if($account_type == ACCOUNT_TYPE::MOBILE || $account_type == ACCOUNT_TYPE::BOTH){
		  if($is_multi == 0){ // for the fisrt time and when the user don't want to enter from deffrent devices. 
		  	    $server_key = uniqid();
		        while ($this->get_by(array('server_key'=>$server_key))) {
		            $server_key = uniqid();
		        }
				$data['server_key'] =  md5($server_key);
		  }
	    }
	    // checking the user code ,update the state of this code to active code 
	    $activatin_code = $this->user_activation_codes->activate_user_code($user_id , $code);
		if($activatin_code){
		   // update the state of the user to active user 
		    $data['is_active'] = 1;
			$saved_user  = parent::save($data , $user_id);
			 // return the user  
			return parent::get($saved_user , true , 1);
		}else{
			return false;
		}
	  }else{
	  	return false; 
	  }
  }

  public function get_user_info($lang , $user_id)
  {
      $this->db->select('users.* , cites.'.$lang.'_name as city_name');
	  $this->db->join('cites', 'users.city_id = cites.city_id', 'left');
	  return parent::get($user_id);
  }

  public function get_all($lang)
  {
      $this->db->select('users.* , cites.'.$lang.'_name as city_name');
	  $this->db->join('cites', 'users.city_id = cites.city_id', 'left');
	  return parent::get();
  }
  
  public function get_with_ads_info($lang)
  {
      $this->db->select('users.* , cites.'.$lang.'_name as city_name , COUNT(ad_id) ads_num , cites.country_code');
	  $this->db->join('cites', 'users.city_id = cites.city_id', 'left');
	  $this->db->join('ads', 'users.user_id = ads.user_id', 'left outer');
	  $this->db->group_by('users.user_id' );
	  return parent::get();
  }
  
  public function get_user_ids_by_city($city_id)
  {
      $users = parent::get_by(array('city_id' => $city_id));
	  $ids_array = array();
	  if($users){
	  	foreach ($users as $row) {
			$ids_array[] = $row->user_id;  
		}
	  }
	  return $ids_array;
  }
}
