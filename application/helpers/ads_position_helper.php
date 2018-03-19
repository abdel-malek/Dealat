<?php

class POSITION{
	const SIDE_MENU = 1;
	const SLIDER = 2;
	const MOBILE = 3;
	
	public static function get_position_name($id , $lang)
	{
		if($lang == 'en'){
			switch ($id) {
				case POSITION::SIDE_MENU:
					return 'Side banner';
					break;
			    case POSITION::SLIDER:
					return 'Main banner';
					break;
				case POSITION::MOBILE:
					return 'Mobile';
					break;
				default:
					return 'NOT SET';
					break;
			}
		}else if ($lang == 'ar'){
			switch ($id) {
				case POSITION::SIDE_MENU:
					return 'القائمة الجانبية';
					break;
			    case POSITION::SLIDER:
					return 'السلايدر الرئيسي';
					break;
				case POSITION::MOBILE:
					return 'الموبايل';
					break;
				default:
					return 'غير محدد';
					break;
			}
		}
	}
	
	public static function get_position_list($lang)
	{
	     if($lang == 'en'){
	     	return array(
	     	   POSITION::SIDE_MENU => 'Side banner',
	     	   POSITION::SLIDER => 'Main banner' , 
	     	   POSITION::MOBILE => 'Mobile'
			);
		}else if ($lang == 'ar'){
			return array(
	     	   POSITION::SIDE_MENU => 'القائمة الجانبية',
	     	   POSITION::SLIDER => 'السلايدر الرئيسي' , 
	     	   POSITION::MOBILE => 'الموبايل'
			);
		}
	}
}
