<?php

class Commercial_ads extends MY_Model {
	protected $_table_name = 'commercial_ads';
	protected $_primary_key = 'commercial_ad_id';
	protected $_order_by = 'commercial_ad_id';
	//protected $_order_by = '';
	public $rules = array();
	
	// public function get_commercial_ads($category_id, $lang ,$city, $from_web = null )
	// {
	//    if($category_id == 0){
	//    	 $this->db->where('category_id' ,0);
	//    }else{
	//    	 $this->db->select('commercial_ads.* ,
	// 	                  categories.'.$lang.'_name as category_name ,
	// 	                  c.'.$lang.'_name as parent_category_name ,');
	//      $this->db->join('categories' , 'commercial_ads.category_id = categories.category_id' , 'left');
	//      $this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	//      $this->db->where("(categories.category_id = '$category_id' OR categories.parent_id = '$category_id' OR c.parent_id = '$category_id')");
	//    }
	//    if($from_web == null){
	//    	 $this->db->where('position' , POSITION::MOBILE);
	//    }else{
	//    	 $this->db->where('position !=' , POSITION::MOBILE);
	//    }
	//    $this->db->where('commercial_ads.is_active' , 1);
	//    $this->db->where('city_id' , $city);
	//    return parent::get();
	// }


	public function get_commercial_ads($category_id, $lang ,$city, $from_web = null )
	{
	   if($category_id == 0){
	   	 $this->db->where('category_id' , 0);
	   	 $this->db->join('commercials_cities' ,'commercials_cities.commercial_id = commercial_ads.commercial_ad_id' , 'left outer');
	   	 $this->db->where('commercials_cities.city_id' ,$city );
	   }else{
	   	 $this->db->select('commercial_ads.* ,
		                  categories.'.$lang.'_name as main_category_name ,
		                  sun_cat.'.$lang.'_name as parent_category_name ,
		                  sun_sun_cat.'.$lang.'_name as category_name');
	     $this->db->join('categories' , 'commercial_ads.category_id = categories.category_id' , 'left');
	     $this->db->join('categories as sun_cat' ,'sun_cat.parent_id = categories.category_id' , 'left outer');
	     $this->db->join('categories as sun_sun_cat' ,'sun_sun_cat.parent_id = sun_cat.category_id' , 'left outer');
	     $this->db->join('commercials_cities' ,'commercials_cities.commercial_id = commercial_ads.commercial_ad_id' , 'left outer');
	     $this->db->where("(categories.category_id = '$category_id' OR sun_cat.category_id = '$category_id' OR sun_sun_cat.category_id = '$category_id')");
	     $this->db->where('commercials_cities.city_id' ,$city );
	     $this->db->group_by('commercial_ads.category_id');
	   }
	   if($from_web == null){
	   	 $this->db->where('position' , POSITION::MOBILE);
	   }else{
	   	 $this->db->where('position !=' , POSITION::MOBILE);
	   }
	   $this->db->where('commercial_ads.is_active' , 1);
	   return parent::get();
	}
	
   public function get_all($lang)
	{
	   $this->db->select('commercial_ads.* ,
		                 categories.'.$lang.'_name as category_name ,
		                 ');
	   $this->db->where('commercial_ads.category_id !=' , 0 );
	   $this->db->join('categories' , 'commercial_ads.category_id = categories.category_id' , 'left'); 
	  // $this->db->join('cites' , 'commercial_ads.city_id = cites.city_id' , 'left');
	   return parent::get();
	}
	
   public function delete_image($image)
    {
      $ok = unlink(PUBPATH.$image);
	  return $ok;
	}
	
	public function check_active_number($category , $position , $city)
	{
		$cities = explode("-",$city);
		$ok = true;
		if($cities){
			foreach ($cities as $key => $city) {
				$this->db->select('COUNT(commercial_ad_id) as  active_count');
				$this->db->join('commercials_cities' , 'commercial_ads.commercial_ad_id = commercials_cities.commercial_id', 'left');
				$this->db->where('commercials_cities.city_id' , $city);
				$q = parent::get_by(array('category_id' => $category , 'is_active' => 1 , 'position' => $position));
			    $count = $q[0]->active_count;
				$limit = POSITION::get_limit($position);
				if($count >= $limit){
					$ok = false;
					break;
				}else{
					$ok = true;
				}
			}
		}
		return $ok;
	}
	
	public function get_all_main($lang)
	{
		$this->db->select('commercial_ads.*' );
	//	$this->db->join('cites' , 'commercial_ads.city_id = cites.city_id' , 'left');
		$this->db->where('category_id' , 0);
		return parent::get();
	}	
}