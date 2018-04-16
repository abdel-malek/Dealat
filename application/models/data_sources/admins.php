<?php

class Admins extends MY_Model {
	protected $_table_name = 'admins';
	protected $_primary_key = 'admin_id';
	protected $_order_by = 'admin_id';
	public $rules = array();
	
	
   public function login($user_name , $password)
   {
        $admin = $this->check_authentication($user_name, md5($password));
        if ($admin != NULL) {
            $newdata = array(
                'PHP_AUTH_USER' => $user_name,
                'LOGIN_USER_ID' => $admin->admin_id,
                'USERNAME' => $admin->name,
                'PHP_AUTH_PW' => md5($password),
                'IS_LOGGED_IN' => 1,
                'IS_ADMIN' => 1
            );
            $this->session->set_userdata($newdata);
            return $admin;
        } else {
            return false;
        }
   }
   
   public function check_authentication($username , $password)
   {
       	return $this -> db -> select("admins.*") 
                   -> from('admins')
				   -> where('username', $username) 
				   -> where('password', $password) 
				   -> get() 
				   -> row();
   }
}