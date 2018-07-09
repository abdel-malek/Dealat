<?php

class User_favorite_ads extends MY_Model {
	protected $_table_name = 'user_favorite_ads';
	protected $_primary_key = 'user_favorite_id';
	protected $_order_by = 'user_favorite_id';
	public $rules = array();
	
	public function delete_by_user($ad_id , $user_id)
	{
		$this->db->where('ad_id' , $ad_id);
		$this->db->where('user_id' , $user_id);
	    if(!$this->db->delete($this->_table_name)){
			 throw new Database_Exception();
		}else{
			return true;
		}
	}
	
	public function get_my_favorites($user_id , $lang)
	{
	  $this->db->select('ads.* ,
	                  categories.'.$lang.'_name as category_name ,
	                  c.'.$lang.'_name as parent_category_name ,
	                  categories.tamplate_id,
	                  locations.'.$lang.'_name as location_name ,
	                  cites.'.$lang.'_name as  city_name,
	                 ');
	  $this->db->join('ads' , 'ads.ad_id = user_favorite_ads.ad_id');
	  $this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
	  $this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	  $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
	  $this->db->join('cites', 'locations.city_id = cites.city_id', 'left');
	  $this->db->join('users' , 'users.user_id = ads.user_id ' , 'left');
	  $this->db->join('show_periods', 'ads.show_period = show_periods.show_period_id', 'left outer');
	  $this->db->where('status' , STATUS::ACCEPTED);
	  $this->db->where('categories.is_active' , 1);
	 // $this->db->where('categories.is_deleted' , 0);
	  $this->db->where('users.is_deleted' , 0);
	  $this->db->where('users.is_active' , 1);
	  $this->db->where('user_favorite_ads.user_id' , $user_id);
	  $this->db->where('(DATE_ADD(publish_date, INTERVAL days DAY) > NOW())');  
	  return parent::get();
	}

    public function get_my_favorites_os($user_id , $lang)
	{
	  $this->db->select('ads.* ,
	                  categories.'.$lang.'_name as category_name ,
	                  c.'.$lang.'_name as parent_category_name ,
	                  categories.tamplate_id,
	                  locations.'.$lang.'_os_name as location_name ,
	                  cites.'.$lang.'_os_name as  city_name,
	                 ');
	  $this->db->join('ads' , 'ads.ad_id = user_favorite_ads.ad_id');
	  $this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
	  $this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	  $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
	  $this->db->join('cites', 'locations.city_id = cites.city_id', 'left');
	  $this->db->join('users' , 'users.user_id = ads.user_id ' , 'left');
	  $this->db->join('show_periods', 'ads.show_period = show_periods.show_period_id', 'left outer');
	  $this->db->where('status' , STATUS::ACCEPTED);
	  $this->db->where('categories.is_active' , 1);
	 // $this->db->where('categories.is_deleted' , 0);
	  $this->db->where('users.is_deleted' , 0);
	  $this->db->where('users.is_active' , 1);
	  $this->db->where('user_favorite_ads.user_id' , $user_id);
	  $this->db->where('(DATE_ADD(publish_date, INTERVAL days DAY) > NOW())');  
	  return parent::get();
	}
	
	public function check_favorite($user_id , $ad_id)
	{
		$result = parent::get_by(array('ad_id'=>$ad_id , 'user_id'=>$user_id));
		if($result != null){
			return 1;
		}else{
		    return 0; 
		}
	}
    
	
	public function delete_by_ad($ad_id)
	{
		
	}
	
}

