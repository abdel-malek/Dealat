<?php

class Locations extends MY_Model {
	protected $_table_name = 'locations';
	protected $_primary_key = 'location_id';
	protected $_order_by = 'name';
	public $rules = array();
}