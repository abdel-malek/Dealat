var users_codes_table;
var users_codes_buttons = [];
 $(document).ready(function() {
 	
 	if($.inArray(EXPORT_USERS, permissions) != -1){
		  users_codes_buttons.push( 
		  	 {
              extend: "excel",
              text: lang_array['export_to_excel'],
              title : 'Users Activation Codes Report '+ moment().format('YYYY-MM-DD'),
              className: "btn-sm",
              exportOptions: {
                // columns: [0,1,2, 3, 4, 5]
              }
            }
		 );
 	}

 	var users_codes_TableButtons = function() {
           users_codes_table = $("#users_codes_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/users_manage/get_users_activation_codes/format/json',
             "columnDefs": [
	          ],
              dom: "Bfrtip",
              buttons:users_codes_buttons
            });
        };
        users_codes_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              users_codes_TableButtons();
            }
          };
        }();

       users_codes_TableManageButtons.init();  
 });