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

 $(document).ready(function() {
	 	
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
                       return '<button id="" onclick="show_ad_details(\'' + full[0] + '\', \'' + full[10] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
		             }
		         },
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
		ads_table.ajax.reload( null, false );
		$.ajax({
        url: base_url + '/api/items_control/get_pending_count/format/json',
        type: "get",
        dataType: "json",
        global: false,     // this makes sure ajaxStart is not triggered
        success: function(response) {
            // highlight the new pendeing rows. 
            var new_count = response.data;
            var diff = new_count - current_pending_count;
            console.log(diff);
            if(diff > 0){
              $(ads_table.rows().nodes()).each(function(index){
            	//console.log(index);
             	if(index < diff){
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
	              buttons: {
				        sticker: false
				   }
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
 
 function show_ad_details (ad_id , tamplate_id) {
 	  $('.ads_details  #post_id').val(ad_id);
 	  $('.slider_div').append('<div class="images-slider slick-slider"></div>');
 	  $('.ads_details  .template_info').css('display', 'none');
 	  var url =  base_url + '/api/items_control/get_item_details/format/json?ad_id='+ad_id+'&template_id='+tamplate_id;
      $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        success: function(response) {
            console.log(response.data);   
            $item_info = response.data;
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
            if($item_info['description'] != null){
               $('.ads_details  #ad_description').html($item_info['description']);	
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
            $('.ads_details  #ad_status').html(status_array[$item_info['status']]);
            $('.ads_details  #ad_edit_status').html(edit_status_array[$item_info['edit_status']]);
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
                 $('.ads_details  #reject_btn').css('display', 'inline');
            }
            if($item_info['status'] ==  HIDDEN){
            	 $('.ads_details  #show_btn').css('display', 'inline');
            }
            
            //fill rejects notes
            if($item_info['reject_note'] != null  && ($item_info['status'] == 5 || $item_info['edit_status'] == 5)){ // rejected or pending after reject
            	$('#reject_note_label').css('display' , 'inline');
            	$('#reject_note_label').html('<b style="color: red">'+ lang_array['reject_note'] + '</b> ' +$item_info['reject_note'] );
            }
            if(($item_info['expired_after'] != null && $item_info['expired_after'] <= 0 && $item_info['status'] != 2 )){ // after expired 
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
 
 $('.ads_details').on('hidden.bs.modal', function () {
      $('.ads_details  .template_info').css('display', 'none');
 	  $('.images-slider').remove();
 	  $('.ads_details  #accept_btn').css('display', 'none');
      $('.ads_details  #reject_btn').css('display', 'none');
      $('.ads_details  #hide_btn').css('display', 'none');
      $('.ads_details  #reject_note_label').css('display', 'none');
      $('#expiry_edit_label').css('display' , 'none');
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

// function get_pending_ads () {
  // var url_pending =  base_url + '/api/items_control/get_pending_items/format/json';
 // // console.log(url_pending);
  // $.ajax({
        // url: url_pending,
        // type: "get",
        // dataType: "json",
        // success: function(response) {
        	// //console.log(response.data);
            // $('.orders').html('');
             // if(response.data != ''){
               // pending_count = response.data.orders.length;
               // $('#order_counts').html(pending_count);
             // }else{
             	// new PNotify({
                      // title: 'ملاحظة',
                      // text: 'ليس لديك أي طلبات تنتظرك حاليّاً',
                      // type: 'info',
                      // hide: false,
                      // styling: 'bootstrap3',
                      // buttons: {
				        // sticker: false
				      // }
                // });
             // }
             // var str ='';
             // $.each(response.data.orders, function(index, order) { 
             	// //  console.log(order);
                  // str += '<li id='+order['order_id']+'> <div class="row">';
              // //    str += '<div class="col-md-2"></div>';
                  // str += '<div class="col-md-8">';
                  // str += '<a>';
                  // str += '<span style="font-size:17px !important"><span>';
                  // str += '<b  style="color:#169F85 !important">الطلب #'+ order['order_id']+'</b>';
                  // if(order['unseen_log_count'] != 0){
                  	  // str += '   <span class="countIcon' + order['order_id'] + '"><span class="badge bg-green count' + order['order_id'] + '">' + order['unseen_log_count'] + '</span></span>';
                  // }
                  // str += '</span></span>';
                  // str += '<span style="font-size:14px !important" class="message">';
                  // str += '<b  style="color:rgba(243,156,18,0.88);">';
                  // str += 'الزبون:  </b>';
                  // str += order['customer_name'];
                  // str += '</span>';
                  // str += '<span style="font-size:14px !important" class="message">';
                  // str += '<b  style="color:rgba(243,156,18,0.88);">';
                  // str += 'تاريخ التسليم المطلوب:  </b>';
                  // if(order['delivery_date'] == null){
                  	// str += 'بأقرب وقت';
                  // }else{
                  	// str += order['delivery_date'];
                  // }
                  // if(order['user_role_id'] == 1){ // logistic man then show the stage
                  	  // str += '</span>';
	                  // str += '<span style="font-size:14px !important" class="message">';
	                  // str += '<b  style="color:rgba(243,156,18,0.88);">';
	                  // str += 'المرحلة الحاليّة:  </b>';
	                  // str += order['stage_name'];
	                  // str += '</span>';
                  // }
                  // str += '<span style="font-size:14px !important" class="message">';
                  // str += '<b  style="color:rgba(243,156,18,0.88);">';
                  // str += 'الحالة:  </b>';
                  // str += order['status_name'];
                  // str += '</span>';
                  // str += '</a>';
                  // str += '</div>';
                  // str += '<div  class=" col-md-4 pull-right">';
               // // str += '<button   onclick=\'show_order_details_model(' +JSON.stringify(order)+ ')\' id="order_button" type="button" class="btn btn-primary" data-toggle="modal" data-target=".order_details" >view</button>';
                  // str+= '<button   onclick=\'show_order_details_model(' +JSON.stringify(order)+ ')\' id="order_button" type="button" class="btn btn-primary" >التفاصيل</button>';
                  // str += '</div>';
                  // str += '</div> </li>';
	          // }); 
            // $('.orders').html(str);
        // },error: function(xhr, status, error){
        	// new PNotify({
                  // title: 'Oh No!',
                  // text: 'Something Went Wrong while getting you pending orders, please refresh!',
                  // type: 'error',
                  // styling: 'bootstrap3',
                  // buttons: {
				        // sticker: false
				      // }
          // });
        // }
      // });
// }


  
  
  

        
  

