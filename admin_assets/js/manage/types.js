var types_table;
var template = $('#type_template_select').val();
 $(document).ready(function() {

 	var types_TableButtons = function() {
           types_table = $("#types_table").DataTable({
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
             aaSorting : [[3, 'desc']],
             "sAjaxSource": base_url + '/admin/data_manage/get_all_types/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // models
                    "data": null,
                 //   "visible" : false,
                    "mRender": function(date, type, full) {
                       if(full[3] == 'vehicles' || full[3] == 'مركبات' ){
                       	   return '<button id="" onclick="show_models_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
                       }else{
                       	   return '';
                       }
                      
		             }
		         },
		         {
                    "targets": -2, // details
                    "data": null,
                 //   "visible" : false,
                    "mRender": function(date, type, full) {
                       	 return '<button id="" onclick="show_manage_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		             }
		         } 
	          ],
              dom: "Bfrtip",
              buttons: [
              ],
            });
        };
        types_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              types_TableButtons();
            }
          };
        }();

       types_TableManageButtons.init();  
       
       
     // filter by template
	 $('#type_template_select').change(function(event) {
	    template = $("#type_template_select").val();
	    if(template == 0){
	      //  types_table.column(4).visible(false);
	    	types_table
			 .search( '' )
			 .columns().search( '' )
			 .draw();
	    }else{
	    	// if(template == 1){ // vehicles
	    	    // types_table.column(4).visible(true);	
	    	// }else{
	    	    // types_table.column(4).visible(false);	
	    	// }
	        template_name = $(this).find("option:selected").text();
	    	types_table.search( template_name ).draw();
	    }
     });
       
 });
