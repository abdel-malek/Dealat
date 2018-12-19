<?php

class User_search_bookmarks extends MY_Model {
	protected $_table_name = 'user_search_bookmarks';
	protected $_primary_key = 'user_bookmark_id';
	protected $_order_by = 'created_at';
	protected $is_dec_order = true;
	protected $_timestamps = TRUE; 
	public $rules = array();
}