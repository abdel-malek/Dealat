<?php 

class Properties_tamplate extends MY_Model {
	protected $_table_name = 'properties_tamplate';
	protected $_primary_key = 'property_id';
	protected $_order_by = 'property_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::PROBERTIES);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}