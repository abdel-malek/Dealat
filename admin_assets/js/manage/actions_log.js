var log_table;
var create_start =  moment().subtract(6, 'days').format('YYYY-MM-DD');
var create_end = moment().format('YYYY-MM-DD');
$('#create_from').val(moment().subtract(6, 'days').format('YYYY-MM-DD'));
$('#create_to').val(moment().format('YYYY-MM-DD'));
var admin_val = $('#admin_select').val();
var log_buttons = [];
 $(document).ready(function() {
 	
 	 if($.inArray(EXPORT_ACTIONS_LOG, permissions) != -1){
		  log_buttons.push( 
		  	    {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Admin Log report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                }
	 	  );
 	  }
 	
 	var log_TableButtons = function() {
           log_table = $("#log_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/users_manage/get_admins_log/format/json?admin_id='
		                                                                     + admin_val
	                                                                         +'&from='
	                                                                         + create_start
	                                                                         +'&to='
	                                                                         + create_end,
             "columnDefs": [
	          ],
              dom: "Bfrtip",
              buttons: log_buttons,
            });
        };
        log_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              log_TableButtons();
            }
          };
        }();

      log_TableManageButtons.init(); 
      
      
     // from selecter
      $('#create_from').change(function(event) {
      	create_start = $("#create_from").val();
        // remove date check pnotify
        $.each( PNotify.notices, function( key, note ) {
            if(note['options']['name'] == "date_check_note"){
            	note.remove();
            }
        });
      	 if(create_end < create_start){
       		new PNotify({
                  title: lang_array['attention'],
                  text:  lang_array['bad_date_entry'],
                  type: 'error',
                  hide : false,
                  styling: 'bootstrap3',
                  name : 'date_check_note',
                  buttons: {
				        sticker: false
				  }
            });
      	 }else{
	        log_table.ajax.url( base_url + '/admin/users_manage/get_admins_log/format/json?admin_id='
		                                                                     + admin_val
	                                                                         +'&from='
	                                                                         + create_start
	                                                                         +'&to='
	                                                                         + create_end
	                                                                         ).load();
	     }
      });  
      
     // to selecter 
     $('#create_to').change(function(event) {
      	create_end = $("#create_to").val();
        // remove date check pnotify
        $.each( PNotify.notices, function( key, note ) {
            if(note['options']['name'] == "date_check_note"){
            	note.remove();
            }
        });
      	 if(create_end < create_start){
       		new PNotify({
                  title: lang_array['attention'],
                  text:  lang_array['bad_date_entry'],
                  type: 'error',
                  hide : false,
                  styling: 'bootstrap3',
                  name : 'date_check_note',
                  buttons: {
				        sticker: false
				  }
            });
      	 }else{
	        log_table.ajax.url( base_url + '/admin/users_manage/get_admins_log/format/json?admin_id='
		                                                                     + admin_val
	                                                                         +'&from='
	                                                                         + create_start
	                                                                         +'&to='
	                                                                         + create_end
	                                                                         ).load();
	     }
      });
      
      // admin selecter
      $('#admin_select').change(function(event) {
      	 admin_val = $("#admin_select").val();
        // remove date check pnotify
	     log_table.ajax.url( base_url + '/admin/users_manage/get_admins_log/format/json?admin_id='
		                                                                     + admin_val
	                                                                         +'&from='
	                                                                         + create_start
	                                                                         +'&to='
	                                                                         + create_end
	                                                                         ).load();
      });
 });