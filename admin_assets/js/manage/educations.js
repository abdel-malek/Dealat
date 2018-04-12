var education_table;
 $(document).ready(function() {
 	var educations_TableButtons = function() {
           education_table = $("#educations_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/data_manage/get_all_educations/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // edit
                    "data": null,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_education_manage_modal(\'' + full[0] + '\');"  type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         },
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
            });
        };
        educations_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              educations_TableButtons();
            }
          };
        }();

       educations_TableManageButtons.init();  
 });
 
function show_education_manage_modal (id) {
   
}