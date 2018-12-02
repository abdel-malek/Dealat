<?php

class Commercials_cities extends MY_Model {
	protected $_table_name = 'commercials_cities';
	protected $_primary_key = 'commercial_city_id';
	protected $_order_by = 'commercial_id';
	public $rules = array();


	public function save_cities($com_id , $cities){
	   // delete all current cities
	   $this->db->where('commercial_id' , $com_id);
	   $this->db->delete('commercials_cities');
	   // add new cities
	   foreach ($cities as  $city) {
	   	   $this->save(array('city_id' => $city , 'commercial_id' => $com_id));
	   }
	}


	public function get_cities($com_id , $lang){
		$this->db->select('commercials_cities.* , cites.'.$lang.'_name as city_name');
		$this->db->where('commercial_id' , $com_id);
	    $this->db->join('cites' , 'commercials_cities.city_id = cites.city_id' , 'left');
	    return parent::get();
	}
	
}