   <div class="modal fade ads_details" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('ad_details') ?></h4>
        </div>
        <div class="modal-body">
        	
            <div class="" role="tabpanel" data-example-id="togglable-tabs">
              <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true"><?php echo $this->lang->line('') ?></a>
                </li>
                <li role="presentation" class=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false">Profile</a>
                </li>
                <li role="presentation" class=""><a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false">Profile</a>
                </li>
              </ul>
              <div id="myTabContent" class="tab-content">
                <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
        
                  
                </div>
                <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">
                   <form id="pending_order_form" acrion="post" data-parsley-validate class="form-horizontal form-label-left">
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('created_at') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_creation_date'> 2-19-2017</label>
		      	         </div>
		             </div>
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('location') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_location'> syria</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('status') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_status'> pending</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('price') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_price'> 10000$</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('is_negotiable') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_negotiable'>Yes</label>
		      	         </div>
		             </div>
		             
		             <div class="form-group">
		      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('is_featured') ?></label>
		      	         <div class="col-md-6 col-sm-6 col-xs-12">
		      	           <label class="form-control"  id='ad_featured'>Yes</label>
		      	         </div>
		             </div>
		           </form>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">
                  <p>xxFood truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee. Qui photo
                    booth letterpress, commodo enim craft beer mlkshk </p>
                </div>
              </div>
            </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>

      </div>
    </div>
  </div>