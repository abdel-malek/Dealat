 var comm_ads_table;
 var cat_id = 0;
 var cat_name;
 var position_val_other = $('#comm_position_filter_others').val();
 var position_name_other;
 var city_val_other = $('#comm_city_filter_others').val();
 var city_name_other;
 var current_comm_id;
 var comm_image_path='';
 var comm_buttons = [];
 // const SIDE_MENU = 1 , SIDE_LIMIT = 2;
 // const SLIDER = 2 , SLIDER_LIMIT = 3;
 // const MOBILE = 3, MOBILE_LIMIT =3;
 // var $activated_number = 0;
 $(document).ready(function() {
 	if($.inArray(EXPORT_COMMERCIALS, permissions) != -1){
		  comm_buttons.push(
		  	 {
              extend: "excel",
              text: lang_array['export_to_excel'],
              title : 'Other Commercials Report '+ moment().format('YYYY-MM-DD'),
              className: "btn-sm",
              exportOptions: {
                 columns: [0,1,2 ,3 , 4]
              }

             }
		 );
 	}

 	var comm_TableButtons = function() {
           comm_ads_table = $("#commercial_ads_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/commercial_items_manage/get_without_main/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_comm_ad_modal(\'' + full[0] + '\',\'' + 0 + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         }
	          ],
              dom: "Bfrtip",
              buttons: comm_buttons,
              initComplete: function(nRow, settings, json){
	          	 activated_number =0;
	           },
            });
        };
        comm_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              comm_TableButtons();
            }
          };
        }();
       comm_TableManageButtons.init();


   $('.cat_tap').click(function(event) {
        cat_id = $(this).attr('id');
        cat_name = $(this).html();
        if(cat_id == 0){
            $('#add_comm_btn').css('display' , 'none');
	    	comm_ads_table
			 .search( '' )
			 .columns().search( '' )
			 .draw();
        }else{
        	$('#add_comm_btn').css('display' , 'inline');
        	if(position_val_other != 0){
        		comm_ads_table.search( cat_name+' '+position_name_other ).draw();
        	}else{
        		comm_ads_table.search( cat_name ).draw();
        	}
        }
       // alert(cat_name);
   });


    // filter by position
	// $('#comm_position_filter_others').change(function(event) {
	    // position_val = $("#comm_position_filter_others").val();
	    // if(position_val == 0){
	    	// comm_ads_table
			 // .search( '' )
			 // .columns().search( '' )
			 // .draw();
	    // }else{
	        // position_name = $(this).find("option:selected").text();
	        // if(cat_id != 0){
	          // comm_ads_table.search(cat_name+' '+position_name ).draw();
	        // }else{
	          // comm_ads_table.search(position_name).draw();
	        // }
	    // }
	// });


  $('#comm_position_filter_others').change(function(event) {
    position_val_other =  $('#comm_position_filter_others').val();
    position_name_other = $(this).find("option:selected").text();
    check_other_filter_values();
    comm_ads_table.search( position_name_other+' '+city_name_other ).draw();
  });

  $('#comm_city_filter_other').change(function(event) {
    city_val_other =  $('#comm_city_filter_other').val();
    city_name_other = $(this).find("option:selected").text();
    check_other_filter_values();
    comm_ads_table.search( position_name_other+' '+city_name_other ).draw();
  });

  function check_other_filter_values() {
      if(  position_val_other== 0){
      	 position_name_other = '';
      }
      if(city_val_other == 0){
      	 city_name_other = '';
      }
   }

	// show the write ration note
   $('#comm_position').change(function(event) {
        var position = $(this).val();
        $('.image_ration_note').css('display', 'none');
        $('#label'+position).css('display' , 'inline');
   });


   $("#fileuploader-comm_ad").uploadFile({
        url: base_url + '/api/commercial_items_control/item_images_upload',
        multiple: false,
        dragDrop: false,
        fileName: "image",
        acceptFiles: "image/*",
        maxFileSize: 10000 * 1024,
        //see docs for localization(lang)
        showDelete: true,
        //                statusBarWidth:600,
        dragdropWidth: "100%",
        showPreview: true,
        previewHeight: "100px",
        previewWidth: "100px",
        uploadStr: "Upload Image",
        returnType: "json",
        onSuccess: function (files, data, xhr, pd) {
        	//alert('sucess');
            //console.log(data);
            comm_image_path = data.data;

        },
        onError: function (files, status, errMsg, pd) {
            //console.log("upload failed");
        },
        deleteCallback: function (data, pd) {
             var image_path  = data.data;
            $.post(base_url + '/api/commercial_items_control/delete_image', {
                    image: image_path
                },
                function (resp, textStatus, jqXHR) {
                });
            //                }
        }
    });
 });

function show_comm_ad_modal (id , is_main) {
   current_comm_id = id;
   var current_postion = $('#comm_position').val();
   if(id != 0){// edit
   	   	$.ajax({
        url: base_url + '/api/commercial_items_control/get_info/format/json?comm_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
        	var info = response.data['info'];
        	cities =  response.data['cities'];
            $('#created_div').css('display', 'inline');
        	$('#comm_created_at').html(info['created_at']);
            if(info['title']!= null){
        	  $('#comm_title').val(info['title']);
        	}
            if(info['description']!= null){
        	  $('#comm_description').val(info['description']);
        	}
        	if(info['ad_url']!= null){
        	  $('#comm_url').val(info['ad_url']);
        	}
        	$('#image_div').css('display', 'inline');
            $("#comm_image").attr("src",site_url + info['image']);
            $('#comm_position').val(info['position']).trigger('change');
            $('#label'+info['position']).css('display' , 'inline');
            $("#comm_city").val(cities).trigger("change");
          // console.log(cities);
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
   }else{  // add
   	 var current_pos = $('#comm_position').val();
   	 $('#label'+current_pos).css('display' , 'inline');
   }
   //show the delete btn
   show_commercial_delete_btn(is_main);
   $('.comm_ads_details').modal('show');
 }

 function show_commercial_delete_btn (is_main) {
 	if(is_main == 1){
 	  if($.inArray(DELETE_MAIN_COMMERCIAL, permissions) != -1){
      	   $('#delete_comm_btn').css('display' , 'inline');
      }
 	}else{
 	  if($.inArray(DELETE_OTHER_COMMERCIAL, permissions) != -1){
      	   $('#delete_comm_btn').css('display' , 'inline');
      }
 	}
 }

 $('.comm_ads_details').on('hidden.bs.modal', function () {
      $('#image_div').css('display', 'none');
   	  $('#created_div').css('display', 'none');
   	  $('.image_ration_note').css('display', 'none');
   	  $('#delete_comm_btn').css('display' , 'none');
   	  $('#comm_title').val('');
   	  $('#comm_description').val('');
   	  $('#comm_url').val('');
   	  $("#comm_city").val([]).trigger("change");
   	  $(".comm_ads_details .ajax-file-upload-container").empty();
   	  comm_image_path = '';
 });

function save_comm() {
  //if(current_comm_id != 0){ // edit
  	//console.log(cat_id);
  	 var data = {
  	 	comm_id : current_comm_id,
  	 	ad_url : $('#comm_url').val(),
  	 	position : $("#comm_position").val(),
      external : $("#comm_external").val(),
  	    title : $('#comm_title').val(),
  	 	description :  $('#comm_description').val(),
  	 	city_id : $('#comm_city').val()

  	 };

  	 if(comm_image_path != ''){
  	 	data['image'] =comm_image_path;
  	 }
	 if(current_comm_id == 0){ // add
	 	if(cat_id != 0){ // it this is not main ad
	 		data['category_id'] = cat_id;
	 	}
	 }
  	 var url = base_url + '/api/commercial_items_control/save';
  	 console.log(data);
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
	                  text: lang_array['ad_saved'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 comm_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_without_main/format/json').load();
	                 main_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_main/format/json').load();
			         $('#'.cat_id).click();
			         $('.comm_ads_details').modal('hide');
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
  //}
}

function delete_comm() {
    data = {
    	'comm_ad_id' : current_comm_id
    };
    console.log(data);
     var url = base_url + '/api/commercial_items_control/delete/format/json';
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
	                  text: lang_array['ad_deleted'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	                 comm_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_without_main/format/json').load();
	                 main_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_main/format/json').load();
			         $('#'.cat_id).click();
			         $('.comm_ads_details').modal('hide');
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

function change_status (id , category , position ,city, to_active ) {
    data = {
    	'comm_id' : id,
    	'category_id' :category ,
    	'position' :position,
    	'to_active' : to_active,
    	'city_id' : city
    };
   // console.log(city);
     var url = base_url + '/api/commercial_items_control/change_status/format/json';
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
	                  text: lang_array['show_status_changed'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	             }
	             comm_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_without_main/format/json').load();
	             main_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_main/format/json').load();
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

// function check_active_limit(category , position) {
   // if(category == 0){ // main ads table
   	 // $('#main_commercials_table > tbody > tr').each(function() {
   	 	   // switch(position) {
			    // case SIDE_MENU:
			        // if($(this).find("#comm_status_check").is(':checked')){
			        	// console.log('check '+$(this).find("#comm_status_check").is(':checked'));
			          // if($(this).find("#comm_status_check").attr('position') == SIDE_MENU){
			          	  // console.log('pos '+($(this).find("#comm_status_check").attr('position') == SIDE_MENU));
			          	  // $activated_number++;
			          	  // console.log('num : '+$activated_number);
			          	  // console.log('limit : '+SIDE_LIMIT);
			          	  // if($activated_number >= SIDE_LIMIT){
					        	// return false;
					      // }
			           // }
	  	            // }
			        // break;
			    // case SLIDER:
			       // if($(this).find("#comm_status_check").is(':checked')){
			          // if($(this).find("#comm_status_check").attr('position') == SLIDER){
			          	  // $activated_number++;
			          	  // if($activated_number >= SLIDER_LIMIT){
					        	// return false;
					      // }
			           // }
	  	            // }
			        // break;
			    // case MOBILE:
			       // if($(this).find("#comm_status_check").is(':checked')){
			          // if($(this).find("#comm_status_check").attr('position') == MOBILE){
			          	  // $activated_number++;
			          	  // if($activated_number >= MOBILE_LIMIT){
					        	// return false;
					      // }
			           // }
	  	            // }
			        // break;
			    // default:
			      // //  code block
		   // }
      // });
   // //   console.log($activated_number);
      // return true;
   // }else{ //others table
//
   // }
// }
