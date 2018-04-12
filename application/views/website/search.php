<body class="search-page">
	<?php $this->load->view('website/common'); ?>

	<section class="search">
		<div class="container">
			<header>
				<div class="row align-items-center text-center">
					<div class="col-sm-3"><span class="logo"><img src="<?php echo base_url("assets/images/Dealat%20logo%20white%20background.png"); ?>" width="60px" alt=""></span></div>
					<div class="col-md-3"><button class="btn button2 w-75 filter"><?php echo $this->lang->line('filter'); ?></button></div>
					<!--					<div class="col-md-5 "><input type="search" class="form-control" placeholder="Search"></div>-->
					<div class="col-md-5 ">
						<div class="search-wrapper">
							<input type="search" class="form-control" placeholder="<?php echo $this->lang->line('search'); ?>">
							<span class="icon"><i class="fas fa-search"></i></span>
						</div>
					</div>
				</div>
				<!--				<button class="btn button2 ad">Place an Ad</button>-->
			</header>
		</div>
	</section>

<button class="btn button2 bookmark">bookmark</button>

	<section class="products">

		<div class="container-fluid main">
			<div class="row no-gutters">
				<div class="col-md-10 left-col">
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
													<div class="text"><i class="fas fa-info-circle"></i> View Details</div>
												</div>
												
												<?php if($ad->is_featured != 0): ?>
												<div class="feat"><img src="<?php echo base_url('assets/images/featured/featured-ads-new.png'); ?>" alt=""></div>
												<?php endif; ?>
												
												<div class="card-img-top" style="background-image: url('<?php echo base_url($ad->main_image); ?>')"></div>

												<?php if($ad->price != 0): ?>
												<div class="price">
													<div class="price-val">
														<?php echo $ad->price ?>
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
												<div class="details mb-2">
													<?php echo $ad->description ?>
												</div>
												<div class="location"><span class="location-lbl">Location: </span><span class="location-val"><?php echo $ad->city_name.'-'.$ad->location_name ?></span></div>

<!--												<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>-->
												<!--									<div class="rating">stars</div>-->


												<div class="date"><span class="date-lbl">Published </span><span class="date-val"><?php $timestamp = strtotime($ad->publish_date); echo date('d-m-Y',$timestamp); ?></span></div>
												<div class="negotiable"><span class="negotiable-lbl">Is negotiable: </span><span class="negotiable-val"><?php echo $ad->is_negotiable ?></span></div>
												<div class="seller"><span class="seller-lbl">Seller: </span><span class="seller-val"><?php echo $ad->seller_name ?></span></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<?php endforeach; ?>
						<?php else: ?>
						<?php endif;?>
						<!-- ad end -->
					</div>
				</div>
				<div class="col-md-2 right-col">
					<aside class="banners">
						<div class="banner">
							<button type="button" class="close">
							  <span>&times;</span>
							</button>
							<a href=""><img src="<?php echo base_url("assets/images/af-coinbase-2.jpg"); ?>" class="img-fluid" alt=""></a>

						</div>
						<div class="banner">
							<button type="button" class="close">
							  <span>&times;</span>
							</button>
							<a href=""><img src="<?php echo base_url("assets/images/af-coinbase-2.jpg"); ?>" class="img-fluid" alt=""></a>
						</div>
					</aside>
				</div>
			</div>
		</div>
	</section>
