<?php

class Certificates extends MY_Model {
	protected $_table_name = 'certificates';
	protected $_primary_key = 'certificate_id';
	protected $_order_by = 'certificate_id DESC';
	public $rules = array();
	
	public function get_all($lang)
	{
	    $this->db->select('certificates.'.$lang.'_name as name , certificate_id');
		$this->db->where('is_active' , 1);
		return $this->db->get('certificates')->result_array();
	}
}