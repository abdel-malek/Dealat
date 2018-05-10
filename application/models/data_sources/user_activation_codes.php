<?php

class User_activation_codes extends MY_Model {
	protected $_table_name = 'user_activation_codes';
	protected $_primary_key = 'activation_code_id';
	protected $_order_by = 'activation_code_id';
    protected $_timestamps = TRUE; 
	public $rules = array();
	
	public function add_new_for_user($activation_code , $user_id)
	{
		// update the active activation code for the user to inactive 
		$this->db->set('is_active' , 0);
		$this->db->where('user_id', $user_id);
		$this->db->where('is_active' , 1);
		$this->db->update('user_activation_codes');
		
		// add new inactive activation code for the user
		$data = array(
		 'user_id' =>$user_id , 
		 'code' => $activation_code ,
		);
		return $this->save($data);
	}
	
	public function activate_user_code($user_id , $code)
	{
		$this->db->where('user_id' , $user_id);
		$this->db->where('code' , $code);
		$code_row = parent::get(null , true , 1);
		if($code_row){
			return parent::save(array('is_active' => 1) , $code_row->activation_code_id);
		}else {
			return false;
		}
	}
	
	public function generate_activation_code() {
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

    public function send_code_to_email($email , $code)
    {
    	$this->email->set_mailtype('html');
	    $this->email->from('ola.mh237@gmail.com','Dealat Team');
	    $this->email->to($email);
	    $this->email->subject('Dealat Activation Code');
		
	    $msg = $this->lang->line('verification_sms').'<p><b> '.$code. '</b></p>';
		
	    $this->email->message($msg);
	    $result = $this->email->send();
	
	    if ($result){
	        return true;
	    } else {
	        return false;  
	    }
    }
	
	public function send_code_SMS($phone , $msg)
	{
	     //$url ='https://bms.syriatel.sy/API/SendSMS.aspx?user_name=dealat1&password=P@1234567&msg=' . urlencode($msg) . '&sender=DEALAT&to=963' . $phone . ';';
	     $url ='https://bms.syriatel.sy/API/SendSMS.aspx?user_name=dealat1&password=P@1234567&msg=' . urlencode($msg) . '&sender=DEALAT&to=963' . $phone;
	     try {
	       $result = file_get_contents($url);
	       if ($result) {
	           return 1;
	       } else
	           return 0;
	     } catch (Exception $e) {
	        return false;
	     }
	}
	
}