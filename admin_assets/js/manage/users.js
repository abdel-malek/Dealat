
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
                 // {
                    // "targets": -1, // details
                    // "data": null,
                    // "mRender": function(date, type, full) {
                       // return '<button id="" onclick="show_ad_details(\'' + full[0] + '\', \'' + full[8] + '\');" type="button" class="btn btn-primary" >View</button>';
		             // }
		         // } 
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