 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('educations_list') ?></b></h3>  
	              </div>
	              	 <!-- filter form -->
		              <!-- /filter form -->
		            </div>
		      
	              
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
                  	    <div class="pull-left">
                           <button onclick="show_education_manage_modal(0);" type="button" class="btn btn-primary"><li class="fa fa-plus"></li> <?php echo $this->lang->line('add_education') ?></button>
                        </div>
	                    <table id="educations_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('english_name') ?></th>
	                          <th><?php echo $this->lang->line('arabic_name') ?></th>
	                          <th><?php echo $this->lang->line('edit') ?></th>
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
	        <?php $this->load->view('admin/educations/education_manage_modal') ?>