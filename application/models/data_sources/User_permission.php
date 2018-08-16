<?php

class User_permission extends MY_Model {
	protected $_table_name = 'user_permission';
	protected $_primary_key = 'user_permission_id';
	protected $_order_by = 'user_permission_id';
	public $rules = array();

	public function get_user_permissions_ids($id_user) {
		$this -> db -> select('permission_id');
		$result = parent::get_by(array('user_id' => $id_user) , false , null , true);
		$result_array = array();
		foreach ($result as $array) {
			foreach ($array as $permission_id) {
				$result_array[] = $permission_id;
			}
		}
		return $result_array;
	}

	// for mobile
	public function check_permission($permission, $user_permissions, $user_id) {
		if ($user_permissions)
			foreach ($user_permissions as $p)
				if ($p == $permission) {
					return true;
				}
		throw new Parent_Exception("Permission denied");
	}

	// for web
	public function check_permission_CMS($permission, $user_permissions, $user_id) {
		if ($user_permissions)
			foreach ($user_permissions as $p)
				if ($p == $permission) {
					return true;
				}
		return false;
	}

	public function delete_user_permissions($admin_id, $permission_id = null) {
		$this -> db -> where('user_id', $admin_id);
		if ($permission_id) {
			$this -> db -> where('permission_id', $permission_id);
		}
		if (!$this -> db -> delete($this -> _table_name)) {
			throw new Database_Exception();
		}
	}

}
