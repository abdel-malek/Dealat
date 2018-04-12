<?php 

class Reported_ads extends MY_Model {
	protected $_table_name = 'reported_ads';
	protected $_primary_key = 'reported_ad_id';
	protected $_order_by = 'reported_ad_id';
	public $rules = array();
	
	
 public function get_reported_ads()
  {
     $this->db->select('count(reported_ad_id) as reports_count, 
                         reported_ads.reported_ad_id,
                         reported_ads.ad_id as ad_number,
                         reported_ads.created_at, 
                         categories.tamplate_id,
                         ads.title as ad_title, 
                         ads.status'
						 );
	 $this->db->join('ads' , 'ads.ad_id = reported_ads.ad_id' , 'left');
	 $this->db->join('categories' , 'categories.category_id = ads.category_id' , 'left');
	 $this->db->group_by('reported_ads.ad_id');
	 return parent::get();
  }
  
 public function get_ad_reports($ad_id , $lang)
 {
     $this->db->select('reported_ads.*,
                        report_messages.'.$lang.'_text as report_msg,
                        users.name as user_name, 
                       ');
     $this->db->join('users' , 'users.user_id = reported_ads.user_id' , 'left outer');
	 $this->db->join('report_messages' , 'report_messages.report_message_id = reported_ads.report_message_id', 'left');
	 $this->db->where('reported_ads.ad_id'  , $ad_id);
	 return parent::get();
 }
}