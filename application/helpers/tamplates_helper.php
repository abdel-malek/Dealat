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

	public static function get_tamplate_attributes($id) {
		switch($id) {
			case 1 :
				return array('type_id', 'type_model_id', 'manufacture_date', 'is_automatic', 'is_new', 'kilometer');
		    break;
			case 2 :
				return array('space', 'rooms_num', 'floor', 'state', 'with_furniture');
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
				return array('schedule_id', 'education_id', 'experience', 'salary');
			break;
			case 10 :
				return array();
			break;
			default :
				return array();
		}
	}
	
	public static function get_tamplate_attributes_array()
	{
		
		return array(
		  1 =>  array('type_id', 'type_model_id', 'manufacture_date', 'is_automatic', 'is_new', 'kilometer'),
		  2 =>  array('space', 'rooms_num', 'floor', 'state', 'with_furniture'),
		  3 =>  array('type_id', 'is_new'),
		  4 =>  array('type_id', 'size', 'is_new'),
		  5 =>  array('is_new'),
		  6 =>  array('is_new'),
		  7 =>  array('is_new'), 
		  8 =>  array('schedule_id', 'education_id', 'experience', 'salary'),
		  9 =>  array('is_new'),
		  10 => array(),
		  11 => array()
		);
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
				return array('education' ,'schedule');
			break;
			default :
				return array();
			break;
		}
		
	}

	public static function get_tamplate_name($id) {

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
	}

}
