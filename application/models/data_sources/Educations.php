<?php

class Educations extends MY_Model {
	protected $_table_name = 'educations';
	protected $_primary_key = 'education_id';
	protected $_order_by = 'education_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
   public function get_all($lang)
	{
		$this->db->select($lang.'_name as name , education_id');
		$this->db->where('is_active' , 1);
		return parent::get();
	}
	
}