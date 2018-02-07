<?php

class Ads extends MY_Model {
	protected $_table_name = 'ads';
	protected $_primary_key = 'ad_id';
	protected $_order_by = 'is_featured DESC , publish_date ASC';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	
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
		//$this->db->where("(categories.category_id = '$main_category_id' OR categories.parent_id = '$main_category_id')");
		$this->db->where("(categories.parent_id = '$main_category_id')");
		$q = parent::get();
		return $q;
	}
    
	public function get_ad_details($ad_id , $lang)
	{
	    $this->db->select('ads.* ,
		                   categories.'.$lang.'_name as category_name ,
		                   tamplate.*
		                 ');
       	$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
		// union with other tamplates
		$this->db->join("(SELECT v.* FROM vehicles_tamplate v) as tamplate",
		                "ads.ad_id = tamplate.ad_id AND categories.tamplate_name = tamplate.name AND categories.tamplate_name <> 'basic'",
		                'left outer');
		//$this->db->where('categories.tamplate_name !=','basic');
		$this->db->where('ads.ad_id' , $ad_id);
		$q = parent::get();
		return $q;
	}
	
	
}



