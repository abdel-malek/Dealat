<?php 

class Property_states extends MY_Model {
	protected $_table_name = 'property_states';
	protected $_primary_key = 'state_id';
	protected $_order_by = 'property_state_id';
	public $rules = array();
	
	public function get_all($lang)
	{
	   $this->db->select('property_states.'.$lang.'_name as name , state_id');
	   $this->db->where('is_active' , 1);
	   return $this->db->get('property_states')->result_array();
	}
}