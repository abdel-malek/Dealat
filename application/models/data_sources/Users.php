<?php

class Users extends MY_Model {
	protected $_table_name = 'users';
	protected $_primary_key = 'user_id';
	protected $_order_by = 'name';
	public $rules = array();

	public function check_authentication($username, $password) {
		return $this -> db -> select("users.*") 
	                       -> from('users')
						   -> where('username', $username) 
						   -> where('password', $password) 
						   -> where('is_active', 1)
						   -> get() 
						   -> row();
	}

}
