   <div class="modal fade comm_ads_details" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('ad_details') ?></h4>
        </div>
        <div class="modal-body">
                <form id="basic_info_ad" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
                   <div class="form-group" id='created_div' style="display:none">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('created_at') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	           <label class="form-control"  id='comm_created_at'>ad date</label>
	      	         </div>
	               </div> 
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('url') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <input  class="form-control col-md-7 col-xs-12" id="comm_url" value='<?php echo $this->lang->line('not_set') ?>'></input>
	      	         </div>
	               </div> 
	                  
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('position') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12 fit_select_div">
	      	               <select class="form-control select2_single" id="comm_position" tabindex="-1">
      	               	        <?php $positions = POSITION::get_position_list($this->session->userdata('language'));?>
	                         	<?php if($positions!= null): foreach ($positions as $key => $value): ?>
	                         		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
	                            <?php  endforeach; ?>
	                            <?php endif; ?> 
		                   </select>
	      	         </div>
	               </div> 
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('upload_image') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <div id="fileuploader-comm_ad">Upload</div>
	      	         </div>
	               </div>
	              
	               <div class="form-group " id='image_div' style="display: none">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('comm_image') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <img id='comm_image' style="margin: auto; height:100%;  width:100%"  src="<?php echo base_url('assets/images/google-play-badge.png'); ?>" />
	      	         </div>
	               </div>
	               
                </form>
          </div>
        <!-- </div> -->
        <input type="hidden"  id="post_id"/>
        <div class="modal-footer">
          <button   onclick="save_comm()"  id="" type="button" class="btn btn-success"><?php echo $this->lang->line('save') ?></button>
          <button   onclick="delete_comm()" id=""  type="button" class="btn btn-danger"><?php echo $this->lang->line('delete') ?></button>
          <!-- <div id="delete-modal" class="modal fade delete_modal" tabindex="-1" role="dialog" aria-hidden="true">
          	 data-toggle="modal" data-target=".delete_modal"
		    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		         <span aria-hidden="true">&times;</span>
		       </button>
		            </div>
		            <div class="modal-body text-center">
		                <h6>Are you sure you want to delete this ad?</h6>
		            </div>
		            <div class="modal-footer">
		                <button type="submit" class="btn button2 submit"  onclick="delete_comm()">Yes</button>
		            </div>
		        </div>
		    </div>
         </div> -->
        </div>
      </div>
    </div>
  </div>