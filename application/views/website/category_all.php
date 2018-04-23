<body class="category-page" data-category-id="<?php echo $parent_id ?>" data-category-name="<?php echo $parent_name ?>">
	<?php $this->load->view('website/common'); ?>

	<section>
		<div class="container-fluid">
			<div class="ads-slider">
			
			</div>
		</div>
	</section>

	<section class="products">
		<div class="categories">
		<div style="position: relative">
				<div class="sub-categories">
					<ul class="sub-list">
					</ul>
				</div>
			<div class="category-slider slick-slider">

				<?php if($main_categories != null): foreach ($main_categories as $category): ?>
				<div class="category" data-category-id="<?php echo $category->category_id; ?>">
					<img src="<?php echo base_url($category->web_image); ?>" width="60px" alt="<?php echo $category->category_name ?>">
					<h6 class="name">
						<?php echo $category->category_name ?>
					</h6>
				</div>
				<?php endforeach; ?>
				<?php endif; ?>

			</div>
			</div>
		</div>

		<div class="container-fluid main">
			<div class="row no-gutters">
				<div class="col-md-10 left-col">
					<div class="row mb-4 ml-0 mr-0">
						<div class="col-md-2">
							<div class="category-name">
								<?php echo $category_name; echo " ". $parent_name ?></div>
						</div>
						<div class="col-sm-8 col-md-6">
							<div class="search-wrapper mb-2">
								<input type="search" class="form-control" placeholder="<?php echo $this->lang->line('search'); ?>">
								<span class="icon"><i class="fas fa-search"></i></span>
							</div>
						</div>
						<div class="col-sm-4 col-md-3 offset-md-1"><button class="btn button2 w-75 filter"><?php echo $this->lang->line('filter'); ?></button></div>
					</div>
					<div class="row ">
						<?php if($ads != null): foreach ($ads as $ad):?>
						<div class="col-sm-6 col-lg-4 mix <?php echo $ad->category_name ?> ">
							<div class="card mb-4" data-ad-id="<?php echo $ad->ad_id ?>" data-template-id="<?php echo $ad->tamplate_id ?>">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i>
										<?php echo $this->lang->line('view_details'); ?>
									</div>
								</div>

								<?php if($ad->is_featured != 0): ?>
								<div class="feat feat-ar">
								<img class="" src="<?php echo base_url('assets/images/featured/featured_ad_ar.png'); ?>" alt="">
								</div>
								<div class="feat feat-en">
								<img class="" src="<?php echo base_url('assets/images/featured/featured-ads-new.png'); ?>" alt="">
								</div>
								<?php endif; ?>

								<?php if($ad->main_image == null): ?>
								<div class="card-img-top" style="background-image: url('<?php echo base_url('assets/images/default_ad/' .$ad->tamplate_id. '.png'); ?>')">
								</div>
								<?php else: ?>
								<div class="card-img-top" style="background-image: url('<?php echo base_url($ad->main_image); ?>')">
								</div>
								<?php endif; ?>
								
								<div class="card-body">
									<div class="row">
<!--										<div class="col-7 mt-2">-->
										<div class="col-12 mt-2">
											<div class="card-title">
												<?php echo $ad->title ?>
											</div>
											<div>
												<div class="clearfix"></div>
												<div class="category"><span class="category-lbl"></span><span class="category-val"><?php echo $ad->parent_category_name .' - '.$ad->category_name ?></span></div>
											</div>
											<div class="location"><span class="location-lbl"></span><span class="location-val"><?php echo $ad->city_name .'-'.$ad->location_name ?></span></div>
										</div>
<!--										<div class="col-5 mt-2">-->
										<div class="col-12">
<!--											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>-->
											<div class="clearfix"></div>
											<div class="date"><span class="date-lbl"></span><span class="date-val"><?php $timestamp = strtotime($ad->publish_date); echo date('d-m-Y',$timestamp); ?></span></div>
										</div>
									</div>
									<div class="price">
										<div class="price-val">
											<?php echo $ad->price; ?><?php echo $this->lang->line('sp'); ?>
										</div>
									</div>
<!--									<div class="fav">-->
										<!--										<span class="text">Add to favorites</span>-->
										<!--								<span class="icon" data-added="0" title="Add to favorites"><i class="far fa-star fa-2x"></i></span>-->
<!--
										<span class="icon" data-added="0" title="Add to favorites"><i class="far fa-heart fa-2x"></i></span>
									</div>
-->
								</div>
							</div>
						</div>
						<?php endforeach ?>
						<?php endif;?>
					</div>
				</div>
				<div class="col-md-2 right-col order-first order-md-last">
					<button class="btn button2 place-ad animated infinite pulse "><i class="fas fa-plus"></i> <?php echo $this->lang->line('place_ad'); ?></button>
					<aside class="banners">
					
					</aside>
				</div>
			</div>
		</div>

	</section>