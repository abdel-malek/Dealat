<?php

class PERMISSION {
	const ADS_MANAGE = 1,
          EXPORT_ADS = 2, 
	      REPORTS_MANAGE = 3, 
	      EXPORT_REPORTS = 4, 
	      USERS_MANAGE = 5, 
	      EXPORT_USERS = 6, 
	      COMMERCIALS_MANAGE = 7, 
	      EXPORT_COMMERCIALS = 8,
	      CATEGORIES_MANAGE = 9,
	      EXPORT_CATEGORIES = 10, 
	      DATA_MANAGE = 11, 
	      EXPORT_DATA = 12, 
	      NOTIFICATION_MANAGE = 13, 
	      EXPORT_NOTIFICATIONS = 14, 
	      ADMINS_MANAGE = 15, 
	      EXPORT_ADMINS = 16, 
	      ABOUT_MANAGE = 17;
		  

	public static function Check_permission($permission, $user_id) {
		$CI = &get_instance();
		$CI -> load -> model('data_sources/user_permission');
		$user_permissions = $CI -> user_permission -> get_user_permissions_ids($user_id);
		return $CI -> user_permission -> check_permission_CMS($permission, $user_permissions, $user_id);
    }

}
