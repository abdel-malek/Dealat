
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Dealat</title>
    
    <script type="text/javascript">
		var base_url = "<?php echo base_url() . 'index.php'; ?>";
		var site_url = "<?php echo base_url() ; ?>";
		var lang = "<?php echo $this->session->userdata('language') ?>";
	</script>

    <!-- Bootstrap -->
    <link href="<?php echo base_url() ?>admin_assets/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <?php if($this->session->userdata('language') == "ar"):?>
    	 <link href="<?php echo base_url() ?>admin_assets/css/rtl.css" rel="stylesheet"> 
    <?php endif; ?>
    
    <!-- Font Awesome -->
    <link href="<?php echo base_url() ?>admin_assets/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Cairo" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="<?php echo base_url() ?>admin_assets/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Switchery -->
    <link href="<?php echo base_url() ?>admin_assets/vendors/switchery/dist/switchery.min.css" rel="stylesheet">
    <!-- Select2 -->
    <link href="<?php echo base_url() ?>admin_assets/vendors/select2/dist/css/select2.min.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="<?php echo base_url() ?>admin_assets/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/datatable_fixedColumns/fixed_css.css" rel="stylesheet">
    
     <!-- PNotify -->
    <link href="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="<?php echo base_url() ?>admin_assets/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">
    
    <!--  slick slider  -->
	<link rel="stylesheet" href="<?php echo base_url('assets/css/slick.css'); ?>" />
	<link rel="stylesheet" href="<?php echo base_url('assets/css/slick-theme.css'); ?>" />

    <!-- Custom Theme Style -->
    <!-- <link href="<?php echo base_url() ?>admin_assets/build/css/custom.min.css" rel="stylesheet">  arabic layout --> 
    <?php if($this->session->userdata('language') == "ar"):?>
    	 <link href="<?php echo base_url() ?>admin_assets/build/css/custom.min.css" rel="stylesheet">
    <?php else: ?>
    	 <link href="<?php echo base_url() ?>admin_assets/build/css/en/custom.min.css" rel="stylesheet">
    <?php endif;?>
    
    <!-- datepicker -->
    <link href="<?php echo base_url() ?>admin_assets/datepicker/datepicker.min.css" rel="stylesheet">

    <link href="<?php echo base_url() ?>admin_assets/css/general.css" rel="stylesheet">
  </head>

  <body class="nav-md">
  <div class="dim-overlay"></div>
  	<div class="loader" id="loading-image"></div>
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0; text-align: center;">
              <a href="#" class="site_title"><span style="font-weight: bold;">Dealat CMS</span></a>
              
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile">
              <div class="profile_pic">
                <img src="<?php echo base_url() ?>admin_assets/images/default.png" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span><?php echo $this->lang->line('welcome') ?>,</span>
                <h2>Ola</h2>
              </div>
            </div>
            <!-- /menu profile quick info -->
            <br />
            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            	
              <!-- general menu -->
               
              <div class="menu_section">
                <h3><?php echo $this->lang->line('management') ?></h3>
 				 <ul class="nav side-menu">
                  <!-- <li><a href="<?php echo base_url('index.php/admin/categories_manage'); ?>"><i class="fa fa-clock-o"></i><?php echo $this->lang->line('categories_management') ?></a></li> -->
                  <li><a href="<?php echo base_url('index.php/admin/ads_manage'); ?>"><i class="fa fa-clock-o"></i><?php echo $this->lang->line('ads_management') ?></a></li>
                  <!-- <li><a><i class="fa fa-table"></i> Tables <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="tables.html">Tables</a></li>
                      <li><a href="tables_dynamic.html">Table Dynamic</a></li>
                    </ul>
                  </li> -->
                </ul>
              </div>
            </div>
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav class="" role="navigation">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

             <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href=""><i class="fa fa-sign-out pull-right"></i><?php echo $this->lang->line('log_out') ?></a></li>
                  </ul>
                </li>
                
                <!-- pending orders -->
                <li role="presentation" class="dropdown pending_orders_dropdown" > 
                  <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                  	<b></b>
                  	<span class=" fa fa-angle-down"></span>
                     <i class="fa fa-envelope-o"></i>
                    <span class="badge bg-green"></span> 
                  </a>
                  <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu" style="width:400px !important; overflow-y:scroll; max-height: 400px;">

                  </ul>
                </li>
              </ul> 
            </nav>
          </div>
        </div>
        <!-- /top navigation -->