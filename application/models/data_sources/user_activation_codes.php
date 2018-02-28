<?php

class User_activation_codes extends MY_Model {
	protected $_table_name = 'user_activation_codes';
	protected $_primary_key = 'activation_code_id';
	protected $_order_by = 'activation_code_id';
    protected $_timestamps = TRUE; 
	public $rules = array();
	
	public function add_new_for_user($activation_code , $user_id)
	{
		// update the active activation code for the user to inactive 
		$this->db->set('is_active' , 0);
		$this->db->where('user_id', $user_id);
		$this->db->where('is_active' , 1);
		$this->db->update('user_activation_codes');
		
		// add new inactive activation code for the user
		$data = array(
		 'user_id' =>$user_id , 
		 'code' => $activation_code ,
		);
		return $this->save($data);
	}
	
	public function activate_user_code($user_id , $code)
	{
		$this->db->where('user_id' , $user_id);
		$this->db->where('code' , $code);
		$code_row = parent::get(null , true , 1);
		if($code_row){
			return parent::save(array('is_active' => 1) , $code_row->activation_code_id);
		}else {
			return false;
		}
	}
	
}