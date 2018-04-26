<?php 
class NOTIFICATION_MESSAGES {
	const ACCEPTED_MSG = 1 , REJECTED_MSG = 2 , HIDE_MSG = 3 , SHOW_MSG = 4;
	
	
    public static function get_action_msg($action, $lang = 'ar')
    {
        if($lang == 'ar'){
        	switch ($action) {
				case NOTIFICATION_MESSAGES::ACCEPTED_MSG:
					return 'تم قبول إعلانك';
					break;
				case NOTIFICATION_MESSAGES::REJECTED_MSG:
					return 'عذراً! تم رفض إعلانك';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return 'تم إخفاء إعلانك من قبل المدير';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return 'تم إظهار إعلانك من قبل المدير';
					break;
				default:
					return '';
					break;
			}
        }else{
        	switch ($action) {
				case NOTIFICATION_MESSAGES::ACCEPTED_MSG:
					return 'Congrats! Your Ad has been Accepted';
					break;
				case NOTIFICATION_MESSAGES::REJECTED_MSG:
					return 'Sorry! Your Ad is Rejected';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return 'Your ad is hidden by the admin';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return 'Your ad is Shown by the admin';
					break;
				default:
					return '';
					break;
			}
        }
    }

   public static function get_action_title($action ,$lang = 'ar')
    {
        if($lang == 'ar'){
        	switch ($action) {
				case NOTIFICATION_MESSAGES::ACCEPTED_MSG:
					return 'قبول';
					break;
				case NOTIFICATION_MESSAGES::REJECTED_MSG:
					return 'رفض';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return 'إخفاء';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return 'إظهار';
					break;
				default:
					return '';
					break;
			}
        }else{
        	switch ($action) {
				case NOTIFICATION_MESSAGES::ACCEPTED_MSG:
					return 'Accepted';
					break;
				case NOTIFICATION_MESSAGES::REJECTED_MSG:
					return 'Rejected';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return 'Hidden';
					break;
				case NOTIFICATION_MESSAGES::HIDE_MSG:
					return  'Shown';
					break;
				default:
					return '';
					break;
			}
        }
    }
}