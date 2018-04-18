<?php

class Ads extends MY_Model {
	protected $_table_name = 'ads';
	protected $_primary_key = 'ads.ad_id';
	protected $_order_by = 'is_featured DESC , publish_date DESC'; //ASC
	public $rules = array();
	
    function __construct() {
		parent::__construct();
	}
	
	
	public function get_latest_ads($lang)
	 {
		$this->db->select('ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  categories.tamplate_id,
		                  c.'.$lang.'_name as parent_category_name ,
		                  locations.'.$lang.'_name as location_name ,
		                  cites.'.$lang.'_name as  city_name,
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
    	$this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left outer');
		$this->db->join('cites', 'ads.city_id = cites.city_id', 'left');
		$this->db->where('status' , STATUS::ACCEPTED);
        $this->db->where('(DATE_ADD(publish_date, INTERVAL show_period DAY) > NOW())');                              
        $q = parent::get(null , false, 12);
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
		                  show_periods.days
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
		$this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left outer');
		$this->db->join('cites', 'ads.city_id = cites.city_id', 'left');
		$this->db->join('show_periods', 'ads.show_period = show_periods.show_period_id', 'left outer');
		$this->db->where('status' , STATUS::ACCEPTED);
    	$this->db->where('(DATE_ADD(publish_date, INTERVAL show_period DAY) > NOW())');   
		$this->db->where("(categories.category_id = '$main_category_id' OR categories.parent_id = '$main_category_id' OR c.parent_id = '$main_category_id')");
		$q = parent::get();
		return $q;
	 }
    
	public function get_ad_details($ad_id , $lang , $tamplate_id = TAMPLATES::BASIC , $user_id = null)
	{
	    $this->db->select('ads.* ,
		                   categories.'.$lang.'_name as category_name ,
		                   c.'.$lang.'_name as parent_category_name ,
		                   categories.tamplate_id,
		                   users.user_id as seller_id,
		                   users.name as seller_name,
		                   users.phone as seller_phone,
		                   users.whatsup_number,
		                   locations.'.$lang.'_name as location_name ,
		                   cites.'.$lang.'_name as  city_name,
		                   show_periods.days,
						   DATE_ADD(publish_date, INTERVAL days DAY) as expiry_date,
	                       timestampdiff(DAY,now(),(DATE_ADD(publish_date, INTERVAL days DAY))) as expired_after,
		                  ', false);
       	$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
		$this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
		$this->db->join('users' , 'ads.user_id = users.user_id', 'left');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left outer');
		$this->db->join('cites', 'ads.city_id = cites.city_id', 'left');
		$this->db->join('show_periods', 'ads.show_period = show_periods.show_period_id', 'left outer');
		if($tamplate_id != TAMPLATES::BASIC){
			$tamplate_name = TAMPLATES::get_tamplate_name($tamplate_id);
			$this->db->select('tamplate.*');
			$this->db->join($tamplate_name.'_tamplate as tamplate', 'ads.ad_id = tamplate.ad_id', 'left outer');
		    $tamplate_secondry_tamplate = TAMPLATES::get_tamplate_secondry_tables($tamplate_id);
		    if($tamplate_secondry_tamplate){
		       foreach ($tamplate_secondry_tamplate as $table) {
				  $this->db->select($table.'s.'.$lang.'_name as '.$table.'_name');
				  $this->db->join($table.'s' , $table.'s.'.$table.'_id = tamplate.'.$table.'_id','left' );
			   }
		    }
		}
		$q = parent::get($ad_id);
		$q->images = $this->get_ad_images($ad_id);
		if($user_id != null){
			$this->load->model('data_sources/user_favorite_ads');
			$q->is_favorite =  $this->user_favorite_ads->check_favorite($user_id , $ad_id);
		}
		return $q;
	}

    public function get_info($ad_id ,$lang)
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
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left outer');
		$this->db->join('cites', 'ads.city_id = cites.city_id', 'left');
		return parent::get($ad_id , true);
    }
	
	public function get_ad_images($ad_id)
	{
	   $this->load->model('data_sources/ad_images');
	   return $this->ad_images->get_by(array('ad_id'=>$ad_id));
	}
	
   public function create_an_ad($basic_data,$main_image, $second_images , $tamplate_id = TAMPLATES::BASIC)
    {
   	 	$this -> db -> trans_start();
		// save basic info
		$new_ad_id = $this->save($basic_data);
		// save tamplate info 
		$tamplate_date = array();
		$tamplate_date['ad_id'] = $new_ad_id;
		$tamplate_name = 'basic';
		if($tamplate_id !=  TAMPLATES::BASIC){
			$tamplate_name = TAMPLATES::get_tamplate_name($tamplate_id);
			$model = $tamplate_name.'_tamplate';
			$this->load->model('data_sources/'.$model);
			$attributes = TAMPLATES::get_tamplate_attributes($tamplate_id);
			foreach ($attributes as  $attribute) {
			  if($this->input->post($attribute) != null && $this->input->post($attribute)!=''){
			  		$tamplate_date[$attribute] = $this->input->post($attribute);
			  }
			}
		    // save tamplate info
			$this->$model->save($tamplate_date);
		}
	    // save ad main image according to tamplate
	    $ad_main_image = null;
	    if($main_image != null){
	    	$ad_main_image = $main_image;
	    }else{
	    	//$ad_main_image = ADS_IMAGES_PATH.'default/'.$tamplate_name.'.png';
	    }
		$updated_ad_id = $this->save(array('main_image'=>$ad_main_image) , $new_ad_id);
        // save ad images 
        if($second_images!= null && is_array($second_images)){
        	$this->load->model('data_sources/ad_images');
        	foreach ($second_images as $image) {
				$data = array('ad_id'=>$new_ad_id , 'image'=>$image);
				$this->ad_images->save($data);
			}
        }
        
		//delete unwanted images 
		if($this->input->post('deleted_images')){
	   	  $deleted_images =  json_decode($this -> input -> post('deleted_images'), true);
		  $this->delete_images($deleted_images);
	    }
        
		//delete unwanted vedios
	    if($this->input->post('deleted_videos')){
	   	  $deleted_vedios =  json_decode($this -> input -> post('deleted_videos'), true);
		  $this->delete_videos($deleted_vedios);
	    }
		
		$this -> db -> trans_complete();
		if ($this -> db -> trans_status() === FALSE) {
			if($main_image != null){
				unlink($main_image);
			}
			if($second_images != null){
				foreach ($second_images as $image) {
				  unlink($image);
				}
			}
			$this -> db -> trans_rollback();
			return false;
		} else {
			$this -> db -> trans_commit();
			return $new_ad_id;
		}
  }

 
 
  public function edit($ad_id , $category_id)
  {
  	   $this->load->model('data_sources/categories');
  	   $this -> db -> trans_start();
	   $data = array();
	   if($this->input->post('city_id')){
	   	  $data['city_id'] = $this->input->post('city_id');
	   }
	   if($this->input->post('location_id') != null){
	   	 if(trim($this->input->post('location_id')) == -1){
	   	 	$data['location_id'] = NULL;
	   	 }else{
	   	 	$data['location_id'] = $this->input->post('location_id');
	   	 } 
	   }
	   if($this->input->post('price')){
	   	  $data['price'] = $this->input->post('price');
	   }
	   if($this->input->post('is_negotiable') != NULL){
	   	  $data['is_negotiable'] = $this->input->post('is_negotiable');
	   }
	   if($this->input->post('is_featured') != NULL){
	   	  $data['is_featured'] = $this->input->post('is_featured');
	   }
	   if($this->input->post('title') != NULL){
	   	  $data['title'] = $this->input->post('title');
	   }
	   if($this->input->post('description')!= NULL){
	   	 if(trim($this->input->post('description')) == -1){
	   	 	 $data['description'] = Null;
	   	 }else{
	   	 	 $data['description'] = $this->input->post('description');
	   	 }
	   }
	   if ($this->input->post('main_image')!= null) {
		  $data['main_image'] = $this->input->post('main_image');
	   }
	   
	   if ($this->input->post('main_video')!= null) {
	   	if(trim($this->input->post('description')) == -1){
	   		$data['main_video'] = NULL;
	   	}else{
	   	    $data['main_video'] = $this->input->post('main_video');
	   	}
	   }
	   
	   // delete unwanted images
	   if($this->input->post('deleted_images')){
	   	  $deleted_images =  json_decode($this -> input -> post('deleted_images'), true);
		  $this->db->where_in('image'  , $deleted_images);
		  $this->db->where('ad_id'  , $ad_id);
		  $this->db->delete('ad_images');
		  $this->delete_images($deleted_images);
	   }
	   
	   	//delete unwanted vedios
	   if($this->input->post('deleted_videos')){
	   	  $deleted_vedios =  json_decode($this -> input -> post('deleted_videos'), true);
		  $this->delete_videos($deleted_vedios);
	   }
	   
	   //add new second images
	   if($this->input->post('images')){
	   	  $this->load->model('data_sources/ad_images');
	   	   $ads_images_paths = json_decode($this -> input -> post('images'), true);
		   if($ads_images_paths!= null && is_array($ads_images_paths)){
        	$this->load->model('data_sources/ad_images');
			// delete all ad's images. 
			$this->ad_images->delete_ad_images($ad_id);
        	foreach ($ads_images_paths as $image) {
				$data_images = array('ad_id'=>$ad_id , 'image'=>$image);
				$this->ad_images->save($data_images);
			}
          }
	   }
	   $data['status'] = STATUS::PENDING;
	   $edited_ad_id = parent::save($data  , $ad_id);
	   
	   //save tamplate edits
	   $category_info = $this->categories->get($category_id);
	   $tamplate_id = $category_info->tamplate_id;
	   $tamplate_name = 'basic';
	   if($tamplate_id !=  TAMPLATES::BASIC){
			$tamplate_name = TAMPLATES::get_tamplate_name($tamplate_id);
			$model = $tamplate_name.'_tamplate';
			$this->load->model('data_sources/'.$model);
			$edit_result = $this->$model->edit($ad_id);
			if(!$edit_result){
			   $this -> db -> trans_rollback();
			   return false;
			}
	   }
	   $this -> db -> trans_complete();
	   if ($this -> db -> trans_status() === FALSE) {
			$this -> db -> trans_rollback();
			return false;
	   } else {
			$this -> db -> trans_commit();
			return $edited_ad_id;
	   }
  }

  public function delete_images($images_array)
  {
  	  $ok = true;
      foreach ($images_array as $image_path) {
      	  $ok = unlink(PUBPATH.$image_path);	
      }
	  return $ok;
  }
  
  public function delete_videos($videos_array)
  {
  	  $ok = true;
      foreach ($videos_array as $path) {
      	  $ok = unlink(PUBPATH.$path);	
      }
	  return $ok;
  }
 
  public function serach_with_filter($lang , $query_string = null , $category_id = null)
   {
	 //filter
	 if($category_id != null){
	 	$this->load->model('data_sources/categories');
	 	$category_info = $this->categories->get($category_id);
		if($category_info->tamplate_id != TAMPLATES::BASIC){
		  	$tamplate_name = TAMPLATES::get_tamplate_name($category_info->tamplate_id);
			$model = $tamplate_name.'_tamplate';
			$this->load->model('data_sources/'.$model);
		    $this->$model->filter();
			$this->db->select('ads.* ,
			                   c1.'.$lang.'_name as category_name ,
			                   c1.tamplate_id,
		                       c.'.$lang.'_name as parent_category_name ,
		                       users.name as seller_name,
		                       locations.'.$lang.'_name as location_name ,
		                       cites.'.$lang.'_name as  city_name,
					           tamplate.*
		                      ');
			$this->db->join($tamplate_name.'_tamplate as tamplate', 'ads.ad_id = tamplate.ad_id', 'left outer');	
		}else{
			$this->db->select('ads.* ,
			                   c1.'.$lang.'_name as category_name ,
			                   c1.tamplate_id,
		                       c.'.$lang.'_name as parent_category_name ,
		                       users.name as seller_name,
		                       locations.'.$lang.'_name as location_name ,
		                       cites.'.$lang.'_name as  city_name,
		                      ');
		}
		//$this->db->where('ads.category_id' , $category_id);
		$this->db->where("(c1.category_id = '$category_id' OR c1.parent_id = '$category_id' OR c.parent_id = '$category_id')");
	 }else{
		$this->db->select('ads.* ,
		                   c1.'.$lang.'_name as category_name ,
		                   c1.tamplate_id,
	                       c.'.$lang.'_name as parent_category_name ,
	                       users.name as seller_name,
	                       locations.'.$lang.'_name as location_name ,
		                   cites.'.$lang.'_name as  city_name,
                          ');
	 }
	 $this->db->where('status' , STATUS::ACCEPTED );
     $this->db->where('(DATE_ADD(publish_date, INTERVAL show_period DAY) > NOW())');   
	//serach
	 if($query_string != null){
	     if(strlen($query_string) < 3){
		 	$this->db->like('ads.title', $query_string); 
			$this->db->or_like('ads.description', $query_string); 
	   	}else{
		 	$this->db->where("(MATCH(ads.title) AGAINST (\"<" . $this->db->escape($query_string) . "*\"  IN BOOLEAN MODE)
		 	                   OR MATCH(ads.description) AGAINST  (\"<" . $this->db->escape($query_string) . "*\"  IN BOOLEAN MODE))", NULL, FALSE);
		}
	 }
	 $this->db->join('categories as c1' , 'ads.category_id = c1.category_id' , 'left');
	 $this->db->join('categories as c' , 'c.category_id = c1.parent_id' , 'left outer');
	 $this->db->join('users' , 'ads.user_id = users.user_id', 'left');
	 $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left outer');
	 $this->db->join('cites', 'ads.city_id = cites.city_id', 'left');
	// users.name as seller_name
	 if($this->input->get('location_id')){
	 	$this->db->where('ads.location_id' , $this->input->get('location_id'));
	 }
	 if($this->input->get('city_id')){
	 	$this->db->where('ads.city_id' , $this->input->get('city_id'));
	 }
	 if($this->input->get('price_max') && $this->input->get('price_max') != ''){
	   	 $this->db->where('price <= ' , $this->input->get('price_max'));
	 }
	 if($this->input->get('price_min') && $this->input->get('price_min')!=''){	
		$this->db->where('price >= ' , $this->input->get('price_min')); 
	 }
     return parent::get(); 
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

   public function check_category_ads_existence($category_id)
	{
	   $ads = parent::get_by(array('category_id' => $category_id), false ,1);
	   if($ads != null){
	   	 return true;
	   }else{
	   	 return false;
	   }
	}
	
   public function get_all_ads_with_details($lang)
	{
	    $this->db->select('ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  categories.tamplate_id,
		                  c.'.$lang.'_name as parent_category_name ,
		                  locations.'.$lang.'_name as location_name ,
		                  cites.'.$lang.'_name as  city_name,
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
    	$this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	    $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
		$this->db->join('cites', 'ads.city_id = cites.city_id', 'left');
		if($this->input->get('status')){
			$this->db->where('status' , $this->input->get('status'));
		}
		return parent::get();
	}
	
  public function get_user_ads($user_id , $lang)
	{
	  $this->db->select('ads.* ,
	                  categories.'.$lang.'_name as category_name ,
	                  c.'.$lang.'_name as parent_category_name ,
	                  categories.tamplate_id,
	                  locations.'.$lang.'_name as location_name ,
	                  cites.'.$lang.'_name as  city_name,
	                  DATE_ADD(publish_date, INTERVAL days DAY) as expiry_date,
	                  timestampdiff(DAY,now(),(DATE_ADD(publish_date, INTERVAL days DAY))) as expired_after,
	                 ' , false);
	  $this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
	  $this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
	  $this->db->join('show_periods', 'show_periods.show_period_id = ads.show_period', 'left');
	  $this->db->join('locations' , 'ads.location_id = locations.location_id' , 'left');
	  $this->db->join('cites', 'ads.city_id = cites.city_id', 'left');
	  $this->db->where('ads.user_id' , $user_id);
	  $this->db->where('ads.status != ' , STATUS::DELETED);
	  return parent::get();
	}

  public function get_seller_id($ad_id)
    {
       $this->db->select('user_id');
	   $q = parent::get($ad_id);
	   if($q){
	   	 return $q->user_id;
	   }else{
	   	 return null;
	   }
    }

  public function get_pending_ads($lang)
   {
	   $this->db->select('ads.* ,
		                  categories.'.$lang.'_name as category_name ,
		                  categories.tamplate_id,
		                  c.'.$lang.'_name as parent_category_name ,
		                  users.name as seller_name
		                 ');
		$this->db->join('categories' , 'ads.category_id = categories.category_id' , 'left');
		$this->db->join('categories as c' , 'c.category_id = categories.parent_id' , 'left outer');
		$this->db->join('users' , 'users.user_id = ads.user_id' , 'left');
	    $this->db->where('status' , STATUS::PENDING);
		return parent::get();
   }

 public function get_pending_ads_counts()
   { 
   	  $this->db->select('count(ad_id) as pending_count');
	  $this->db->where('status' , STATUS::PENDING);
	  $q = parent::get();
	  return $q[0]->pending_count;
   }
   


}



