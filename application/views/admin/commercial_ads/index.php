 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b id="pending_label"><?php echo $this->lang->line('commercial_ads_list') ?></b></h3>  
	              </div>
	                    
	              	 <!-- filter form -->
		             <div class="row" id="filter_panel">
		              <div class="col-md-12 col-sm-12 col-xs-12">
		                <div class="x_panel">
		                  <div class="x_content" >
		                    </br>
		                     </br>
		                     <div class='row'>
			                   <div class="col-md-4">
		                      	<label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('position') ?></label>
		                         <select class="form-control select2_single" id="comm_position_filter_others" tabindex="-1">
		                            <option value="0"><?php echo $this->lang->line('all') ?></option>
	      	               	        <?php $positions = POSITION::get_position_list($this->session->userdata('language'));?>
		                         	<?php if($positions!= null): foreach ($positions as $key => $value): ?>
		                         		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
		                            <?php  endforeach; ?>
		                            <?php endif; ?> 
		                         </select>
		                        </div>
		                        
		                        
		                         <!-- city filter -->
		                        <div class="col-md-4">
		                      	 <label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('city') ?></label>
		                         <select class="form-control select2_single" id="comm_city_filter_other" tabindex="-1">
		                            <option value="0"><?php echo $this->lang->line('all') ?></option>
	      	               	        <?php $data = get_cities_array($this->session->userdata('language'));?>
		                         	<?php if($data!= null): foreach ($data as $key => $value): ?>
		                         		  <option value="<?php echo $value['city_id']; ?>"><?php echo $value['name'] ?></option>
		                            <?php  endforeach; ?>
		                            <?php endif; ?> 
		                         </select>
		                        </div>
		                        
		                     </div>
		                     
		                     
		                     
		                    </div>
		                  </div>
		                </div>
		              </div>
	              
	     
		        </div>
	           </div>
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
	                           <?php if(PERMISSION::Check_permission(PERMISSION::ADD_OTHER_COMMERCIAL , $this->session->userdata('LOGIN_USER_ID_ADMIN'))): ?>
	                            <div class="pull-left">
	                              <button style='display: none' id='add_comm_btn' onclick="show_comm_ad_modal(0);" type="button" class="btn btn-primary"><li class="fa fa-plus"></li> <?php echo $this->lang->line('add_new_commercial') ?></button>
	                            </div>
	                           <?php endif; ?>
	                            <table id="commercial_ads_table" class="table table-striped table-bordered">
			                      <thead>
			                        <tr>
			                          <th>#</th>
			                          <th><?php echo $this->lang->line('created_at') ?></th>
			                          <th><?php echo $this->lang->line('category') ?></th>
			                          <th><?php echo $this->lang->line('title') ?></th>
			                          <th><?php echo $this->lang->line('position') ?></th>
			                          <th><?php echo $this->lang->line('city') ?></th>
			                          <th><?php echo $this->lang->line('show_status') ?></th>
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
	       <!-- </div>
	      </div> -->
        
        <!-- /page content -->