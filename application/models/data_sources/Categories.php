<?php

class Categories extends MY_Model {
	protected $_table_name = 'categories';
	protected $_primary_key = 'categories.category_id';
	protected $_order_by = 'categories.parent_id ,	categories.queue ,categories.is_other, categories.category_id';
	public $rules = array();

	
	public function get_nested($lang)
    {
        $this->db->select('categories.'.$lang.'_name as category_name , description,  categories.category_id , parent_id  , web_image, mobile_image , tamplate_id  , hidden_fields , count(ads_temp.ad_id) as ads_count');
        $this->db->join("(SELECT ads.ad_id , show_periods.days , ads.category_id , status , publish_date
                         FROM ads
                         left join show_periods on ads.show_period = show_periods.show_period_id) as ads_temp" ,
                        "ads_temp.category_id = categories.category_id AND ads_temp.status = 2 AND (DATE_ADD(publish_date, INTERVAL days DAY) > NOW())" ,
                        "left outer" , false);
        $this->db->where('is_active', 1); 
        $this->db->group_by('categories.category_id');
        $this->db->where('is_active', 1);
        $this -> db -> order_by($this -> _order_by);
        $categories = $this -> db -> get('categories') -> result_array();
        // create an array to hold the references
        $refs = array();
    
       // create an array to hold the root parents list
        $list = array();
    
       // loop over the results
        foreach($categories as $data)
        {
           // Assign by reference (assign the children)
           $thisref = &$refs[ $data['category_id'] ];

           // add the the menu parent
           $thisref['category_id'] = $data['category_id'];
           $thisref['category_name'] = $data['category_name'];
           $thisref['parent_id']=$data['parent_id'];
           $thisref['web_image']=$data['web_image'];
           $thisref['mobile_image']=$data['mobile_image'];
           $thisref['tamplate_id']= $data['tamplate_id'];
           $thisref['description']=$data['description'];
           $thisref['hidden_fields']=$data['hidden_fields'];
           $thisref['ads_count'] = $data['ads_count'];
           // if there is no parent id
           if ($data['parent_id']== 0)
           {
              $list[ $data['category_id'] ] = &$thisref; 
           }
           else
           {
              $refs[ $data['parent_id'] ]['children'][ ] = &$thisref;
           }
        
       }
        $result_array = array();
        foreach ($list as $key => $value) {
            $result_array[] = $value;
        }
        return $result_array;
    }

  public function get_all($lang)
    {
       $this->db->select('categories.'.$lang.'_name as category_name , categories.category_id , parent_id  , web_image, mobile_image , tamplate_id  , hidden_fields , count(ads_temp.ad_id) as ads_count');
	   $this->db->join("(SELECT ads.ad_id , show_periods.days , ads.category_id , status , publish_date
					     FROM ads
					     left join show_periods on ads.show_period = show_periods.show_period_id) as ads_temp" ,
					    "ads_temp.category_id = categories.category_id AND ads_temp.status = 2 AND (DATE_ADD(publish_date, INTERVAL days DAY) > NOW())" ,
				        "left outer" , false);
	   $this->db->where('is_active', 1); 
	   $this->db->group_by('categories.category_id');
	   return parent::get();
    }
	
   public function get_main_categories($lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id , web_image, mobile_image , tamplate_id , description , hidden_fields');
		$this->db->where('is_active' , 1);
		return parent::get_by(array('parent_id'=>0));
	}

   public function get_main_categories_for_manage($lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id , web_image, mobile_image , tamplate_id , description , hidden_fields');
		return parent::get_by(array('parent_id'=>0));
	}
	
   public function get_category_subcategories($category_id , $lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id , web_image, mobile_image , tamplate_id , description , hidden_fields');
		$q = parent::get_by(array('parent_id'=>$category_id , 'is_active' =>1));
		$array = array();
		if($q != null){
			foreach ($q as $row) {
				$array[] = $row;
			}
		}
		return $array;
	}
	
	// for manage
   public function get_subcats_with_parents($category_id , $lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name ,
		                  p.'.$lang.'_name as parent_name , 
		                  categories.category_id , categories.parent_id , categories.web_image, categories.mobile_image ,
		                  categories.tamplate_id , categories.description , categories.hidden_fields');
		$this->db->join('categories as p' , 'categories.parent_id = p.category_id' , 'outer left');
		$q = parent::get_by(array('categories.parent_id'=>$category_id));
		$array = array();
		if($q != null){
			foreach ($q as $row) {
				$array[] = $row;
			}
		}
		return $array;
	}
	
   public function create_category()
	{
	    $this -> db -> trans_start();
		$this->load->model('data_sources/ads');
		//check if it is main category
	    $parent_id = $this->input->post('parent_id');
		if($parent_id != 0){ // not main category
		  	// then check if it's parent have any ads 
		  	$has_ads = $this->ads->check_category_ads_existence_for_add($parent_id);
			if($has_ads){
			   // get the parent category info	
			   $parent_info = $this->get($parent_id);
			   // create new category with the same name and the same info
			   $data = array(
			    'en_name' =>$parent_info->en_name,
			    'ar_name' =>$parent_info->ar_name, 
			    'tamplate_id'=>$parent_info->tamplate_id , 
			    'tamplate_name' => TAMPLATES::get_tamplate_name($parent_info->tamplate_id),
			    'web_image' =>$parent_info->web_image, 
			    'mobile_image' =>$parent_info->mobile_image,
			    'description' =>$parent_info->description,
			    'queue' => $this->max_queue($parent_id)
			   );
			   
			   $new_parent = $this->save($data);
			   //change the current name to other and change the parent id to be the newly created category 
			   $this->save(array('en_name'=>'Others', 'ar_name'=>'أخرى', 'parent_id'=>$new_parent), $parent_id);
			   // and then create the wanted subcategory with parent id of the new parent created
			   $parent_id = $new_parent;
			}
		}
	   $category_data = array(
	    'en_name'=> $this->input->post('en_name'),
	    'ar_name'=>$this->input->post('ar_name'),
	    'tamplate_id' => $this->input->post('tamplate_id'),
	    'tamplate_name' =>TAMPLATES::get_tamplate_name($this->input->post('tamplate_id')),
	    'description'=>$this->input->post('description'),
	    'hidden_fields' => $this->input->post('hidden_fields'),
	    'parent_id' =>$parent_id,
	    'queue' => $this->max_queue($parent_id)
	   );
	   if($parent_id == 0){
	   	  $template_name = TAMPLATES::get_tamplate_name($this->input->post('tamplate_id'));
	   	  $category_data['web_image'] = CATEGORIES_IMAGES_WEB_PATH.$template_name.'.png';
		  $category_data['mobile_image'] = CATEGORIES_IMAGES_MOBILE_PATH.$template_name.'.png';;
	   }else{
	   	  $parent_info = parent::get($parent_id);
	   	  $category_data['is_active'] = $parent_info->is_active;
	   }
	   // set the new category order. 
	   $new_subcategory = $this->save($category_data);
       $this -> db -> trans_complete();
		if ($this -> db -> trans_status() === FALSE) {
			$this -> db -> trans_rollback();
			return false;
		} else {
			$this -> db -> trans_commit();
			return $new_subcategory;
	    }
   }


  public function has_child($cat_id)
   {
      $res = $this->get_by(array('parent_id' => $cat_id) , true ,1);
	  if($res != null){
	  	 return 1;
	  }else{
	  	return 0;
	  }
   }
   
  public function get_nested_ids($category_id )
   {
       $this->db->select('categories.category_id as category_id , sun_cat.category_id as sun_id, sun_sun_cat.category_id as sun_sun_id');
	   $this->db->join('categories as sun_cat' ,'sun_cat.parent_id = categories.category_id' , 'left outer');
	   $this->db->join('categories as sun_sun_cat' ,'sun_sun_cat.parent_id = sun_cat.category_id' , 'left outer');
	   $this->db->where('categories.category_id' , $category_id);
	   $q =  $this->db->get('categories')->result();
	   $ids = array();
	   $ids[$category_id] = $category_id;
	   foreach ($q as $row) {
	   	   if($row->sun_id != null){
	   	   	  $ids[$row->sun_id] = $row->sun_id;
	   	   }
	   	   if($row->sun_sun_id){
	   	   	  $ids[$row->sun_sun_id] = $row->sun_sun_id;
	   	   }
	   }
       return $ids;
   }
   
  public function diactivate($ids)
   {
	  $this->db->set('is_active' , 0);
	  $this->db->where_in('category_id' , $ids);
	  return $this->db->update('categories');
   }
   
  public function activate($ids)
   {
	  $this->db->set('is_active' , 1);
	  $this->db->where_in('category_id' , $ids);
	  return $this->db->update('categories');
   }
   
  public function delete_cats($ids)
   {
	  $this->db->where_in('category_id' , $ids);
	  return $this->db->delete('categories');
   }
   
  public function update_queue($parent_id , $cats_queue)
   {
        $queue = 0;
		$result;
	    foreach ($cats_queue as $cat_id) {
	        $queue++;
	        $result = parent::save(array('queue' => $queue) , $cat_id);
	    }
        return $result;
   }
   
  public function get_childs_only($lang)
   {
       $this->db->select('categories.'.$lang.'_name as category_name ,
		                  parent_category.'.$lang.'_name as parent_name , 
		                  categories.category_id , categories.parent_id , categories.web_image, categories.mobile_image ,
		                  categories.tamplate_id , categories.description');
	   $this->db-> join('categories as child_category' , 'child_category.parent_id = categories.category_id' , 'left outer');
	   $this->db-> join('categories as parent_category' , 'categories.parent_id = parent_category.category_id' , 'left outer');
	   $this->db->where('child_category.category_id' , null );
	   $this->db->where_in('categories.tamplate_id' , array(TAMPLATES::VEHICLES , TAMPLATES::MOBILES ,TAMPLATES::ELECTRONICS));
	   $this->db->where('categories.is_active' , 1);
	   return parent::get();
   }
   
  public function get_category_name($cat_id , $lang)
   {
       $this->db->select('categories.'.$lang.'_name as category_name ,
		                  parent_category.'.$lang.'_name as parent_name , 
		                ');
	   $this->db-> join('categories as parent_category' , 'categories.parent_id = parent_category.category_id' , 'left outer');
	   $this->db->where('categories.category_id' , $cat_id);
	   return parent::get(null , true);
   }

  public function max_queue($parent_id) {
        $res = $this->db->select('max(queue) as queue')
                        ->from('categories')
                        ->where('parent_id', $parent_id)
                        ->where('is_deleted', 0)
                        ->get()->row();
	    if($res != null){
			return $res->queue + 1;
		}else{
		    return 0;	
		}
     //   return $res ? $res->queue : 0;
   }
   
  public function get_counts_by_category()
   {
      // $this->db->select('count(ads.ad_id) as ads_count  , categories.category_id' );
	  // $this->db->join('ads' , 'ads.category_id = categories.category_id' , 'left outer');
	  // $this->db->group_by('categories.category_id');
	  // $this->db->order_by('categories.category_id');
	  // return parent::get();
	  $this->db->select('count(ads.ad_id) as ad_count , categories.category_id as category_id , sun_cat.category_id as sun_id, sun_sun_cat.category_id as sun_sun_id');
	  $this->db->join('categories as sun_cat' ,'sun_cat.parent_id = categories.category_id' , 'left outer');
	  $this->db->join('categories as sun_sun_cat' ,'sun_sun_cat.parent_id = sun_cat.category_id' , 'left outer');
	  
	  
	   
   }
}
