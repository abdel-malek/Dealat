<?php
class TAMPLATES {
	const VEHICLES = 1,
	      PROBERTIES = 2,
	      MOBILES = 3,
	      ELECTRONICS = 4,
	      FASHION = 5,
	      KIDS = 6, 
	      SPORTS = 7, 
	      JOB_POSITIONS = 8, 
	      INDUSTRIES = 9, 
	      SERVICES = 10, 
	      BASIC = 11;
		  
		  
      public function get_template_list($lang = 'en')
	  {
	      if($lang == 'en'){
	      	 return array(
	      	    TAMPLATES::VEHICLES => 'Vehicles',
	      	    TAMPLATES::PROBERTIES => 'Properties' , 
	      	    TAMPLATES::MOBILES => 'Mobiles' , 
	      	    TAMPLATES::ELECTRONICS => 'Electronics' , 
	      	    TAMPLATES::FASHION => 'Fashion' , 
	      	    TAMPLATES::KIDS => 'Kids' , 
	      	    TAMPLATES::SPORTS => 'Sposts' , 
	      	    TAMPLATES::JOB_POSITIONS => 'Job Positions',
	      	    TAMPLATES::INDUSTRIES => 'Industries' , 
	      	    TAMPLATES::BASIC =>'Basic'
			 );
	      }else{
	        return array(
	      	    TAMPLATES::VEHICLES => 'مركبات',
	      	    TAMPLATES::PROBERTIES => 'عقارات' , 
	      	    TAMPLATES::MOBILES => 'موبايلات' , 
	      	    TAMPLATES::ELECTRONICS => 'أجهزة الكترونية' , 
	      	    TAMPLATES::FASHION => 'موضة' , 
	      	    TAMPLATES::KIDS => 'مستلزمات أطفال' , 
	      	    TAMPLATES::SPORTS => 'مستلزمات رياضية' , 
	      	    TAMPLATES::JOB_POSITIONS => 'فرص عمل',
	      	    TAMPLATES::INDUSTRIES => 'صناعة وتجارة' , 
	      	    TAMPLATES::BASIC =>'أساسي'
			 );
	      }
	  }

   public static function get_templates_with_types($lang = 'en')
   {
       if($lang == 'en'){
      	 return array(
      	    TAMPLATES::VEHICLES => 'Vehicles',
      	    TAMPLATES::MOBILES => 'Mobiles' , 
      	    TAMPLATES::ELECTRONICS => 'Electronics' , 
		 );
      }else{
        return array(
      	    TAMPLATES::VEHICLES => 'مركبات',
      	    TAMPLATES::MOBILES => 'موبايلات' , 
      	    TAMPLATES::ELECTRONICS => 'الكترونيات' , 
		 );
      }
   }		  

	public static function get_tamplate_attributes($id) {
		switch($id) {
			case 1 :
				return array('type_id', 'type_model_id', 'manufacture_date', 'engine_capacity' , 'is_automatic', 'is_new', 'kilometer');
		    break;
			case 2 :
				return array('space', 'rooms_num', 'floor', 'floors_number' , 'state_id', 'with_furniture');
		    break;
			case 3 :
				return array('type_id', 'is_new');
			break;
			case 4 :
				return array('type_id', 'size', 'is_new');
			break;
			case 5 :
				return array('is_new');
			break;
			case 6  :
				return array('is_new');
			break;
			case 7 :
				return array('is_new');
			break;
			case 9 :
				return array('is_new');
			break;
			case 8 :
				return array('schedule_id', 'education_id', 'experience','gender' , 'certificate_id', 'salary');
			break;
			case 10 :
				return array();
			break;
			default :
				return array();
		}
	}
	
	// public static function get_tamplate_attributes_array()
	// {
		// return array(
		  // 1 =>  array('type_name', 'type_model_name', 'manufacture_date', 'is_automatic', 'is_new', 'kilometer'),
		  // 2 =>  array('space', 'rooms_num', 'floor', 'state', 'with_furniture'),
		  // 3 =>  array('type_id', 'is_new'),
		  // 4 =>  array('type_id', 'size', 'is_new'),
		  // 5 =>  array('is_new'),
		  // 6 =>  array('is_new'),
		  // 7 =>  array('is_new'), 
		  // 8 =>  array('schedule_name', 'education_name', 'experience', 'salary'),
		  // 9 =>  array('is_new'),
		  // 10 => array(),
		  // 11 => array()
		// );
	// }
	
   public static function get_tamplate_attributes_array()
	{
		return array(
		  1 =>  array('type_name', 'type_model_name', 'manufacture_date', 'is_automatic', 'engine_capacity', 'is_new', 'kilometer'),
		  2 =>  array('space', 'rooms_num', 'floor','floors_number', 'state_name', 'with_furniture'),
		  3 =>  array('type_name', 'is_new'),
		  4 =>  array('type_name', 'size', 'is_new'),
		  5 =>  array('is_new'),
		  6 =>  array('is_new'),
		  7 =>  array('is_new'), 
		  8 =>  array('schedule_name', 'education_name', 'experience','gender', 'certificate_name', 'salary'),
		  9 =>  array('is_new'),
		  10 => array(),
		  11 => array()
		);
	}
	
	public static function get_filter_type($attr_name)
	{
	     if($attr_name == 'type_model_id' ||
	         $attr_name == 'schedule_id' ||
	         $attr_name=='education_id' ||
		     $attr_name == 'manufacture_date' ||
			 $attr_name == 'engine_capacity'||
			 $attr_name == 'state_id' ||
			 $attr_name == 'certificate_id'){ // array 
		  	return 'array';
		  }else if(
		     $attr_name == 'is_new' ||
             $attr_name == 'with_furniture' ||
             $attr_name == 'is_automatic' ||
		     $attr_name == 'type_id'
			 ){ // single value 
		  	return 'single';
		  }else{
		  	return 'range';
		  }
	}
	
	public static  function get_tamplate_secondry_tables($id)
	{
		switch($id) {
			case 1 :
				return array('type', 'type_model');
		    break;
			case 3 :
				return array('type');
		    break;
			case 4 :
			    return array('type');
			break;
			case 8 :
				return array('education' ,'schedule' , 'certificate');
			break;
			default :
				return array();
			break;
		}
		
	}

	public static function get_tamplate_name($id , $lang = 'en') {
      if($lang == 'en'){
      	 switch($id) {
			case 1 :
				return 'vehicles';
				break;
			case 2 :
				return 'properties';
				break;
			case 3 :
				return 'mobiles';
				break;
			case 4 :
				return 'electronics';
				break;
			case 5 :
				return 'fashion';
				break;
			case 6 :
				return 'kids';
				break;
			case 7 :
				return 'sports';
				break;
			case 8 :
				return 'job_positions';
				break;
			case 9 :
				return 'industries';
				break;
			case 10 :
				return 'services';
				break;
			case 11 :
				return 'basic';
				break;
			default :
				return 'basic';
		}
      }else {
      	 switch($id) {
			case 1 :
				return 'مركبات';
				break;
			case 2 :
				return 'عقارات';
				break;
			case 3 :
				return 'موبايلات واكسسواراتها';
				break;
			case 4 :
				return 'الكترونيات';
				break;
			case 5 :
				return 'موضة وجمال';
				break;
			case 6 :
				return 'مستلزمات أطفال';
				break;
			case 7 :
				return 'معدّات رياضية';
				break;
			case 8 :
				return 'وظائف';
				break;
			case 9 :
				return 'تجارة وصناعة';
				break;
			case 10 :
				return 'خدمات';
				break;
			case 11 :
				return 'أساسي';
				break;
			default :
				return 'أساسي';
		}
      }
	}

  

}
