<?php

class Messages extends MY_Model {
	protected $_table_name = 'messages';
	protected $_primary_key = 'message_id';
	protected $_order_by = 'created_at';
    protected $_timestamps = TRUE;
	public $rules = array();
    
   public function send_msg($ad_id ,$chat_session_id ,  $msg , $user_id)
	{
		$this -> db -> trans_start();
	    $this->load->model('data_sources/chat_sessions');
	    $this->load->model('data_sources/ads');
		$data =  array('text' => $msg);
		$ad_info = $this->ads->get($ad_id);
		//$seller_id = $this->ads->get_seller_id($ad_id);
		$seller_id = $ad_info->user_id;
		$to_seller = 1; // from user to seller
	    if($chat_session_id != null){
			$data['chat_session_id'] = $chat_session_id;
			$session_info = $this->chat_sessions->get($chat_session_id);
		    if($session_info->seller_id == $user_id){ // from seller to user msg. 
				$data['to_seller'] = 0;
			    $to_seller = 0;
			}else{
				
			}
			$msg_id = parent::save($data);
		}else if( $ad_id != null){  // from user to seller 
			$check_exist_result = $this->chat_sessions->check_by_ad_and_user($ad_id , $user_id , $seller_id);
			$msg_id = null;
			if($check_exist_result){ // there is already chat session for this ad and user and seller
			    $data['chat_session_id'] = $check_exist_result->chat_session_id;
		        $msg_id = parent::save($data);
			}else{ // no chat session so create new one 
			    if($seller_id == $user_id){ // the seller is sending msg to his self so we can not create a chat session!!
			    	 $this->db->trans_rollback();
				     return false; 
			    }else{
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
			}
		}else{
		  $this->db->trans_rollback();
	      return false;
		}
	    $this -> db -> trans_complete();
		if ($this -> db -> trans_status() === FALSE) {
			$this->db->trans_rollback();
			return false;
		} else {
			$this -> db -> trans_commit();
		    $chat_session_info = $this->chat_sessions->get_with_users($chat_session_id);
			if($to_seller == 1){ // to seller
				$this->send_msg_notification($seller_id, $msg, $chat_session_info , $ad_info->title);
			}else{
			    $this->send_msg_notification($chat_session_info->user_id, $msg, $chat_session_info , $ad_info->title);
			}
			return $msg_id;
		}
	}

  public function send_msg_notification($user_id , $msg , $chat_session_info , $ad_title)
  {
  	$this->load->model('notification');
    $this->load->helper('notification'); 
	$this->notification->send_notification($user_id ,$msg , $chat_session_info , $ad_title , NotificationHelper::MSG);
  }
}