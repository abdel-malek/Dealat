<?php 

class Report_messages extends MY_Model {
	protected $_table_name = 'report_messages';
	protected $_primary_key = 'report_message_id';
	protected $_order_by = 'report_message_id';
	public $rules = array();
	
	public function get_all($lang)
	{
	   $this->db->select('report_messages.'.$lang.'_text as msg , report_message_id');
	   return $this->db->get('report_messages')->result_array();
	}
	
	public function get_info($id , $lang)
	{
	   $this->db->select('report_messages.'.$lang.'_text as msg');
	   $this->db->where('report_message_id' , $id);
	   return $this->db->get('report_messages')->row();
	}
}