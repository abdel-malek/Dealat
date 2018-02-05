<?php

class User_ratings extends MY_Model {
	protected $_table_name = 'user_ratings';
	protected $_primary_key = 'user_rating_id';
	protected $_order_by = 'rate';
	protected $is_dec_order = true;
	public $rules = array();
}