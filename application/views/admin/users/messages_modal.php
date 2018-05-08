   <div class="modal fade messages_modal" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('messages') ?></h4>
        </div>
        <div class="modal-body">
        	<div class="row">
	            <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
			             <table id="messages_table"  class="table table-bordered " style="width:100% !important">
			               <thead>
			                 <tr>
			                  <th>#</th>
			                  <th><?php echo $this->lang->line('send_date') ?></th>
			                  <th><?php echo $this->lang->line('from') ?></th>
			                  <th><?php echo $this->lang->line('to') ?></th>
			                  <th><?php echo $this->lang->line('text') ?></th>
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
        <div class="modal-footer">
        </div>
      </div>
    </div>
  <!-- </div> -->