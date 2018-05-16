 var main_ads_table;
 var main_comm_buttons = [];
 $(document).ready(function() {
 	var main_comm_TableButtons = function() {
 		
 		if($.inArray(EXPORT_COMMERCIALS, permissions) != -1){
		  main_comm_buttons.push( 
		  	 {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Main Commercials Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2 ,3]
                  }
            }
		 );
 	   }
           main_ads_table = $("#main_commercials_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/commercial_items_manage/get_main/format/json',
             "columnDefs": [
                  // {
                    // "targets": -2, // checkbox
                    // "data": null,
                    // "mRender": function(date, type, full) {
                     // // var	$html =   '<div class="">';
					    // // $html +=  '<label>';
					    // // $html +=  '<input type="checkbox" class="js-switch" />';
					    // // $html +=  ' </label>';
					    // // $html +=  '</div>';
                       // // return $html;
                       // return '<div class=""><label><input onclick="change_active_status(\'' + full[0] + '\');" name="active"  type="checkbox" class="js-switch" checked/> </label></div>';
		             // }
		         // }, 
                 {
                    "targets": -1, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_comm_ad_modal(\'' + full[0] + '\');" type="button" class="btn btn-primary" ><li class="fa fa-edit"></li></button>';
		            }
		         },
	          ],
              dom: "Bfrtip",
              buttons: main_comm_buttons,
              initComplete: function(nRow, settings, json){
	          	 activated_number =0;
	           },
            });
        };
        main_comm_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              main_comm_TableButtons();
            }
          };
        }();
       main_comm_TableManageButtons.init();
       
       
       // filter by position
	  $('#comm_position_filter_main').change(function(event) {
	    var  position_val = $("#comm_position_filter_main").val();
	    if(position_val == 0){
	    	main_ads_table
			 .search( '' )
			 .columns().search( '' )
			 .draw();
	    }else{
	        position_name = $(this).find("option:selected").text();
	    	main_ads_table.search( position_name ).draw();
	    }
	  });
	       
   });   
  
  