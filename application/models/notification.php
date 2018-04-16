<?php

class Notification extends MY_Model {
	
	
	public function __construct() {
		$this->load->helper('notification_helper');
		$this->load->model('data_sources/users');
		$this->load->model('data_sources/user_tokens');
	}
	
	public function send_notification($user_id, $msg, $data, $title , $type) {
        $tokens = $this->user_tokens->get_by(array('user_id'=>$user_id));
		//dump($tokens);
        $notification_helper = new NotificationHelper();
        foreach ($tokens as $token) {
            $sent = $notification_helper->send_notification_to_device(
                    array($token->token), $msg, $data, $token->os , $title , $type
            );
        }
    }
	
	public function send_notofication_to_group($user_ids , $msg , $data , $title , $type)
	{
	    $tokens = $this->user_tokens->get_tokens_by_ids($user_ids);
		//dump($tokens);
        $notification_helper = new NotificationHelper();
        foreach ($tokens as $token) {
            $sent = $notification_helper->send_notification_to_device(
                    array($token->token), $msg, $data, $token->os , $title , $type
            );
        }
	}
	
	public function send_public_notification($msg , $data ,  $title , $type)
	{
		$this->load->helper('os_helper');
		$notification_helper = new NotificationHelper();
	    $sent_andriod = $notification_helper->send_public_notification($msg, $data,OS::ANDROID , $title , $type);
	    $sent_ios = $notification_helper->send_public_notification($msg, $data,OS::IOS , $title , $type);
	}
	
	
	
}