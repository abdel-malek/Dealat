<?php 

class Reported_ads extends MY_Model {
	protected $_table_name = 'reported_ads';
	protected $_primary_key = 'reported_ad_id';
	protected $_order_by = 'reported_ad_id';
	public $rules = array();
	
	
 public function get_reported_ads()
  {
     $this->db->select(' temp.reports_count,
                         reported_ads.reported_ad_id,
                         reported_ads.ad_id as ad_number,
                         reported_ads.report_seen,
                         reported_ads.created_at,
                         reported_ads.user_id , 
                         categories.tamplate_id,
                         ads.title as ad_title, 
                         ads.status'
						 );
	 $this->db->join('ads' , 'ads.ad_id = reported_ads.ad_id' , 'left');
	 $this->db->join('categories' , 'categories.category_id = ads.category_id' , 'left');
	// $this->db->join('users' , 'reported_ads.user_id = users.user_id', 'left outer');
	// $this->db->where('users.is_deleted' , 0);
	 $this->db->where('categories.is_active' , 1);
	 $this->db->join("(SELECT max(reported_ad_id) Max_id,count(reported_ad_id) as reports_count
					   FROM reported_ads
					   GROUP BY reported_ads.ad_id) as temp" ,
					  "temp.Max_id = reported_ads.reported_ad_id" ,
					  'inner');
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

 public function send_email($ad_id , $report_message)
 {
    $to = 'dealat.co@gmail.com';
    $subject = 'Message from Dealat';
	$message =  $this->lang->line('reported_ad_email');
	$message .= $this->lang->line('report_message') . $report_message;
    mail($to, $subject, $message,  "From: ola@tradinos.com");
 }
 
 public function set_to_seen()
 {
   $this->db->where('report_seen' , 0);
   $this->db->set('report_seen'  , 1) ;
   $this->db->update($this->_table_name); 
 }
 
 public function get_not_seen()
 {
   $this->db->select('ads.title , reported_ads.*');
   $this->db->join('ads' , 'ads.ad_id = reported_ads.ad_id' , 'left');
   $this->db->where('report_seen' , 0);
   return parent::get();
 }
 
}