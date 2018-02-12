<?php

class Types extends MY_Model {
	protected $_table_name = 'types';
	protected $_primary_key = 'type_id';
	protected $_order_by = 'type_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	public function get_all($lang)
	{
		$this->db->select($lang.'_name as name , tamplate_id , category_id , type_id');
		$this->db->where('is_active' , 1);
		return parent::get();
	}
	
}