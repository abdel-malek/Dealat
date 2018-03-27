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
}