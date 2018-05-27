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
	      ABOUT_MANAGE = 17,
		  VIEW_ADMINS_ACTIONS = 18,
		  EXPORT_ACTIONS_LOG = 19,
		  
		  ACCEPT_AD = 20, 
		  REJECT_AD = 21,
		  HIDE_AD = 22, 
		  DELETE_AD = 23, 
		  ACTIONS_AFTER_ACCEPRT = 24,
		  HIDE_AFTER_REPORT = 25,
		  REJECT_AFTER_REPORT = 26,
		  DELETE_AFTER_REPORT = 27,
		  BLOCK_USER = 28,
		  SEND_NOTIFICATION_TO_USER = 29,
		  ADD_MAIN_COMMERCIAL = 30, 
		  SHOW_MAIN_COMMERCIAL = 31, 
		  DELETE_MAIN_COMMERCIAL = 32, 
		  ADD_OTHER_COMMERCIAL = 33, 
		  SHOW_OTHER_COMMERCIAL = 34, 
		  DELETE_OTHER_COMMERCIAL = 35,
		  ADD_MAIN_CAT = 36, 
		  UPDATE_MAIN_CAT = 37, 
		  ADD_SUB_CAT = 38, 
		  UPDATE_SUB_CAT = 39, 
		  HIDE_MAIN_CAT = 40, 
		  DELETE_MAIN_CAT = 41, 
		  HIDE_SUB_CAT = 42, 
		  DELETE_SUB_CAT = 43,
		  ADD_DATA = 44, 
		  UPDATE_DATA = 45, 
		  DELETE_DATA = 46,
		  SEND_PUBLIC_NOTIFICATION = 47, 
		  VIEW_NOTIFICATIONS = 48, 
		  UPDATE_ABOUT_INFO = 49,
		  REJECT_AFTER_ACCEPT = 50 , 
		  HIDE_AFTER_ACCEPT = 51, 
		  DELETE_AFTER_ACCEPT = 52,
		  MAIN_COMMERCIALS_MANAGE = 53,
		  EXPORT_MAIN_COMMERCIALS = 54;
		  
		  

	public static function Check_permission($permission, $user_id) {
		$CI = &get_instance();
		$CI -> load -> model('data_sources/user_permission');
		$user_permissions = $CI -> user_permission -> get_user_permissions_ids($user_id);
		return $CI -> user_permission -> check_permission_CMS($permission, $user_permissions, $user_id);
    }

}
