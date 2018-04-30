function save_about(){
	 data = {
	 	'ar_about_us' : $('#about_ar').val(),
	 	'en_about_us' : $('#about_en').val(),
	 	'phone' : $('#about_phone').val(),
	 	'email' : $('#about_email').val(),
	 	'facebook_link' : $('#facebook_link').val(),
	 	'instagram_link' : $('#instagram_link').val(),
	 	'twiter_link' : $('#twiter_link').val(),
	 	'youtube_link': $('#youtube_link').val(),
	 	'linkedin_link': $('#linkedin_link').val(),
	 	'ar_terms' : $('#ar_terms').val(),
	 	'en_terms' : $('#en_terms').val()
	 };
	$.ajax({
	        url: base_url + '/admin/data_manage/save_about/format/json',
	        type: "post",
	        dataType: "json",
	        data: data,
	        success: function(response) {
	        //	location.reload();
	            // if(response.status == false){
	           	  // new PNotify({
		                  // title: lang_array['attention'],
		                  // text: response.message,
		                  // type: 'error',
		                  // styling: 'bootstrap3',
		                  // buttons: {
						        // sticker: false
						// }
		          // });
	            // }else{
	                // new PNotify({
	                  // title:  lang_array['success'],
	                  // text: lang_array['ad_saved'],
	                  // type: 'success',
	                  // styling: 'bootstrap3',
	                  // buttons: {
					        // sticker: false
					 // }
	               // });
	                 // window.location.reload();
			         // $('.manage_cat').modal('hide');
	             // }
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
