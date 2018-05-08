<?php

class LOG_ACTIONS {
	const ACCEPT_AD = 1, 
	      REJECT_AD = 2, 
	      CHANGE_USER_STATUS = 3, 
	      ADD_COMMERCIAL = 4, 
	      EDIT_COMMERCIAL = 5 , 
	      CHANGE_COMMERCIAL_SHOW_STATUS = 6, 
	      DELETE_COMMERCIAL = 7, 
	      ADD_CATEGORY = 8, 
	      EDIT_CATEGORY = 9, 
	      DELETE_CATEGORY = 10, 
	      CHANGE_CATEGORY_STATUS = 11, 
	      CHANGE_CATEGORIES_ORDER = 12,
	      ADD_BRAND = 13, 
	      EDIT_BRAND = 14, 
	      DELETE_BRAND = 15, 
	      ADD_BRAND_MODEL = 16, 
	      EDIT_BRAND_MODEL = 17, 
	      DELETE_BRAND_MODEL = 18, 
	      ADD_EDUCATION = 20 ,
	      EDIT_EDUCATION = 21, 
	      DELETE_EDUCATION = 22, 
	      ADD_SCHEDUAL = 23, 
	      EDIT_SCHEDUAL = 24, 
	      DELETE_SCHEDUAL = 25, 
	      ADD_CITY = 26, 
	      EDIT_CITY = 27, 
	      DELETE_CITY = 28 , 
	      ADD_LOCATION = 29, 
	      EDIT_LOCATION = 30 , 
	      DELETE_LOCATION = 31, 
	      EDIT_ABOUT_INFO = 32, 
	      SEND_PUBLIC_NOTIFICATION = 33,
		  ADD_CERTIFICATE = 34 ,
	      EDIT_CERTIFICATE= 35, 
	      DELETE_CERTIFICATE = 36; 
		  
  public function get_note($action_id)
  {
      switch ($action_id) {
          case LOG_ACTIONS::ACCEPT_AD:
			  return array('ar_action' => 'قبل إعلان' , 'en_action' => 'Accept an ad');
              break;
		  case LOG_ACTIONS::REJECT_AD:
			  return array('ar_action' => 'رفض إعلان' , 'en_action' => 'Reject an ad');
              break;
		  case LOG_ACTIONS::CHANGE_USER_STATUS:
			  return array('ar_action' => 'تغيير حالة مستخدم' , 'en_action' => 'Change user status');
              break;
          
          default:
              
              break;
      }
  } 
}