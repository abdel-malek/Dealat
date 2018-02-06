<?php

class Ads extends MY_Model {
	protected $_table_name = 'ads';
	protected $_primary_key = 'ad_id';
	protected $_order_by = 'is_featured DESC , publish_date ASC';
	public $rules = array();
	
	
	public function get_latest_ads($lang)
	{
		$this->db->select('ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  locations.'.$lang.'_name as location_name ,
		                  L.'.$lang.'_name as  parent_location,
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
		$this->db->join('locations as L', 'locations.parent_id = L.location_id', 'left');
		$this->db->where('status' , STATUS::ACCEPTED);
        $q = parent::get(null , false, 10);
		return $q; 
	}
	
	public function get_ads_by_category($main_category_id , $lang)
	{
	    $this->db->select('ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  locations.'.$lang.'_name as location_name ,
		                  L.'.$lang.'_name as  parent_location,
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
		$this->db->join('locations as L', 'locations.parent_id = L.location_id', 'left');
		$this->db->where('status' , STATUS::ACCEPTED);
		$this->db->where("(categories.category_id = '$main_category_id' OR categories.parent_id = '$main_category_id')");
		$q = parent::get();
		return $q;
	}
	
	
}



