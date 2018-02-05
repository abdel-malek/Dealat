<?php
/**
 *
 *
 * @author Amal Abdulraouf
 */

class Database_Exception extends Parent_Exception {

	public function __construct($message = "") {
		parent::__construct('Database Exception');
	}

}
