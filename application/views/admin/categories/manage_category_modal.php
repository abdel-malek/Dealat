   <div class="modal fade manage_cat" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('category_details') ?></h4>
        </div>
        <div class="modal-body">
                <form id="manage_cat" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('arabic_name') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <input  class="form-control col-md-7 col-xs-12" id="cat_arabic_name"></input>
	      	         </div>
	               </div> 
	               
	                
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('english_name') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <input  class="form-control col-md-7 col-xs-12" id="cat_english_name"></input>
	      	         </div>
	               </div> 
	               
	               <div class="form-group choose_tamplate_div" style="display: none">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('templates') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12 fit_select_div">
	      	               <select class="form-control select2_single" id="cat_tamplate" tabindex="-1">
      	               	        <?php $tamplates = TAMPLATES::get_template_list($this->session->userdata('language'));?>
	                         	<?php if($tamplates!= null): foreach ($tamplates as $key => $value): ?>
	                         		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
	                            <?php  endforeach; ?>
	                            <?php endif; ?> 
		                   </select>
	      	         </div>
	               </div>
	               
	               <!-- <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('fields_status') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat is_new_cat" id="cat_is_new"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 1_info_cat" id="cat_type_id"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 1_info_cat" id="cat_type_modal_id"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 1_info_cat" id="cat_manufacture_date"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 1_info_cat" id="cat_kilometer"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 1_info_cat" id="cat_is_automatic'"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 2_info_cat" id="cat_with_furniture"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 2_info_cat" id="cat_space"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 2_info_cat" id="cat_rooms_num"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 2_info_cat" id="cat_floor"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 2_info_cat" id="cat_state"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 4_info_cat" id="cat_size"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 8_info_cat" id="cat_education_id"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 8_info_cat" id="cat_schedule_id"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 8_info_cat" id="cat_experience"></input>
	      	            <input  type="checkbox" class="form-control col-md-7 col-xs-12 template_info_cat 8_info_cat" id="cat_salary"></input>
	      	         </div>
	               </div>  -->
	               
                </form>
          </div>
        <div class="modal-footer">
          <button   onclick="save_category()"  id="" type="button" class="btn btn-success"><?php echo $this->lang->line('save') ?></button>
          <button   onclick="" id=""  type="button" class="btn btn-danger"><?php echo $this->lang->line('delete') ?></button>
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