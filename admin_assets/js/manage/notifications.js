var schedules_table;
 $(document).ready(function() {
    
 });
 
function send_notificaion () {
	var title = $('#notify_title').val();
	var body = $('#notify_body').val();
	var city_id = $('#cities_select').val();
	if(title == '' || body == ''){
	  	new PNotify({
              title: lang_array['attention'],
              text: 'You have to provide title and body',
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
		if(city_id != 0){ // by city
			data['city_id'] = city_id;
		  	url = base_url + '/admin/users_manage/send_notifications_by_city/format/json'; 
		}else{ //public
		   url = base_url + '/admin/users_manage/send_public_notification/format/json';
		}
		// console.log(url);
		// console.log(data);
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