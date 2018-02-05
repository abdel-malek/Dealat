<?php

class PERMISSION {
	const POST_AD = 1;
	const CATEGORY_MANAGEMENT = 2;
	const ADS_MANAGEMENT = 3;
	const USERS_MANAGEMENT = 4;

	public static function Check_permission($permission, $user_id) {
		$CI = &get_instance();
		$CI -> load -> model('data_sources/User_permission');
		$user_permissions = $CI -> user_permission -> get_user_permissions_ids($user_id);
		return $CI -> user_permission -> check_permission_web_frontend($permission, $user_permissions, $user_id);
    }

}
