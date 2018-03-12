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
		  //	dump($attribute)
		  	if(TAMPLATES::get_filter_type($attribute) == 'array'){ // array
		  	   if(($this->input->get($attribute) && $this->input->get($attribute) != '')){
		  	   	  if(! is_array($attribute_array)){
		  	   	  	  $attribute_array = json_decode($this->input->get($attribute), true);
		  	   	  }
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

   public function edit($ad_id)
   {
   	 $tamplate_data = array();
     $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::FASHION);
	 foreach ($attributes as $attribute) {
 	   if(($this->input->post($attribute) && $this->input->post($attribute) != '')){
	  	   $tamplate_data[$attribute] = $this->input->post($attribute);
	   } 
	 }
 	 $this->db->set($tamplate_data);
	  $this->db->where('ad_id', $ad_id);
	  if(!$this->db->update($this->_table_name)){
		   throw new Database_Exception();
		   return false;
	  }else{
	  	 return true;
	  }
   }
}
