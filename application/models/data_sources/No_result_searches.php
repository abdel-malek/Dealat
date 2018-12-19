<?php 

class No_result_searches extends MY_Model {
	protected $_table_name = 'no_result_searches';
	protected $_primary_key = 'no_result_search_id';
	protected $_order_by = 'no_result_search_id';
	public $rules = array();


	public function get_all(){
	   $this->db->select('no_result_searches.* , users.name, users.phone');
       $this->db->join('users' , 'users.user_id = no_result_searches.user_id', 'left');
	   $this->db->where('users.is_deleted' , 0);
	   $this->db->where('users.is_active' , 1);
	   return parent::get();
	}
}