 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('admins_actions') ?></b></h3>  
	              </div>
	        
	              	 <!-- filter form -->
		             <div class="row" id="filter_panel">
		              <div class="col-md-12 col-sm-12 col-xs-12">
		                <div class="x_panel">
		                  <div class="x_content" >
		                    </br>
		                     </br>
		                     <div class='row'>
			                   <div class="col-md-6">
		                      	<label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('from') ?></label>
		                         <input name="from" type="text" id="create_from" class="form-control " style="width: 316px  !important;"  data-toggle="datepicker">
		                       </div>
		                       
		                       <div class="col-md-6">
		                      	<label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('to') ?></label>
		                         <input name="to" type="text" id="create_to" class="form-control " style="width: 316px  !important;"  data-toggle="datepicker">
		                       </div>
		                        
		                     </div>
		                     </br> 
		                    <div class="x_content" >
		                     <div class='row'>
		                       <div class="col-md-6">
		                      	<label class="control-label col-md-3 col-sm-3 col-xs-12"><?php echo $this->lang->line('admin_name') ?></label>
		                          <select class="form-control select2_single" id="admin_select" tabindex="-1">
		                           <option value ='0'><?php echo $this->lang->line('all') ?></option>
		                        	<?php $admins = get_admins();?>
		                         	<?php if($admins!= null): foreach ($admins as $key => $value): ?>
		                         		<?php if($value->name != 'Ola_dev'): ?>
		                         		  <option value="<?php echo $value->admin_id; ?>"><?php echo $value->name ?></option>
		                         		<?php endif; ?>
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
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                    <table id="log_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('created_at') ?></th>
	                          <th><?php echo $this->lang->line('admin_name') ?></th>
	                          <th><?php echo $this->lang->line('action') ?></th>
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