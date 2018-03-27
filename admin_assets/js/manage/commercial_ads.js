 var comm_ads_table;
 var cat_id;
 var cat_name;
 var current_comm_id; 
 var comm_image_path='';
 $(document).ready(function() {
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
             "sAjaxSource": base_url + '/admin/commercial_items_manage/get_all/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_comm_ad_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         } 
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
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
	    	comm_ads_table.search( cat_name ).draw();
        }
       // alert(cat_name);
   });
   
   
   $("#fileuploader-comm_ad").uploadFile({
        url: base_url + '/api/commercial_items_control/item_images_upload',
        multiple: true,
        dragDrop: true,
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
 
function show_comm_ad_modal (id) {
   current_comm_id = id;
   if(id != 0){// edit 
   	   	$.ajax({
        url: base_url + '/api/commercial_items_control/get_info/format/json?comm_id='+id,
        type: "get",
        dataType: "json",
        success: function(response) {
        	var info = response.data;
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
        	if(info['is_main']== 1){
        	  $('#is_main').prop('checked' , true);	
        	}else{
        	  $('#is_main').prop('checked'  , false);	
        	}
        	$('#image_div').css('display', 'inline');
            $("#comm_image").attr("src",site_url + info['image']);
            $('#comm_position').val(info['position']).trigger('change');
        //   console.log(response);
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
   $('.comm_ads_details').modal('show');
}

 $('.comm_ads_details').on('hidden.bs.modal', function () {
      $('#image_div').css('display', 'none');
   	  $('#created_div').css('display', 'none');  
   	  $('#comm_title').val('');
   	  $('#comm_description').val('');
   	  $('#comm_url').val('');
   	  $(".comm_ads_details .ajax-file-upload-container").empty();
   	  comm_image_path = '';
 });

function save_comm() { 
  //if(current_comm_id != 0){ // edit
  	//console.log(cat_id);
  	 var data = {
  	 	comm_id : current_comm_id,
  	 	title : $('#comm_title').val(),
  	 	description :  $('#comm_description').val(),
  	 	ad_url : $('#comm_url').val(),
  	 //	category_id : cat_id,
  	 	position : $("#comm_position").val(),
  	 };
  	 if(comm_image_path != ''){
  	 	data['image'] =comm_image_path; 
  	 }
  	 if($('#is_main'). prop("checked") == true){
	  	data['is_main'] = 1; 
	 }else{
	 	data['is_main'] = 0; 
	 }
	 if(current_comm_id == 0){ // add
	 	data['category_id'] = cat_id;
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
	                 comm_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_all/format/json').load();
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
	                 comm_ads_table.ajax.url(base_url + '/admin/commercial_items_manage/get_all/format/json').load();
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