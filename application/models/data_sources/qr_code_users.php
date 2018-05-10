<?php 

class Qr_code_users extends MY_Model {
	protected $_table_name = 'qr_code_users';
	protected $_primary_key = 'qr_code_user_id';
	protected $_order_by = 'qr_code_user_id DESC';
	public $rules = array();
	
	public function get_generated_code()
	{
	   $digites = '0123456789';
       $randomString = $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
        ;
        return $randomString;
	}
}