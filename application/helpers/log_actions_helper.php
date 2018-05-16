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
	      DELETE_CERTIFICATE = 36,
		  ADD_ADMIN = 37,
		  EDIT_ADMIN_INFO = 38,
		  EDIT_ADMIN_PERMISSIONS = 39,
		  DELETE_ADMIN = 40,
		  HIDE_AD= 41,
		  SHOW_AD = 42, 
		  DELETE_AD = 43,
		  ADD_PERIOD = 34 ,
	      EDIT_PERIOD= 35, 
	      DELETE_PERIOD = 36;
		  
  public static function get_note($action_id , $exstrainfo = null)
  {
      switch ($action_id) {
          case LOG_ACTIONS::ACCEPT_AD:
			  return array('ar_action' => 'قبل الإعلان رقم '.$exstrainfo , 'en_action' => 'Accept the ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::REJECT_AD:
			  return array('ar_action' => 'رفض الإعلان رقم '.$exstrainfo , 'en_action' => 'Reject the ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::CHANGE_USER_STATUS:
			  return array('ar_action' => 'تغيير حالة المستخدم رقم '.$exstrainfo , 'en_action' => 'Change the status of the user #'.$exstrainfo);
              break;
	      case LOG_ACTIONS::ADD_COMMERCIAL:
			  return array('ar_action' => 'إضافة إعلان تجاري رقم '.$exstrainfo , 'en_action' => 'Add the commercial ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_COMMERCIAL:
			  return array('ar_action' => 'تعديل إعلان تجاري رقم '.$exstrainfo , 'en_action' => 'Edit the commercial ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_COMMERCIAL:
			  return array('ar_action' => 'حذف إعلان تجاري رقم '.$exstrainfo , 'en_action' => 'Delete the commercial ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::CHANGE_COMMERCIAL_SHOW_STATUS:
			  return array('ar_action' => 'تغيير حالة الظهور للإعلان التجاري رقم '.$exstrainfo , 'en_action' => 'change show status for commercial ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_CATEGORY:
			  return array('ar_action' => 'إضافة صنف رقم '.$exstrainfo , 'en_action' => 'Add the category  #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_CATEGORY:
			  return array('ar_action' => 'تعديل صنف رقم '.$exstrainfo , 'en_action' => 'Edit the category  #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_CATEGORY:
			  return array('ar_action' => 'حذف صنف رقم '.$exstrainfo , 'en_action' => 'Delete the category  #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::CHANGE_CATEGORY_STATUS:
			  return array('ar_action' => 'تغيير حالة الصنف رقم '.$exstrainfo , 'en_action' => 'Edit the status of the category  #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::CHANGE_CATEGORIES_ORDER:
			  return array('ar_action' => 'تغيير ترتيب الأصناف '.$exstrainfo , 'en_action' => 'Edit the categories order'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_BRAND:
			  return array('ar_action' => 'إضافة ماركة رقم '.$exstrainfo , 'en_action' => 'Add the Brand #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_BRAND:
			  return array('ar_action' => 'تعديل ماركة رقم '.$exstrainfo , 'en_action' => 'Edit the Brand #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_BRAND:
			  return array('ar_action' => 'حذف ماركة رقم '.$exstrainfo , 'en_action' => 'Delete the Brand #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_BRAND_MODEL:
			  return array('ar_action' => 'إضافة الموديل رقم '.$exstrainfo , 'en_action' => 'Add the model #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_BRAND_MODEL:
			  return array('ar_action' => 'تعديل الموديل رقم '.$exstrainfo , 'en_action' => 'Edit the model #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_BRAND_MODEL:
			  return array('ar_action' => 'حذف الموديل رقم '.$exstrainfo , 'en_action' => 'Delete the model #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_EDUCATION:
			  return array('ar_action' => 'إضافة المستوى التعليمي رقم '.$exstrainfo , 'en_action' => 'Add the education #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_EDUCATION:
			  return array('ar_action' => 'تعديل المستوى التعليمي رقم '.$exstrainfo , 'en_action' => 'Edit the education #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_EDUCATION:
			  return array('ar_action' => 'حذف المستوى التعليمي رقم '.$exstrainfo , 'en_action' => 'Delete the education #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_SCHEDUAL:
			  return array('ar_action' => 'إضافة جدول دوام العمل رقم '.$exstrainfo , 'en_action' => 'Add the ScheduaL #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_SCHEDUAL:
			  return array('ar_action' => 'تعديل جدول دوام العمل رقم '.$exstrainfo , 'en_action' => 'Edit the ScheduaL #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_SCHEDUAL:
			  return array('ar_action' => 'حذف جدول دوام العمل رقم '.$exstrainfo , 'en_action' => 'Delete the ScheduaL #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_CITY:
			  return array('ar_action' => 'إضافة محافظة رقم '.$exstrainfo , 'en_action' => 'Add the city #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_CITY:
			  return array('ar_action' => 'تعديل محافظة رقم '.$exstrainfo , 'en_action' => 'Edit the city #'.$exstrainfo);
              break;
	      case LOG_ACTIONS::DELETE_CITY:
			  return array('ar_action' => 'حذف محافظة رقم '.$exstrainfo , 'en_action' => 'Delete the city #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_LOCATION:
			  return array('ar_action' => 'إضافة منطقة رقم '.$exstrainfo , 'en_action' => 'Add the area #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_LOCATION:
			  return array('ar_action' => 'تعديل منطقة رقم '.$exstrainfo , 'en_action' => 'Edit the area #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_LOCATION:
			  return array('ar_action' => 'حذف منطقة رقم '.$exstrainfo , 'en_action' => 'Delete the area #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_ABOUT_INFO:
			  return array('ar_action' => 'تعديل معلومات حول '.$exstrainfo , 'en_action' => 'Edit the about info'.$exstrainfo);
              break;
		  case LOG_ACTIONS::SEND_PUBLIC_NOTIFICATION:
			  return array('ar_action' => 'إرسال الإشعار رقم '.$exstrainfo , 'en_action' => 'Send the notification #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_CERTIFICATE:
			  return array('ar_action' => 'إضافة الشهادة رقم '.$exstrainfo , 'en_action' => ' Add the certificate #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_CERTIFICATE:
			  return array('ar_action' => 'تعديل الشهادة رقم '.$exstrainfo , 'en_action' => ' Edit the certificate #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_CERTIFICATE:
			  return array('ar_action' => 'حذف الشهادة رقم '.$exstrainfo , 'en_action' => ' Delete the certificate #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::ADD_ADMIN:
			  return array('ar_action' => 'إضافة مستخدم لوحة تحكم رقم '.$exstrainfo , 'en_action' => ' Add the CMS user #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_ADMIN_INFO:
			  return array('ar_action' => 'تعديل معلومات مستخدم لوحة تحكم رقم '.$exstrainfo , 'en_action' => ' Edit the info of CMS user #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_ADMIN_PERMISSIONS:
			  return array('ar_action' => 'تعديل صلاحيات مستخدم لوحة تحكم رقم '.$exstrainfo , 'en_action' => ' Edit the permissions of CMS user #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_ADMIN:
			  return array('ar_action' => 'حذف مستخدم لوحة تحكم رقم '.$exstrainfo , 'en_action' => ' Delete the CMS user #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::HIDE_AD:
			  return array('ar_action' => 'إخفاء الإعلان رقم '.$exstrainfo , 'en_action' => 'Hide the ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::SHOW_AD:
			  return array('ar_action' => 'إظهار الإعلان رقم '.$exstrainfo , 'en_action' => 'Show the ad #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_AD:
			  return array('ar_action' => 'حذف الإعلان رقم '.$exstrainfo , 'en_action' => 'Delete the ad #'.$exstrainfo);
              break;
		   case LOG_ACTIONS::ADD_PERIOD:
			  return array('ar_action' => 'إضافة فترة الظهور رقم '.$exstrainfo , 'en_action' => ' Add the Show period #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::EDIT_PERIOD:
			  return array('ar_action' => 'تعديل فترة الظهور رقم '.$exstrainfo , 'en_action' => ' Edit the Show period #'.$exstrainfo);
              break;
		  case LOG_ACTIONS::DELETE_PERIOD:
			  return array('ar_action' => 'حذف فترة الظهور رقم '.$exstrainfo , 'en_action' => ' Delete the Show period #'.$exstrainfo);
              break;
          default:
              return array('ar_action' => ' ' , 'en_action' => '');
              break;
	     
      }
  } 
}