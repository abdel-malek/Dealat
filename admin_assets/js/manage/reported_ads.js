var reported_ads_table;
var reports_table;

 $(document).ready(function() {
 	
 	var reported_ads_TableButtons = function() {
           reported_ads_table = $("#reported_ads_table").DataTable({
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
            aaSorting : [[2, 'asc']],
            "sAjaxSource": base_url + '/admin/items_manage/get_all_reported_items/format/json',
             "columnDefs": [
                 {
                    "targets": -1, // reports
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_reports(\'' + full[0] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button><span class="badge bg-green reports_count">'+full[4]+'</span>';
		             }
		         },
		          {
                    "targets": -2, // details
                    "data": null,
                    "mRender": function(date, type, full) {
                       return '<button id="" onclick="show_ad_details(\'' + full[0] + '\', \'' + full[3] + '\');" type="button" class="btn btn-primary" >'+lang_array['view']+'</button>';
		             }
		         }  
	          ],
              dom: "Bfrtip",
              buttons: [
                {
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'Reported Ads Report '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                  exportOptions: {
                     columns: [0,1,2]
                  }
                },
              ],
            });
        };
        reported_ads_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              reported_ads_TableButtons();
            }
          };
        }();

       reported_ads_TableManageButtons.init();  
       
      // refresh every houre
      // setInterval(function() {
		// reported_ads_table.ajax.reload( null, false );
	  // }, 3600000 ); 
   
 });
 

function show_reports (ad_id) {
	// reports datatable
	var reports_TableButtons = function() {
       reports_table = $("#reports_table").DataTable({
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
        "sAjaxSource": base_url + '/admin/items_manage/get_item_reports/format/json?ad_id='+ad_id,
         "columnDefs": [
          ],
          dom: "Bfrtip",
          buttons: [
          ],
        });
    };
    reports_TableManageButtons = function() {
      "use strict";
      return {
        init: function() {
          reports_TableButtons();
        }
      };
    }();

   reports_TableManageButtons.init();  
   $('.reports_modal').modal('show');
}

 $('.reports_modal').on('hidden.bs.modal', function () {
  	 reports_table.destroy();
 });