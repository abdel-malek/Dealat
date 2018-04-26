   <div class="modal fade types_manage_modal" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('type_details') ?></h4>
        </div>
        <div class="modal-body">
            <form id="" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
               <div class="form-group" id='template_label_div' style="display:none">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('template') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	           <label class="form-control"  id='type_template_label'>Mobiles</label>
      	         </div>
               </div> 	
               
               <div class="form-group" id='template_select_div' style="display:none">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('template') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12 fit_select_div">
  	                <select class="form-control select2_single" id="type_template_select" tabindex="-1" style="margin-bottom: 10px; !important;">
                    	<?php $templates = TAMPLATES::get_templates_with_types($this->session->userdata('language'));?>
                     	<?php if($templates!= null): foreach ($templates as $key => $value): ?>
                     		  <option value="<?php echo $key; ?>"><?php echo $value ?></option>
                        <?php  endforeach; ?>
                        <?php endif; ?> 
	                </select>
      	         </div>
	           </div> 
	           
	           <div class="form-group" id='type_category_label_div' style="display:none">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('category') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	           <label class="form-control"  id='type_category_label'>Mobiles</label>
      	         </div>
               </div> 
	           
	           <div class="form-group" id='type_category_select_div' style="display:none">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('category') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12 fit_select_div">
  	                <select class="form-control select2_single" id="type_category_select" tabindex="-1" style="margin-bottom: 10px; !important;">
                     	<?php if($childs_cats!= null): foreach ($childs_cats as $row): ?>
                     		  <option value="<?php echo $row->category_id; ?>"><?php echo $row->parent_name .' - ' .$row->category_name ?></option>
                        <?php  endforeach; ?>
                        <?php endif; ?> 
	                </select>
      	         </div>
	           </div> 
            	
               <div class="form-group">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('english_name') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	           <input type='text' class="form-control"  id='type_en_name' value </input>
      	         </div>
               </div> 
               
               <div class="form-group">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('arabic_name') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	            <input  class="form-control col-md-7 col-xs-12" id="type_ar_name" value ></input>
      	         </div>
               </div> 
            </form>
          </div>
        <!-- </div> -->
        <input type="hidden"  id="type_id"/>
        <input type="hidden"  id="type_template_id"/>s
        <div class="modal-footer">
          <button   onclick="save_type()"  id="" type="button" class="btn btn-success"><?php echo $this->lang->line('save') ?></button>
          <button   onclick="delete_type()" id="type_delete_btn"  type="button" class="btn btn-danger"><?php echo $this->lang->line('delete') ?></button>
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