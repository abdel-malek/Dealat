 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b id="pending_label"><?php echo $this->lang->line('commercial_ads_list') ?></b></h3>  
	              </div>
		            </div>
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	            <div class="row">
                     <div class="x_panel">
	                  <div class="x_title">
	                    <h2><i class="fa fa-bars"></i> <?php echo $this->lang->line('commercial_ads_by_category') ?></h2>
	                    <div class="clearfix"></div>
	                  </div>
	                  <div class="x_content">
	                    <div class="col-xs-3">
	                      <!-- required for floating -->
	                      <!-- Nav tabs -->
	                      <ul class="nav nav-tabs tabs-left">
	                        <li class="active"><a class="cat_tap" href="#all" id="0" data-toggle="tab"><?php echo $this->lang->line('all') ?></a>
	                        </li>
	                        <?php if(isset($main_categories) && $main_categories!= null): ?>
	                        	<?php foreach ($main_categories as $row):?>
	                        	   <li><a class="cat_tap" href="#<?php echo $row->category_id ?> "id=<?php echo $row->category_id ?> data-toggle="tab"><?php echo $row->category_name ?></a>
	                               </li>
	                        	<?php endforeach;?>
	                        <?php endif; ?>
	                      </ul>
	                    </div>
	
	                    <div class="col-xs-9">
	                      <!-- Tab panes -->
	                      <div class="tab-content">
	                        <div class="tab-pane active" id="all">
	                            <div class="pull-left">
	                              <button style='display: none' id='add_comm_btn' onclick="show_comm_ad_modal(0);" type="button" class="btn btn-primary"><li class="fa fa-plus"></li> <?php echo $this->lang->line('add_new_commercial') ?></button>
	                            </div>
	                            <table id="commercial_ads_table" class="table table-striped table-bordered">
			                      <thead>
			                        <tr>
			                          <th>#</th>
			                          <th><?php echo $this->lang->line('created_at') ?></th>
			                          <th><?php echo $this->lang->line('title') ?></th>
			                          <th><?php echo $this->lang->line('category') ?></th>
			                          <th><?php echo $this->lang->line('position') ?></th>
			                          <th><?php echo $this->lang->line('is_main_ad') ?></th>
			                          <th><?php echo $this->lang->line('details') ?></th>
			                        </tr>
			                      </thead>
			                      <tbody>
			                     </tbody>
			                    </table>
	                        </div>
	                        <!-- <div class="tab-pane" id="profile">Profile Tab.</div>
	                        <div class="tab-pane" id="messages">Messages Tab.</div>
	                        <div class="tab-pane" id="settings">Settings Tab.</div> -->
	                      </div>
	                    </div>
	
	                    <div class="clearfix"></div>
	
	                  </div>
	                </div>   
	            </div>
	          </div>
	        </div>
	        <?php $this->load->view('admin/commercial_ads/details_modal') ?>
	       <!-- </div>
	      </div> -->
        
        <!-- /page content -->