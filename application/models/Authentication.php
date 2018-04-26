<?php
/**
 * @author Amal Abdulraouf
 */
class Authentication extends MY_Model {
	public function __construct() {
		$this -> load -> model('data_sources/users');
		$this -> load -> model('data_sources/admins');
	}

	public function check_user($username, $password , $type , $is_admin) {
		$user = null;
		$user = $this -> admins -> check_authentication($username, $password);
		if(!$user){
		   $user = $this -> users -> check_authentication($username, $password ,$type);
		}
		return $user;
	}
}
?>
