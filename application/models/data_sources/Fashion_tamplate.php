<?php 

class Fashion_tamplate extends MY_Model {
	protected $_table_name = 'fashion_tamplate';
	protected $_primary_key = 'fashion_id';
	protected $_order_by = 'fashion_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::FASHION);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)&& $this->input->get($attribute) != ''){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}
