<?php

class Notification extends MY_Model {
	
	
	public function __construct() {
		$this->load->helper('notification_helper');
		$this->load->model('data_sources/users');
		$this->load->model('data_sources/user_tokens');
	}
	
	public function send_notification($user_id, $msg, $data, $title , $type) {
      //$user = $this->user->get($user_id);
        $tokens = $this->user_tokens->get_by(array('user_id'=>$user_id));
		//dump($tokens);
        $notification_helper = new NotificationHelper();
        foreach ($tokens as $token) {
            $sent = $notification_helper->send_notification_to_device(
                    array($token->token), $msg, $data, $token->os , $title , $type
            );
        }
    }
	
	
	
}