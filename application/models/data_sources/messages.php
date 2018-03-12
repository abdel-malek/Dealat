<?php

class Messages extends MY_Model {
	protected $_table_name = 'messages';
	protected $_primary_key = 'message_id';
	protected $_order_by = 'created_at';
    protected $_timestamps = TRUE;
	public $rules = array();
	
	
	public function send_msg($ad_id , $msg , $user_id)
	{
		$this -> db -> trans_start();
	    $this->load->model('data_sources/chat_sessions');
		$check_exist_result = $this->chat_sessions->check_by_ad_and_user($ad_id , $user_id);
		$data =  array('text' => $msg);
		$msg_id = null;
		if($check_exist_result){ // there is already chat session
		    $data['chat_session_id'] = $check_exist_result[0]->chat_session_id;
			if($check_exist_result[0]->seller_id == $user_id){
				$data['to_seller'] = 0;
			}
	        $msg_id = parent::save($data);
		}else{ // no chat session so create new one 
			$this->load->model('data_sources/ads');
			$seller_id = $this->ads->get_seller_id($ad_id);
			if($seller_id){
				$chat_data = array( 
				  'user_id' => $user_id , 
				  'seller_id' => $seller_id , 
				  'ad_id' => $ad_id , 
				);
				$chat_session_id = $this->chat_sessions->save($chat_data);
				if($chat_session_id){
					 $data['chat_session_id'] = $chat_session_id;
					 $msg_id = parent::save($data);
				}else{
				  $this->db->trans_rollback();
			      return false;
				}
			}else{
			  $this->db->trans_rollback();
			  return false;
			}
		}
	    $this -> db -> trans_complete();
		if ($this -> db -> trans_status() === FALSE) {
			$this->db->trans_rollback();
			return false;
		} else {
			$this -> db -> trans_commit();
			return $msg_id;
		}
	}

}