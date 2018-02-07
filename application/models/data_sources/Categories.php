<?php

class Categories extends MY_Model {
	protected $_table_name = 'categories';
	protected $_primary_key = 'category_id';
	protected $_order_by = 'parent_id , category_id';
	public $rules = array();

	public function get_nested($lang) {
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id  , web_image, mobile_image , tamplate_name , description');
		$this -> db -> order_by($this -> _order_by);
		$categories = $this -> db -> get('categories') -> result_array();

		$array = array();
		foreach ($categories as $category) {
			if (!$category['parent_id']) {
				// This category has no parent
				$array[$category['category_id']] = $category;
			} else {
				// This is a child category
				$array[$category['parent_id']]['children'][] = $category;
			}
		}
		$result_array = array();
		foreach ($array as $key => $value) {
			$result_array[] = $value;
		}
		return $result_array;
	}
	
	public function get_main_categories($lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id , web_image, mobile_image , tamplate_name , description');
		return parent::get_by(array('parent_id'=>0));
	}
	
	public function get_category_subcategories($category_id , $lang)
	{
		$this->db->select('categories.'.$lang.'_name as category_name , category_id , parent_id , web_image, mobile_image , tamplate_name , description');
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
