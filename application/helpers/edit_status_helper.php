<?php 
class EDIT_STATUS{
	
    const NONE = 7;
	const WHILE_PENDING = 1;
	const AFTER_ACCEPT = 2;
	const AFTER_REJECT = 5;
	const AFTER_HIDDEN = 4;
    const AFTER_EXPIRE = 3;
	
	
	public static function get_edit_status_name($id , $lang='en')
	{
	  if($lang == 'en'){
	  	 switch ($id) {
			case EDIT_STATUS::NONE:
				return 'Not edited';
				break;
			case EDIT_STATUS::WHILE_PENDING:
				return 'While Waiting';
				break;
			case EDIT_STATUS::AFTER_ACCEPT:
				return 'After Accept';
				break;
			case EDIT_STATUS::AFTER_REJECT:
				return 'After Reject';
				break;
			case EDIT_STATUS::AFTER_HIDDEN:
				return 'After Hidden';
				break;
			case EDIT_STATUS::AFTER_EXPIRE:
				return 'After Expired';
				break;
			default:
				return '';
				break;
		}
	  }else{
	  	 switch ($id) {
			case EDIT_STATUS::NONE:
				return 'غير معدّل';
				break;
			case EDIT_STATUS::WHILE_PENDING:
				return 'خلال الانتظار';
				break;
			case EDIT_STATUS::AFTER_ACCEPT:
				return 'بعد القبول';
				break;
			case EDIT_STATUS::AFTER_REJECT:
				return 'بعد الرفض';
				break;
			case EDIT_STATUS::AFTER_HIDDEN:
				return 'بعد الإخفاء';
				break;
			case EDIT_STATUS::AFTER_HIDDEN:
				return 'بعد الانتهاء';
				break;
			default:
				return '';
				break;
		}
	  }

	}

   public static function get_list($lang = 'en')
   {
   	  if($lang == 'en'){
   	  	 return array(
   	  	  EDIT_STATUS::NONE => 'Not edited' , 
   	  	  EDIT_STATUS::WHILE_PENDING => 'While Waiting',
   	  	  EDIT_STATUS::AFTER_ACCEPT => 'After Accept',
   	  	  EDIT_STATUS::AFTER_REJECT => 'After Reject' ,
   	  	  EDIT_STATUS::AFTER_EXPIRE => 'After Expired',
   	  	  EDIT_STATUS::AFTER_HIDDEN => 'After Hidden'
		 );
   	  }else{
   	  	return array(
   	  	  EDIT_STATUS::NONE => 'غير معدّل' , 
   	  	  EDIT_STATUS::WHILE_PENDING => 'خلال الانتظار',
   	  	  EDIT_STATUS::AFTER_ACCEPT => 'بعد القبول',
   	  	  EDIT_STATUS::AFTER_REJECT => 'بعد الرفض' ,
   	  	  EDIT_STATUS::AFTER_EXPIRE => 'بعد الانتهاء',
   	  	  EDIT_STATUS::AFTER_HIDDEN => 'بعد الإخفاء'
		 );
   	  }
       
   }
}