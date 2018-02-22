<body class="home-page">
	<?php $this->load->view('website/common'); ?>
	<section>
		<div class="container-fluid">
			<div class="ads-slider">
				<div class="slide">
					<a href="">
						<div class="ad-image" style="background-image: url('<?php echo base_url("assets/images/slider1.png"); ?>')"></div>
					</a>
				</div>
				<div class="slide">
					<a href="">
						<div class="ad-image" style="background-image: url('<?php echo base_url("assets/images/banner-772x250.jpg"); ?>')"></div>
					</a>
				</div>
				<div class="slide">
					<a href="">
						<div class="ad-image" style="background-image: url('<?php echo base_url("assets/images/slider1.png"); ?>')"></div>
					</a>
				</div>
			</div>
		</div>
	</section>

	<section class="search">
		<div class="container">
			<header>
				<div class="row align-items-center">
					<!--
					<div class="col-0 col-md-2">
						<span class="logo"><img src="<?php //echo base_url("assets/images/Dealat%20logo%20white%20background.png"); ?>" width="60px" alt=""></span>
					</div>
-->
					<div class="col-sm-12 col-md-4 col-lg-5 offset-lg-1 offset-xl-0 mt-2">
						<h2 class="text">
							<?php echo $this->lang->line('download_app'); ?>
						</h2>
					</div>
					<div class="col-6 col-sm-6 col-md-3 col-lg-2">
						<div class="download android text-center">
							<a href="">
								<?php if( $this->session->userdata("language")  == "en" ) { ?>
								<img src="<?php echo base_url('assets/images/google-play-badge.png'); ?>" height="45px" alt="">
								<?php } else {?>
								<img src="<?php echo base_url('assets/images/google-play-badge-arabic.png'); ?>" height="45px" alt="">
								<?php }?>
							</a>
						</div>
					</div>
					<div class="col-6 col-sm-6 col-md-3 col-lg-2">
						<div class="download ios text-center">
							<a href="">
								<?php if( $this->session->userdata("language")  == "en" ) { ?>
								<img src="<?php echo base_url('assets/images/ios%20en%20black.png'); ?>" height="45px" width="152.5px" alt="">
								<?php } else {?>
								<img src="<?php echo base_url('assets/images/ios%20ar%20black.png'); ?>" height="45px" width="152.5px" alt="">
								<?php }?>
							</a>
						</div>
					</div>

					<!--
					<div class="col-sm-12 col-md-4 col-lg-5 offset-lg-1 offset-xl-0 mt-2">
						<div class="search-wrapper">
							<input type="search" class="form-control" placeholder="Search">
							<span class="icon"><i class="fas fa-search"></i></span>
						</div>
					</div>
-->

				</div>

			</header>
		</div>
	</section>

	<section class="products">
		<div class="categories">
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

		<div class="container-fluid main">
			<div class="row no-gutters">
				<div class="col-md-10 left-col">
					<h5 class="recent-txt">
						<?php echo $this->lang->line('latest_ads'); ?>
					</h5>
					<div class="row ">
						<?php if($ads != null): foreach ($ads as $ad): ?>
						<div class="col-sm-6 col-lg-4">
							<div class="card mb-4">
								<div class="overlay">
									<div class="text"><i class="fas fa-info-circle"></i>
										<?php echo $this->lang->line('view_details'); ?>
									</div>
								</div>
								<!--
								<div class="featured" title="Featured Ad"></div>
								<span class="featured-icon" title="Featured Ad"><i class="fas fa-bookmark fa-lg"></i></span>
-->
								<div class="feat"><img src="<?php echo base_url('assets/images/featured/featured-ads-new.png'); ?>" alt=""></div>
								<!--									<span class="featured-icon">uu</span>-->
								<div class="card-img-top" style="background-image: url('<?php echo base_url($ad->main_image); ?>')">

								</div>
								<div class="card-body">
									<div class="row">
										<div class="col-7 mt-2">
											<div class="card-title">
												<?php echo $ad->title ?>
											</div>
											<div class="location"><span class="location-lbl"></span><span class="location-val"><?php echo $ad->city_name .'-'.$ad->location_name ?></span></div>
										</div>
										<div class="col-5 mt-2">
											<div class="views"><span class="views-val">350 </span><span class="views-lbl">Views</span></div>
											<div class="clearfix"></div>
											<div class="date"><span class="date-lbl"></span><span class="date-val"><?php $timestamp = strtotime($ad->publish_date); echo date('d-m-Y',$timestamp); ?></span></div>
										</div>
									</div>
									<?php if($ad->price != 0): ?>
									<div class="price">
										<div class="price-val">
											<?php echo $ad->price; ?>
										</div>
									</div>
									<?php endif; ?>
									<div class="fav">
										<!--										<span class="text">Add to favorites</span>-->
<!--										<span class="icon" data-added="0" title="Add to favorites"><i class="far fa-star fa-2x"></i></span>-->
										<span class="icon" data-added="0" title="Add to favorites"><i class="far fa-heart fa-2x"></i></span>
									</div>
								</div>
							</div>
						</div>
						<?php endforeach; ?>
						<?php endif; ?>

					</div>
				</div>

				<div class="col-md-2 right-col order-first order-md-last">
					<button class="btn button2 place-ad animated infinite pulse"><i class="fas fa-plus"></i> <?php echo $this->lang->line('place_ad'); ?></button>
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
