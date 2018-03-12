<?php

class User_ratings extends MY_Model {
	protected $_table_name = 'user_ratings';
	protected $_primary_key = 'user_rating_id';
	protected $_order_by = 'rate';
	protected $is_dec_order = true;
	public $rules = array();
	
	public function save_rate($data)
	{
		$check_exist = parent::get_by(array('rated_user_id' => $data['rated_user_id'], 'rated_by_user_id' => $data['rated_by_user_id']) , true);
		if($check_exist != null){
			$user_rating_id = $check_exist->user_rating_id;
			$id = parent::save($data , $user_rating_id);
		}else{
			$id = parent::save($data);
		}
		return $id; 
	}
	

}