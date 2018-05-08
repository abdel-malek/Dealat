<?php

class Admin_actions_log extends MY_Model {
	protected $_table_name = 'admin_actions_log';
	protected $_primary_key = 'admin_actions_log.admin_action_log_id';
	protected $_order_by = 'admin_action_log_id';
	public $rules = array();
	
	
	public function add_log($admin_id)
	{
		
	}
}