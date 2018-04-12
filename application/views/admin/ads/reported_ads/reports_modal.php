   <div class="modal fade reports_modal" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('reports_list') ?></h4>
        </div>
        <div class="modal-body">
        	<div class="row">
	            <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
			             <table id="reports_table"  class="table table-bordered order_stock_items_class" style="width:100% !important">
			               <thead>
			                 <tr>
			                  <th>#</th>
			                  <th><?php echo $this->lang->line('created_at') ?></th>
			                  <th><?php echo $this->lang->line('username') ?></th>
			                  <th><?php echo $this->lang->line('report_msg') ?></th>
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