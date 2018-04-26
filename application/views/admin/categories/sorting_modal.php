
   <div class="container"><div class="modal fade sort_category_modal" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content" style="width:350px; !important">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel">Sort</h4>
        </div>
        <div class="modal-body">
            <ol id="categories_list"  class="block__list_words block__list sorted_list simple_with_animation ">
                  
            </ol>
         </div> 
         <input type='hidden' id='sort_parent_id' />
        <div class="modal-footer">
          <button onclick="save_sorted_categories()" class="btn btn-success"><?php echo $this->lang->line('save') ?></button>
        </div>
      </div>
    </div>
  </div>
  </div>