<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

require(APPPATH . '/libraries/emojis/RulesetInterface.php');
require(APPPATH . '/libraries/emojis/ClientInterface.php');
require(APPPATH . '/libraries/emojis/Client.php');
require(APPPATH . '/libraries/emojis/Ruleset.php');
require(APPPATH . '/libraries/emojis/Emojione.php');

class Items_control extends REST_Controller {

	function __construct() {
		parent::__construct();
		$this->load->model('data_sources/ads');
		$this->data['lang']=  $this->response->lang;
		$this->data['version'] = $this->response->version;
		$this->data['city'] = $this->response->city;
		if($this->response->os == OS::IOS){
			$this->data['os'] = '_os'; 
		}else{
			$this->data['os']= '';
		}
	}
	
	public function get_latest_items_get()
	{
    	$ads_list  = $this->ads->get_latest_ads($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}
	
	public function get_items_by_main_category_get()
	{
		$main_category_id = $this->input->get('category_id');
		// get ads
		$ads_list = $this->ads->get_ads_by_category($main_category_id , $this->data['lang']);
		$data['ads'] = $ads_list;
		//get commrecial ads.
		if($this->input->get('page_num') && $this->input->get('page_num') == 1){// if calling the services for the firt page then get the commercials
			$this->load->model('data_sources/commercial_ads');
			$commercials = $this->commercial_ads->get_commercial_ads($main_category_id,$this->data['lang'],$this->data['city'], $this->input->get('from_web'));
			$data['commercials'] = $commercials;
		}
        // for old versions
        if($this->data['version'] == '1.0'){
        	 $data = $ads_list;
        }
		$this->response(array('status' => true, 'data' => $data, 'message' => ''));
	}
	
	public function get_pending_items_get()
	{
		$ads_list = $this->ads->get_pending_ads($this->data['lang']);
		$this->response(array('status' => true, 'data' =>$ads_list, 'message' => ''));
	}
	
	public function get_pending_count_get()
	{
		$count = $this->ads->get_pending_ads_counts();
		$this->response(array('status' => true, 'data' =>$count, 'message' => ''));
	}
	
	public function get_item_details_get()
    {
        $user_id = null;
        if($this->session->userdata('PHP_AUTH_USER')){
            $user_id = $this->session->userdata('LOGIN_USER_ID');
        }
        else if($this->response->is_auth){
            $user_id = $this->response->is_auth;
        }
        $ad_id = $this->input->get('ad_id');
        $tamplate_id = $this->input->get('template_id');
		//$method = 'get_ad_details'.$this->data['os'];
        $deatils = $this->ads->get_ad_details($ad_id , $this->data['lang'] , $tamplate_id , $user_id);
		if($deatils){
			// increent the number of views for this ad.
			if(isset($user_id)&& $user_id != null){
				if($deatils->user_id != $user_id){
				   $this->ads->increment_views($ad_id);	
				}
			}else{
			   $this->ads->increment_views($ad_id);	
			}
			if(floatval($this->data['version']) >= 1.2){
        	   $deatils->views_num = null; 
            }
			$this->response(array('status' => true, 'data' =>$deatils, 'message' => ''));
		}else{
			$this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		}
    }

    public function post_new_item_post()
    {
        $this->load->model('data_sources/categories');
		$this -> form_validation -> set_rules('category_id', 'lang:category_id', 'required');
		$this -> form_validation -> set_rules('city_id', 'lang:city', 'required');
		$this -> form_validation -> set_rules('show_period', 'lang:show_period', 'required');
		$this -> form_validation -> set_rules('price', 'lang:price', 'required');
		$this -> form_validation -> set_rules('title', 'lang:title', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		}else {
		   $basic_data = array(
		     'user_id' => $this->current_user->user_id,
		     'city_id' => $this->input->post('city_id'),
		     'show_period' => $this->input->post('show_period'),
		     'price' => $this->input->post('price'),
		     'title' => $this->input->post('title'),
		     'category_id' => $this->input->post('category_id'),
		     'is_featured' => $this->input->post('is_featured'),
		     'is_negotiable' => $this->input->post('is_negotiable'),
		     'ad_visible_phone' => $this->input->post('ad_visible_phone')
		   );
		   if($this->input->post('main_video') && $this->input->post('main_video')!=''){
		   	  $basic_data['main_video'] = $this->input->post('main_video');
		   }
		   if($this->input->post('location_id') && $this->input->post('location_id')!=''){
		   	  $basic_data['location_id'] = $this->input->post('location_id');
		   }
		   if($this->input->post('description') && $this->input->post('description')!=''){
		   	  $basic_data['description'] = $this->input->post('description');
		   }
		   $main_image = null;
		   if ($this->input->post('main_image') && $this->input->post('main_image') != '') {
			  $main_image = $this->input->post('main_image');
		   }
		   $ads_images_paths = array();
		   if($this->input->post('images')){
		   	   $ads_images_paths = json_decode($this -> input -> post('images'), true);
		   }
		   $category_info = $this->categories->get($this->input->post('category_id'));
		   $tamplate_id = $category_info->tamplate_id;
		   $save_result = $this->ads->create_an_ad($basic_data, $main_image , $ads_images_paths , $tamplate_id);
		   if($save_result != false){
		   	 // send an email 
		   	 $this->ads->send_pending_email($save_result);
		   	 $this -> response(array('status' => true, 'data' => $save_result, 'message' => $this->lang->line('sucess')));
		   }else{
		   	 $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		   }
		}
    }

   public function edit_post()
    {
      if(!$this->input->post('ad_id')){
      	 throw new Parent_Exception('ad id is required');
      }else{
      	$ad_id = $this->input->post('ad_id');
		$ad_info = $this->ads->get($ad_id);
		if($ad_info->user_id != $this->current_user->user_id){
			 throw new Parent_Exception('you do not have the permission to edit this ad');
		}else{
		   $category_id  = $ad_info->category_id;
		   $edit_result = $this->ads->edit($ad_id , $category_id );
		   if($edit_result){
		   	 // send an email
		   	  $this->ads->send_pending_email($edit_result , true);
		   	  $this -> response(array('status' => true, 'data' => $edit_result, 'message' => $this->lang->line('sucess')));
		   }else{
		   	  $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		   }
		}
      } 
    }

  public function item_images_upload_post()
   {
      $image_name = date('m-d-Y_hia').'-'.uniqid(rand()).'-'.$this->current_user->user_id;
      $image = upload_attachement($this, ADS_IMAGES_PATH , $image_name);
      if (isset($image['image'])) {
          $image_path =  ADS_IMAGES_PATH.$image['image']['upload_data']['file_name'];
          $this -> response(array('status' => true, 'data' => $image_path, 'message' => $this->lang->line('sucess')));
      }else{
           $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
      }
   }
	
  public function item_video_upload_post()
   {
   	  $vedio_name = date('m-d-Y_hia').'-'.$this->current_user->user_id;
	  $vedio = upload_attachement($this, ADS_VEDIO_PATH , $vedio_name, true); 
	  if (isset($vedio['video'])) {
          $vedio_path =  ADS_VEDIO_PATH.$vedio['video']['upload_data']['file_name'];
          $this -> response(array('status' => true, 'data' => $vedio_path, 'message' => $this->lang->line('sucess')));
      }else{
          $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
      }
   }
   
  public function delete_vedios_post()
   {
        $videos = $this -> input -> post('videos');
		if(!$videos){
			throw new Parent_Exception('You have to provide videos array');
		}
		$videos_array = json_decode($videos, true);
	    $deleted = $this->ads->delete_videos($videos_array);
		if($deleted){
		   $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));	
		}else{
		   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		}
   }	
	
  public function delete_images_post()
   {
		$images = $this -> input -> post('images');
		if(!$images){
			throw new Parent_Exception('You have to provide images array');
		}
		$images_array = json_decode($images, true);
	    $deleted = $this->ads->delete_images($images_array);
		if($deleted){
		   $this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));	
		}else{
		   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		}
   }

	
  public function search_get()
   {
		$query_string = $this->input->get('query');
		$category_id = $this->input->get('category_id');
		$data['ads'] = $this->ads->serach_with_filter( $this->data['lang']  , $query_string , $category_id);
		//save if no results are back for this search
		// if($data['ads'] == null){ // no data 
		// 	$user_id = $this->current_user->user_id;
		// 	$data_json = json_encode($this->input->post());
		// 	$no_res_data = array(
		// 	  'user_id' => $user_id , 
		// 	  'query' => $data_json
		//     );
		//     $res = $this->no_result_searches->save($no_res_data); 
		// }
		//get commrecial ads.
		if($this->input->get('page_num') && $this->input->get('page_num') == 1){// if calling the services for the firt page then get the commercials
			$this->load->model('data_sources/commercial_ads');
		    if($category_id){
		    	$commercials = $this->commercial_ads->get_commercial_ads($category_id,$this->data['lang'],$this->data['city'], $this->input->get('from_web'));
		    }else{
		    	$commercials = $this->commercial_ads->get_commercial_ads(0 ,$this->data['lang'],$this->data['city'], $this->input->get('from_web'));
		    }
			$data['commercials'] = $commercials;
		}
		// for old versions
        if($this->data['version'] == '1.0'){
        	 $data = $data['ads'];
        }
		$this->response(array('status' => true, 'data' =>$data, 'message' => $this->lang->line('sucess')));
   }


  public function notify_post(){
  	    $this->load->model('data_sources/no_result_searches');
		$user_id = $this->current_user->user_id;
		$data_json = json_encode($this->input->post());
		$data = array(
		  'is_notifiable' => 1 , 
		);
		$res = $this->no_result_searches->save($data); 
		$this->response(array('status' => true, 'data' => $res, "message" => $this->lang->line('sucess')));
    }
	
  public function get_bookmark_search_get()
    {
		$this->load->model('data_sources/user_search_bookmarks');
		$bookmark_id = $this->input->get('user_bookmark_id');
		$bookmark_info = $this->user_search_bookmarks->get($bookmark_id , true ,null , true);
		$ok = true;
		if($bookmark_info){
		   $filter_data = $bookmark_info->query;
		   if($filter_data){
		   	  $filter_data_array = json_decode($filter_data , true);
		   	  if($filter_data){
		   	  	  foreach ($filter_data_array as $key => $value) {
					$_GET[$key] = $value;
				  }
		   	  }else{
		   	  	 $ok = false;
		   	  }
		   }else{
		   	 $ok = false;
		   }
		}else{
		   $ok = false;
		}
		if($ok){
			$this->search_get();
		}else{
		  $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed'))); 
		}
	}
	
  public function get_data_lists_get()
	{
	     $this->load->model('data_sources/types');
		 $this->load->model('data_sources/educations');
		 $this->load->model('data_sources/schedules');
		 $this->load->model('data_sources/locations');
		 $this->load->model('data_sources/show_periods');
		 $this->load->model('data_sources/certificates');
		 $this->load->model('data_sources/property_states');
		 // $locations_method = 'get_all'.$this->data['os'];
		 // $nested_locations_method = 'get_cities_with_locations'.$this->data['os'];
		 $locations = $this->locations->get_all($this->data['lang']);
		 //$cities = $this->locations->get_cities($this->data['lang']);
		 $nested_locations = $this->locations->get_cities_with_locations($this->data['lang']);
		 $types = $this->types->get_all_by_tamplate($this->data['lang']);
		 $educations = $this->educations->get_all($this->data['lang']);
		 $schedules = $this->schedules->get_all($this->data['lang']);
		 $show_periods = $this->show_periods->get_all($this->data['lang']);
		 $certificates = $this->certificates->get_all($this->data['lang']);
		 $property_states = $this->property_states->get_all($this->data['lang']);
		 $data = array('location' =>$locations ,'nested_locations'=>$nested_locations, 'types' =>$types , 'educations' =>$educations , 'schedules'=>$schedules , 'show_periods'=>$show_periods , 'certificates'=>$certificates , 'states' => $property_states);
		 $this -> response(array('status' => true, 'data' => $data, 'message' => ''));
	}
	
  public function get_data_get()
	{
		$attrs = TAMPLATES::get_tamplate_attributes_array();
		$status = STATUS::get_list();
		$data = array('attrs'=>$attrs , 'status'=>$status);
		$this -> response(array('status' => true, 'data' => $data, 'message' => ''));
	}
	
  public function set_as_favorite_post()
   {
   	 if(!$this->input->post('ad_id')){
   	 	throw new Parent_Exception('ad id is required');
   	 }else{
	   	 $ad_id = $this->input->post('ad_id');
		 $user_id = $this->current_user->user_id;
		 $this->load->model('data_sources/user_favorite_ads');
		 $check = $this->user_favorite_ads->check_favorite($user_id , $ad_id);
		 $favorate_id = $ad_id;
		 if(!$check){
		 	$favorate_id = $this->user_favorite_ads->save(array('ad_id'=>$ad_id , 'user_id'=>$user_id));
		 }
		 if($favorate_id){
		 	$this -> response(array('status' => true, 'data' => $favorate_id, 'message' => $this->lang->line('sucess')));
		 }else{
		 	$this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		 }
	 }
   }

  public function remove_from_favorite_post()
   {
   	 if(!$this->input->post('ad_id')){
   	 	throw new Parent_Exception('ad id is required');
   	 }else{
   	 	 $ad_id = $this->input->post('ad_id');
		 $user_id = $this->current_user->user_id;
		 $this->load->model('data_sources/user_favorite_ads');
		 $deleted = $this->user_favorite_ads->delete_by_user($ad_id , $user_id);
		 if($deleted){
		 	$this -> response(array('status' => true, 'data' => '', 'message' => $this->lang->line('sucess')));
		 }else{
		 	$this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
		 }
   	  }
   }
   
   

  public function change_status_post()
	{
	  if(!($this->input->post('status') == STATUS::HIDDEN || $this->input->post('status') == STATUS::DELETED )){
	  	  throw new Parent_Exception($this->lang->line('change_status_warning'));
	  }else{
	   	 if(!$this->input->post('ad_id')){
	   	 	throw new Parent_Exception('ad_id is requierd');
	   	 }else{
	   	 	$ad_id = $this->ads->save(array('status'=>$this->input->post('status')) , $this->input->post('ad_id'));
	   	 	$this -> response(array('status' => true, 'data' => $ad_id, 'message' => $this->lang->line('sucess')));
	   	 }
 	  }
	}
	
  public function get_report_messages_get()
    {
	    $this->load->model('data_sources/report_messages');
	    $messages = $this->report_messages->get_all($this->data['lang']);
	    $this -> response(array('status' => true, 'data' => $messages, 'message' => $this->lang->line('sucess'))); 
    }
   
  public function report_item_post()
    {
		$this -> form_validation -> set_rules('ad_id', 'ad_id', 'required');
		$this -> form_validation -> set_rules('report_message_id', 'report_message_id', 'required');
		if (!$this -> form_validation -> run()) {
			throw new Validation_Exception(validation_errors());
		} else {
			$this->load->model('data_sources/reported_ads');
			$ad_id = $this->input->post('ad_id');
			$message = $this->input->post('report_message_id');
		    $user_id = null;
	        if($this->session->userdata('PHP_AUTH_USER')){
	            $user_id = $this->session->userdata('LOGIN_USER_ID');
	        }
	        else if($this->response->is_auth){
	            $user_id = $this->response->is_auth;
	        }
			$data = array('ad_id' => $ad_id , 'report_message_id' =>$message , 'user_id'=> $user_id ,'report_seen' =>0 );
			$repored_ad_id = $this->reported_ads->save($data);
			if($repored_ad_id){
			   $this->load->model('data_sources/report_messages');
			   $message_info = $this->report_messages->get_info($this->input->post('report_message_id'), $this->data['lang']);
			   $this->reported_ads->send_email($this->input->post('ad_id') , $message_info->msg);
			   $this -> response(array('status' => true, 'data' => $repored_ad_id, 'message' => $this->lang->line('sucess')));	
			}else{
			   $this -> response(array('status' => false, 'data' => '', 'message' => $this->lang->line('failed')));
			}
		}
   	}


   	public function showcode_to_unicode_post(){
   		$client = new Client(new Ruleset());
   // 		$items = $this->ads->get();
   // 		foreach ($items as $key => $row) {
   // 			dump($row->ad_id);
   // 			$new_title = $client->shortnameToUnicode($row->title);
   // 			dump($new_title);
   // 			//$new_desc = $client->shortnameToImage($row->description);
   // 			//dump($new_desc);
   // 			$data = array('title' => $new_title
   // 		    //, 'description'=> $new_desc
   // 			);
   //          // $this->db->set($data);
			// // $this->db->where('ad_id', $row->ad_id);
			// // $this->db->update('ad');
   // 			$this->ads->save($data , $row->ad_id);
   // 		}

   		$ad_id = 188;
   		$item = $this->ads->get($ad_id, true,null, true );
        $test = 'تجربة بالعربي  :smile:';
   		$new_title = $client->shortnameToUnicode($test);
   		dump($new_title);
   		$new_desc =  $client->shortnameToUnicode($item->description);
   		$data = array('title' => $new_title , 'description' => $new_desc);
   		//$this->ads->save($data , $item->ad_id);
   		echo 'true';
   	}


}
