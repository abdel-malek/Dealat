   <div class="modal fade user_details" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel"><?php echo $this->lang->line('user_details') ?></h4>
        </div>
        <div class="modal-body">
                <form acrion="post" data-parsley-validate class="form-horizontal form-label-left">
                   <div class="form-group" id='created_div'>
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('username') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	           <label class="form-control"  id='user_name'></label>
	      	         </div>
	               </div> 
	               
	               <div class="form-group" id='created_div'>
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('city') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	           <label class="form-control"  id='user_city'></label>
	      	         </div>
	               </div> 
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('phone') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	           <label class="form-control"  id='user_phone' value></label>
	      	         </div>
	               </div> 
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('whatsup_number') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <label class="form-control"  id='user_whatsup_number' value></label>
	      	         </div>
	               </div> 
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('email') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <label class="form-control"  id='user_email' value></label>
	      	         </div>
	               </div> 
	               
	               <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('gender') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <label class="form-control"  id='user_gender' value></label>
	      	         </div>
	               </div> 
	               
	                <div class="form-group">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('birthday') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12">
	      	            <label class="form-control"  id='user_birthday' value></label>
	      	         </div>
	               </div>
	               
	              
	               <div class="form-group ">
	      	         <label class="control-label col-md-3 col-sm-3 col-xs-12 "><?php echo $this->lang->line('user_image') ?></label>
	      	         <div class="col-md-6 col-sm-6 col-xs-12" id='user_image'>
	      	            <img style="margin: auto; height:100%;  width:100%"  src="" />
	      	         </div>
	               </div>
	               
                </form>
          </div>
        <div class="modal-footer">
        </div>
      </div>
    </div>
  </div>