   <div class="container"><div class="modal fade certificate_manage_modal" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content" style="width:350px; !important">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('certificate_details') ?></h4>
        </div>
        <div class="modal-body">
             <form  data-parsley-validate class="form-horizontal form-label-left">
               <div class="form-group">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('english_name') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	           <input type='text' class="form-control"  id='certificate_en_name' value </input>
      	         </div>
              </div> 
               
              <div class="form-group">
      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('arabic_name') ?></label>
      	         <div class="col-md-6 col-sm-6 col-xs-12">
      	            <input  class="form-control col-md-7 col-xs-12" id="certificate_ar_name" value ></input>
      	         </div>
              </div> 
             </form>
         </div> 
         <input type='hidden' id='certificate_id' />
        <div class="modal-footer">
          <button onclick="save_certificate()" class="btn btn-success"><?php echo $this->lang->line('save') ?></button>
          <button style="display: none"  onclick="delete_certificate()" id="certificate_delete_btn"  type="button" class="btn btn-danger"><?php echo $this->lang->line('delete') ?></button>
        </div>
      </div>
    </div>
  </div>
  </div>
