<?php

class features extends MY_Model {
	protected $_table_name = 'features';
	protected $_primary_key = 'feature_id';
	protected $_order_by = 'feature_id';
	public $rules = array();

	public function get_all($lang = null)
	{
		if($lang){
			$this->db->select('features.name_'.$lang.' as name ,
													 features.feature_id');
		}


		return parent::get();
	}

	public function get_all_os($lang)
	{
		$this->db->select('locations.'.$lang.'_os_name as location_name ,
	                       cites.'.$lang.'_os_name as city_name , locations.city_id , locations.location_id');
	    $this->db->join('cites', 'locations.city_id = cites.city_id', 'left');
		$this->db->where('locations.is_active' , 1);
		return parent::get();
	}



	public function get_feature_info($feature_id)
	{
		$this->db->select('features.*');
		$this->db->from('features');
		$this->db->where('feature_id' , $feature_id);
		return $this->db->get()->row_array();

	}
}
