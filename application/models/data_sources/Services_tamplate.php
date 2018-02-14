<?php 

class Services_tamplate extends MY_Model {
	protected $_table_name = 'services_tamplate';
	protected $_primary_key = 'service_id';
	protected $_order_by = 'service_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::SERVICES);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}