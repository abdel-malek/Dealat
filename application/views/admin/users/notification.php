 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b><?php echo $this->lang->line('send_public_notification') ?></b></h3>  
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
		                      	<label class="control-label col-md-4 col-sm-3 col-xs-12"><?php echo $this->lang->line('select_usres_city') ?></label>
		                         <select class="form-control select2_single" id="cities_select" tabindex="-1">
		                           <option value ='0'><?php echo $this->lang->line('all') ?></option>
		                        	<?php $cities = get_cities_array($this->session->userdata('language'));?>
		                         	<?php if($cities!= null): foreach ($cities as $key => $value): ?>
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
		             <!-- /filter form -->
	             </div> 
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
			             <form acrion="post" data-parsley-validate class="form-horizontal form-label-left">
			               <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('notify_title') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	           <input type='text' class="form-control"  id='notify_title' value></input>
			      	         </div>
				            </div> 
			               <div class="form-group">
			      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('notify_text') ?></label>
			      	         <div class="col-md-6 col-sm-6 col-xs-12">
			      	            <textarea  class="form-control col-md-7 col-xs-12" id="notify_body"></textarea>
			      	         </div>
			               </div> 
			               <div class='pull-right'>
			               	  <button onclick="send_notificaion()"  id="" type="button" class="btn btn-primary"><?php echo $this->lang->line('send_notification') ?></button>
			               </div>
			               
		                </form>
	                  </div>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>
        <!-- /page content -->