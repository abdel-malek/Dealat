<?php 

class Show_periods extends MY_Model {
	protected $_table_name = 'show_periods';
	protected $_primary_key = 'show_period_id';
	protected $_order_by = 'days';
	public $rules = array();
	
	
    public function get_all($lang)
	{
	    $this->db->select('show_periods.'.$lang.'_name as name , show_period_id , days');
		$this->db->where('is_active' , 1);
		return $this->db->get('show_periods')->result_array();
	}
}


