<?php

class User_tokens extends MY_Model {
	protected $_table_name = 'user_tokens';
	protected $_primary_key = 'use_token_id';
	protected $_order_by = 'use_token_id';
    protected $_timestamps = TRUE; 
	public $rules = array();
}