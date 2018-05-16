<?php

class Admin_actions_log extends MY_Model {
	protected $_table_name = 'admin_actions_log';
	protected $_primary_key = 'admin_actions_log.admin_action_log_id';
	protected $_order_by = 'admin_action_log_id';
	//protected $_timestamps = TRUE;
	
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
	
	public function get_log($lang)
	{
		$this->db->select('admin_actions_log.'.$lang.'_action as action , admins.name as admin_name ,admin_actions_log.created_at, admin_action_log_id');
		$this->db->join('admins' , 'admins.admin_id = admin_actions_log.admin_id' , 'left');
		if($this->input->get('from')  && $this->input->get('to')){
		   $this->db->where('DATE(admin_actions_log.created_at) <=', $this->input->get('to'));
	       $this->db->where('DATE(admin_actions_log.created_at) >=', $this->input->get('from')); 
		} 
		if($this->input->get('admin_id')){
		   $this->db->where('admin_actions_log.admin_id' , $this->input->get('admin_id'));
		}
		return parent::get();
	}
}