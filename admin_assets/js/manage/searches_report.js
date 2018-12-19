var searches_table;

 $(document).ready(function() {
    	var search_TableButtons = function() {
           notifications_table = $("#searches_table").DataTable({
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
             "sAjaxSource": base_url + '/admin/items_manage/get_no_result_searches/format/json',
             "columnDefs": [
                  {
                    "targets": -2, // query
                    "data": null,
                    "mRender": function(date, type, full) {
                       var data = $.parseJSON(full[4]);
                       template = $('#searches-template').html();
					   Mustache.parse(template);
					   rendered = Mustache.render(template, data);
					   return rendered;
		            }
		          }
	          ],
              dom: "Bfrtip",
              buttons: [{
                  extend: "excel",
                  text: lang_array['export_to_excel'],
                  title : 'No results searches '+ moment().format('YYYY-MM-DD'),
                  className: "btn-sm",
                }],
            });
        };
        search_TableManageButtons = function() {
          "use strict";
          return {
            init: function() {
              search_TableButtons();
            }
          };
        }();

       search_TableManageButtons.init();
 });