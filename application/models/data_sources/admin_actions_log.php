<?php

class Admin_actions_log extends MY_Model {
	protected $_table_name = 'admin_actions_log';
	protected $_primary_key = 'admin_actions_log.admin_action_log_id';
	protected $_order_by = 'admin_action_log_id';
	protected $_timestamps = TRUE;
	
	public $rules = array();
	
	
	public function add_log($admin_id , $action_id , $extra = NULL)
	{
		$action = LOG_ACTIONS::get_note($action_id , $extra);
		$data = array(
		  'admin_id' => $admin_id,
		  'action_id'=> $action_id,
		  'ar_action' => $action['ar_action'],
		  'en_action' => $action['en_action']
		);
		return parent::save($data);
	}
}