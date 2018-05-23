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
			                   <div class="col-md-6">
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
		                        <div class="col-md-6">
		                      	<label class="control-label col-md-4 col-sm-3 col-xs-12"><?php echo $this->lang->line('gender') ?></label>
		                         <select class="form-control select2_single" id="noti_gender_select" tabindex="-1">
		                           <option value ='0'><?php echo $this->lang->line('all') ?></option>
		                           <option value =<?php echo GENDER::MALE ?>><?php echo $this->lang->line('male') ?></option>
		                           <option value =<?php echo GENDER::FEMALE ?>><?php echo $this->lang->line('female') ?></option>  
		                          </select>
		                        </div>
		                     </div>
		                    </br>
		                    <div class="row">
		                      <div class="col-md-6">
		                      	<label class="control-label col-md-4 col-sm-3 col-xs-12"><?php echo $this->lang->line('from_birthday') ?></label>
		                         <input name="from" type="text" id="birthday_from" class="form-control " style="width: 316px  !important;"  data-toggle="datepicker">
		                      </div>
		                       
		                      <div class="col-md-6">
		                      	<label class="control-label col-md-4 col-sm-3 col-xs-12"><?php echo $this->lang->line('to_birthday') ?></label>
		                         <input name="to" type="text" id="birthday_to" class="form-control " style="width: 316px  !important;"  data-toggle="datepicker">
		                      </div> 	
		                    </div>
		                    </br>
		                    <div class="row">
		                      <div class="col-md-6">
		                      	<label class="control-label col-md-4 col-sm-3 col-xs-12"><?php echo $this->lang->line('send_to_user') ?></label>
		                         <select class="form-control select2_single" id="noti_users_select" tabindex="-1">
		                           <option value ='0'><?php echo $this->lang->line('all') ?></option>
		                        	<?php $users = get_users();?>
		                         	<?php if($users!= null): foreach ($users as $key => $value): ?>
		                         		  <option value="<?php echo $value->user_id; ?>"><?php echo $value->name ?></option>
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
			               	  <button   id="" type="button" class="btn btn-primary"   data-toggle="modal" data-target=".confirm-modal"><?php echo $this->lang->line('send_notification') ?></button>
			               </div>
			               
		                </form>
	                  </div>
	                </div>
	              </div>
	            </div>
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                    <table id="notification_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('send_date') ?></th>
	                          <th><?php echo $this->lang->line('sender_name') ?></th>
	                          <th><?php echo $this->lang->line('city') ?></th>
	                          <th><?php echo $this->lang->line('gender') ?></th>
	                          <th><?php echo $this->lang->line('from_birthday') ?></th>
	                          <th><?php echo $this->lang->line('to_birthday') ?></th>
	                          <th><?php echo $this->lang->line('to_user_name') ?></th>
	                          <th><?php echo $this->lang->line('notify_title') ?></th>
	                          <th><?php echo $this->lang->line('notify_text') ?></th>
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
	       <div id="confirm-modal" class="modal fade confirm-modal" tabindex="-1" role="dialog" aria-hidden="true">
		    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		            	<?php echo $this->lang->line('confirmation') ?>
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                  <span aria-hidden="true">&times;</span>
		                </button>
		            </div>
		            <div class="modal-body text-center">
		                <h6><?php echo $this->lang->line('notification_confirmation') ?></h6>
		            </div>
		            <div class="modal-footer">
		                <button type="submit" class="btn button2 submit"  onclick="send_notificaion()"><?php echo $this->lang->line('yes') ?></button>
		            </div>
		        </div>
		    </div>
         </div>
        <!-- /page content -->