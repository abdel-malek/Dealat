<?php 

class Mobiles_tamplate extends MY_Model {
	protected $_table_name = 'mobiles_tamplate';
	protected $_primary_key = 'mobile_id';
	protected $_order_by = 'mobile_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::MOBILES);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)&& $this->input->get($attribute) != ''){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}