$(function () {
	$(".controls li").click(function () {
		$(this).siblings("li").removeClass("selected");
		$(this).addClass("selected");
	});

	$(".home-page .card .card-img-top,.home-page .card .overlay,.profile-page .favorites .card-left, .search-page .card-left").click(function () {
		//		$(".products .row .ad").addClass("invisible");
		$("#card-modal").modal("show");
		//		setTimeout(function () {
		//$(window).trigger('resize');
		//			$('.card-img-slider').resize();
		//			$('.card-img-slider').slick('slickSetOption', 'speed', 500, true);
		//			$('.card-img-slider').slick('setPosition');
		//			$('.card-img-slider')[0].slick.setPosition()
		//		}, 500);
		$(".card-img-slider").slick("refresh");

	});

	$("button.register").click(function () {
		$("#register-modal").modal("show");
	});

	$("button.login").click(function () {
		$("#login-modal").modal("show");
	});

	$("button.filter").click(function () {
		$("#filter-modal").modal("show");
	});

	$("button.place-ad").click(function () {
		$("button.ad").addClass("invisible");
		$("#ad-modal").modal("show");
	});

	$("#place-ad-form").submit(function () {
		//		evnt.preventDefault();
		//		evnt.stopImmediatePropagation();
		if ($("#ad-modal .featured input").is(':checked')) {
			$("#ad-modal").modal("hide");
			setTimeout(function () {
				$("#pay-modal").modal("show");
			}, 500);
		} else {
			$("#ad-modal").modal("hide");
			setTimeout(function () {
				$("#success-modal").modal("show");
			}, 500);
		}
		$("#place-ad-form").trigger("reset");
		return false;

	});

	$("#ad-modal .featured").click(function () {
		if ($(this).find("input").is(':checked')) {
			$(this).find(".warning").removeClass("d-none");
		} else {
			$(this).find(".warning").addClass("d-none");
		}
	});

	
	$(window).scroll(function () {
		//		if ($(this).scrollTop() > $(".products .row").offset().top + 100 && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
		//			$(".products .row .ad").removeClass("invisible");
		//		} else {
		//			$(".products .row .ad").addClass("invisible");
		//		}

		if ($(this).scrollTop() > $(".products").offset().top && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
			//			$(".categories").addClass("slider-fixed");	
			$(".categories").css({
				position: "fixed",
				top: 0,
				"z-index": 10
			});
			$(".category-slider img").css("width", "30%");
			$(".category-slider h6").css("font-size", ".6rem");
		} else {
			//			$(".categories").removeClass("slider-fixed");	
			//			setTimeout(function () {
			$(".categories").css({
				position: "absolute"
			});
			$(".category-slider img").css("width", "60%");
			$(".category-slider h6").css("font-size", ".7rem");
		}

	});

	//remove aside ads
	$("aside.banners .close").click(function () {
		$(this).parents(".banner").fadeOut();
	});
	//display rating in card modal
	var userRateValue = 2,
		i;
	$("#card-modal").on("show.bs.modal", function () {
		$(".rating .rate-group").each(function () {
			if ($(this).data("value") <= userRateValue) {
				$(this).children("label").css("color", "#FFCC36");
			} else {
				$(this).children("label").css("color", "#ddd");
			}
		});
	});

	$(".profile-page .rating .rate-group").each(function () {
		if ($(this).data("value") <= userRateValue) {
			$(this).children("label").css("color", "#FFCC36");
		} else {
			$(this).children("label").css("color", "#ddd");
		}
	});

	//add item to favorite
	$(".card .fav .icon").mouseenter(function () {
		$(this).css("color", "orange");
	});
	$(".card .fav .icon").mouseleave(function () {
		if ($(this).data("added") === 0) {
			$(this).css("color", "#999");
		} else if ($(this).data("added") === 1) {
			$(this).css("color", "orange");
		}
	});

	$(".card .fav .icon").click(function () {
		if ($(this).data("added") === 0) {
			$(this).html('<i class="fas fa-star fa-2x"></i>');
			$(this).css("color", "orange");
			$(this).data("added", 1);
			//			$(this).siblings(".text").text("Add to favorites");
			$(this).attr("title", "Remove from favorites");
		} else if ($(this).data("added") === 1) {
			$(this).html('<i class="far fa-star fa-2x"></i>');
			$(this).css("color", "#999");
			$(this).data("added", 0);
			//			$(this).siblings(".text").text("Remove from favorites");
			$(this).attr("title", "Add to favorites");
		}

	});

	//menu nav left and right arrows
	if ($(".controls ul").width() > $(".controls").width()) {
		$(".nav-scroller.next").removeClass("d-none");
	}

	$(".nav-scroller.next").click(function () {
		$(".nav-scroller.prev").removeClass("d-none");
		if (($(".controls ul").offset().left + $(".controls ul").width()) <= ($(".controls").offset().left + $(".controls").width())) {
			$(this).addClass("d-none");
			return;
		}
		$(".controls ul").css({
			"margin-left": "-=100px"
		});
		if (($(".controls ul").offset().left + $(".controls ul").width()) <= ($(".controls").offset().left + $(".controls").width() + 80)) {
			$(this).addClass("d-none");
		}
	});

	$(".nav-scroller.prev").click(function () {
		$(".nav-scroller.next").removeClass("d-none");
		if ($(".controls ul").offset().left >= $(".controls").offset().left) {
			$(this).addClass("d-none");
			$(".controls ul").css({
				"margin-left": "0px"
			});
			return;
		}
		$(".controls ul").css({
			"margin-left": "+=100px"
		});

		if ($(".controls ul").offset().left >= ($(".controls").offset().left - 100)) {
			$(this).addClass("d-none");
			$(".controls ul").css({
				"margin-left": "0px"
			});
		}
	});

	//show/hide social media sidebar
	$(".show-social").click(function () {
		if ($(".social-fixed .icons").data("show") === 0) {
			$(".social-fixed .icons").css("left", "0");
			$(".social-fixed .icons").data("show", 1);
			$(".social-fixed svg").removeClass("fa-angle-right");
			$(".social-fixed svg").addClass("fa-angle-left");
		} else {
			$(".social-fixed .icons").css("left", "-30px");
			$(".social-fixed .icons").data("show", 0);
			$(".social-fixed svg").removeClass("fa-angle-left");
			$(".social-fixed svg").addClass("fa-angle-right");
		}
	});

	$(".profile-page .my-ads .delete-ad").click(function () {
		$("#delete-modal").modal("show");
	});
	$(".profile-page .my-ads .edit-ad").click(function () {
		$("#ad-modal").modal("show");
	});

	$(".category").click(function () {
		var firstTime = 0,
			is_category_pg, is_home_pg;
		is_home_pg = $(this).parents(".home-page").length;
		is_category_pg = $(this).parents(".category-page").length;
		if (is_home_pg > 0) {
			firstTime = 1;
			window.location = base_url + "/home_control/load_ads_by_category_page?category_id=3&category_name=vehicles&first_time=1";
		} else if (is_category_pg > 0) {
			firstTime = 0;
			$(this).parents(".products").find(".change").html(" ");
			console.log(base_url + "/home_control/test?category_id=3&category_name=vehicles");
			$(this).parents(".products").find(".change").load(base_url + "/home_control/load_subcategories_div?category_id=2&category_name=vehicles");
		}

	});

});
