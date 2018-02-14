<?php

class Schedules extends MY_Model {
	protected $_table_name = 'schedules';
	protected $_primary_key = 'schedual_id';
	protected $_order_by = 'schedual_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	public function get_all($lang)
	{
		$this->db->select($lang.'_name as name , schedual_id');
		$this->db->where('is_active' , 1);
		return parent::get();
	}
	
}