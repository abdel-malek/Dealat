<?php

class Commercial_ads extends MY_Model {
	protected $_table_name = 'commercial_ads';
	protected $_primary_key = 'commercial_ad_id';
	protected $_order_by = 'commercial_ad_id';
	public $rules = array();
	
	public function get_commercial_ads($category_id, $lang , $for_mobile = 0 )
	{
	   if($category_id == 0){
	   	 $this->db->where('is_main' ,1);
	   }else{
	   	 $this->db->select('commercial_ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  c.'.$lang.'_name as parent_category_name ,');
	     $this->db->join('categories' , 'commercial_ads.category_id = categories.category_id' , 'left');
	     $this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	     $this->db->where("(categories.category_id = '$category_id' OR categories.parent_id = '$category_id' OR c.parent_id = '$category_id')");
	   }
	   if($for_mobile == 1){
	   	 $this->db->where('position' , POSITION::MOBILE);
	   }
	   return parent::get();
	}
}