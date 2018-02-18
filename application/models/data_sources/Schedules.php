<?php

class Schedules extends MY_Model {
	protected $_table_name = 'schedules';
	protected $_primary_key = 'schedule_id';
	protected $_order_by = 'schedule_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	public function get_all($lang)
	{
		$this->db->select($lang.'_name as name , schedule_id');
		$this->db->where('is_active' , 1);
		return parent::get();
	}
	
}