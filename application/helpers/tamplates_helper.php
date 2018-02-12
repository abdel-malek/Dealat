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
			case 2 :
				return array('space', 'rooms_num', 'floor', 'state', 'with_furniture');
			case 3 :
				return array('type_id', 'is_new');
			case 4 :
				return array('type_id', 'size', 'is_new');
			case (5 || 6 || 7 || 9) :
				return array('is_new');
			case 8 :
				return array('schedule_id', 'education_id', 'experince', 'salary');
			default :
				return array();
		}
	}

	public static function get_tamplate_name($id) {

		switch($id) {
			case 1 :
				return 'vehicles';
			case 2 :
				return 'properties';
			case 3 :
				return 'mobiles';
			case 4 :
				return 'electronics';
			case 5 :
				return 'fashion';
			case 6 :
				return 'kids';
			case 7 :
				return 'sports';
			case 8 :
				return 'job_positions';
			case 9 :
				return 'industries';
			case 10 :
				return 'services';
			case 11 :
				return 'basic';
			default :
				return 'basic';
		}
	}

}
