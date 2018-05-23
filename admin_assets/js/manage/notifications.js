var notifications_table;
var noti_buttons = [];
 $(document).ready(function() {
 	
 	  if($.inArray(EXPORT_NOTIFICATIONS, permissions) != -1){
		  noti_buttons.push( 
		  	    {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'notifications '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                }
	 	 );
 	  }
    
     	var notify_TableButtons = function() {
           notifications_table = $("#notification_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/users_manage/get_all_notifications/format/json',
             "columnDefs": [
	          ],
              dom: "Bfrtip",
              buttons: noti_buttons,
            });
        };
        notify_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              notify_TableButtons();
            }
          };
        }();

       notify_TableManageButtons.init();
 });
 
function send_notificaion () {
	var title = $('#notify_title').val();
	var body = $('#notify_body').val();
	var city_id = $('#cities_select').val();
	var to_user_id = $('#noti_users_select').val();
	var from_birthday = $('#birthday_from').val();
	var to_birthday = $('#birthday_to').val();
	var gender = $('#noti_gender_select').val();
	
	if(title == '' || body == ''){
	  	new PNotify({
              title: lang_array['attention'],
              text: lang_array['body_and_title'],
              type: 'error',
              styling: 'bootstrap3',
              buttons: {
			        sticker: false
			}
        });
	}else{
			var data = {
			'body' : body, 
			'title' : title
		};
		var url;
		if(to_user_id != 0){ // to user
			data['to_user_id'] = to_user_id;
			url = base_url + '/admin/users_manage/send_notification_to_user/format/json';
		}else if(city_id != 0 || gender != 0 || from_birthday != '' || to_birthday != ''){ // tp group
			if(city_id != 0){
				data['city_id'] = city_id;
			}
			if(gender != 0){
				data['gender'] = gender;
			}
			if(from_birthday != ''){
				data['from_birthday'] = from_birthday;
			}
			if(to_birthday != ''){
				data['to_birthday'] = to_birthday;
			}
			url =  base_url + '/admin/users_manage/send_notifications_to_group/format/json'; 
		}else{ // public
		   url = base_url + '/admin/users_manage/send_public_notification/format/json';
		}
		// if(city_id != 0){ // by city
			// data['city_id'] = city_id;
		  	// url = base_url + '/admin/users_manage/send_notifications_by_city/format/json'; 
		// }else if(to_user_id != 0){
			// data['to_user_id'] = to_user_id;
			// url = base_url + '/admin/users_manage/send_notification_to_user/format/json';
		// }else{ //public
		   // url = base_url + '/admin/users_manage/send_public_notification/format/json';
		// }
	   	$.ajax({
	        url: url,
	        type: "post",
	        dataType: "json",
	        data : data,
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
		                  text: lang_array['notification_sent'],
		                  type: 'success',
		                  styling: 'bootstrap3',
		                  buttons: {
						        sticker: false
						 }
		               });
		            }
		       notifications_table.ajax.url(base_url + '/admin/users_manage/get_all_notifications/format/json').load();
		       $('.confirm-modal').modal('hide');
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