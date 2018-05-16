
var users_table;
var user_chats_table;
var messages_table;
var users_buttons = [];
var gender_array = {
	1: 'Male',
	2: 'Female'
};
 $(document).ready(function() {
 	
 	if($.inArray(EXPORT_USERS, permissions) != -1){
		  users_buttons.push( 
		  	 {
              extend: "excel",
              text: lang_array['export_to_excel'],
              title : 'Users Report '+ moment().format('YYYY-MM-DD'),
              className: "btn-sm",
              exportOptions: {
                 columns: [0,1,2, 3, 4, 5]
              }
            }
		 );
 	}

 	var users_TableButtons = function() {
           users_table = $("#users_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/users_manage/get_all/format/json',
             "columnDefs": [
                 {
                    "targets": -2, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_user_details(\'' + full[0] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
		             }
		         },
		         {
                    "targets": -1, // chats
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_user_chats(\'' + full[0] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons:users_buttons
            });
        };
        users_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              users_TableButtons();
            }
          };
        }();

       users_TableManageButtons.init();  
 });
 
function change_user_status(user_id , is_active){
	var data = {
		user_id : user_id , 
		is_active : is_active
	};
	
	var url = base_url + '/admin/users_manage/change_status/format/json';
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
	                  text: lang_array['user_status_changed'],
	                  type: 'success',
	                  styling: 'bootstrap3',
	                  buttons: {
					        sticker: false
					 }
	               });
	             }
	             users_table.ajax.url(base_url + '/admin/users_manage/get_all/format/json').load();
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

function change_admin_lang (lang) {
	var url = base_url + '/admin/users_manage/change_language/format/json?lang='+lang;
    $.ajax({
	        url: url,
	        type: "get",
	        dataType: "json",
	        success: function(response) {
	            window.location.reload();  
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

function show_user_chats (user_id) {
	var user_chats_TableButtons = function() {
       users_chat_table = $("#user_chats_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/users_manage/get_user_chat_sessions/format/json?user_id='+user_id,
             "columnDefs": [
                {
                "targets": -1, // messages
                "data": null,
                "mRender": function(date, type, full) {
                   return '<button id="" onclick="show_chat_messages(\'' + full[0] + '\' ,\'' + full[2] + '\' ,\'' + full[3] + '\'  );" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
	             }
		        },
	          ],
              dom: "Bfrtip",
              buttons: [
                {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'User chats '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2, 3, 4]
                  }
                },
              ],
            });
        };
        user_chats_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              user_chats_TableButtons();
            }
          };
        }();

       user_chats_TableManageButtons.init(); 
    $('.user_chats_modal').modal('show');
}

 $('.user_chats_modal').on('hidden.bs.modal', function () {
  	 users_chat_table.destroy();
 });
 
 
function show_chat_messages (chat_id , seller_name , user_name) {
     var messages_TableButtons = function() {
       messages_table = $("#messages_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/users_manage/get_chat_messages/format/json?chat_session_id='+chat_id
             +'&seller_name='+seller_name+ '&user_name='+user_name,
             "columnDefs": [
	          ],
              dom: "Bfrtip",
              buttons: [
                {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'messages '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                },
              ],
            });
        };
        messages_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
               messages_TableButtons();
            }
          };
        }();

        messages_TableManageButtons.init(); 
    $('.messages_modal').modal('show');
}

 $('.messages_modal').on('hidden.bs.modal', function () {
  	 messages_table.destroy();
  	 $("body").addClass("modal-open");
 });
 
 function show_user_details (user_id) {
       $.ajax({
        url: base_url + '/admin/users_manage/get_user_info/format/json?user_id='+user_id,
        type: "get",
        dataType: "json",
        success: function(response) {
              console.log(response.data);
              $('.user_details #user_name').html(response.data.name);
              $('.user_details #user_city').html(response.data.city_name);
              $('.user_details #user_phone').html(response.data.phone);
              $('.user_details #user_whatsup_number').html(response.data.whatsup_number);
              if(response.data.email != null && response.data.email != '' ){
              	  $('.user_details #user_email').html(response.data.email); 
              }else{
              	  $('.user_details #user_email').html(lang_array['not_set']);
              }
              if(response.data.gender != null  && response.data.gender != '' ){
              	  $('.user_details #user_gender').html(gender_array[response.data.gender]);
              }else{
              	  $('.user_details #user_gender').html(lang_array['not_set']);
              }
              if(response.data.birthday != null && response.data.birthday  != ''){
              	  $('.user_details #user_birthday').html(response.data.birthday);
              }else{
              	  $('.user_details #user_birthday').html(lang_array['not_set']);
              }
              if(response.data.is_deleted == 1){
              	  $('.user_details #user_is_deleted').html(lang_array['yes']);
              }else{
              	  $('.user_details #user_is_deleted').html(lang_array['no']);
              }
              var user_image = ''; 
              if(response.data.personal_image != null){
              	 user_image = ' <img style="margin: auto; height:100%;  width:100%"';
              	 user_image += 'src= "'+ site_url+response.data.personal_image +'"/>';
              }else{
              	 user_image += '<label class="form-control" value>'+ lang_array['not_set']+'</label>';
              }
              $('.user_details #user_image').html(user_image);
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
    $('.user_details').modal('show');
 }



