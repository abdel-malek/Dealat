<?php

class User_tokens extends MY_Model {
	protected $_table_name = 'user_tokens';
	protected $_primary_key = 'user_token_id';
	protected $_order_by = 'user_token_id';
    protected $_timestamps = TRUE; 
	public $rules = array();
	
	public function check($user_id , $token)
	{
		$token_row = parent::get_by(array('user_id' => $user_id , 'token' => $token) , true);
		if($token_row){
			return $token_row->user_token_id;
		}else{
			return null;
		}
	}
	
	public function delete_by_token($user , $token)
	{
		$this->db->where('token'  , $token);
		$this->db->where('user_id' , $user);
		if($this->db->delete($this->_table_name)){
			return true;
		}else{
			return false;
		}
	}
	
	public function get_tokens_by_ids($users_ids)
	{
		$this->db->where_in('user_id'  , $users_ids);
	    return parent::get();
	}
	
	public function delete_by_user($user)
	{
		$this->db->where('user_id' , $user);
		if($this->db->delete($this->_table_name)){
			return true;
		}else{
			return false;
		}
	}
	
	public function update_token_lang($token , $user , $lang)
	{
		$this->db->where('token' , $token);
		$this->db->where('user_id' , $user);
		$this->db->set('lang' , $lang);
		return $this->db->update('user_tokens');
	}
}