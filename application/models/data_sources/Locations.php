<?php

class Locations extends MY_Model {
	protected $_table_name = 'locations';
	protected $_primary_key = 'location_id';
	protected $_order_by = 'location_id';
	public $rules = array();
	
	public function get_all($lang)
	{
		$this->db->select('locations.'.$lang.'_name as location_name ,
	                       cites.'.$lang.'_name as city_name , locations.city_id , locations.location_id');
	    $this->db->join('cites', 'locations.city_id = cites.city_id', 'left');
		$this->db->where('locations.is_active' , 1);
		return parent::get();
	}
	
	public function get_cities($lang)
	{
	    $this->db->select('cites.'.$lang.'_name as name , city_id');
		$this->db->where('is_active' , 1);
		return $this->db->get('cites')->result_array();
	}
	
	public function get_cities_with_locations($lang)
	{
        $this->db->select('cites.'.$lang.'_name as city_name , city_id');
		$this->db->where('is_active' , 1);
		$q =  $this->db->get('cites')->result(); 
		foreach ($q as $row) {
			$row->locations = $this->get_by_city($lang, $row->city_id);
		}
		return $q;
	}
	
	public function get_by_city($lang , $city_id)
	{
		$this->db->select('locations.'.$lang.'_name as location_name ,
	                       locations.location_id');
		$this->db->where('locations.is_active' , 1);
		$this->db->where('locations.city_id' , $city_id);
		return parent::get();
	}
}