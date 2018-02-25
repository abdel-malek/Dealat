<?php 

class Job_positions_tamplate extends MY_Model {
	protected $_table_name = 'job_positions_tamplate';
	protected $_primary_key = 'job_position_id';
	protected $_order_by = 'job_position_id';
	public $rules = array();
	
	public function filter()
	{
	  $attributes = TAMPLATES::get_tamplate_attributes(TAMPLATES::JOB_POSITIONS);
	  foreach ($attributes as $attribute) {
		  if($this->input->get($attribute)&& $this->input->get($attribute) != ''){
		  	$this->db->where($attribute , $this->input->get($attribute));
		  }
	  }
	}
}