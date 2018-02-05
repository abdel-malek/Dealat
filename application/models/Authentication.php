<?php
/**
 * @author Amal Abdulraouf
 */
class Authentication extends MY_Model {
	public function __construct() {
		$this -> load -> model('data_sources/users');
	}

	public function check_user($username, $password) {
		$user = $this -> users -> check_authentication($username, $password);
		return $user;
	}
}
?>
