<?php

class Electronics_tamplete extends MY_Model {
	protected $_table_name = 'electronics_tamplate';
	protected $_primary_key = 'electronic_id';
	protected $_order_by = 'electronic_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::ELECTRONICS);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute) && $this->input->get($attribute) != ''){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}