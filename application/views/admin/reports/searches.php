 <!-- page content -->
	       <div class="right_col" role="main">
	          <div class="">
	            <div class="page-title">
	              <div class="title_left">
	                 <h3><b id=""><?php echo $this->lang->line('no_result_searhes') ?></b></h3>  
	              </div>
	             </div> 
	            </div>
	           <!-- orders list -->
	          <div class="clearfix"></div>
	
	            <div class="row">
	              <div class="col-md-12 col-sm-12 col-xs-12">
	                <div class="x_panel">
	                  <div class="x_content">
	                    <table id="searches_table" class="table table-striped table-bordered">
	                      <thead>
	                        <tr>
	                          <th>#</th>
	                          <th><?php echo $this->lang->line('date') ?></th>
	                          <th><?php echo $this->lang->line('username') ?></th>
	                          <th><?php echo $this->lang->line('phone') ?></th>
	                          <th><?php echo $this->lang->line('search_filters') ?></th>
	                          <th><?php echo $this->lang->line('is_notify') ?></th>
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
        <!-- /page content -->

        <script id="searches-template" type="text/template">
			{{# query}}
			<div class="search"><span class="search-lbl"><?php echo $this->lang->line('search'); ?>: </span><span class="search-val">{{query}}</span></div>{{/query}} {{#category_name}}
			<div class="category"><span class="category-lbl"><?php echo $this->lang->line('category'); ?>: </span><span class="category-val">{{category_name}}</span></div>{{/category_name}} {{#city_name}}
			<div class="city"><span class="city-lbl"><?php echo $this->lang->line('city'); ?>: </span><span class="city-val">{{city_name}}</span></div>{{/city_name}} {{#location_name}}
			<div class="location"><span class="location-lbl"><?php echo $this->lang->line('location'); ?>: </span><span class="location-val">{{location_name}}</span></div>{{/location_name}} {{#price_min}}
			<div class="price"><span class="price-lbl"><?php echo $this->lang->line('item_price'); ?>: </span><span class="price-val"><?php echo $this->lang->line('from'); ?>: {{price_min}} <?php echo $this->lang->line('sp'); ?> <?php echo $this->lang->line('to'); ?>: {{price_max}} <?php echo $this->lang->line('sp'); ?></span></div>{{/price_min}} {{#type_name}}
			<div class="type "><span class="type-lbl"><?php echo $this->lang->line('type'); ?>: </span><span class="type-val">{{type_name}}</span></div>{{/type_name}} {{#model_name}}
			<div class="model "><span class="model-lbl"><?php echo $this->lang->line('type_model'); ?>: </span><span class="model-val">{{model_name}}</span></div>{{/model_name}} 
			{{#engine_capacity_min}}
			<div class="engine_capacity"><span class="engine_capacity-lbl"><?php echo $this->lang->line('engine_capacity'); ?>: </span><span class="engine_capacity-val"><?php echo $this->lang->line('from'); ?>: {{engine_capacity_min}} <?php echo $this->lang->line('to'); ?>: {{engine_capacity_max}}</span></div>{{/engine_capacity_min}} 
			{{#years_name}}
			<div class="manufacture_date"><span class="manufacture_date-lbl"><?php echo $this->lang->line('manufacture_date'); ?>: </span><span class="manufacture_date-val">{{years_name}}</span></div>{{/years_name}} {{#automatic_name}}
			<div class="is_automatic"><span class="is_automatic-lbl"><?php echo $this->lang->line('motion'); ?>: </span><span class="is_automatic-val">{{automatic_name}}</span></div>{{/automatic_name}} {{#state_name}}
			<div class="is_new"><span class="is_new-lbl"></span>
				<?php echo $this->lang->line('item_status'); ?>: <span class="is_new-val">{{state_name}}</span></div>{{/state_name}} {{#kilometers_min}}
			<div class="kilometers"><span class="kilometers-lbl"><?php echo $this->lang->line('kilometers'); ?>: </span><span class="kilometers-val">from: {{kilometers_min}} to: {{kilometers_max}}</span></div>{{/kilometers_min}} {{#space_min}}

			<div class="space"><span class="space-lbl"><?php echo $this->lang->line('space'); ?>: </span><span class="space-val"><?php echo $this->lang->line('from'); ?>: {{space_min}} <?php echo $this->lang->line('to'); ?>: {{space_max}}</span></div>{{/space_min}} {{#rooms_num_min}}
			<div class="rooms_num"><span class="rooms_num-lbl"><?php echo $this->lang->line('rooms_num'); ?>: </span><span class="rooms_num-val"><?php echo $this->lang->line('from'); ?>: {{rooms_num_min}} <?php echo $this->lang->line('to'); ?>: {{rooms_num_max}}</span></div>{{/rooms_num_min}} {{#floor_min}}
			<div class="floor"><span class="floor-lbl"><?php echo $this->lang->line('floor'); ?>: </span><span class="floor-val">from: {{floor_min}} to: {{floor_max}}</span></div>{{/floor_min}} {{#floors_number_min}}
			<div class="floors_number"><span class="floors_number-lbl"><?php echo $this->lang->line('floors_number'); ?>: </span><span class="floors_number-val">from: {{floors_number_min}} to: {{floors_number_max}}</span></div>{{/floors_number_min}} 
			{{#property_state_name}}
			<div class="education property_state_name"><span class="education-lbl"><?php echo $this->lang->line('state'); ?>: </span><span class="education-val">{{property_state_name}}</span></div>{{/property_state_name}}
			{{#furniture_name}}
			<div class="with_furniture"><span class="with_furniture-lbl"><?php echo $this->lang->line('with_furniture'); ?>: </span><span class="with_furniture-val">{{furniture_name}}</span></div>{{/furniture_name}} {{#size_min}}

			<div class="size"><span class="size-lbl"><?php echo $this->lang->line('size'); ?>: </span><span class="size-val"><?php echo $this->lang->line('from'); ?>: {{size_min}} <?php echo $this->lang->line('to'); ?>: {{size_max}}</span></div>{{/size_min}} {{#schedule_name}}

			<div class="schedule schedule_name"><span class="schedule-lbl"><?php echo $this->lang->line('schedule'); ?>: </span><span class="schedule-val">{{schedule_name}}</span></div>{{/schedule_name}} {{#education_name}}
			<div class="education education_name"><span class="education-lbl"><?php echo $this->lang->line('education'); ?>: </span><span class="education-val">{{education_name}}</span></div>{{/education_name}} {{#certificate_name}}
			<div class="certificate certificate_name"><span class="certificate-lbl"><?php echo $this->lang->line('certificate'); ?>: </span><span class="certificate-val">{{certificate_name}}</span></div>{{/certificate_name}} {{#gender_name}}
			<div class="education gender"><span class="education-lbl"><?php echo $this->lang->line('gender'); ?>: </span><span class="education-val">{{gender_name}}</span></div>{{/gender_name}} {{#salary_min}}
			<div class="salary"><span class="salary-lbl"><?php echo $this->lang->line('salary_'); ?>: </span><span class="salary-val"><?php echo $this->lang->line('from'); ?>: {{salary_min}} <?php echo $this->lang->line('sp'); ?> <?php echo $this->lang->line('to'); ?>: {{salary_max}} <?php echo $this->lang->line('sp'); ?></span></div>{{/salary_min}}
		</script>