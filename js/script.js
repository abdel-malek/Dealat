$(function () {
	$(".controls li").click(function () {
		$(this).siblings("li").removeClass("selected");
		$(this).addClass("selected");
	});

	$(".card").click(function () {
		$(".products .row .ad").addClass("invisible");
		$("#card-modal").modal("show");
	});

	$("button.register").click(function () {
		$("#register-modal").modal("show");
	});

	$("button.ad").click(function () {
		$(this).addClass("invisible");
		$("#ad-modal").modal("show");
	});

	$(window).scroll(function () {
		if ($(this).scrollTop() > $(".products .row").offset().top + 100 && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
			$(".products .row .ad").removeClass("invisible");
		} else {
			$(".products .row .ad").addClass("invisible");
		}

		//		if ($(this).scrollTop() > $(".categories").offset().top && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
		if ($(this).scrollTop() > $(".products").offset().top ) {
			//			$(".categories").addClass("slider-fixed");	
			$(".categories").css({
				position: "fixed",
				top: 0,
				"z-index": 10
			});
			$(".slider img").css("width", "30%");
			$(".slider h6").css("font-size", ".7rem");
//			$(".controls").css({
//				"margin-top": "120px"
//			});
		} else {
			//			$(".categories").removeClass("slider-fixed");	
//			setTimeout(function () {
				$(".categories").css({
					position: "absolute"
				});
			$(".slider img").css("width", "60%");
			$(".slider h6").css("font-size", ".6rem");
//			$(".controls").css({
//				"margin-top": "10px"
//			});
//			}, 1000);
			//			$(".categories").css({position: "relative"});
		}

	});
});
