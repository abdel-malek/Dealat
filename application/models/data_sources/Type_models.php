<?php

class Type_models extends MY_Model {
	protected $_table_name = 'type_models';
	protected $_primary_key = 'type_model_id';
	protected $_order_by = 'type_model_id';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
}