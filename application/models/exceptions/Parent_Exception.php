<?php

/**
 * @author Amal Abdulraouf
 */

class Parent_Exception extends Exception {

	public function __construct($message = "") {

		parent::__construct($this -> fillMessage($message));
	}

	public function Get_Parent_Exception($message = "") {
		$CI = &get_instance();
		parent::__construct($message);
	}

	public function fillMessage($msg) {
		/*   $CI = & get_instance();
		 $m = $CI->lang->line($msg);
		 if ($m != "")
		 return $m;
		 else {*/
		return $msg;
		//   }
	}
}
