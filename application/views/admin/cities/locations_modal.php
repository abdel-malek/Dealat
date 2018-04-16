   <div class="modal fade locations_modal" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('locations_list') ?></h4>
        </div>
        <div class="modal-body">
        	<div class="row">
	            <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                    <div class="pull-left">
                           <button onclick="show_manage_areas_modal(0);" type="button" class="btn btn-primary"><li class="fa fa-plus"></li></button>
                        </div>
			             <table id="areas_table"  class="table table-bordered order_stock_items_class" style="width:100% !important">
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
        <input  type='hidden' id='city_id'/>
        <div class="modal-footer">
        </div>
      </div>
    </div>
     <?php $this->load->view('admin/cities/locations_manage_modal') ?>
  <!-- </div> -->