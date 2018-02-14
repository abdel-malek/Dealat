<?php 

class Industries_tamplate extends MY_Model {
	protected $_table_name = 'industries_tamplate';
	protected $_primary_key = 'industry_id';
	protected $_order_by = 'industry_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::INDUSTRIES);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}