<?php

class Deleted_chat_sessions extends MY_Model {
	protected $_table_name = 'deleted_chat_sessions';
	protected $_primary_key = 'deleted_chat_session_id';
	protected $_order_by = 'deleted_chat_session_id';
    protected $_timestamps = TRUE;
	public $rules = array();
}