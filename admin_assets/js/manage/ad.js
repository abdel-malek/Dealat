var ads_table;
var status_val = $("#status_select").val();
var status_name = $("#status_select").find("option:selected").text();
var templates_attrs;
var status_array;
var ACCEPTED = 2 , PENDING = 1 , HIDDEN = 4 , REJECTED = 5 , DELETED = 6;

 $(document).ready(function() {
 	
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
             "sAjaxSource": base_url + '/admin/items_manage/all/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_ad_details(\'' + full[0] + '\', \'' + full[8] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
		             }
		         } 
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
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
       
       
       
       
 	
 });


 // $('#status_select').change(function(event) {
  	 // status_val = $("#status_select").val();
     // ads_table.ajax.url(base_url + '/admin/ads_manage/all_ads/format/json?status='+status_val ).load();
     // console.log(ads_table.ajax.url());                                                                    
 // });
 
// filter by status
 $('#status_select').change(function(event) {
    status_val = $("#status_select").val();
    if(status_val == 0){
    	ads_table
		 .search( '' )
		 .columns().search( '' )
		 .draw();
    }else{
        status_name = $(this).find("option:selected").text();
    	ads_table.search( status_name ).draw();
    }
 });
 
 function show_ad_details (ad_id , tamplate_id) {
 	 // console.log(ad_id);
 	//  console.log(tamplate_id);
 	  $('.ads_details  #post_id').val(ad_id);
 	  $('.slider_div').append('<div class="images-slider slick-slider"></div>');
 	  $('.ads_details  .template_info').css('display', 'none');
 	  var url =  base_url + '/api/items_control/get_item_details/format/json?ad_id='+ad_id+'&template_id='+tamplate_id;
      $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        success: function(response) {
           // console.log(response.data);   
            $item_info = response.data;
            //fill basic info
            $('.ads_details  #ad_title').html($item_info['title']);
            if($item_info['description'] != null){
               $('.ads_details  #ad_description').html($item_info['description']);	
            } 
            // image slider 
             var main_image = $item_info['main_image'];
             $('.images-slider').append('<div> <img style="margin: auto; height:100%" src="'+site_url+main_image+'"  alt=""></div>');
             
             var images = $item_info['images'];
            // console.log(images);
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
            $('.ads_details  #ad_location').html($item_info['city_name']+' - '+$item_info['location_name']);
            $('.ads_details  #ad_status').html(status_array[$item_info['status']]);
            $('.ads_details  #ad_price').html($item_info['price']);
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
            	}else{
            	  if($item_info[value] != null){
            		 $('.ads_details  #ad_'+value).html($item_info[value]);
            	  }
            	}
			});
			
			
			//fill seller info
			$('.ads_details  #ad_seller_name').html($item_info['seller_name']);
			$('.ads_details  #ad_seller_phone').html($item_info['seller_phone']);
            
            $('.ads_details  .'+tamplate_id+'_info').css('display', 'inline');
            
            //show hide btns
            if($item_info['status'] ==  PENDING){
            	 $('.ads_details  #accept_btn').css('display', 'inline');
            	 $('.ads_details  #reject_btn').css('display', 'inline');
            	 $('.ads_details  .featured_div').css('display', 'none');
            	 $('.ads_details  .featured_select_div').css('display', 'inline');
            }
            if($item_info['status'] ==  ACCEPTED){
            	 $('.ads_details  #hide_btn').css('display', 'inline');
            }
            if($item_info['status'] ==  HIDDEN){
            	 $('.ads_details  #show_btn').css('display', 'inline');
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
 
 $('.ads_details').on('hidden.bs.modal', function () {
      $('.ads_details  .template_info').css('display', 'none');
 	  $('.images-slider').remove();
 	  $('.ads_details  #accept_btn').css('display', 'none');
      $('.ads_details  #reject_btn').css('display', 'none');
      $('.ads_details  #hide_btn').css('display', 'none');
      $('.ads_details  #show_btn').css('display', 'none');
 });
 
 
 function perform_action (action) {
 	 var can_proceed = true; 
 	 if(can_proceed == true){
 	    var data = {
	     	action : action,
	        ad_id : $('.ads_details  #post_id').val()
	     };
	     if(action == 'accept'){
	     	data['publish_date'] = moment().format('YYYY-MM-DD HH:mm:ss');
	     	data['is_featured'] =  $('.ads_details  #select_featured').val();
	     }else if(action == 'reject'){
	     	data['reject_note'] = $('.reject_model  #reject_note').val();
	     }
	     console.log(data);
	     $.ajax({
	        url: base_url + '/api/items_control/action/format/json',
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
	         ads_table.ajax.url(base_url + '/admin/items_manage/all/format/json?status='+status_val ).load();
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
 


  
  
  

        
  

