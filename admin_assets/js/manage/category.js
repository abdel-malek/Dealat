 //var sub_sub_cats_tabel;
 var has_child; 
 $(document).ready(function() {
    
   $('#cat_tamplate').change(function(event) {
       var template_id = $(this).val();
       $('.hidden_fields_manage_div').css('display' , 'none'); 
	   $('.template_info_cat').css('display' , 'none'); 
	      // show templates checkboxes. 
	   $('.hidden_fields_manage_div').css('display' , 'inline');
	   $('.manage_cat  .'+template_id+'_info_cat').css('display', 'inline');
	   var template_attr = templates_attrs[template_id];
	    
       // check showed fields
       $.each( template_attr, function( key, value ) {
	       if(value == 'is_new'){
	    	   $('.manage_cat  .is_new_cat').css('display', 'inline');
	    	}
       });
   });   
     
	       
   }); 
   
function show_manage_cat_modal (id , is_main , parent_id ,template_id) {
   console.log(templates_attrs);
   $('#cat_id_input').val(id);
   $('#cat_id_input').attr('parent_id' , parent_id);
   $('#cat_id_input').attr('template_id' , template_id);
   var is_edit = false;
  
      
   if(is_main){ // manage main cat
   	  if(id == 0){ //add
   	  	 $('.choose_tamplate_div').css('display' , 'inline'); 
   	  	 
   	  }else{
   	  	is_edit = true;
   	  }
   }else{ // manage sub cat
   	  if(id == 0){ //add
   	  	
   	  }else{
   	  	is_edit = true;
   	  }
   }
   if(is_edit){
   	    $.ajax({
        url: base_url + '/api/categories_control/get_info/format/json?category_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
        	cat_info = response.data;
            // check the deactivation or deletion ability
        	var has_ads = check_ad_exsistence(cat_info['category_id']);
        	//console.log('check : '+ has_ads);
        	if(has_ads == 0){
        		// check wich btn to show the deactivate or the activate. 
        		if(cat_info['is_active'] == 0){
        			$('#activate_category_btn').css('display', 'inline');
        		}else{
        			$('#delete_category_btn').css('background-color','#bdc3c7');
        			$('#delete_category_btn').css('display', 'inline');// deactivate btn
        		}
        		$('#final_delete_category_btn').css('display', 'inline');//delete btn
        	}
        	
            $('#cat_arabic_name').val(cat_info['ar_name']);
            $('#cat_english_name').val(cat_info['en_name']);
            has_child = check_child_exsistence(id);
           // console.log($res);
            if(has_child == 0 ){
               // show templates checkboxes. 
               $('.hidden_fields_manage_div').css('display' , 'inline');
               $('.manage_cat  .'+cat_info['tamplate_id']+'_info_cat').css('display', 'inline');
	           var template_attr = templates_attrs[cat_info['tamplate_id']];
	           var hidden_fields_array = jQuery.parseJSON( cat_info['hidden_fields'] );
	           console.log(hidden_fields_array);
	            // check showd fields
	            $.each( template_attr, function( key, value ) {
	               if(value == 'is_new'){
	            	   $('.manage_cat  .is_new_cat').css('display', 'inline');
	            	}
	               // uncheck hidden feilds
	               if(hidden_fields_array != null){
	               	//alert(value);
	               //	console.log($.inArray(value  , hidden_fields_array) );
	               	 if($.inArray(value  , hidden_fields_array)  != -1){
	               	 	var checkbox_id = 'cat_'+value;
	               	    $('#'+checkbox_id).prop('checked' , false);
	               	 }
	               } 
				});
            }
        },error: function(xhr, status, error){ 
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
            });
        }
     });
   }else{ // add
   	 $('#delete_category_btn').css('display', 'none');
   	  if(is_main){
   	  	 template_id = $('#cat_tamplate').val();
   	  }
    // show templates checkboxes. 
	  $('.hidden_fields_manage_div').css('display' , 'inline');
	  $('.manage_cat  .'+template_id+'_info_cat').css('display', 'inline');
	  var template_attr = templates_attrs[template_id];
	    
    // check showed fields
      $.each( template_attr, function( key, value ) {
	       if(value == 'is_new'){
	    	   $('.manage_cat  .is_new_cat').css('display', 'inline');
	    	}
      });
   }	
   $('.manage_cat').modal('show');
}


 $('.manage_cat').on('hidden.bs.modal', function () {
	  $('#cat_arabic_name').val('');
	  $('#cat_english_name').val('');
	  $('.choose_tamplate_div').css('display' , 'none'); 
	  $('.hidden_fields_manage_div').css('display' , 'none'); 
	  $('.template_info_cat').css('display' , 'none');
	  $('.hide_check_box').prop('checked' , true); 
	  $('#activate_category_btn').css('display', 'none');
	  $('#delete_category_btn').css('display', 'none');
	  $('#final_delete_category_btn').css('display', 'none');
 });

 
 
 
function save_category(){
	var id = $('#cat_id_input').val();
	var template_id =  $('#cat_id_input').attr('template_id');
	if(template_id == 0){ // adding main category
		template_id = $('#cat_tamplate').val();
	}
	var data = {
		'ar_name' : $('#cat_arabic_name').val(),
		'en_name' : $('#cat_english_name').val()
	};
	// save hidden fields
    var template_attr = templates_attrs[template_id];
	    
    // check showed fields
    var hidden_fields = [];
    $.each( template_attr, function( key, value ) {
    	var checkbox_id = 'cat_'+value;
    	if($('#'+checkbox_id).prop('checked') == false){
    	  hidden_fields.push(value);
    	}
    });
    //console.log(hidden_fields);
    if(hidden_fields.length != 0){
    	data['hidden_fields'] = JSON.stringify(hidden_fields);
    }else{
    	if(id != 0){
    	  data['hidden_fields'] = -1;
    	}
    }
    var url; 
	if(id == 0){ // add
		data['parent_id'] = $('#cat_id_input').attr('parent_id');
		data['tamplate_id'] = template_id;
		url = base_url + '/admin/categories_manage/add/format/json';
		console.log(data);
	}else{ // edit
		data['category_id'] = id;
		url = base_url + '/admin/categories_manage/edit/format/json';
	}
	$.ajax({
	        url: url,
	        type: "post",
	        dataType: "json",
	        data: data,
	        success: function(response) {
	            if(response.status == false){
	           	  new PNotify({
		                  title: lang_array['attention'],
		                  text: response.message,
		                  type: 'error',
		                  styling: 'bootstrap3',
		                  buttons: {
						        sticker: false
						}
		          });
	            }else{
	                new PNotify({
	                  title:  lang_array['success'],
	                //  text: lang_array['category_saved'],
	                  text: '',
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 window.location.reload();
			         $('.manage_cat').modal('hide');
	             }
	        },error: function(xhr, status, error){
	        	new PNotify({
	                  title: lang_array['attention'],
	                  text: lang_array['something_wrong'],
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	             });
	        }
	    });
}


function deactivate_cat () {
  var id = $('#cat_id_input').val();
   data = {'category_id' : id};
    $.ajax({
        url:  base_url + '/admin/categories_manage/deactivate_cat/format/json',
        type: "post",
        dataType: "json",
        data: data,
        success: function(response) {
            if(response.status == false){
           	  new PNotify({
	                  title: lang_array['attention'],
	                  text: response.message,
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	          });
            }else{
                new PNotify({
                  title:  lang_array['success'],
                  //text: lang_array['category_deactivated'],
                  text: '',
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                // education_table.ajax.url( base_url + '/admin/data_manage/get_all_educations/format/json').load();
			    // $('.education_manage_modal').modal('hide');
			    window.location.reload();
             }
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
             });
        }
     });
}


function activate_cat () {
  var id = $('#cat_id_input').val();
   data = {'category_id' : id};
    $.ajax({
        url:  base_url + '/admin/categories_manage/activate_cat/format/json',
        type: "post",
        dataType: "json",
        data: data,
        success: function(response) {
            if(response.status == false){
           	  new PNotify({
	                  title: lang_array['attention'],
	                  text: response.message,
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	          });
            }else{
                new PNotify({
                  title:  lang_array['success'],
                  //text: lang_array['category_activated'],
                  text: '',
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
                // education_table.ajax.url( base_url + '/admin/data_manage/get_all_educations/format/json').load();
			    // $('.education_manage_modal').modal('hide');
			    window.location.reload();
             }
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
             });
        }
     });
}

function delete_cat () {
  var id = $('#cat_id_input').val();
   data = {'category_id' : id};
    $.ajax({
        url:  base_url + '/admin/categories_manage/delete_cat/format/json',
        type: "post",
        dataType: "json",
        data: data,
        success: function(response) {
            if(response.status == false){
           	  new PNotify({
	                  title: lang_array['attention'],
	                  text: response.message,
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	          });
            }else{
                new PNotify({
                  title:  lang_array['success'],
                  //text: lang_array['category_deactivated'],
                  text: '',
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
			    window.location.reload();
             }
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
             });
        }
     });
}



function check_child_exsistence (cat_id) {
    return $.ajax({
	        url: base_url + '/admin/categories_manage/check_child_exsist?category_id='+cat_id,
	        type: "get",
	        dataType: "json",
	        async: false,
	     }).responseText;
}

function check_ad_exsistence (cat_id) {
    return $.ajax({
	        url: base_url + '/admin/categories_manage/check_ad_exsit?category_id='+cat_id,
	        type: "get",
	        dataType: "json",
	        async: false,
	     }).responseText;
}

function show_sort_modal (parent_id) {
	   $('#sort_parent_id').val(parent_id);
	   var url;
	   if(parent_id == 0){
	   	 url = base_url + '/api/categories_control/get_main_categories/format/json';
	   }else{
	   	 url = base_url + '/api/categories_control/get_subcategories/format/json?category_id='+parent_id ;
	   }
       $.ajax({
        url:  url,
        type: "get",
        dataType: "json",
        success: function(response) {
           console.log(response.data);
            $('#categories_list').html('');
            $.each( response.data, function( key, cat ) {
                 $('#categories_list').append('<li class="sorted_li" id="' + cat['category_id'] + '">' + cat['category_name'] + '</li>');
			});
			set_sortable_config();
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
             });
        }
     });
     $('.sort_category_modal').modal('show'); 
}


function save_sorted_categories () {
    var categories_list = [];
    $('#categories_list li').each(function (i)
    {
        categories_list.push(
                $(this).attr("id")
                );
    });
    var parent_id = $('#sort_parent_id').val();
    var data = {
        parent_id: parent_id,
        categories_queue: categories_list
    };
    
    console.log(data);
      $.ajax({
        url:  base_url + '/admin/categories_manage/update_categories_order/format/json',
        type: "post",
        dataType: "json",
        data: data,
        success: function(response) {
            if(response.status == false){
           	  new PNotify({
	                  title: lang_array['attention'],
	                  text: response.message,
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	          });
            }else{
                new PNotify({
                  title:  lang_array['success'],
                  //text: lang_array['categories_sorted'],
                  text : '',
                  type: 'success',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				 }
               });
			    window.location.reload();
             }
        },error: function(xhr, status, error){
        	new PNotify({
                  title: lang_array['attention'],
                  text: lang_array['something_wrong'],
                  type: 'error',
                  styling: 'bootstrap3',
                  buttons: {
				        sticker: false
				}
             });
        }
     });
    
}


function set_sortable_config () {
      $("#categories_list").sortable({
	  group: 'simple_with_animation',
	  pullPlaceholder: false,
	  // animation on drop
	  onDrop: function  ($item, container, _super) {
	    var $clonedItem = $('<li/>').css({height: 0});
	    $item.before($clonedItem);
	    $clonedItem.animate({'height': $item.height()});
	
	    $item.animate($clonedItem.position(), function  () {
	      $clonedItem.detach();
	      _super($item, container);
	    });
	  },
	
	  // set $item relative to cursor position
	  onDragStart: function ($item, container, _super) {
	    var offset = $item.offset(),
	        pointer = container.rootGroup.pointer;
	
	    adjustment = {
	      left: pointer.left - offset.left,
	      top: pointer.top - offset.top
	    };
	
	    _super($item, container);
	  },
	  onDrag: function ($item, position) {
	    $item.css({
	      left: position.left - adjustment.left,
	      top: position.top - adjustment.top
	    });
	  }
	});
}
  
  
  