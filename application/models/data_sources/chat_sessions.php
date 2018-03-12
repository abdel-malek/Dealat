<?php

class Chat_sessions extends MY_Model {
	protected $_table_name = 'chat_sessions';
	protected $_primary_key = 'chat_session_id';
	protected $_order_by = 'created_at';
    protected $_timestamps = TRUE;
	public $rules = array();
	
	public function get_user_chat_sessions($user_id)
	{
		$this->db->select('chat_sessions.* , u.name as user_name  , s.name as seller_name , ads.title as ad_title');
		$this->db->join('users as u' , 'u.user_id = chat_sessions.user_id' , 'left');
		$this->db->join('users as s' , 's.user_id = chat_sessions.seller_id' , 'left');
		$this->db->join('ads' , 'chat_sessions.ad_id = ads.ad_id' , 'left');
		$this->db->where('chat_sessions.user_id' , $user_id );
		$this->db->or_where('chat_sessions.user_id' , $user_id);
		return parent::get();
	}
	
	public function change_seen_status($chat_session_id , $current_user)
	{
	   $chat_info = parent::get($chat_session_id);
	   if($current_user == $chat_info->user_id){
	   	   return parent::save(array('user_seen' => 1) , $chat_session_id);
	   }else if($current_user == $chat_info->seller_id){
	   	   return parent::save(array('seller_seen' => 1) , $chat_session_id); 
	   }else{
	   	   return false;
	   }
	}

    public function check_by_ad_and_user($ad_id , $user_id)
    {
        $this->db->where('ad_id' , $ad_id);
		$this->db->where('user_id', $user_id);
		$this->db->or_where('seller_id' , $user_id);
		return parent::get();
    }
}