  <div class="modal fade reject_model" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel2"><?php echo $this->lang->line('set_reject_note') ?></h4>
        </div>
        <div class="modal-body">
          <label class="control-label"><?php echo $this->lang->line('write_reject_note') ?></label>
           <form data-parsley-validate class="form-horizontal form-label-left">
   	         <div class="form-group">
                <div class="col-md-6 col-sm-6 col-xs-12">
                  <textarea rows="4" cols="50" name="note"  id="reject_note" class="form-control col-md-7 col-xs-12" required="required" style="margin: 0px; width: 259px; height: 136px;"></textarea>
                </div>
                <input type="hidden" value="msg" name="action" />
             </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal"><?php echo $this->lang->line('cancel') ?></button>
          <button  onclick="perform_action('reject')" type="button" class="btn btn-primary" ><?php echo $this->lang->line('cont') ?></button>
        </div>
       </form>
      </form>
      </div>
    </div>
  </div>