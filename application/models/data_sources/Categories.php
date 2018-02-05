<?php

class Categories extends MY_Model {
	protected $_table_name = 'categories';
	protected $_primary_key = 'category_id';
	protected $_order_by = 'category_id';
	public $rules = array();
}