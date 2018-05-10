<?php 

class Public_notifications extends MY_Model {
	protected $_table_name = 'public_notifications';
	protected $_primary_key = 'notification_id';
	protected $_order_by = 'notification_id DESC';
	public $rules = array();
	
	
	public function get_all($lang)
	{
		$this->db->select('public_notifications.* ,  cites.'.$lang.'_name as city_name , users.name as user_name');
		$this->db->join('cites' , 'cites.city_id = public_notifications.city_id' , 'left outer');
		$this->db->join('users', 'users.user_id = public_notifications.user_id' , 'left');
		return parent::get();
	}
}