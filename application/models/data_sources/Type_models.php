<?php

class Type_models extends MY_Model {
	protected $_table_name = 'type_models';
	protected $_primary_key = 'type_model_id';
	protected $_order_by = 'type_model_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
    public function get_by_type($lang , $type_id)
	{
		$this->db->select('type_models.'.$lang.'_name as name, type_models.type_id , type_model_id');
		$this->db->where('is_active' , 1);
		$this->db->where('type_id' , $type_id);
		return parent::get();
	}
	
	public function get_all_by_type($type_id)
	{
	   $this->db->where('is_active' , 1);
	   $this->db->where('type_id' , $type_id);
	   return parent::get();
	}
	
}