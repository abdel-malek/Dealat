<?php

class Locations extends MY_Model {
	protected $_table_name = 'locations';
	protected $_primary_key = 'location_id';
	protected $_order_by = 'location_id';
	public $rules = array();
	
	public function get_all($lang)
	{
		$this->db->select('locations.'.$lang.'_name as location_name ,
	                       cites.'.$lang.'_name as city_name');
	    $this->db->join('cites', 'locations.city_id = cites.city_id', 'left');
		$this->db->where('is_active' , 1);
		return parent::get();
	}
}