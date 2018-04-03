<?php

class Categories extends MY_Model {
	protected $_table_name = 'categories';
	protected $_primary_key = 'categories.category_id';
	protected $_order_by = 'parent_id ,categories.is_other, category_id';
	public $rules = array();

	
	public function get_nested($lang)
	{
	    $this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id  , web_image, mobile_image , tamplate_id , description');
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
       $this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id  , web_image, mobile_image , tamplate_id , description');
	   return parent::get();
    }
	
	public function get_main_categories($lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id , web_image, mobile_image , tamplate_id , description');
		return parent::get_by(array('parent_id'=>0));
	}
	
	public function get_category_subcategories($category_id , $lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id , web_image, mobile_image , tamplate_id , description');
		$q = parent::get_by(array('parent_id'=>$category_id));
		$array = array();
		if($q != null){
			foreach ($q as $row) {
				$array[] = $row;
			}
		}
		return $array;
	}
	
    public function get_subcats_with_parents($category_id , $lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name ,
		                  p.'.$lang.'_name as parent_name , 
		                  categories.category_id , categories.parent_id , categories.web_image, categories.mobile_image ,
		                  categories.tamplate_id , categories.description');
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
		  	$has_ads = $this->ads->check_category_ads_existence($parent_id);
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
			    'description' =>$parent_info->description
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
	    'parent_id' =>$parent_id
	   );
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
}
