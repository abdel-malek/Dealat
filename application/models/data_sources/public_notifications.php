<?php 

class Public_notifications extends MY_Model {
	protected $_table_name = 'public_notifications';
	protected $_primary_key = 'notification_id';
	protected $_order_by = 'notification_id DESC';
	public $rules = array();
	
	
	public function get_all($lang)
	{
		$this->db->select('public_notifications.* ,  cites.'.$lang.'_name as city_name , admins.name as user_name , users.name as to_user_name');
		$this->db->join('cites' , 'cites.city_id = public_notifications.city_id' , 'left outer');
		$this->db->join('admins', 'admins.admin_id = public_notifications.user_id' , 'left');
		$this->db->join('users', 'users.user_id = public_notifications.to_user_id' , 'left outer');
		return parent::get();
	}
	
	public function get_user_notifications($user_id , $city_id)
	{
		$this->db->where("(city_id = $city_id AND  to_user_id IS NULL )");
		$this->db->or_where("(city_id IS NULL AND  to_user_id IS NULL )");
		$this->db->or_where("(city_id IS NULL AND  to_user_id = $user_id )");
		return parent::get();
	}
	
    public function get_not_seen_count($current_user)
    {
       $this->db->select('COUNT(notification_id) as my_noti_count');
       $this->db->where('to_user_id' , $current_user);
	   $this->db->where('seen' , 0);
	   $q = parent::get(null , true);
	   return $q->my_noti_count;
    }

   public function change_to_seen($current_user)
   {
     $this->db->where('to_user_id' , $current_user);
	 $this->db->where('seen' , 0);
	 $this->db->set('seen' , 1);
	 $this->db->update($this->_table_name);  
   }

}