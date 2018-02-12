<?php

class Categories extends MY_Model {
	protected $_table_name = 'categories';
	protected $_primary_key = 'category_id';
	protected $_order_by = 'parent_id , category_id';
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

}
