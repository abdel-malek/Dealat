<?php 

class Kids_tamplate extends MY_Model {
	protected $_table_name = 'kids_tamplate';
	protected $_primary_key = 'kid_id';
	protected $_order_by = 'kid_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::KIDS);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}