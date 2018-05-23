var reported_ads_table;
var reports_table;
var reports_buttons = [];
 $(document).ready(function() {
 	
 	if($.inArray(EXPORT_REPORTS, permissions) != -1){
		  reports_buttons.push( 
		  	 {
              extend: "excel",
              text: lang_array['export_to_excel'],
              title : 'Reported Ads Report '+ moment().format('YYYY-MM-DD'),
              className: "btn-sm",
              exportOptions: {
                 columns: [0,1,2]
              }
            }
		 );
 	}
 	
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
            "ajax": {
              "url": base_url + '/admin/items_manage/get_all_reported_items/format/json',
		      "type": "GET",
		      global: false,     // this makes sure ajaxStart is not triggered
		      'async' : false,
              dataType: 'json',
             },
            //"sAjaxSource": base_url + '/admin/items_manage/get_all_reported_items/format/json',
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
              buttons: reports_buttons
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
       
   // refresh
   setInterval(function() {
		reported_ads_table.ajax.reload( null, false );
		// reported_ads_table.rows().every( function ( rowIdx, tableLoop, rowLoop ) {
		    // var data = this.data();
		   // // console.log(data);
		    // if(data[5] == 0){ // the reported not seen
		    	// new PNotify({
	              // title: lang_array['attention'],
	              // text: lang_array['new_reported_ad']+data[1],
	              // type: 'warning',
	              // styling: 'bootstrap3',
	              // buttons: {
				        // sticker: false
				   // }
	          // });
	         // // color the row (not working!)
	         // $(this).css("background-color", "#f5dbc2");
              // setTimeout(function () {
                 // //   this.row.css("background-color", "#fff");
              // }, 2500);
		    // }
		// });
	     // set to seen
		// $.ajax({
		    // url: base_url + '/admin/items_manage/set_reports_to_seen/format/json',
		    // type: "post",
		    // dataType: "json",
		    // global: false,     // this makes sure ajaxStart is not triggered
		    // success: function(response) {
		    // },error: function(xhr, status, error){
		    // }
		 // });
		 
		$.ajax({
		    url: base_url + '/admin/items_manage/get_not_seen_reported/format/json',
		    type: "get",
		    dataType: "json",
		    global: false,     // this makes sure ajaxStart is not triggered
		    success: function(response) {
		    	var unseen_count = response.data.length;
		    	if(unseen_count != 0){
		    		$.each(response.data, function( index, value ) {
					   new PNotify({
			              title: lang_array['attention'],
			              text: lang_array['new_reported_ad']+value.title,
			              type: 'warning',
			              styling: 'bootstrap3',
			              buttons: {
						        sticker: false
						   }
			           });  
					});
		    	}
		    	console.log(response.data);
		    },error: function(xhr, status, error){
		    }
		 });
	  },  6000);  //3600000
   
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