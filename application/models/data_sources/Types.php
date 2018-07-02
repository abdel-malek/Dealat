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
		$this->db->group_by('tamplate_id');
		$q =  parent::get();
		foreach ($q as  $row) {
		  $row->models = null;
          //if($row->category_id == 12){ // cars
           	 $row->models = $this->type_models->get_by_type($lang , $row->type_id);
          //}			
		}
		return $q;
	}
	
	public function get_all_by_tamplate($lang)
	{
	    $this->load->model('data_sources/type_models');
		$this->db->select('types.'.$lang.'_name as name , types.tamplate_id , types.category_id , types.type_id,
		                   CONCAT_WS("-", categories.'.$lang.'_name, types.'.$lang.'_name) AS full_type_name' , false);
		$this->db->join('categories', 'types.category_id = categories.category_id' , 'left outer');
		$this->db->where('types.is_active' , 1);
		$this->db->order_by('name');
		$q =  parent::get();
		foreach ($q as  $row) {
		  $row->models = null;
       //   if($row->category_id == 12){ // cars
          $row->models = $this->type_models->get_by_type($lang , $row->type_id);
        //  }			
		}
	    $result_array = array();
		foreach ($q as $row) {
		  $result_array[$row->tamplate_id][]= $row;
		}
		return $result_array;
	}
	
}