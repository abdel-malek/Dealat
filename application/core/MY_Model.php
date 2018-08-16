<?php
class MY_Model extends CI_Model {
	
	protected $_table_name = '';
	protected $_primary_key = 'id';
	protected $_primary_filter = 'intval';
	protected $_order_by = '';
	protected $is_dec_order = false;
	public $rules = array();
	protected $_timestamps = FALSE;
	
	function __construct() {
		parent::__construct();
	}
	
	public function array_from_post($fields){
		$data = array();
		foreach ($fields as $field) {
		    // if($field == "password"){
				// $data[$field] = md5($this->input->post($field));
			// }else{
				$data[$field] = $this->input->post($field);
		  //  }
		}
		return $data;
	}
	
	public function get($id = NULL, $single = FALSE , $limit= null , $get_all = FALSE){
		
		if ($id != NULL) {
			$filter = $this->_primary_filter;
			$id = $filter($id);
			$this->db->where($this->_primary_key, $id);
			$method = 'row';
		}
		elseif($single == TRUE) {
			$method = 'row';
		}
		else {
			$method = 'result';
		}
	   if($this->_order_by != ''){
	   	   if($this->is_dec_order){
		   	  $this->db->order_by($this->_order_by , "desc");
		   }else{
		   	  $this->db->order_by($this->_order_by);
		   }
	   }
	   // for pageing 
	   if($this->input->get('page_size')!= null && $this->input->get('page_num') != null && !$get_all){ 
			$page_size = $this->input->get('page_size');
			$page_num = $this->input->get('page_num');
			$offset = $page_num * $page_size - $page_size;
			$r = $this->db->get($this->_table_name , $page_size , $offset)->$method();
			//dump($r);
			return $r;
	   }else{ // without pageing
	   	    return $this->db->get($this->_table_name , $limit)->$method();
	   }
	}
	
	public function get_by($where, $single = FALSE, $limit= null, $get_all = FALSE){
		$this->db->where($where);
		return $this->get(NULL, $single,$limit,$get_all);
	}
	
	public function save($data, $id = NULL){
		
		// Set timestamps
		if ($this->_timestamps == TRUE) {
			$time_zone = new DateTime(null, new DateTimeZone('Asia/Damascus'));
            $now = $time_zone->format('Y-m-d H:i:s');
			//$now = date('Y-m-d H:i:s');
			$id || $data['created_at'] = $now;
			$data['modified_at'] = $now;
		}
		
		// Insert
		if ($id === NULL) {
			!isset($data[$this->_primary_key]) || $data[$this->_primary_key] = NULL;
			$this->db->set($data);
			if(!$this->db->insert($this->_table_name)){
			   throw new Database_Exception();
			}
			$id = $this->db->insert_id();
		}
		// Update
		else {
			$filter = $this->_primary_filter;
			$id = $filter($id);
			$this->db->set($data);
			$this->db->where($this->_primary_key, $id);
			if(!$this->db->update($this->_table_name)){
			   throw new Database_Exception();
			}
		}
		
		return $id;
	}
	
	public function delete($id){
		$filter = $this->_primary_filter;
		$id = $filter($id);
		
		if (!$id) {
			return FALSE;
		}
		$this->db->where($this->_primary_key, $id);
		$this->db->limit(1);
		if(!$this->db->delete($this->_table_name)){
			 throw new Database_Exception();
		};
	}
}