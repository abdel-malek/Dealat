<?php

class About_info extends MY_Model {
	protected $_table_name = 'about_info';
	protected $_primary_key = 'about_id';
	protected $_order_by = 'about_id';
	public $rules = array();
	
	
	public function get_info($lang)
	{
		$this->db->select(
		   ' about_info.'.$lang.'_about_us as about_us , 
		     facebook_link, 
		     twiter_link , 
		     youtube_link , 
		     linkedin_link,
		     instagram_link,
		     phone , 
		     email, 
		     about_info.'.$lang.'_terms as terms , 
		   '
		);
		return parent::get(null , true , 1);
	}
}