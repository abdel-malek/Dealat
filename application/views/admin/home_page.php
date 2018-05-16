    <div class="right_col" role="main">
          <div class="">
            <div class="row top_tiles">
            
             <?php if(PERMISSION::Check_permission(PERMISSION::ADS_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
	             <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon col-md-3"><i class="fa fa-gears"></i></div>
	                   <h3 class='home_header col-md-9'><a href="<?php echo base_url('index.php/admin/items_manage'); ?>"><?php echo $this->lang->line('ads_management') ?></a></h3>
	                </div>
	             </div>
             <?php endif; ?> 
            
             <?php if(PERMISSION::Check_permission(PERMISSION::REPORTS_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
	             <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon col-md-3"><i class="fa fa-ban"></i></div>
	                   <h3 class='home_header col-md-9'><a href="<?php echo base_url('index.php/admin/items_manage/load_reported_items_page'); ?>"><?php echo $this->lang->line('reported_ads_management') ?></a></h3>
	                </div>
	             </div>
             <?php endif; ?> 
             <?php if(PERMISSION::Check_permission(PERMISSION::USERS_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon col-md-3"><i class="fa fa-group"></i></div>
	                   <h3 class='home_header col-md-9'><a href="<?php echo base_url('index.php/admin/users_manage'); ?>"><?php echo $this->lang->line('users_manage') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?> 
	         
	         <?php if(PERMISSION::Check_permission(PERMISSION::COMMERCIALS_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-money"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/commercial_items_manage/load_main_manage_page'); ?>"><?php echo $this->lang->line('main_ads') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?> 
	         <?php if(PERMISSION::Check_permission(PERMISSION::COMMERCIALS_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-money"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/commercial_items_manage'); ?>"><?php echo $this->lang->line('others') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::CATEGORIES_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-tags"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/categories_manage'); ?>"><?php echo $this->lang->line('categories_management') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::DATA_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-database"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/data_manage/load_types_page'); ?>"><?php echo $this->lang->line('types') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::DATA_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-database"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/data_manage/load_educations_page'); ?>"><?php echo $this->lang->line('educations') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::DATA_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-database"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/data_manage/load_certificates_page'); ?>"><?php echo $this->lang->line('certificates') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::DATA_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-database"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/data_manage/load_schedules_page'); ?>"><?php echo $this->lang->line('schedules') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::DATA_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-database"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/data_manage/load_cities_page'); ?>"><?php echo $this->lang->line('cities_and_areas') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::DATA_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-database"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/data_manage/load_periods_page'); ?>"><?php echo $this->lang->line('show_periods') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::NOTIFICATION_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-bell"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/users_manage/load_notification_page'); ?>"><?php echo $this->lang->line('notifications') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::ADMINS_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon col-md-3"><i class="fa fa-users"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header col-md-9'><a href="<?php echo base_url('index.php/admin/users_manage/load_admins_page'); ?>"><?php echo $this->lang->line('admins_manage') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::VIEW_ADMINS_ACTIONS , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-align-justify"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/users_manage/load_actions_page'); ?>"><?php echo $this->lang->line('admins_log') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
	         <?php if(PERMISSION::Check_permission(PERMISSION::ABOUT_MANAGE , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
                 <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                <div class="tile-stats home_card">
	                   <div class="icon"><i class="fa fa-folder-open"></i></div>
	                   <div class="count"></div>
	                   <h3 class='home_header'><a href="<?php echo base_url('index.php/admin/data_manage/load_about_manage'); ?>"><?php echo $this->lang->line('about_us_manage') ?></a></h3>
	                </div>
	             </div>
	         <?php endif; ?>
           </div>
        </div>
     </div>
   </div>
        <!-- /page content -->
