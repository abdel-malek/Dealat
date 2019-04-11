<?php
class STATUS{

	const PENDING = 1;
	const ACCEPTED = 2;
	const EXPIRED = 3;
	const HIDDEN = 4;
	const REJECTED = 5;
	const DELETED = 6;

    public static function get_name($id , $lang)
	{
		if($lang == 'en'){
		  switch($id) {
			case 1 :
				return 'pending';
				break;
			case 2 :
				return 'Accepted';
				break;
			case 3 :
				return 'Expired';
				break;
			case 4 :
				return 'Hidden';
				break;
			case 5 :
				return 'Rejected';
				break;
			case 6 :
				return 'Deleted';
				break;
			default :
				return 'no net';
			}
		}else{
		  switch($id) {
			case 1 :
				return 'قيد الانتظار';
				break;
			case 2 :
				return 'مقبول';
				break;
			case 3 :
				return 'منتهي';
				break;
			case 4 :
				return 'مخفي';
				break;
			case 5 :
				return 'مرفوض';
				break;
			case 6 :
				return 'محذوف';
				break;
			default :
				return 'غير محدد';
			}
		}
	}

	public static function get_list($lang = 'en')
	{
		if($lang == 'en'){
	      return array(
			   1 => 'pending',
			   2 => 'Accepted',
			   3 => 'Expired',
			   4 => 'Hidden',
			   5 => 'Rejected',
			   6 => 'Deleted'
		  );
		}else{
		  return array(
			   1 => 'قيد الانتظار',
			   2 => 'مقبول',
			   3 => 'منتهي',
			   4 => 'مخفي',
			   5 => 'مرفوض',
			   6 => 'محذوف'
		  );
		}

	}
}
