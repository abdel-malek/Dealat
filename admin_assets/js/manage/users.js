
var users_table;
 $(document).ready(function() {

 	var users_TableButtons = function() {
           users_table = $("#users_table").DataTable({
             "oLanguage": {
				  	"sProcessing":   "جارٍ التحميل...",
					"sLengthMenu":   "أظهر _MENU_ مدخلات",
					"sZeroRecords":  "لم يعثر على أية سجلات",
					"sInfo":         "إظهار _START_ إلى _END_ من أصل _TOTAL_ مدخل",
					"sInfoEmpty":    "يعرض 0 إلى 0 من أصل 0 سجل",
					"sInfoFiltered": "(منتقاة من مجموع _MAX_ مُدخل)",
					"sInfoPostFix":  "",
					"sSearch":       "ابحث:",
					"sUrl":          "",
					"oPaginate": {
						"sFirst":    "الأول",
						"sPrevious": "السابق",
						"sNext":     "التالي",
						"sLast":     "الأخير"
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