var ads_table;
var status_val = $("#status_select").val();
var status_name = $("#status_select").find("option:selected").text();
var edit_status_val = $("#edit_status_select").val();
var edit_status_name = $("#edit_status_select").find("option:selected").text();
var templates_attrs;
var status_array;
var ACCEPTED = 2 , PENDING = 1 , HIDDEN = 4 , REJECTED = 5 , DELETED = 6;
var status_array_for_label;
var edit_status_array;
var current_pending_count = 0;
var sound_notify_path = site_url +'admin_assets/definite.mp3';
var test = 0;
var ads_buttons =[];
var types;
var current_type_index;
var chosen_type_models; 
var current_template; 
//var current_type_model_id = 0; 

 $(document).ready(function() {
 	if($.inArray(EXPORT_ADS, permissions) != -1){
		  ads_buttons.push( 
		  	 {
              extend: "excel",
              text: lang_array['export_to_excel'],
              title : 'Ads Report '+ moment().format('YYYY-MM-DD'),
              className: "btn-sm",
              exportOptions: {
                 columns: [0,1,2,3,4,5,6,7,8,9]
              }
             }
		 );
 	}
	 	
	if(lang == 'en'){
	   status_array_for_label ={
	   	    0 : 'All',
			1 : 'Pending',
			2 : 'Accepted',
			4:  'Hidden',
			5 : 'Rejected',
			6:  'Deleted'
		};
		edit_status_array ={
			0: 'All',
			7: 'Not edited',
			1: 'While Waiting' , 
			2 : 'After Accept' , 
			5 : 'After Reject',
			4: 'After Hidden', 
			3 : 'After Expired'
		};
	}else{
		status_array_for_label ={
			0 : 'الكل',
			1 : 'قيد الانتظار',
			2 : 'مقبولة',
			4:  'مخفية',
			5 : 'مرفوضة',
			6:  'محذوفة'
		};
		edit_status_array ={
			0 : 'الكل',
			7: 'غير معدّل',
			1: 'خلال الانتظار' , 
			2 : 'بعد القبول' , 
			5 : 'بعد الرفض',
			4: 'بعد الإخفاء', 
			3 : 'بعد الانتهاء'
		};
	}

 	// get templates attrbutes. 
 	$.ajax({
        url: base_url + '/api/items_control/get_data/format/json',
        type: "get",
        dataType: "json",
        success: function(response) {
            templates_attrs = response.data.attrs;
            status_array =  response.data.status;
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
 	
 	
 	var ads_TableButtons = function() {
           ads_table = $("#ads_table").DataTable({
             "oLanguage": {
				  	"sProcessing":   lang_array['sProcessing'],
					"sLengthMenu":   lang_array['sLengthMenu'],
					"sZeroRecords":  lang_array['sZeroRecords'],
					"sInfo":         lang_array['sInfo'],
					"sInfoEmpty":    lang_array['sInfoEmpty'],
					"sInfoFiltered": lang_array['sInfoFiltered'],
					"sInfoPostFix":  "",
					"sSearch":       lang_array['sSearch'],
					"sUrl":          "",
					"oPaginate": {
						"sFirst":    lang_array['sFirst'],
						"sPrevious": lang_array['sPrevious'],
						"sNext":     lang_array['sNext'],
						"sLast":     lang_array['sLast']
				   }
			 },
             "bServerSide": false,
             aaSorting : [[0, 'desc']],
           //   aaSorting :[], // sort by modified date.
           //  "sAjaxSource": base_url + '/admin/items_manage/all/format/json',
             "ajax": {
              "url": base_url + '/admin/items_manage/all/format/json',
		      "type": "GET",
		      global: false,     // this makes sure ajaxStart is not triggered
		      'async' : false,
               dataType: 'json',
             },
             "columnDefs": [
                 {
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                      // var full9 = full[9].split(" ");
                       //var template_id = full9[0];
                       return '<button id="" onclick="show_ad_details(\'' + full[0] + '\', \'' + full[10] + '\', \'' + 0 + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: ads_buttons,
              "initComplete": function(settings, json) {
			      
			  }
            });
        };
        ads_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              ads_TableButtons();
            }
          };
        }();

       ads_TableManageButtons.init();  
       
      
  
  // get penind count
     $.ajax({
        url: base_url + '/api/items_control/get_pending_count/format/json',
        type: "get",
        dataType: "json",
        global: false,     // this makes sure ajaxStart is not triggered
        success: function(response) {
            current_pending_count = response.data;
            $('.pending_count').html(response.data);
        },error: function(xhr, status, error){
        }
     });
     
 // reload to get new pending ads.      
   setInterval(function() {
		$.ajax({
        url: base_url + '/api/items_control/get_pending_count/format/json',
        type: "get",
        dataType: "json",
        global: false,     // this makes sure ajaxStart is not triggered
        success: function(response) {
        	$('#ads_table > tbody > tr').removeAttr('style');
            // highlight the new pendeing rows. 
            var new_count = response.data;
            //console.log(new_count);
            var diff = new_count - current_pending_count;
            //console.log(diff);
            if(diff > 0){
              ads_table.ajax.reload( null, false );
              //alert('fgb');
              $(ads_table.rows().nodes()).each(function(index){
             	if(index < diff){
             		$.playSound(sound_notify_path);
             		$(this).css("background-color", "#1abb9c63");
                    setTimeout(function () {
                        //$(this).removeAttr("style");
                        $(this).css("background-color", "#fff");
                    }, 2500);
             	}else{
             	   return false;
             	}
             });
	          new PNotify({
	              title: lang_array['note'],
	              text: lang_array['new_pending'],
	              type: 'info',
	              styling: 'bootstrap3',
	              hide: false,
		               // buttons: {
					   //     sticker: false
					   //}
	          });
            }
            $('.pending_count').html(response.data);
            current_pending_count = response.data;
        },error: function(xhr, status, error){
        }
     });
	 }, 6000 );  // 4000
	// set select to pending 
     $("#status_select").val(PENDING).trigger('change'); 
 });
 
// filter by status 
 $('#status_select').change(function(event) {
 	
    status_val = $("#status_select").val();
    $('#status_count_label').html(status_array_for_label[status_val]);
    if(status_val == PENDING){
    	// show count circle 
    	$('.countIcon').css('display' , 'inline');
    }else{
        $('.countIcon').css('display' , 'none');
    }
    if(status_val == 0){
      if(edit_status_val != 0){
      	ads_table
		 .search( edit_status_name )
		// .columns().search( edit_status_name )
		 .draw();
      }else{
      	ads_table
		 .search( '' )
		 .columns().search( '' )
		 .draw();
      }
  
    }else{
        status_name = $(this).find("option:selected").text();
        if(edit_status_val != 0){
        	ads_table.search( status_name +' '+edit_status_name).draw();
        }else{
        	ads_table.search( status_name).draw();
        }
    	
    }
 });
 
 //filter by edit status
  $('#edit_status_select').change(function(event) {
    edit_status_val = $("#edit_status_select").val();
     if(edit_status_val == 0){
       if(status_val != 0){
       	 ads_table
		 .search( status_name )
	//	 .columns().search( status_name )
		 .draw();
       }else{
       	 ads_table
		 .search( '' )
		 .columns().search( '' )
		 .draw();
       }
    }else{
      edit_status_name = $(this).find("option:selected").text();
      if(status_val != 0){
      	ads_table.search( status_name+' '+edit_status_name).draw();
      }else{
      	ads_table.search(edit_status_name).draw();
      }
	  
    }
  });
 
 function show_ad_details (ad_id , tamplate_id , from_reports) {
 	  $('.ads_details  #post_id').val(ad_id);
 	  $('.ads_details  #ad_details_template_id').val(tamplate_id);
 	  $('.slider_div').append('<div class="images-slider slick-slider"></div>');
 	  $('.ads_details  .template_info').css('display', 'none');
 	  var url_details =  base_url + '/admin/items_manage/get_item_details/format/json?ad_id='+ad_id+'&template_id='+tamplate_id;
      $.ajax({
        url: url_details,
        type: "get",
        dataType: "json",
        success: function(response) {
           // console.log(response.data);   
            $item_info = response.data;
            $('.ads_details  #ad_details_category_id').val($item_info['category_id']);
            
            $('#delete_ad_btn').attr('template_id' , $item_info['tamplate_id']);
            if(lang == 'en'){
            	$('#ad_deatils_title').html('Ad #'+$item_info['ad_id']+' Details');
            }else{
            	$('#ad_deatils_title').html('تفاصيل الإعلان #'+ $item_info['ad_id']);
            }
            if($item_info['publish_date'] != null){
            	$('#publish_date_div').css('display' , 'inline');
            	$('#ad_publish_date').html($item_info['publish_date']);
            }
            if($item_info['expired_after'] != null){
            	$('#expiry_date_div').css('display' , 'inline');
            	$('#ad_expire_date').html($item_info['expiry_date']);
            	if($item_info['expired_after'] <= 0){
            		if(lang =='en'){
            			$('#ad_deatils_title').append('<div class="pull-right"><h2 style="color: red"><b>Expired</b></h2></div>');
            		}else{
            			$('#ad_deatils_title').append('<div class="pull-left"><h2 style="color: red"><b>منتهي</b></h2></div>');
            		}
            		
            	}
            }
            //fill basic info
            $('.ads_details  #ad_title').html($item_info['title']);
            $('.ads_details  #ad_input_title').val($item_info['title']);
            if($item_info['description'] != null){
               $('.ads_details  #ad_description').html($item_info['description']);	
               $('.ads_details  #ad_input_description').val($item_info['description']);	
            } 
            // image slider 
             var main_image = $item_info['main_image'];
             $('.images-slider').append('<div> <img style="margin: auto; height:100%" src="'+site_url+main_image+'"  alt=""></div>');
             var images = $item_info['images'];
             if(images.length != 0){ // not empty
             //  $('.images-slider').css('display', 'inline');
               var str ='';
               $.each( images, function( key, value ) {	
               	   //console.log(value);
               	   str +='<div>'; 
               	   str +='<img   style="margin: auto; height:100%" src="'+site_url+value.image+'"  alt="">'; 
               	   str += '</div>';
               });
               //console.log(str);
                $('.images-slider').append(str);
             }
             
             $('.images-slider').slick({
		        infinite: true,
		        slidesToShow: 1,
		        mobileFirst: true,
		        swipeToSlide: true,
		        touchThreshold: 20
		      }); 
             
            // fill deteilad basic info
            $('.ads_details  #ad_id').html($item_info['ad_id']);
            $('.ads_details  #ad_creation_date').html($item_info['created_at']);
            $('.ads_details  #ad_category').html($item_info['parent_category_name']+' --> '+$item_info['category_name']);
            $('.ads_details  #ad_location').html($item_info['city_name']+' - '+$item_info['location_name']);
            $.each(response.data.cities, function(index, value) {
	        	if($item_info['city_id']  == value.id){
	        	    $('.ads_details #ad_input_city').append($('<option/>', {
	                  value: value.id,
	                  text: value.name,
	                  selected : true,
	                }));
	                $('.ads_details #ad_input_city').trigger('change');
	        	}else{
	        	    $('.ads_details #ad_input_city').append($('<option/>', {
	                  value: value.id,
	                  text: value.name,
	                }));	
	        	 }
		    });
		    $.each(response.data.locations, function(index, value) {
	        	if($item_info['location_id']  == value.id){
	        	    $('.ads_details #ad_input_location').append($('<option/>', {
	                  value: value.id,
	                  text: value.name,
	                  selected : true,
	                }));
	                $('.ads_details #ad_input_location').trigger('change');
	        	}else{
	        	    $('.ads_details #ad_input_location').append($('<option/>', {
	                  value: value.id,
	                  text: value.name,
	                }));	
	        	}
		    });
            $('.ads_details  #ad_status').html(status_array[$item_info['status']]);
            $('.ads_details  #ad_edit_status').html(edit_status_array[$item_info['edit_status']]);
            $('.ads_details  #ad_price').html(new Intl.NumberFormat('ja-JP').format($item_info['price']));
            $('.ads_details  #ad_input_price').val($item_info['price']);
            if($item_info['is_negotiable'] == '1'){
            	$('.ads_details  #ad_negotiable').html('Yes');
            }
            if($item_info['is_featured'] == 1){
            	$('.ads_details  #ad_featured').html('Yes');
            }
            $('.ads_details  #select_featured').val($item_info['is_featured']);
            
            // fill temaplet info
            var template_attr = templates_attrs[tamplate_id];
            $.each( template_attr, function( key, value ) {
               if(value == 'is_automatic'){
            	   if($item_info[value] == 1){
            	   	 $('.ads_details  #ad_is_automatic').html('Automatic'); 
            	   }else{
            	   	 $('.ads_details  #ad_is_automatic').html('Manual'); 
            	   }
            	}
               else if(value == 'with_furniture'){
            	   if($item_info[value] == 1){
            	   	 $('.ads_details  #ad_with_furniture').html('Yes'); 
            	   }
              }else if(value == 'is_new'){
            	   $('.ads_details  .is_new').css('display', 'inline');
            	   if($item_info[value] == 1){
            	   	 $('.ads_details  #ad_is_new').html('Yes'); 
            	   }
              }else if(value == 'salary' || value =='kilometer' || value == 'size' || value == 'space'){ // format numbers. 
            	   if($item_info[value] != null){
            	   	 $('.ads_details  #ad_'+value).html(new Intl.NumberFormat('ja-JP').format($item_info[value])); 
            	   }
              }else{
            	  if($item_info[value] != null){
            		 $('.ads_details  #ad_'+value).html($item_info[value]);
            		 $('.ads_details  #ad_input_'+value).val($item_info[value]);
            	  }
              }
		   });
			
			
			//fill seller info
			$('.ads_details  #ad_seller_name').html($item_info['seller_name']);
			$('.ads_details  #ad_seller_phone').html($item_info['seller_phone']);
			if($item_info['ad_contact_phone'] != null){
				$('.ads_details  #ad_contact_phone').val($item_info['ad_contact_phone']);
			}else{
				$('.ads_details  #ad_contact_phone').val($item_info['seller_phone']);
			}
            $('.ads_details  .'+tamplate_id+'_info').css('display', 'inline');
            $('.editable_elem').css('display' , 'none');
            
            //show hide btns
            show_delete_btn(from_reports , $item_info['publish_date']);
            if($item_info['status'] ==  PENDING){
            	// $('.ads_details  #accept_btn').css('display', 'inline');
            	 show_accept_btn(from_reports , $item_info['publish_date']);
            	 //$('.ads_details  #reject_btn').css('display', 'inline');
            	 show_reject_btn(from_reports , $item_info['publish_date']);
            	 $('.ads_details  .featured_div').css('display', 'none');
            	 $('.ads_details  .featured_select_div').css('display', 'inline');
            }
            if($item_info['status'] ==  ACCEPTED){
               // $('.ads_details  #hide_btn').css('display', 'inline');
                show_hide_btn(from_reports , 0 , $item_info['publish_date']);
               // $('.ads_details  #reject_btn').css('display', 'inline');
                show_reject_btn(from_reports);
            }
            if($item_info['status'] ==  HIDDEN){
            	// $('.ads_details  #show_btn').css('display', 'inline');
            	show_hide_btn(from_reports , 1 , $item_info['publish_date']);
            }
            
            //fill rejects notes
            if($item_info['reject_note'] != null  && ($item_info['status'] == REJECTED|| $item_info['edit_status'] == REJECTED)){ // rejected or pending after reject
            	$('#reject_note_label').css('display' , 'inline');
            	$('#reject_note_label').html('<b style="color: red">'+ lang_array['reject_note'] + ':</b>' +$item_info['reject_note'] +'</br>');
            }
            if(($item_info['expired_after'] != null && $item_info['expired_after'] <= 0 && $item_info['status'] == PENDING )){ //  the ad is expired and pending.
            	$('#expiry_edit_label').css('display' , 'inline');
            }
            $('.ads_details').modal('show');
            setTimeout(function () {
                $(".images-slider").slick("refresh");
            }, 200);
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


 function show_accept_btn (from_reported) {
 	if(from_reported == 0){ // before report
 	   if($.inArray(ACCEPT_AD, permissions) != -1){
 	   	  $('.ads_details  #accept_btn').css('display', 'inline');
       }
    }
 }
 
 function show_reject_btn (from_reported , publish_date) {
 	if(from_reported == 0){ // before report
 	   if($.inArray(REJECT_AD, permissions) != -1){ // if he have the permission to reject => always reject
 	   	  $('.ads_details  #reject_btn').css('display', 'inline');
       }else{ // check if he have the permission to reject after accept
       	  if(publish_date != null){ // the ad is accepted before
       	  	  if($.inArray(REJECT_AFTER_ACCEPT, permissions) != -1){
       	  	  	 $('.ads_details  #reject_btn').css('display', 'inline');
       	  	  }
       	  }
       }
   }else{ // after report
      if($.inArray(REJECT_AFTER_REPORT, permissions) != -1){
 	   	  $('.ads_details  #reject_btn').css('display', 'inline');
      }	
   }
 }
 
 function show_hide_btn (from_reported , show_btn , publish_date) {
 	if(from_reported == 0){ // before report
 	   if($.inArray(HIDE_AD, permissions) != -1){
 	   	 if(show_btn == 0){
 	   	 	$('.ads_details  #hide_btn').css('display', 'inline');
 	   	 }else{
 	   	 	$('.ads_details  #show_btn').css('display', 'inline');
 	   	 }
       }else{ // check if he have the permission to hide after accept
       	  if(publish_date != null){ // the ad is accepted before
       	  	  if($.inArray(HIDE_AFTER_ACCEPT, permissions) != -1){
       	  	  	 if(show_btn == 0){
		 	   	 	$('.ads_details  #hide_btn').css('display', 'inline');
		 	   	 }else{
		 	   	 	$('.ads_details  #show_btn').css('display', 'inline');
		 	   	 }
       	  	  }
       	  }
       }
   }else{ // after report
      if($.inArray(HIDE_AFTER_REPORT, permissions) != -1){
 	   	 if(show_btn == 0){
 	   	 	$('.ads_details  #hide_btn').css('display', 'inline');
 	   	 }else{
 	   	 	$('.ads_details  #show_btn').css('display', 'inline');
 	   	 }
      }	
   }
 }
 
 function show_delete_btn (from_reported , publish_date) {
 	if(from_reported == 0){ // before report
 	   if($.inArray(DELETE_AD, permissions) != -1){
 	   	 $('.ads_details  #delete_ad_btn').css('display', 'inline');
       }else{ // check if he have the permission to delete after accept
       	  if(publish_date != null){ // the ad is accepted before
       	  	  if($.inArray(DELETE_AFTER_ACCEPT, permissions) != -1){
       	  	  	 $('.ads_details  #delete_ad_btn').css('display', 'inline');
       	  	  }
       	  }
       }
   }else{ // after report
      if($.inArray(DELETE_AFTER_REPORT, permissions) != -1){
 	   	  $('.ads_details  #delete_ad_btn').css('display', 'inline');
      }	
   }
 }
 
 $('.ads_details').on('hidden.bs.modal', function () {
      $('.ads_details  .template_info').css('display', 'none');
 	  $('.images-slider').remove();
 	  $('.ads_details  #accept_btn').css('display', 'none');
      $('.ads_details  #reject_btn').css('display', 'none');
      $('.ads_details  #hide_btn').css('display', 'none');
      $('.ads_details  #reject_note_label').css('display', 'none');
      $('#expiry_edit_label').css('display' , 'none');
      $('.ads_details  #show_btn').css('display', 'none');
      $('.ads_details  #delete_ad_btn').css('display', 'none');
      $('.readonly_elem').css('display' , 'block');
      $('.editable_elem > div.fit_select_ad_details_div > select.select2_single').html('');
      $('.editable_elem > div.fit_select_ad_details_div > select.select2_single').val('').trigger('change');
      $('.editable_elem').css('display' , 'none');
      $('#edit_btn').css('display' ,'inline');
      $('#save_ad_edits_btn').css('display' , 'none');
 });
 
 
 // function make_ad_eitable () {
//  	
    // $('.readonly_elem').css('display' , 'none');
    // $('.editable_elem').css('display' , 'inline');
    // $('#edit_btn').css('display' ,'none');
    // $('#save_ad_edits_btn').css('display' , 'inline');
    // new PNotify({
          // title: lang_array['note'],
          // text: lang_array['price_and_kelo_edit'],
          // type: 'info',
          // styling: 'bootstrap3',
          // hide: true,
          // buttons: {
		       // sticker: false
		   // }
      // });
 // }
 
 function make_ad_eitable () {
 	 tamplate_id = $('.ads_details  #ad_details_template_id').val();
 	 current_template = tamplate_id;
 	 ad_id = $('.ads_details  #post_id').val();
     var url =  base_url + '/admin/items_manage/get_data_lists/format/json?ad_id='+ad_id+'&template_id='+tamplate_id;
     $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        success: function(response) {
        	 // hide labels and  show inputs 
	         $('.readonly_elem').css('display' , 'none');
	         $('.editable_elem.'+tamplate_id+'_info').css('display' , 'inline');
	         $('.editable_elem.basics').css('display' , 'inline');
	         $('#edit_btn').css('display' ,'none');
	         $('#save_ad_edits_btn').css('display' , 'inline');
        	//console.log(response.data);
            $item_info = response.data.info;
            //fill basic inputs 
            $('.ads_details  #ad_input_title').val($item_info['title']);
            if($item_info['description'] != null){
               $('.ads_details  #ad_input_description').val($item_info['description']);	
            } 
             
            // fill deteilad basic inputs
            $('.ads_details  #ad_input_price').val($item_info['price']);
            $('.ads_details  #ad_input_negotiable').val($item_info['is_negotiable']);
            $('.ads_details  #select_featured').val($item_info['is_featured']);
            //location
            $.each(response.data.cities, function(index, value) {
	        	if($item_info['city_id']  == value.city_id){
	        	    $('.ads_details #ad_input_city').append($('<option/>', {
	                  value: value.city_id,
	                  text: value.name,
	                  selected : true,
	                }));
	                $('.ads_details #ad_input_city').trigger('change');
	        	}else{
	        	    $('.ads_details #ad_input_city').append($('<option/>', {
	                  value: value.city_id,
	                  text: value.name,
	                }));	
	        	 }
		    });
		    $.each(response.data.location, function(index, value) {
	        	if($item_info['location_id']!= null && $item_info['location_id']  == value.location_id){
	        	    $('.ads_details #ad_input_location').append($('<option/>', {
	                  value: value.location_id,
	                  text: value.location_name,
	                  selected : true,
	                }));
	                $('.ads_details #ad_input_location').trigger('change');
	        	}else{
	        	    $('.ads_details #ad_input_location').append($('<option/>', {
	                  value: value.location_id,
	                  text: value.location_name,
	                }));	
	        	}
		    });
            // fill temaplet info inputs 
            var template_attr = templates_attrs[tamplate_id];
            //console.log(template_attr);
        
            $.each( template_attr, function( key, value ) {
             if(value == 'type_name' || value == 'type_model_name' || value == 'education_name' || value == 'schedule_name' || value == 'schedule_name' || value == 'certificate_name' || value == 'property_state_name'){
             	if(value == 'type_name'){
             	  types = response.data.types[tamplate_id];
               	  $.each(response.data.types[tamplate_id], function(index, value) {
				      	if( $item_info['type_id']!= null && $item_info['type_id']  == value.type_id){
				      		current_type_index = index;
				      		chosen_type_models = value.models;
			        	    $('.ads_details #ad_input_type_name').append($('<option/>', {
			                  value: value.type_id,
			                  text: value.full_type_name,
			                  type_index : index,
			                  selected : true,
			                }));
			                $('.ads_details #ad_input_type_name').trigger('change');
			        	}else{
			        	    $('.ads_details #ad_input_type_name').append($('<option/>', {
			                  value: value.type_id,
			                  text: value.full_type_name,
			                  type_index : index,
			                }));	
			        	}
				   });
              }
              if(value == 'type_model_name'){
              	 // current_type_model_id = $item_info['type_model_id'];
              	 $('.ads_details #ad_input_type_model_name').trigger('change');
               	  // $.each(chosen_type_models, function(index, value) {
				      	// if($item_info['type_model_id']  == value.type_model_id){
			        	    // $('.ads_details #ad_input_type_model_name').append($('<option/>', {
			                  // value: value.type_model_id,
			                  // text: value.name,
			                  // selected : true,
			                // }));
			                // //$('#select2-ad_input_type_model_name-container').attr('title', value.name);
			                // $('.ads_details #ad_input_type_model_name').trigger('change');
			        	// }else{
			        	    // $('.ads_details #ad_input_type_model_name').append($('<option/>', {
			                  // value: value.type_model_id,
			                  // text: value.name,
			                // }));	
			        	// }
				   // });
              }
              if(value == 'education_name'){
              	   $.each(response.data.educations, function(index, value) {
				      	if($item_info['education_id'] != null && $item_info['education_id']  == value.education_id){
			        	    $('.ads_details #ad_input_education_name').append($('<option/>', {
			                  value: value.education_id,
			                  text: value.name,
			                  selected : true,
			                }));
			                $('.ads_details #ad_input_education_name').trigger('change');
			        	}else{
			        	    $('.ads_details #ad_input_education_name').append($('<option/>', {
			                  value: value.education_id,
			                  text: value.name,
			                }));	
			        	}
				    });
               }
               if(value == 'schedule_name'){
              	   $.each(response.data.schedules, function(index, value) {
				      	if($item_info['schedule_id'] != null && $item_info['schedule_id']  == value.schedule_id){
			        	    $('.ads_details #ad_input_schedule_name').append($('<option/>', {
			                  value: value.schedule_id,
			                  text: value.name,
			                  selected : true,
			                }));
			                $('.ads_details #ad_input_schedule_name').trigger('change');
			        	}else{
			        	    $('.ads_details #ad_input_schedule_name').append($('<option/>', {
			                  value: value.schedule_id,
			                  text: value.name,
			                }));	
			        	}
				    });
               }
               if(value == 'certificate_name'){
              	   $.each(response.data.certificates, function(index, value) {
				      	if($item_info['certificate_id'] != null && $item_info['certificate_id']  == value.certificate_id){
			        	    $('.ads_details #ad_input_certificate_name').append($('<option/>', {
			                  value: value.certificate_id,
			                  text: value.name,
			                  selected : true,
			                }));
			                $('.ads_details #ad_input_certificate_name').trigger('change');
			        	}else{
			        	    $('.ads_details #ad_input_certificate_name').append($('<option/>', {
			                  value: value.certificate_id,
			                  text: value.name,
			                }));	
			        	}
				    });
               }
               if(value == 'property_state_name'){
              	   $.each(response.data.states, function(index, value) {
				      	if($item_info['property_state_id'] != null && $item_info['property_state_id']  == value.property_state_id){
			        	    $('.ads_details #ad_input_property_state_name').append($('<option/>', {
			                  value: value.property_state_id,
			                  text: value.name,
			                  selected : true,
			                }));
			                $('.ads_details #ad_input_property_state_name').trigger('change');
			        	}else{
			        	    $('.ads_details #ad_input_property_state_name').append($('<option/>', {
			                  value: value.property_state_id,
			                  text: value.name,
			                }));	
			        	}
				    });
                }
             }else if(value == 'is_new'){
             	 $('.editable_elem.is_new_edit').css('display' , 'inline');
             	 $('.ads_details #ad_input_'+value).val($item_info[value]);
             }else{
             	$('.ads_details #ad_input_'+value).val($item_info[value]);
             }
             $('.editable_elem').css('margin-bottom',  "10px");
		   });
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
 
 // change types model acording to selected type. 
 $('#ad_input_type_name').change(function(event) {
      current_type_index =  $(this).find("option:selected").attr('type_index');
      //console.log(current_type_index);
      if(current_type_index != null){
      	  chosen_type_models = types[current_type_index]['models'];
	      $('.ads_details #ad_input_type_model_name').html('');
	      $('.ads_details #ad_input_type_model_name').val('').trigger('change');
	      $.each(chosen_type_models, function(index, value) {
	    	    $('.ads_details #ad_input_type_model_name').append($('<option/>', {
	              value: value.type_model_id,
	              text: value.name,
	            }));	
		  });
      }
 });  
 
function save_ad_edits(){
 	ad_id = $('.ads_details  #post_id').val();
 	var data = {
 		'ad_id':ad_id
 	};
 	var price = $('#ad_input_price').val();
 	var kilometer = $('#ad_input_kilometer').val();
 	var title = $('#ad_input_title').val();
 	var desc = $('#ad_input_description').val();
 	var city = $('#ad_input_city').val();
 	var location = $('#ad_input_location').val();
 	console.log(location);
 	var is_negotiable = $('#ad_input_negotiable').val();
 	var is_featuerd = $('#select_featured').val();
 	if(price != ''){
 	    data['price'] = price;
 	}
 	if(kilometer != ''){
 		data['kilometer'] = kilometer;
 	}
 	if(title != ''){
 	    data['title'] = title;
 	}
 	if(desc != ''){
 	    data['description'] = desc;
 	}else{
 	    data['description'] = -1; 
 	}
 	data['city_id'] =  city;
 	if(location != null && location != '' && location != 0){
 		data['location_id'] =  location;
 	}else{
 		data['location_id'] = -1;
 	}
 	data['is_negotiable'] = is_negotiable;
 	data['is_featured'] = is_featuerd;
 	var template_attr = templates_attrs[tamplate_id];
    //console.log(template_attr);
    $.each( template_attr, function( key, attr_name ) { 
    	 attr_id = attr_name.replace("name", "id");
    	 value = $('#ad_input_'+attr_name).val();
    	 if(value != null && value != ''){
    	 	 data[attr_id] = value;
    	 }else{
    	 	 data[attr_id] = -1;
    	 	// console.log(-1);
    	 }
    });
 	console.log(data);
 	$.ajax({
	        url: base_url + '/admin/items_manage/edit_item/format/json',
	        type: "post",
	        dataType: "json",
	        data: data,
	        success: function(response) {
	            if(response.status == false){
	               new PNotify({
	                  title: lang_array['attention'],
	                  text:  response.message,
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	              });
	            }else{
	                new PNotify({
	                  title: lang_array['success'] ,
	                  text: lang_array['ad_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	          ads_table.ajax.url(base_url + '/admin/items_manage/all/format/json' ).load();
	          //$('.ads_details').modal('hide');
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
 
function perform_action (action) {
 	 var can_proceed = true; 
 	 if(can_proceed == true){
 	 	var conatct_phone = $('.ads_details  #ad_contact_phone').val();
 	 	var user_phone = $('.ads_details  #ad_seller_phone').html();
 	    var data = {
	     	action : action,
	        ad_id : $('.ads_details  #post_id').val()
	     };
	     if(conatct_phone != user_phone){
	     	data['ad_contact_phone'] = conatct_phone;
	     }
	     if(action == 'accept'){
	     	data['publish_date'] = moment().format('YYYY-MM-DD HH:mm:ss');
	     	data['is_featured'] =  $('.ads_details  #select_featured').val();
	     }else if(action == 'reject'){
	     	data['reject_note'] = $('.reject_model  #reject_note').val();
	     }
	      else if(action == 'delete'){
	     	data['template_id'] = $('#delete_ad_btn').attr('template_id');
	     }
	     console.log(data);
	     $.ajax({
	        url: base_url + '/admin/items_manage/action/format/json',
	        type: "post",
	        dataType: "json",
	        data: data,
	        success: function(response) {
	            if(response.status == false){
	               new PNotify({
	                  title: lang_array['attention'],
	                  text:  response.message,
	                  type: 'error',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					}
	              });
	            }else{
	              if(action == 'accept'){
	                new PNotify({
	                  title: lang_array['success'] ,
	                  text: lang_array['ad_accepted'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	              }else if (action == 'reject'){
	              	 new PNotify({
	                  title: lang_array['success'],
	                  text: lang_array['ad_rejected'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                $('.reject_model').modal('hide');
	                reported_ads_table.ajax.url(base_url + '/admin/items_manage/get_all_reported_items/format/json').load();
	              }else if(action == 'hide' ){
	                new PNotify({
	                  title: lang_array['success'],
	                  text: lang_array['ad_hidden'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	              }else if(action == 'show' ){
	                new PNotify({
	                  title: lang_array['success'],
	                  text: lang_array['ad_show'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	              }
	            }
	         ads_table.ajax.url(base_url + '/admin/items_manage/all/format/json' ).load();
	         $('.ads_details').modal('hide');
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
 }



  
  
  

        
  

