<body class="search-page">
	<?php $this->load->view('website/common'); ?>

	<section class="search mt-3">
		<div class="container">
			<header>
				<div class="row align-items-center text-center">
					<div class="col-sm-3">
						<button class="btn button2 w-75 bookmark mb-1"><?php echo $this->lang->line('save_search'); ?></button>
					</div>
					<div class="col-md-3"><button class="btn button2 w-75 filter  mb-1"><?php echo $this->lang->line('filter'); ?></button></div>
					<div class="col-md-5 ">
						<div class="search-wrapper">
							<input type="search" class="form-control" placeholder="<?php echo $this->lang->line('search'); ?>">
							<span class="icon"><i class="fas fa-search"></i></span>
						</div>
					</div>
				</div>
			</header>
		</div>
	</section>


	<section class="products">

		<div class="container main">
			<?php if($category_name){ ?>
			<div class="mb-4 search-result-label">
				<?php echo $this->lang->line('results_from') . $category_name ;?>
			</div>
			<?php }?>
			<div class="row no-gutters">
				<div class="col-md-12 left-col">
					<div class="row">
						<!-- ad start  -->
						<?php if(isset($ads) && $ads != null): ?>
						<?php foreach ($ads as $ad): ?>
						<div class="col-md-6">
							<div class="card mb-4" data-ad-id="<?php echo $ad->ad_id ?>" data-template-id="<?php echo $ad->tamplate_id ?>">
								<div class="container">

									<div class="row no-gutters">
										<div class="col-md-6">
											<div class="card-left">
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

												<?php if($ad->price != 0): ?>
												<div class="price">
													<div class="price-val">
														<?php echo $ad->price ?>
														<?php echo $this->lang->line('sp'); ?>
													</div>
												</div>
												<?php endif; ?>

												<!-- check if the ad is free and show free sign  -->

											</div>
										</div>
										<div class="col-md-6">
											<div class="card-body">
												<div class="card-title mb-1">
													<?php echo $ad->title ?>
												</div>
												<div class="details mb-1">
													<?php echo $ad->description ?>
												</div>
												<div class="category"><span class="category-lbl"></span><span class="category-val"><?php echo $ad->parent_category_name .' - '.$ad->category_name ?></span></div>
												<div class="location"><span class="location-lbl"><?php echo $this->lang->line('location'); ?>: </span><span class="location-val"><?php echo $ad->city_name.'-'.$ad->location_name ?></span></div>

												<div class="date"><span class="date-lbl"><?php echo $this->lang->line('publish_date'); ?> </span><span class="date-val"><?php $timestamp = strtotime($ad->publish_date); echo date('d-m-Y',$timestamp); ?></span></div>
												<div class="seller"><span class="seller-lbl"><?php echo $this->lang->line('seller_name'); ?>: </span><span class="seller-val"><?php echo $ad->seller_name ?></span></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<?php endforeach; ?>
						
							<?php else: ?>
							<div class="mx-auto mt-5" style="font-size: 1.1rem">
							<?php echo $this->lang->line('no_results_found'); ?>
						</div>

						<?php endif;?>
						<!-- ad end -->
					</div>
				</div>
			
			</div>
		</div>
	</section>
