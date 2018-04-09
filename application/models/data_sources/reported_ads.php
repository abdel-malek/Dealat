<?php 

class Reported_ads extends MY_Model {
	protected $_table_name = 'reported_ads';
	protected $_primary_key = 'reported_ad_id';
	protected $_order_by = 'reported_ad_id';
	public $rules = array();
}