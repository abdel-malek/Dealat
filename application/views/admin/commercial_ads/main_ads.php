 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('main_commercials') ?></b>  <small> - <?php echo $this->lang->line('is_main_note') ?></small></h3>  
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
		                         <select class="form-control select2_single" id="comm_position_filter_main" tabindex="-1">
		                            <option value="0"><?php echo $this->lang->line('all') ?></option>
	      	               	        <?php $positions = POSITION::get_position_list($this->session->userdata('language'));?>
		                         	<?php if($positions!= null): foreach ($positions as $key => $value): ?>
		                         		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
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
		             <!-- /filter form -->
	              
	            </div>
	           <!-- orders list -->
	          <!-- <div class=""><label><input onclick="change_active_status(\'' + full[0] + '\');" name="active"  type="checkbox" class="js-switch" checked/> </label></div> -->
	          <div class="clearfix"></div>
	            <div class="pull-left">
                   <button  style="margin-left: 10px;" id='add_main_comm_btn' onclick="show_comm_ad_modal(0);" type="button" class="btn btn-primary"><li class="fa fa-plus"></li> <?php echo $this->lang->line('add_new_main_commercial') ?></button>
                </div>
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                    <table id="main_commercials_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('created_at') ?></th>
	                          <th><?php echo $this->lang->line('title') ?></th>
	                          <th><?php echo $this->lang->line('position') ?></th>
	                          <th><?php echo $this->lang->line('show_status') ?></th>
	                          <th><?php echo $this->lang->line('details') ?></th>
			                </tr>
	                      </thead>
	                      <tbody>
	                     </tbody>
	                    </table>
	                  </div>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>
	       <!-- </div>
	      </div> -->
        
        <!-- /page content -->