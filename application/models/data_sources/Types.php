<?php

class Types extends MY_Model {
	protected $_table_name = 'types';
	protected $_primary_key = 'type_id';
	protected $_order_by = 'types.type_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	public function get_all($lang)
	{
		$this->load->model('data_sources/type_models');
		$this->db->select('types.'.$lang.'_name as name , tamplate_id , category_id , types.type_id');
		$this->db->where('types.is_active' , 1);
		$q =  parent::get();
		foreach ($q as  $row) {
		  $row->models = null;
          if($row->category_id == 12){ // cars
           	 $row->models = $this->type_models->get_by_type($lang , $row->type_id);
          }			
		}
		return $q;
	}
	
}