<?php 

class Sports_tamplate extends MY_Model {
	protected $_table_name = 'sports_tamplate';
	protected $_primary_key = 'sport_id';
	protected $_order_by = 'sport_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::SPORTS);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)&& $this->input->get($attribute) != ''){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}