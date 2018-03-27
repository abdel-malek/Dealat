<?php

class Ad_images extends MY_Model {
	protected $_table_name = 'ad_images';
	protected $_primary_key = 'ad_images.ad_image_id';
	protected $_order_by = 'ad_image_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	public function delete_ad_images($ad_id)
	{
		$this->db->where('ad_id' , $ad_id);
		$this->db->delete($this->_table_name);
	}
}