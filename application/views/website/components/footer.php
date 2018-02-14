<footer>
		<div class="container">
			<div class="row">
				<div class="col-md-4">
					<div class="section about">
						<h3><?php echo $this->lang->line('about'); ?></h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima cum ipsum amet id sunt distinctio eos! Impedit dolorum neque alias sequi distinctio nam quas provident? Commodi nesciunt, asperiores! Quis, accusamus. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Vero qui est aperiam accusamus nobis, voluptatibus sapiente fugiat atque ipsam consequatur eius laudantium aliquid quidem hic alias officiis rem praesentium dicta.</div>
				</div>
				<div class="col-md-4">
					<div class="section reviews">
						<h3><?php echo $this->lang->line('reviews'); ?></h3>
						<div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quod aliquam adipisci voluptas placeat, dolor, non nemo praesentium eum? Velit necessitatibus vero, earum est quidem, repellat recusandae perspiciatis nemo ad iste!</div>
						<br>
						<div>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus laborum reprehenderit quibusdam veritatis commodi excepturi deserunt sint corporis, ipsum. Nobis architecto inventore, voluptatibus distinctio ratione odio minus quis, amet rerum.</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="section contact">
						<h3><?php echo $this->lang->line('contact_us'); ?></h3>
						<div class="social">
							<span class="facebook"><i class="fab fa-facebook-square fa-2x"></i></span>
							<span class="youtube"><i class="fab fa-youtube-square fa-2x"></i></span>
							<span class="twitter"><i class="fab fa-twitter-square fa-2x"></i></span>
							<span class="google"><i class="fab fa-google-plus-square fa-2x"></i></span>
						</div>
						<ul class="info">
							<li><i class="fas fa-envelope"></i> support@dealat.com</li>
							<li><i class="fas fa-at"></i> support@dealat.com</li>
							<li><i class="fas fa-phone" data-fa-transform="flip-h"></i> 13456789132</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</footer>
	
	<!--  jQuery library  -->
	<script src="<?php echo base_url("assets/js/jquery-3.2.1.min.js"); ?>"></script>
	<!--  bootstrap  -->
	<script src="<?php echo base_url("assets/js/bootstrap.min.js"); ?>"></script>
	<script src="<?php echo base_url("assets/js/popper.min.js"); ?>"></script>

	<!--  smooth scroll  -->
	<!--	<script src="js/SmoothScroll.min.js"></script>-->
	<script src="<?php echo base_url("assets/js/smooth-scroll.min.js"); ?>"></script>
	<script>
		var scroll = new SmoothScroll('a[href*="#"]', {
			speed: 2000
		});
	</script>
	<!--  nice scroll  -->
	<!--
    <script src="js/jquery.nicescroll.min.js"></script>
    <script>
        $("body").niceScroll({
            cursorwidth: "9px",
            cursoropacitymin: .2,
            scrollspeed: 200,
            mousescrollstep: 40,
            nativeparentscrolling: true,
            enablescrollonselection:true
        });
    </script>
-->
	<!--  mustache  -->
	<script src="<?php echo base_url("assets/js/mustache.min.js"); ?>"></script>
	<!--  mixit up  -->
	<script src="<?php echo base_url("assets/js/mixitup.min.js"); ?>"></script>
	<script>
		var mixer = mixitup('.products');
	</script>
	<!--  slick slider  -->
	<script src="<?php echo base_url("assets/js/slick.min.js"); ?>"></script>
	<script>
		$(document).ready(function() {
			$('.ads-slider').slick({
				infinite: true,
				slidesToShow: 1,
				mobileFirst: true,
				swipeToSlide: true,
				dots: true,
				adaptiveHeight: false,
				autoplay: true,
				autoplaySpeed: 2000,
				arrows: false,
				speed: 500,
				fade: true,
				cssEase: 'linear',
				pauseOnHover: false
			});

			$('#card-modal .card-img-slider').slick({
				infinite: true,
				slidesToShow: 1,
				mobileFirst: true,
				swipeToSlide: true
			});

			$('.category-slider').slick({
				infinite: false,
				slidesToShow: 3,
				mobileFirst: true,
				swipeToSlide: true,
				touchThreshold: 20,
				responsive: [{
						breakpoint: 1024,
						settings: {
							slidesToShow: 10,
							swipeToSlide: true

						}
					},
					{
						breakpoint: 600,
						settings: {
							slidesToShow: 6,
							swipeToSlide: true
						}
					},
					{
						breakpoint: 480,
						settings: {
							slidesToShow: 4,
							swipeToSlide: true
						}
					}
				]
			});
		});
	</script>
	<!--  fontawesome  -->
	<script src="<?php echo base_url("assets/js/fontawesome-all.min.js"); ?>"></script>
	<!--  sumoselect  -->
	<script src="<?php echo base_url("assets/js/jquery.sumoselect.min.js"); ?>"></script>
	<script>
		$(document).ready(function() {
			$("select").SumoSelect({});
		});
	</script>
	<!--  file upload  -->
	<script src="<?php echo base_url("assets/js/jquery.uploadfile.min.js"); ?>"></script>
	<script>
		$(document).ready(function() {
			$("#fileuploader-register").uploadFile({
				//				url:"upload.php",
				multiple: false,
				dragDrop: false,
				//				fileName:"myfile"
				acceptFiles: "image/*",
				maxFileSize: 10000 * 1024,
				showDelete: true,
				showPreview: true,
				previewHeight: "100px",
				previewWidth: "100px",
				uploadStr: "Upload Image"
			});
			$("#fileuploader-ad").uploadFile({
				//				url:"upload.php",
				multiple: true,
				dragDrop: true,
				fileName: "myfile",
				acceptFiles: "image/*",
				maxFileSize: 10000 * 1024,
				//see docs for localization(lang)
				showDelete: true,
				//				statusBarWidth:600,
				dragdropWidth: "100%",
				showPreview: true,
				previewHeight: "100px",
				previewWidth: "100px",
				uploadStr: "Upload Images"
			});
		});
	</script>
	<!-- WOW -->
	<script src="<?php echo base_url("assets/js/wow.min.js"); ?>"></script>
	<script>
		new WOW().init();
	</script>
	<!-- main js file -->
	<script src="<?php echo base_url("assets/js/script.js"); ?>"></script>
</body>

</html>