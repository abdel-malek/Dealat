<?php

class Electronics_tamplate  extends MY_Model {
	protected $_table_name = 'electronics_tamplate';
	protected $_primary_key = 'electronic_id';
	protected $_order_by = 'electronic_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::ELECTRONICS);
	  foreach ($attributes as $attribute) {
		  //	dump($attribute)
		  	if(TAMPLATES::get_filter_type($attribute) == 'array'){ // array
		  	   if(($this->input->get($attribute) && $this->input->get($attribute) != '')){
		  	   	  $attribute_array = json_decode($this->input->get($attribute), true);
		  		  $this->db->where_in($attribute , $attribute_array);
		  	   }
		  	}else if(TAMPLATES::get_filter_type($attribute) == 'range'){ // ranges
		  	   if($this->input->get($attribute.'_min') && $this->input->get($attribute.'_min') != ''){
		  		 $attribute_min = $this->input->get($attribute.'_min');
 				 $this->db->where($attribute. '>=' , $attribute_min , false);
			   }
               if($this->input->get($attribute.'_max') && $this->input->get($attribute.'_max') != ''){
               	 $attribute_max = $this->input->get($attribute.'_max');
				 $this->db->where($attribute. '<=' , $attribute_max , false);
               }
		  	}else{
		  	   if(($this->input->get($attribute) && $this->input->get($attribute) != '')){
		  	     $this->db->where($attribute, $this->input->get($attribute));
		  	   }
		  	}
	  }
	}
}