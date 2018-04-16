<?php

class Cities extends MY_Model {
	protected $_table_name = 'cites';
	protected $_primary_key = 'city_id';
	protected $_order_by = 'city_id';
	public $rules = array();
	
	
	public function get_array($lang)
	{
		
	}
}