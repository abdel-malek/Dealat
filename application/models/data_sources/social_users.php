<?php

class Social_users extends MY_Model {
	protected $_table_name = 'social_users';
	protected $_primary_key = 'id';
	protected $_order_by = 'id';
    protected $_timestamps = TRUE; 
	public $rules = array();
	
	
   public function checkUser($data = array()){
   	    $user = parent::get_by(array('oauth_provider'=>$data['oauth_provider'],'oauth_uid'=>$data['oauth_uid']) , true);
        if($user !=  null){
        	$userID = parent::save($data , array('id'=>$user->id));
        }else{
            $userID = parent::save($data); 
        }
        return $userID?$userID:FALSE;
    }
}