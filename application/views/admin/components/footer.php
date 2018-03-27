        <!-- footer content -->
        <footer>
          <div class="pull-right">
            <!-- Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a> -->
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/jquery/dist/jquery.min.js"></script>
    <!-- <script src="<?php echo base_url() ?>Files/vendors/jquery/jquery_new.js"></script> -->
    <!-- Bootstrap -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    
    <!-- FastClick -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/nprogress/nprogress.js"></script>
    
    <!-- bootstrap confirmation -->
    <script src="<?php echo base_url() ?>admin_assets/js/bootstrap-confirmation.js"></script>
    <!-- FastClick -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/nprogress/nprogress.js"></script>
     <!-- iCheck -->
     <script src="<?php echo base_url() ?>admin_assets/vendors/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/jszip/dist/jszip.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/datatable_fixedColumns/dataTables.fixedColumns.min.js"></script>
    
    
     <!-- Switchery -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/switchery/dist/switchery.min.js"></script>
     <!-- Select2 -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/select2/dist/js/select2.full.min.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="<?php echo base_url() ?>admin_assets/build/js/custom.min.js"></script>
    <!-- Datatables -->
    <script src="<?php echo base_url() ?>admin_assets/build/js/moment.min.js"></script>
    <!-- /Datatables -->
    
    
    <!-- bootstrap-daterangepicker -->
    <!-- <script src="<?php echo base_url() ?>Files/js/datepicker/daterangepicker.js"></script>    -->
    
    <!-- echarts -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/echarts/dist/echarts.min.js"></script>
    
   <!-- PNotify -->
    <script src="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.buttons.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.nonblock.js"></script>
    
    <!-- datepicker -->
    <!-- <link href="<?php echo base_url() ?>Files/datepicker/datepicker.min.js" rel="stylesheet"> -->
    <script src="<?php echo base_url() ?>admin_assets/datepicker/datepicker.min.js"></script>
    
    <!-- vars --> 

   <!--  slick slider  -->
   <script src="<?php echo base_url('assets/js/slick.min.js'); ?>"></script>
   
    <!-- jquery uploader -->
    <script src="<?php echo base_url('assets/js/jquery.uploadfile.min.js'); ?>"></script>
    
    <script>
       var lang_array=[];
       if(lang == 'en'){
       	 lang_array['attention'] = 'Oh no..' ;
       	 lang_array['something_wrong'] = 'Some thing went wrong , please refresh.';
       	 lang_array['view'] = 'View' ;
       	 lang_array['success'] = 'Success' ;
       	 lang_array['ad_accepted'] = 'Ad is Accepted' ;
       	 lang_array['ad_rejected'] = 'Ad is Rejected' ;
       	 lang_array['ad_hidden'] = 'Ad is Hidden' ;
       	 lang_array['ad_show'] = 'Ad is Shown' ;
       	 lang_array['ad_deleted'] = 'Ad is Deleted' ;
       	 lang_array['ad_saved'] = 'Ad is saved' ;
       	 //datatable
       	 lang_array['sProcessing'] = 'Processing...' ;
       	 lang_array['sLengthMenu'] = 'Show _MENU_ entries' ;
       	 lang_array['sZeroRecords'] = 'No matching records found' ;
       	 lang_array['sInfo'] = 'Showing _START_ to _END_ of _TOTAL_ entries' ;
       	 lang_array['sInfoEmpty'] = 'Showing 0 to 0 of 0 entries' ;
       	 lang_array['sInfoFiltered'] = '(filtered from _MAX_ total entries)' ;
       	 lang_array['sSearch'] = 'Search:' ;
       	 lang_array['sFirst'] = 'First' ;
       	 lang_array['sPrevious'] = 'Last' ;
       	 lang_array['sNext'] = "Next" ;
       	 lang_array['sLast'] = 'Previous' ;
       }else if (lang == 'ar'){
       	 lang_array['attention'] = 'الرجاء الانتباه' ;
       	 lang_array['something_wrong'] = 'حدث خطأ ما، الرجاء تحديث الصفحة';
       	 lang_array['view'] = 'عرض' ;
       	 lang_array['success'] = 'نجاح' ;
       	 lang_array['ad_accepted'] = 'تم قبول الإعلان ' ;
       	 lang_array['ad_rejected'] = 'تم رفض الإعلان' ;
       	 lang_array['ad_hidden'] = 'تم إخفاء الإعلان' ;
       	 lang_array['ad_show'] = 'تم إظهار الإعلان' ;
       	 lang_array['ad_deleted'] = 'تم حذف الإعلان' ;
       	 lang_array['ad_saved'] = 'تم حفظ الإعلان' ;
       	 //datatable
       	 lang_array['sProcessing'] = "جارٍ التحميل..." ;
       	 lang_array['sLengthMenu'] = "أظهر _MENU_ مدخلات" ;
       	 lang_array['sZeroRecords'] = "لم يعثر على أية سجلات" ;
       	 lang_array['sInfo'] = "إظهار _START_ إلى _END_ من أصل _TOTAL_ مدخل" ;
       	 lang_array['sInfoEmpty'] = "يعرض 0 إلى 0 من أصل 0 سجل" ;
       	 lang_array['sInfoFiltered'] = "(منتقاة من مجموع _MAX_ مُدخل)";
       	 lang_array['sSearch'] = "ابحث:" ;
       	 lang_array['sFirst'] =  "الأول" ;
       	 lang_array['sPrevious'] = "السابق" ;
       	 lang_array['sNext'] = "التالي" ;
       	 lang_array['sLast'] = "الأخير" ;
       }
    </script>
    
    <!-- my js -->
    <script src="<?php echo base_url() ?>admin_assets/js/general.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/ad.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/commercial_ads.js"></script>
    <script src="<?php echo base_url() ?>admin_assets/js/manage/users.js"></script>
   
  </body>
</html>