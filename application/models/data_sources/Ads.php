<?php

class Ads extends MY_Model {
	protected $_table_name = 'ads';
	protected $_primary_key = 'ad_id';
	protected $_order_by = 'publish_date';
    protected $is_dec_order = true;
	public $rules = array();
	
	
	public function get_latest_ads()
	{
		$this->db->select('ads.* ,categories.name as en_category_name , categories.ar_name as ar_category_name , locations.name as en_location_name , locations.ar_name as ar_location_name ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
		$this->db->where('status' , STATUS::ACCEPTED);
        $q = parent::get(null , false, 10);
		return $q; 
	}
}



