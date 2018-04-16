
var users_table;
 $(document).ready(function() {

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
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_user_details(\'' + full[0] + '\', \'' + full[8] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
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
