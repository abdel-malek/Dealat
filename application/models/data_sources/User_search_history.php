<?php

class User_search_history extends MY_Model {
	protected $_table_name = 'user_search_history';
	protected $_primary_key = 'user_search_id';
	protected $_order_by = 'user_search_id';
	//protected $_timestamps = TRUE;
	public $rules = array();
}