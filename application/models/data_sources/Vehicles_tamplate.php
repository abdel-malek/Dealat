<?php

class Vehicles_tamplate extends MY_Model {
	protected $_table_name = 'vehicles_tamplate';
	protected $_primary_key = 'vehicle_id';
	protected $_order_by = 'vehicle_id';
	public $rules = array();
	
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::VEHICLES);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)&& $this->input->get($attribute) != ""){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}