<?php  
class GENDER{
	const MALE = 1 , 
	      FEMALE = 2;
		  
    public static function get_list($lang = 'en')
    {
    	if($lang == 'en'){
        	return array(
        	   GENDER::MALE => 'Male',
        	   GENDER::FEMALE => 'Female'
			);
        }else{
        	return array(
        	   GENDER::MALE => 'ذكر',
        	   GENDER::FEMALE => 'أنثى'
			);
        }
    }
		  
    public static function get_name($id , $lang)
    {
        if($lang == 'en'){
        	if($id == GENDER::MALE){
        		return 'Male';
        	}else{
        		return 'Female';
        	}
        }else{
        	if($id == GENDER::MALE){
        		return 'ذكر';
        	}else{
        		return 'أنثى';
        	}
        }
    }
}
