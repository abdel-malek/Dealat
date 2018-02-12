<?php

class Ads extends MY_Model {
	protected $_table_name = 'ads';
	protected $_primary_key = 'ads.ad_id';
	protected $_order_by = 'is_featured DESC , publish_date ASC';
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	
	public function get_latest_ads($lang)
	{
		$this->db->select('ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  c.'.$lang.'_name as parent_category_name ,
		                  locations.'.$lang.'_name as location_name ,
		                  cites.'.$lang.'_name as  city_name,
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
    	$this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
		$this->db->join('cites', 'locations.city_id = cites.city_id', 'left');
		$this->db->where('status' , STATUS::ACCEPTED);
        $q = parent::get(null , false, 10);
		return $q; 
	}
	
	public function get_ads_by_category($main_category_id , $lang)
	{
	    $this->db->select('ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  c.'.$lang.'_name as parent_category_name ,
		                  categories.tamplate_id,
		                  locations.'.$lang.'_name as location_name ,
		                  cites.'.$lang.'_name as  city_name,
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
		$this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
		$this->db->join('cites', 'locations.city_id = cites.city_id', 'left');
		$this->db->where('status' , STATUS::ACCEPTED);
		$this->db->where("(categories.category_id = '$main_category_id' OR categories.parent_id = '$main_category_id' OR c.parent_id = '$main_category_id')");
		//$this->db->where("(categories.parent_id = '$main_category_id')");
		$q = parent::get();
		return $q;
	}
    
	public function get_ad_details($ad_id , $lang , $tamplate_id = TAMPLATES::BASIC)
	{
	    $this->db->select('ads.* ,
		                   categories.'.$lang.'_name as category_name ,
		                  ');
       	$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
		if($tamplate_id != TAMPLATES::BASIC){
			$tamplate_name = TAMPLATES::get_tamplate_name($tamplate_id);
			$this->db->select('tamplate.*');
			$this->db->join($tamplate_name.'_tamplate as tamplate', 'ads.ad_id = tamplate.ad_id', 'left outer');
		}
		$q = parent::get($ad_id);
		$q->images = $this->get_ad_images($ad_id);
		return $q;
	}
	
	public function get_ad_images($ad_id)
	{
	   $this->load->model('data_sources/ad_images');
	   return $this->ad_images->get_by(array('ad_id'=>$ad_id));
	}
	
   public function create_an_ad($basic_data,$main_image, $tamplate_id = TAMPLATES::BASIC)
    {
   	 	$this -> db -> trans_start();
		$new_ad_id = $this->save($basic_data);
		$tamplate_date = array();
		$tamplate_date['ad_id'] = $new_ad_id;
		if($tamplate_id !=  TAMPLATES::BASIC && $tamplate_id != TAMPLATES::SERVICES){
			$tamplate_name = TAMPLATES::get_tamplate_name($tamplate_id);
			$model = $tamplate_name.'_tamplate';
			$this->load->model('data_sources/'.$model);
			$attributes = TAMPLATES::get_tamplate_attributes($tamplate_id);
			foreach ($attributes as  $attribute) {
				$tamplate_date[$attribute] = $this->input->post($attribute);
			}
			$this->$model->save($tamplate_date);
		}else if($tamplate_id == TAMPLATES::SERVICES){
			$this->load->model('data_sources/services_tamplate');
			$this->services_tamplate->save($tamplate_date);
		}
		$this -> db -> trans_complete();
		if ($this -> db -> trans_status() === FALSE) {
			$this -> db -> trans_rollback();
			unlink(ADS_IMAGES_PATH.$main_image);
			return false;
		} else {
			$this -> db -> trans_commit();
			return $new_ad_id;
		}
   }

  public function serach_ads($query_string ,$lang , $category_id = null)
  {
 	 $this->db->select('ads.* ,
	                   categories.'.$lang.'_name as category_name ,
	                   c.'.$lang.'_name as parent_category_name ,
	                  ');
     $this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
	 $this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	 if(strlen($query_string) < 3){
	 	$this->db->like('ads.title', $query_string); 
		$this->db->or_like('ads.description', $query_string); 
	 }else{
	 	$this->db->where('MATCH(ads.title) AGAINST (' . $this->db->escape($query_string) . '  IN BOOLEAN MODE)', NULL, FALSE);
	    $this->db->or_where('MATCH(ads.description) AGAINST  (' . $this->db->escape($query_string) . ' IN BOOLEAN MODE)', NULL, FALSE);
	 }
     $this->db->where('status' , STATUS::ACCEPTED);
	 if($category_id){
	    $this->db->where("(categories.category_id = '$category_id' OR categories.parent_id = '$category_id' OR c.parent_id = '$category_id')");
	 }
	 return parent::get();
  }

  

 
  public function filter_ads()
  {
      
  }
 
  public function get_lists($lang)
  {
	 $this->load->model('data_sources/types');
	 $this->load->model('data_sources/educations');
	 $this->load->model('data_sources/schedules');
	 $this->load->model('sata_sources/locations');
	 $locations = $this->locations->get_all($lang);
	 $types = $this->types->get_all($lang);
	 $educations = $this->educations->get_all($lang);
	 $schedules = $this->schedules->get_all($lang);
	 $data = array('location' =>$locations , 'types' =>$types , 'educations' =>$educations , 'schedules'=>$schedules);
	 return $data;
  }


}



