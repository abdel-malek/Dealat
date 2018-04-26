/*jslint browser: true*/
/*global $, alert,console,lang, Mustache, base_url, logged, user_id, hiddenFields*/

$(function () {
	//css
	$(".header-account-logged  ul").css("min-width", $(".header-account-logged").width());

	$(window).resize(function () {
		$(".header-account-logged  ul").css("min-width", $(".header-account-logged").width());
		//		$(".categories .sub-categories").addClass("d-none");
		$(".categories .sub-categories").slideUp("fast");
	});

	//global variables
	var typesData, adImgs, mixer, category_id, category_name, i, template, rendered, subcategories = [];
	adImgs = [];
	mixer = mixitup('.products');
	//	category_id = $(".category-page").data("categoryId");
	category_id = $("body").data("categoryId");
	if (!category_id) {
		//home page
		category_id = 0;
	}
	//	category_name = $(".category-page").data("categoryName");
	category_name = $("body").data("categoryName");

	//    Loading screen
	//	$(window).on('load', function () {
	//		$(".loading-overlay .spinner").fadeOut(500, function () {
	//			$(this).parent().fadeOut(500, function () {
	//				$("body").removeAttr('style');
	//				$(this).remove();
	//			});
	//		});
	//	});

	$("button.register").click(function () {
		$("#register-modal").modal("show");
	});

	$("button.login").click(function () {
		$("#login-modal").modal("show");
	});

	$("button.filter").click(function () {
		$("#filter-modal").modal("show");
	});

	function resetPostAd() {
		$("#place-ad-form").trigger("reset");
		//		$("#ad-modal a.select").css("color", "#6c757d");
		if (lang === "ar") {
			$("#ad-modal .categories-nav .select").text("اختر صنف");
			$("#ad-modal .types-nav .select").text("اختر الماركة");
		} else {
			$("#ad-modal .categories-nav .select").text("Select Category");
			$("#ad-modal .types-nav .select").text("Select type");
		}
		$("#ad-modal .ajax-file-upload-container").empty();
		uploadobjMain.reset();
		uploadobjOther.reset();
		uploadobjvideo.reset();
		$("#ad-modal .types-nav").parent(".form-group").addClass("d-none");
		adImgs = [];
		adMainImg = [];
		adVideo = [];
		$("#ad-modal .featured .warning").addClass("d-none");
		$('#ad-modal .error-message').addClass("d-none");
		$("#ad-modal .template").each(function () {
			$(this).addClass("d-none");
		});
		$('#ad-modal .city-select')[0].sumo.unSelectAll();
		$('#ad-modal .automatic-select')[0].sumo.unSelectAll();
		$('#ad-modal .status-select')[0].sumo.unSelectAll();
		$('#ad-modal .location-select')[0].sumo.unSelectAll();
		$('#ad-modal .location-select')[0].sumo.disable();
		$('#ad-modal .period-select')[0].sumo.unSelectAll();
		$('#ad-modal .schedules-select')[0].sumo.unSelectAll();
		$('#ad-modal .educations-select')[0].sumo.unSelectAll();
	}

	function resetEditAd() {
		$("#edit-ad-form").trigger("reset");
		//		$("#edit-ad-modal a.select").css("color", "#6c757d");
		if (lang === "ar") {
			$("#edit-ad-modal .types-nav .select").text("اختر الماركة");
		} else {
			$("#edit-ad-modal .types-nav .select").text("Select type");
		}
		//		$("#edit-ad-modal .ajax-file-upload-container").empty();
		$("#edit-ad-modal .types-nav").parent(".form-group").addClass("d-none");
		//		adImgs = [];
		//		$("#edit-ad-modal .featured .warning").addClass("d-none");
		$('#edit-ad-modal .error-message').addClass("d-none");
		$("#edit-ad-modal .template").each(function () {
			$(this).addClass("d-none");
		});
		$("#edit-ad-modal .ajax-file-upload-container").empty();
		uploadobjEditMain.reset();
		uploadobjEditOther.reset();
		uploadobjEditVideo.reset();
		$('#edit-ad-modal .city-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .location-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .location-select')[0].sumo.disable();
		$('#edit-ad-modal .period-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .schedules-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .educations-select')[0].sumo.unSelectAll();
	}

	function resetFilter() {
		$("#filter-form").trigger("reset");
		//		$("#filter-modal a.select").css("color", "#6c757d");
		if (lang === "ar") {
			$("#filter-modal .categories-nav .select").text("اختر صنف");
		} else {
			$("#filter-modal .categories-nav .select").text("Select Category");
		}
		$("#filter-modal .type-select").parents(".form-group").addClass("d-none");
		$("#filter-modal .model-select").parents(".form-group").addClass("d-none");
		$('#filter-modal .error-message').addClass("d-none");
		$("#filter-modal .template").each(function () {
			$(this).addClass("d-none");
		});
		$('#filter-modal .city-select')[0].sumo.unSelectAll();
		$('#filter-modal .location-select')[0].sumo.unSelectAll();
		$('#filter-modal .location-select')[0].sumo.disable();
		$('#filter-modal .manufacture-date-select')[0].sumo.unSelectAll();
		$('#filter-modal .model-select')[0].sumo.unSelectAll();
		$('#filter-modal .type-select')[0].sumo.unSelectAll();
		$('#filter-modal .automatic-select')[0].sumo.unSelectAll();
		$('#filter-modal .status-select')[0].sumo.unSelectAll();
		$('#filter-modal .schedules-select')[0].sumo.unSelectAll();
		$('#filter-modal .educations-select')[0].sumo.unSelectAll();
	}

	$("#ad-modal, #filter-modal").on("show.bs.modal", function () {
		mixer.destroy();
	});

	$("#ad-modal").on("hide.bs.modal", function () {
		resetPostAd();
		mixer = mixitup('.products');
	});

	$("#edit-ad-modal").on("hide.bs.modal", function () {
		resetEditAd();
	});

	$("#filter-modal").on("hide.bs.modal", function () {
		resetFilter();
		mixer = mixitup('.products');
	});

	$("#register-modal").on("hide.bs.modal", function () {
		$('#register-modal .error-message').addClass("d-none");
	});

	$("#login-modal").on("hide.bs.modal", function () {
		$('#login-modal .error-message').addClass("d-none");
	});

	//change selected in subcategories controls
	$(".controls li").click(function () {
		$(this).siblings("li").removeClass("selected");
		$(this).addClass("selected");
	});

	//get commercial ads
	$.ajax({
		type: "get",
		url: base_url + '/api/commercial_items_control/get_commercial_items',
		dataType: "json",
		data: {
			category_id: category_id,
			from_web: 1
		}
	}).done(function (data) {
		if (data.status === false) {
			console.log(data);
		} else {
			var adData, sliderDefaultImg, sliderImgCount = 0,
				sideImgCount = 0,
				sideDefaultImg;

			sliderDefaultImg = {
				image: "assets/images/default_top_ad.jpg"
			};
			sideDefaultImg = {
				image: "assets/images/default-side_ad.jpg"
			};

			if (data.data.length !== 0) {
				for (i in data.data) {
					if (data.data[i].position === "2") {
						//top slider ad
						sliderImgCount += 1;
						template = $('#main-commercial-ads-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$(".ads-slider").append(rendered);
						$(".ads-slider").slick("refresh");
					} else if (data.data[i].position === "1") {
						//side ad
						sideImgCount += 1;
						template = $('#side-commercial-ads-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$("aside.banners").append(rendered);
					}
				}

			}
			if (data.data.length === 0 || sliderImgCount === 0) {
				//view default ads in slider
				template = $('#main-commercial-ads-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, sliderDefaultImg);
				$(".ads-slider").append(rendered);
				$(".ads-slider").slick("refresh");
			}
			if (data.data.length === 0 || sideImgCount === 0) {
				//view default ads in and side
				for (i = 0; i < 2; i += 1) {
					template = $('#side-commercial-ads-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, sideDefaultImg);
					$("aside.banners").append(rendered);
				}
			}
		}
	});

	//get categories and subcategories
	$.ajax({
		type: "get",
		url: base_url + '/api/categories_control/get_nested_categories',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {
			//console.log(data);
		} else {
			//save subcategories in array
			for (i in data.data) {
				subcategories.push({
					categoryId: data.data[i].category_id,
					children: data.data[i].children
				});
			}
			var catData;
			catData = {
				categories: data.data
			};
			template = $('#ad-modal-categories-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, catData);
			$("#ad-modal .categories-nav .main-categories, #filter-modal .categories-nav .main-categories").append(rendered);

			for (i in data.data) {
				for (j in data.data[i].children) {

					if (data.data[i].children[j].children) {
						for (k in data.data[i].children[j].children) {
							if (data.data[i].children[j].children[k].hidden_fields) {
								hiddenFields.push({
									categoryId: data.data[i].children[j].children[k].category_id,
									hidden: $.parseJSON(data.data[i].children[j].children[k].hidden_fields)
								});
							}
						}
						var catId = data.data[i].children[j].category_id;
						$(".categories-nav .subcategory[data-category-id=" + catId + "]").addClass("dropdown-toggle");
						$(".categories-nav .subcategory[data-category-id=" + catId + "]").removeClass("last-subcategory");

						template = $('#ad-modal-subcategories-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i].children[j].children);
						$(".categories-nav .subcategory[data-category-id=" + catId + "]").after(rendered);
					} else {
						if (data.data[i].children[j].hidden_fields) {
							hiddenFields.push({
								categoryId: data.data[i].children[j].category_id,
								hidden: $.parseJSON(data.data[i].children[j].hidden_fields)
							});
						}
					}
				}
			}
			//			console.log(hiddenFields);
		}
	});

	//view subcategories when click on category slider
	$(".category").click(function () {

		var clickedCat, sub = [],
			subData, all;
		clickedCat = $(this);
		category_id = $(this).data("categoryId");
		category_name = $(this).find(".name").text().trim();

		for (i in subcategories) {
			if (subcategories[i].categoryId == category_id) {
				sub = subcategories[i].children;
				break;
			}
		}

		$(".categories .sub-categories .sub-list").empty();
		if (sub.length > 1) {
			if (lang === "ar") {
				all = "الكل";
			} else {
				all = 'All';
			}
			subData = {
				sub: sub,
				catId: category_id
			};
			template = $('#sub-categories-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, subData);
			$(".categories .sub-categories .sub-list").append(rendered);
			$(".categories .sub-categories").slideDown("fast");

			$("html, body").animate({
				scrollTop: $(".categories").offset().top
			}, 700);

			$(".categories .sub-categories").css({
				top: "35px",
				left: clickedCat.offset().left + 0.5 * clickedCat.width()
			});

		} else {
			window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + category_id + "&category_name=" + category_name;
		}

	});

	//hide subcategories menu
	$(".main").click(function (e) {
		$(".categories .sub-categories").slideUp("fast");
	});

	//click on subcategory
	$(".home-page, .category-page").on("click", ".sub-categories .name", function () {
		var id, name;
		id = $(this).data("id");
		name = $(this).text().trim();

		if ($(this).hasClass("all")) {
			window.location = base_url + "/home_control/load_ads_by_category_page_all?category_id=" + id + "&category_name=" + name + "&parent_id=" + category_id + "&parent_name=" + category_name;
		} else {
			window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + id + "&category_name=" + name + "&parent_id=" + category_id + "&parent_name=" + category_name;
		}
	});

	//get cities and areas - types - educations - schedules
	$.ajax({
		type: "get",
		url: base_url + '/api/items_control/get_data_lists',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {
			//console.log(data);
		} else {
			var j, locData, arr1 = [],
				arr2 = [];
			//locations
			locData = {
				cities: data.data.nested_locations
			};

			//register locations
			for (i in locData.cities) {
				$('#register-modal .city-select, #edit-user-info-modal .city-select, #ad-modal .city-select , #edit-ad-modal .city-select, #filter-modal .city-select').append($('<option>', {
					value: locData.cities[i].city_id,
					text: locData.cities[i].city_name
				}));
			}

			$('#register-modal .city-select')[0].sumo.reload();
			$('#ad-modal .city-select')[0].sumo.reload();
			if ($(".profile-page").length > 0) {
				$('#edit-ad-modal .city-select')[0].sumo.reload();
				$('#edit-ad-modal .location-select')[0].sumo.disable();
			}
			$('#filter-modal .city-select')[0].sumo.reload();

			$('#ad-modal .location-select')[0].sumo.disable();

			$('#filter-modal .location-select')[0].sumo.disable();

			$('#ad-modal  , #edit-ad-modal , #filter-modal ').on("change", ".city-select", function () {
				var cityId, i, j;
				cityId = $(this).find('option:selected').val();
				$('#ad-modal .location-select option:not(.placeholder), #edit-ad-modal .location-select option:not(.placeholder), #filter-modal .location-select option:not(.placeholder)').remove();
				//				$('select.model-select')[0].sumo.reload();
				for (i in locData.cities) {
					if (locData.cities[i].city_id === cityId) {
						//check if the selected type include models
						if (locData.cities[i].locations !== null) {
							//only display selected template types
							for (j in locData.cities[i].locations) {
								$('#ad-modal .location-select , #edit-ad-modal .location-select, #filter-modal .location-select').append($('<option>', {
									value: locData.cities[i].locations[j].location_id,
									text: locData.cities[i].locations[j].location_name
								}));
							}
							$('#ad-modal .location-select')[0].sumo.reload();
							$('#filter-modal .location-select')[0].sumo.reload();
							if ($(".profile-page").length > 0) {
								//		mixer.destroy();
								$('#edit-ad-modal .location-select')[0].sumo.reload();
							}

							if ($(this).parents("#ad-modal").length > 0) {
								$('#ad-modal .location-select')[0].sumo.enable();
							} else if ($(this).parents("#edit-ad-modal").length > 0) {
								$('#edit-ad-modal .location-select')[0].sumo.enable();
							} else if ($(this).parents("#filter-modal").length > 0) {
								//								$('#filter-modal .location-select')[0].sumo.enable();
							}
							//							$('#ad-modal .location-select, #edit-ad-modal .location-select, #filter-modal .location-select')[0].sumo.enable();

						} else {
							//							$('#filter-modal .model-select').parents(".form-group").addClass("d-none");
						}
						break;
					}
				}
			});

			//get types
			//convert types into array
			for (i in data.data.types) {
				arr1.push(data.data.types[i]);
			}
			for (i in arr1) {
				for (j in arr1[i]) {
					arr2.push(arr1[i][j]);
				}
			}
			typesData = {
				types: arr2
			};

			template = $('#ad-modal-types-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, typesData);
			$("#ad-modal .types-nav .types, #edit-ad-modal .types-nav .types").append(rendered);

			//if type with no models don't open a menu and remove arrow 
			$("#ad-modal .types-nav ul.dropdown-menu, #edit-ad-modal .types-nav ul.dropdown-menu").each(function () {
				if ($(this).html().trim() === "") {
					$(this).siblings(".dropdown-item").removeClass("dropdown-toggle");
					$(this).remove();
				}
			});

			//filter modal types
			for (i in typesData.types) {
				$('#filter-modal .type-select').append('<option class="d-none" value="' + typesData.types[i].type_id + '" data-template-id="' + typesData.types[i].tamplate_id + '">' + typesData.types[i].name + '</option>');
			}

			$('#filter-modal select.type-select')[0].sumo.reload();

			$('#filter-modal .type-select').change(function () {
				var typeId, i, j;
				typeId = $(this).find('option:selected').val();
				$('#filter-modal .model-select option').remove();
				//				$('select.model-select')[0].sumo.reload();
				for (i in typesData.types) {
					if (typesData.types[i].type_id === typeId) {
						//check if the selected type include models
						if (typesData.types[i].models !== null) {
							//only display selected template types
							for (j in typesData.types[i].models) {
								$('#filter-modal .model-select').append($('<option>', {
									value: typesData.types[i].models[j].type_model_id,
									text: typesData.types[i].models[j].name
								}));
							}
							$('#filter-modal .model-select').parents(".form-group").removeClass("d-none");
							$('select.model-select')[0].sumo.reload();
						} else {
							$('#filter-modal .model-select').parents(".form-group").addClass("d-none");
						}
						break;
					}
				}
			});

			//get show periods
			for (i in data.data.show_periods) {
				$('#ad-modal .period-select,#edit-ad-modal .period-select').append($('<option>', {
					value: data.data.show_periods[i].show_period_id,
					text: data.data.show_periods[i].name
				}));
			}
			$('#ad-modal .period-select')[0].sumo.reload();
			if ($(".profile-page").length > 0) {
				$('#edit-ad-modal .period-select')[0].sumo.reload();
			}
			//educations
			for (i in data.data.educations) {
				$('#ad-modal .educations-select,#edit-ad-modal .educations-select, #filter-modal .educations-select').append($('<option>', {
					value: data.data.educations[i].education_id,
					text: data.data.educations[i].name
				}));
			}

			for (i = 0; i < $('select.educations-select').length; i += 1) {
				$('select.educations-select')[i].sumo.reload();
			}

			//schedules
			for (i in data.data.schedules) {
				$('#ad-modal .schedules-select,#edit-ad-modal .schedules-select, #filter-modal .schedules-select').append($('<option>', {
					value: data.data.schedules[i].education_id,
					text: data.data.schedules[i].name
				}));
			}

			for (i = 0; i < $('select.schedules-select').length; i += 1) {
				$('select.schedules-select')[i].sumo.reload();
			}

			//change sumo select placeholder color
			//			$('select').each(function () {
			//				if ($(this).attr("multiple") !== "multiple") {
			//					var selected;
			//					selected = $(this).find('option:selected');
			//					if (selected.hasClass("placeholder")) {
			//						$(this).siblings(".CaptionCont").find("span").css("color", "#6c757d");
			//					} else {
			//						$(this).siblings(".CaptionCont").find("span").css("color", "#495057");
			//					}
			//				}
			//			});

			//			$('select').change(function () {
			//				if ($(this).attr("multiple") !== "multiple") {
			//					var selected;
			//					selected = $(this).find('option:selected');
			//					if (selected.hasClass("placeholder")) {
			//						$(this).siblings(".CaptionCont").find("span").css("color", "#6c757d");
			//					} else {
			//						$(this).siblings(".CaptionCont").find("span").css("color", "#495057");
			//					}
			//				}
			//			});
		}

	});


	//filter modal manufacture date
	var dteNow = new Date();
	var intYear = dteNow.getFullYear();
	for (i = 1970; i <= intYear; i += 1) {
		$("#filter-modal .manufacture-date-select").append($('<option>', {
			value: i,
			text: i
		}));
	}

	$('select.manufacture-date-select')[0].sumo.reload();

	var ajaxLoadTimeout;
	$(document).ajaxStart(function () {
		ajaxLoadTimeout = setTimeout(function () {
			$(".loading-overlay1").fadeIn("fast");
		}, 1000);

	}).ajaxComplete(function () {
		clearTimeout(ajaxLoadTimeout);
		$(".loading-overlay1").fadeOut("fast");
	});

	function openCardModal() {
		$("#card-modal .card").remove();
		$.ajax({
			type: "get",
			url: base_url + '/api/items_control/get_item_details',
			dataType: "json",
			data: {
				ad_id: $(this).parents(".card").data("adId"),
				template_id: $(this).parents(".card").data("templateId")
			}
		}).done(function (data) {
			if (data.status === false) {
				console.log(data);
			} else {
				console.log(data);
				var adData, negotiable, automatic, status, furniture, type, templateId;

				if (data.data.is_negotiable === "0") {
					if (lang === "ar") {
						negotiable = "غير قابل للتفاوض";
					} else {
						negotiable = "Not negotiable";
					}

				} else {
					if (lang === "ar") {
						negotiable = "قابل للتفاوض";
					} else {
						negotiable = "Negotiable";
					}
				}
				if (data.data.is_automatic === "0") {
					if (lang === "ar") {
						automatic = "يدوي";
					} else {
						automatic = "Manual";
					}

				} else {
					if (lang === "ar") {
						automatic = "اوتوماتيكي";
					} else {
						automatic = "Automatic";
					}
				}
				if (data.data.with_furniture === "0") {
					if (lang === "ar") {
						furniture = "لا";
					} else {
						furniture = "No";
					}

				} else {
					if (lang === "ar") {
						furniture = "نعم";
					} else {
						furniture = "Yes";
					}
				}
				if (data.data.is_new === "0") {
					if (lang === "ar") {
						status = "مستعمل";
					} else {
						status = "Used";
					}

				} else {
					if (lang === "ar") {
						status = "جديد";
					} else {
						status = "New";
					}
				}

				if (!data.data.main_image) {
					data.data.main_image = 'assets/images/default_ad/' + data.data.tamplate_id + '.png';
				}

				adData = {
					ad: data.data,
					date: data.data.publish_date.split(' ')[0],
					negotiable: negotiable,
					automatic: automatic,
					status: status,
					furniture: furniture
				};

				template = $('#ad-details-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, adData);
				$("#card-modal .modal-body").append(rendered);
				$('#card-modal .card-img-slider').slick({
					infinite: true,
					slidesToShow: 1,
					mobileFirst: true,
					swipeToSlide: true
				});

				if (data.data.seller_id === user_id) {
					$("#card-modal .chat, #card-modal .report, #card-modal .fav").addClass("d-none");
				} else {
					$("#card-modal .chat, #card-modal .report, #card-modal .fav").removeClass("d-none");
				}

				if (data.data.visible_phone === "0") {
					$("#card-modal details").addClass("d-none");
				}
				$("#card-modal .templates [class*='-val']").each(function () {
					if ($(this).text() === " ") {
						$(this).text(" -");
					}
				});

				var subId = data.data.category_id;
				//put all fields as shown
				$("#card-modal .field").each(function () {
					$(this).removeClass("d-none");
				});

				//hide fields from template according to hiddenFields array
				var hideArr = [];
				for (i in hiddenFields) {
					if (hiddenFields[i].categoryId == subId) {
						hideArr = hiddenFields[i].hidden;
						for (j in hideArr) {
							//						console.log(hideArr[j]);
							$("#card-modal .field." + hideArr[j]).addClass("d-none");
						}

					}
				}

				templateId = parseInt(data.data.tamplate_id, 10);
				$("#card-modal .template").each(function () {
					if ($(this).data("templateId") === templateId) {

						$(this).removeClass("d-none");
					}
				});

				if ($(".card .fav .icon").data("added") === 0) {
					$(".card .fav .icon").html('<i class="far fa-heart fa-2x"></i>');
					$(".card .fav .icon").css("color", "#999");
				} else if ($(".card .fav .icon").data("added") === 1) {
					$(".card .fav .icon").html('<i class="fas fa-heart fa-2x"></i>');
					$(".card .fav .icon").css("color", "#FF87A0");
				}

				$("#card-modal").modal("show");
				setTimeout(function () {
					$(".card-img-slider").slick("refresh");
				}, 200);

			}
		});
	}

	//card modal - view ad details
	$(".home-page .card .card-img-top,.home-page .card .overlay,.category-page .card .card-img-top,.category-page .card .overlay, .search-page .card-left").click(openCardModal);

	$(".profile-page .favorites").on("click", ".card-left", openCardModal);

	//post ad modal
	$("button.place-ad").click(function () {
		if (!logged) {
			$("#login-modal").modal("show");
			return;
		}
		$("#ad-modal").modal("show");
	});

	//view template fields when change category in place ad modal
	$("#ad-modal .categories-nav").on("click", ".last-subcategory", function () {
		//		$("#ad-modal .categories-nav a.select").css("color", "#495057");
		var templateId, subId, has_types = 0;
		templateId = $(this).data("templateId");
		subId = $(this).data("categoryId");
		//if category is properties
		//		if (templateId === 2) {
		//			$("#ad-modal .location-select").attr("required", "true");
		//			$('#ad-modal .location-select')[0].sumo.reload();
		//		} else {
		//			$("#ad-modal .location-select").removeAttr("required");
		//			$('#ad-modal .location-select')[0].sumo.reload();
		//		}

		//only show upload video in properties
		if (templateId === 2) {
			$("#ad-modal #fileuploader-ad-video").removeClass("d-none");
		} else {
			$("#ad-modal #fileuploader-ad-video").addClass("d-none");
		}

		//if category is job remove price and negotiable inputs
		if (templateId === 8) {
			$("#ad-modal input[name='price']").closest(".form-group").addClass("d-none");
			$("#ad-modal input[name='is_negotiable']").closest(".form-group").addClass("d-none");
		} else {
			$("#ad-modal input[name='price']").closest(".form-group").removeClass("d-none");
			$("#ad-modal input[name='is_negotiable']").closest(".form-group").removeClass("d-none");
		}

		//put all fields as shown
		$("#ad-modal .field").each(function () {
			$(this).removeClass("d-none");
		});

		//hide fields from template according to hiddenFields array
		var hideArr = [];
		for (i in hiddenFields) {
			if (hiddenFields[i].categoryId == subId) {
				hideArr = hiddenFields[i].hidden;
				for (j in hideArr) {
					$("#ad-modal .field." + hideArr[j]).addClass("d-none");
				}
			}
		}

		$("#ad-modal .template").each(function () {
			$(this).addClass("d-none");

			if ($(this).data("templateId") === templateId) {
				$(this).removeClass("d-none");
			}
		});

		//only display selected template types
		$("#ad-modal .types-nav .type-item").each(function () {
			$(this).addClass("d-none");
			if ($(this).data("templateId") === templateId) {
				has_types = 1;
				$(this).removeClass("d-none");
			}
		});

		//display types select only if category is vehicles, mobiles or electronics
		if (has_types !== 0) {
			//			if (templateId === 1 || templateId === 3 || templateId===4) {
			$("#ad-modal .types-nav").parent(".form-group").removeClass("d-none");
		} else {
			$("#ad-modal .types-nav").parent(".form-group").addClass("d-none");
		}
		if (lang === "ar") {
			$("#ad-modal .types-nav .select").text("اختر الماركة");
		} else {
			$("#ad-modal .types-nav .select").text("Select type");
		}
		$("#place-ad-form .type-model-id").val("");
		$("#place-ad-form .type-id").val("");
		$("#place-ad-form .category-id").val(subId);


		//change select placeholder
		var catText, subText, text;
		catText = $(this).closest(".dropdown-menu").siblings("a").text();
		subText = $(this).text();
		text = catText + "-" + subText;
		$(this).closest(".categories-nav").find(".select").text(text);

		$('#ad-modal .schedules-select')[0].sumo.selectItem(0);
		$('#ad-modal .educations-select')[0].sumo.selectItem(0);
		$('#ad-modal .automatic-select')[0].sumo.selectItem(0);
		$('#ad-modal .status-select')[0].sumo.selectItem(0);
	});

	//view template fields when change category in filter modal
	$("#filter-modal .categories-nav").on("click", ".last-subcategory", function () {
		//		$("#filter-modal .categories-nav a.select").css("color", "#495057");
		var templateId, subId, subName, has_types = 0;
		templateId = $(this).data("templateId");
		$("#filter-form input.template-id").val(templateId);
		subId = $(this).data("categoryId");
		subName = $(this).text();

		//put all fields as shown
		$("#filter-modal .field").each(function () {
			$(this).removeClass("d-none");
		});

		//hide fields from template according to hiddenFields array
		var hideArr = [];
		for (i in hiddenFields) {
			if (hiddenFields[i].categoryId == subId) {
				hideArr = hiddenFields[i].hidden;
				for (j in hideArr) {
					$("#filter-modal .field." + hideArr[j]).addClass("d-none");
				}
			}
		}

		$("#filter-modal .template").each(function () {
			$(this).addClass("d-none");

			if ($(this).data("templateId") === templateId) {
				$(this).removeClass("d-none");
			}
		});

		//only display selected template types
		$("#filter-modal .type-select option").each(function () {
			$(this).addClass("d-none");
			if ($(this).data("templateId") === templateId) {
				has_types = 1;
				$(this).removeClass("d-none");
			}
		});
		$('#filter-modal select.type-select')[0].sumo.reload();
		//display types select only if category is vehicles, mobiles or electronics
		if (has_types !== 0) {
			//		if (templateId === 1 || templateId === 3 || templateId===4) {
			$("#filter-modal .type-select").parents(".form-group").removeClass("d-none");
			$("#filter-modal .model-select").parents(".form-group").addClass("d-none");
		} else {
			$("#filter-modal .type-select").parents(".form-group").addClass("d-none");
			$("#filter-modal .model-select").parents(".form-group").addClass("d-none");
		}
		if (lang === "ar") {
			$("#filter-modal .type-select .placeholder").text("اختر الماركة");
		} else {
			$("#filter-modal .type-select .placeholder").text("Select type");
		}

		$("#filter-form .category-id").val(subId);
		$("#filter-form input[name='category_name']").val(subName);

		//change select placeholder
		var catText, subText, text;
		catText = $(this).closest(".dropdown-menu").siblings("a").text();
		subText = $(this).text();
		text = catText + "-" + subText;
		$(this).closest(".categories-nav").find(".select").text(text);

		$('#filter-modal .manufacture-date-select')[0].sumo.unSelectAll();
		$('#filter-modal .type-select')[0].sumo.selectItem(0);
		$('#filter-modal .model-select')[0].sumo.unSelectAll();
		$('#filter-modal .automatic-select')[0].sumo.selectItem(0);
		$('#filter-modal .status-select')[0].sumo.selectItem(0);
		$('#filter-modal .schedules-select')[0].sumo.unSelectAll();
		$('#filter-modal .educations-select')[0].sumo.unSelectAll();
	});

	//type model select
	$("#ad-modal .types-nav,#edit-ad-modal .types-nav, #filter-modal .types-nav").on("click", ".dropdown-item", function () {
		var typeId, typeModelId;

		$("#place-ad-form .type-model-id").val("");
		$("#place-ad-form .type-id").val("");

		if ($(this).hasClass("type")) {
			typeId = $(this).data("typeId");
			//change select placeholder
			$(this).closest(".types-nav").find(".select").text($(this).text());
		} else if ($(this).hasClass("model")) {
			typeId = $(this).data("typeId");
			typeModelId = $(this).data("typeModelId");
			//change select placeholder
			var typeName, modelName, text;
			typeName = $(this).closest(".dropdown-menu").siblings(".type").text();
			modelName = $(this).text();
			text = typeName + "-" + modelName;
			$(this).closest(".types-nav").find(".select").text(text);
		}

		if ($(this).parents("#ad-modal").length > 0) {
			$("#place-ad-form .type-id").val(typeId);
			$("#place-ad-form .type-model-id").val(typeModelId);
		} else if ($(this).parents("#edit-ad-modal").length > 0) {
			$("#edit-ad-form .type-id").val(typeId);
			$("#edit-ad-form .type-model-id").val(typeModelId);
		} else if ($(this).parents("#filter-modal").length > 0) {
			$("#filter-form .type-id").val(typeId);
			$("#filter-form .type-model-id").val(typeModelId);
		}

		//		console.log("type: " + $("#place-ad-form .type-id").val());
		//		console.log("model: " + $("#place-ad-form .type-model-id").val());
	});

	//featured ad
	$("#ad-modal .featured").click(function () {
		if ($(this).find("input").is(':checked')) {
			$(this).find(".warning").removeClass("d-none");
		} else {
			$(this).find(".warning").addClass("d-none");
		}
	});


	//upload ad main image
	var upload, uploadMain, uploadVideo, adMainImg = [],
		adVideo = [];
	if (lang === "ar") {
		upload = "اختر صور إضافية";
		uploadMain = "اختر صورة الإعلان الرئيسية";
		uploadVideo = "اختر فيديو";
	} else {
		upload = "Upload more images";
		uploadMain = "Upload main ad image";
		uploadVideo = "Upload video";
	}

	var uploadobjMain, uploadobjOther, uploadobjvideo;
	uploadobjMain = $("#fileuploader-ad-main").uploadFile({
		url: base_url + '/api/items_control/item_images_upload',
		multiple: false,
		dragDrop: false,
		fileName: "image",
		acceptFiles: "image/*",
		maxFileSize: 10000 * 1024,
		maxFileCount: 1,
		showDelete: true,
		//				statusBarWidth:600,
		dragdropWidth: "100%",
		showPreview: true,
		previewHeight: "100px",
		previewWidth: "100px",
		uploadStr: uploadMain,
		returnType: "json",
		onSuccess: function (files, data, xhr, pd) {
			adMainImg.push(data.data);
		},
		onError: function (files, status, errMsg, pd) {
			//console.log("upload failed");
		},
		deleteCallback: function (data, pd) {
			//			console.log(data.data);
			var arr;
			arr = [data.data];
			$.post(base_url + '/api/items_control/delete_images', {
					images: JSON.stringify(arr)
				},
				function (resp, textStatus, jqXHR) {
					var i, deleted;
					deleted = data.data;
					for (i in adMainImg) {
						if (adMainImg[i] === deleted) {
							adMainImg.splice(i, 1);
						}
					}
				});
		}
	});

	uploadobjOther = $("#fileuploader-ad").uploadFile({
		url: base_url + '/api/items_control/item_images_upload',
		multiple: true,
		dragDrop: false,
		fileName: "image",
		acceptFiles: "image/*",
		maxFileSize: 10000 * 1024,
		maxFileCount: 8,
		showDelete: true,
		dragdropWidth: "100%",
		showPreview: true,
		previewHeight: "100px",
		previewWidth: "100px",
		uploadStr: upload,
		returnType: "json",
		onSuccess: function (files, data, xhr, pd) {
			adImgs.push(data.data);
		},
		onError: function (files, status, errMsg, pd) {},
		deleteCallback: function (data, pd) {
			var arr;
			arr = [data.data];
			$.post(base_url + '/api/items_control/delete_images', {
					images: JSON.stringify(arr)
				},
				function (resp, textStatus, jqXHR) {
					var i, deleted;
					deleted = data.data;
					for (i in adImgs) {
						if (adImgs[i] === deleted) {
							adImgs.splice(i, 1);
						}
					}
				});
		}
	});

	//video
	uploadobjvideo = $("#fileuploader-ad-video").uploadFile({
		url: base_url + '/api/items_control/item_video_upload',
		multiple: false,
		dragDrop: false,
		fileName: "video",
		acceptFiles: "video/*",
		maxFileSize: 10000 * 1024,
		maxFileCount: 1,
		showDelete: true,
		dragdropWidth: "100%",
		showPreview: true,
		previewHeight: "100px",
		previewWidth: "100px",
		uploadStr: uploadVideo,
		returnType: "json",
		onSuccess: function (files, data, xhr, pd) {
			adVideo.push(data.data);
		},
		onError: function (files, status, errMsg, pd) {},
		deleteCallback: function (data, pd) {
			var arr;
			arr = [data.data];
			$.post(base_url + '/api/items_control/delete_vedios', {
					videos: JSON.stringify(arr)
				},
				function (resp, textStatus, jqXHR) {
					var i, deleted;
					deleted = data.data;
					for (i in adVideo) {
						if (adVideo[i] === deleted) {
							adVideo.splice(i, 1);
						}
					}
				});
		}
	});

	//place ad form submit
	$("#place-ad-form").submit(function (evnt) {
		evnt.preventDefault();
		evnt.stopImmediatePropagation();

		var i, data, uploaded_imgs = [],
			main_img = "",
			secondary_imgs = [];

		data = $(this).serializeArray();
console.log(data);
		if (adMainImg.length > 0) {
			data.push({
				name: "main_image",
				value: adMainImg[0]
			});
		}
		//copy adimgs into uploaded_imgs
		if (adImgs.length > 0) {
			for (i in adImgs) {
				uploaded_imgs.push(adImgs[i]);
			}
			secondary_imgs = uploaded_imgs;
			secondary_imgs = JSON.stringify(secondary_imgs);
			data.push({
				name: "images",
				value: secondary_imgs
			});
		}

		if (adVideo.length > 0) {
			data.push({
				name: "main_video",
				value: adVideo[0]
			});
		}

		$.ajax({
			type: "post",
			url: base_url + '/api/items_control/post_new_item',
			dataType: "json",
			data: $.param(data)
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += errorMessage[node].innerHTML;
					} else {
						wholeMessage += '<br>';
					}
				}
				$('#ad-modal .error-message').html(wholeMessage);
				$('#ad-modal .error-message').removeClass("d-none");
				$("#ad-modal").animate({
					scrollTop: $("body").offset().top
				}, 500);
			} else {
				console.log(data);
				$("#ad-modal").modal("hide");
				setTimeout(function () {
					if (lang === "ar") {
						$("#success-btn-modal .text").html("تم إنشاء إعلانك بنجاح وهو الآن بانتظار الموافقة عليه");
					} else {
						$("#success-btn-modal .text").html("Your ad has been added successfully and it is now waiting for approval");
					}

					$("#success-btn-modal").modal("show");
				}, 500);
				//reset
				resetPostAd();
			}
		});
	});

	$("#success-btn-modal .submit").click(function () {
		$("#success-btn-modal").modal("hide");
	});

	//	$(".place-ad").css("width", $(".right-col").width());
	$(window).scroll(function () {
		if ($(this).scrollTop() > $(".products").offset().top) {
			$(".categories").css({
				position: "fixed",
				top: 0,
				"z-index": 10
			});

			$(".category-slider img").css("width", "30%");
			if (lang === "ar") {
				$(".category-slider h6").css("font-size", ".7rem");
			} else {
				$(".category-slider h6").css("font-size", ".6rem");
			}

		} else {
			$(".categories, .category-slider img, .category-slider h6").removeAttr("style");
		}
		if ($(window).innerWidth() > 768) {
			if ($(".right-col").length > 0 && $(this).scrollTop() > ($(".right-col").offset().top - $(".categories").height())) {
				$(".place-ad").css({
					//				width: $(".right-col").width(),
					top: $(".categories").height(),
					right: "20px",
					position: "fixed"
				});
			} else {
				$(".place-ad").removeAttr("style");
			}
		} else {
			$(".place-ad").removeAttr("style");
		}
	});

	//remove aside ads
	$("aside.banners").on("click", ".close", function () {
		$(this).parents(".banner").fadeOut();
	});

	//display rating in card modal
	var userRateValue = 2;
	$("#card-modal").on("show.bs.modal", function () {
		$(".rating .rate-group").each(function () {
			if ($(this).data("value") <= userRateValue) {
				$(this).children("label").css("color", "#FFCC36");
			} else {
				$(this).children("label").css("color", "#ddd");
			}
		});
	});

	//	$(".profile-page .rating .rate-group").each(function () {
	//		if ($(this).data("value") <= userRateValue) {
	//			$(this).children("label").css("color", "#FFCC36");
	//		} else {
	//			$(this).children("label").css("color", "#ddd");
	//		}
	//	});

	//menu nav left and right arrows
	if ($(".controls ul").width() > $(".controls").width()) {
		if (lang === "ar") {
			$(".nav-scroller.prev").removeClass("d-none");
		} else {
			$(".nav-scroller.next").removeClass("d-none");
		}
	}

	$(".nav-scroller.next").click(function () {
		$(".nav-scroller.prev").removeClass("d-none");
		if (($(".controls ul").offset().left + $(".controls ul").width()) <= ($(".controls").offset().left + $(".controls").width())) {
			$(this).addClass("d-none");
			return;
		}
		if (lang === "ar") {
			$(".controls ul").css("margin-right", "+=100px");
		} else {
			$(".controls ul").css("margin-left", "-=100px");
		}
		if (($(".controls ul").offset().left + $(".controls ul").width()) <= ($(".controls").offset().left + $(".controls").width() + 100)) {
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
		if (lang === "ar") {
			$(".controls ul").css("margin-right", "-=100px");
		} else {
			$(".controls ul").css("margin-left", "+=100px");
		}

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

	//search
	$('input[type="search"]').keypress(function (e) {
		var key, data, query = 0;
		key = e.which;
		query = $(this).val();

		if (key === 13) {
			data = {
				query: $(this).val()
			};

			sessionStorage.removeItem('bookmark');
			sessionStorage.setItem('bookmark', $.param({
				query: query,
				search: query
			}));

			if (category_id) {
				window.location = base_url + '/search_control?query=' + query + '&category_id=' + category_id + '&category_name=' + category_name;
			} else {
				window.location = base_url + '/search_control?query=' + query;
			}
		}
	});

	//bookmark search
	$(".search-page .bookmark").click(function () {
		if (!logged) {
			$("#login-modal").modal("show");
			return;
		}
		var data = sessionStorage.getItem('bookmark');

		$.ajax({
			type: "post",
			url: base_url + '/api/users_control/mark_search',
			dataType: "json",
			data: sessionStorage.getItem('bookmark')
		}).done(function (data) {
			if (data.status === false) {} else {
				if (lang === "ar") {
					$("#success-modal .text").html("تم حفظ البحث بنجاح");
				} else {
					$("#success-modal .text").html("Search saved successfully");
				}
				$("#success-modal").modal("show");
				setTimeout(function () {
					$("#success-modal").modal("hide");
				}, 3000);
			}
		});
	});

	//filter
	$('#filter-form').submit(function (e) {
		e.preventDefault();
		e.stopPropagation();
		var data, data1, query = 0,
			typeModelArr = [],
			typeModelNameArr = "",
			schedulesArr = [],
			schedulesNameArr = "",
			educationArr = [],
			educationNameArr = "",
			manufactureDateArr = [],
			manufactureDateNameArr = "";
		data = $(this).serializeArray();

		if ($('#filter-modal .manufacture-date-select option:selected').length > 0) {
			$('#filter-modal .manufacture-date-select option:selected').each(function (i) {
				manufactureDateArr.push($(this).val());
				manufactureDateNameArr += $(this).text() + ', ';
				$('#filter-modal .manufacture-date-select')[0].sumo.unSelectItem(i);
			});
			manufactureDateArr = JSON.stringify(manufactureDateArr);
			manufactureDateNameArr = manufactureDateNameArr.slice(0, -2);

			data.push({
				name: "manufacture_date",
				value: manufactureDateArr
			}, {
				name: "years_name",
				value: manufactureDateNameArr
			});
		}

		if ($('#filter-modal .model-select option:selected').length > 0) {
			$('#filter-modal .model-select option:selected').each(function (i) {
				typeModelArr.push($(this).val());
				typeModelNameArr += $(this).text() + ', ';
				$('#filter-modal .model-select')[0].sumo.unSelectItem(i);
			});
			typeModelArr = JSON.stringify(typeModelArr);
			typeModelNameArr = typeModelNameArr.slice(0, -2);
			data.push({
				name: "type_model_id",
				value: typeModelArr
			}, {
				name: "model_name",
				value: typeModelNameArr
			});
		}

		if ($('#filter-modal .schedules-select option:selected').length > 0) {
			$('#filter-modal .schedules-select option:selected').each(function (i) {
				schedulesArr.push($(this).val());
				schedulesNameArr += $(this).text() + ', ';
				$('#filter-modal .schedules-select')[0].sumo.unSelectItem(i);
			});
			schedulesArr = JSON.stringify(schedulesArr);
			schedulesNameArr = schedulesNameArr.slice(0, -2);
			data.push({
				name: "schedules_id",
				value: schedulesArr
			}, {
				name: "schedule_name",
				value: schedulesNameArr
			});
		}

		if ($('#filter-modal .educations-select option:selected').length > 0) {
			$('#filter-modal .educations-select option:selected').each(function (i) {
				educationArr.push($(this).val());
				educationNameArr += $(this).text() + ', ';
				$('#filter-modal .educations-select')[0].sumo.unSelectItem(i);
			});
			educationArr = JSON.stringify(educationArr);
			educationNameArr = educationNameArr.slice(0, -2);
			data.push({
				name: "education_id",
				value: educationArr
			}, {
				name: "education_name",
				value: educationNameArr
			});
		}

		var templateId = $("#filter-form input.template-id").val();

		if ($('#filter-modal .template[data-template-id="' + templateId + '"] .status-select option:selected').val() !== "") {
			data.push({
				name: "state_name",
				value: $('#filter-modal .template[data-template-id="' + templateId + '"] .status-select option:selected').text()
			});
		}

		if ($('#filter-modal .city-select option:selected').val() !== "") {
			data.push({
				name: "city_name",
				value: $('#filter-modal .city-select option:selected').text()
			});
		}

		if ($('#filter-modal .location-select option:selected').val() !== "") {
			data.push({
				name: "location_name",
				value: $('#filter-modal .location-select option:selected').text()
			});
		}
		if ($('#filter-modal .type-select option:selected').val() !== "") {
			data.push({
				name: "type_name",
				value: $('#filter-modal .type-select option:selected').text()
			});
		}
		if ($('#filter-modal select[name="with_furniture"] option:selected').val() !== "") {
			data.push({
				name: "furniture_name",
				value: $('#filter-modal select[name="with_furniture"] option:selected').text()
			});
		}
		if ($('#filter-modal .automatic-select option:selected').val() !== "") {
			data.push({
				name: "automatic_name",
				value: $('#filter-modal .automatic-select option:selected').text()
			});
		}

		query = $(this).find('input.search').val();
		if (query !== "") {
			data.push({
				name: "query",
				value: query
			});
		}

		data1 = $.param(data);

		sessionStorage.removeItem('bookmark');
		sessionStorage.setItem('bookmark', data1);

		$.ajax({
			type: "get",
			url: base_url + '/api/items_control/search',
			dataType: "json",
			data: data1
		}).done(function (data) {
			if (data.status === false) {} else {
				window.location = base_url + '/search_control?' + data1;
			}
		});
	});

	//open chat modal when click on chat with seller
	$("#card-modal button.chat").click(function () {
		if (!logged) {
			$("#card-modal").modal("hide");

			setTimeout(function () {
				$("#login-modal").modal("show");
			}, 500);
			return;
		}
		var adId;
		adId = $("#card-modal").find(".card").data("adId");
		$("#chat-form .ad-id").val(adId);

		$("#chat-modal .chat-header .ad-name").text($(this).parents(".modal-content").find(".card-title").text());
		$("#chat-modal .chat-header .user-name").text($(this).parents(".modal-content").find(".seller-val").text());
		
		//get chat message
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_chat_messages',
			dataType: "json",
			data: {
				ad_id: adId
			}
		}).done(function (data) {
			if (data.status === false) {} else {
				$("#chat-modal .chat").empty();
				for (i in data.data) {
					if (data.data[i].to_seller === "1") {
						// message from me to seller
						template = $('#chat-self-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$("#chat-modal .chat").append(rendered);
					} else {
						template = $('#chat-other-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$("#chat-modal .chat").append(rendered);
					}
				}
				$("#card-modal").modal("hide");
				setTimeout(function () {
					$("#chat-modal").modal("show");
				}, 500);
			}
		});
	});

	var notSeenMsgs = 0,
		notSeenInterval;
	//check for not seen messages
	function checkNewMsgs() {
		//		console.log("sess");
		//get chat messages
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_my_chat_sessions',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: $("#chat-form").serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				notSeenMsgs = 0;
				var newMsgSessions = [];
				for (i in data.data) {
					if ((user_id == data.data[i].seller_id && data.data[i].seller_seen == 0) || (user_id == data.data[i].user_id && data.data[i].user_seen == 0)) {
						newMsgSessions.push(data.data[i].chat_session_id);
						notSeenMsgs += 1;
					}
				}
				if (notSeenMsgs > 0) {
					$(".header-account-logged .new-msg").removeClass("d-none");
					//					$(".profile-page .chat-tab-link .new-msg").removeClass("d-none");
					//					$(".profile-page .session .new-msg").removeClass("d-none");
				} else {
					$(".header-account-logged .new-msg").addClass("d-none");
					//					$(".profile-page .chat-tab-link .new-msg").addClass("d-none");
					$(".profile-page .session .new-msg").addClass("d-none");
				}
				if ($(".profile-page").length > 0) {
					$(".profile-page .sessions .session").each(function () {
						$(this).removeAttr("style");
						for (i in newMsgSessions) {
							if ($(this).data("sessionId") == newMsgSessions[i]) {
								$(this).css("background-color", "rgba(195, 10, 48, 0.22)");
								$(this).find(".new-msg").removeClass("d-none");
							}
						}
					});
				}
				notSeenInterval = setTimeout(checkNewMsgs, 3000);
			}
		});
	}

	if (logged) {
		checkNewMsgs();
		//		notSeenInterval = setTimeout(checkNewMsgs, 3000);
	}

	//auto check for new messages for an opened chat session and append it
	function checkLiveSessionMsg() {
		//console.log("msg");
		//get chat messages for a live(opened) chat session
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_chat_messages',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: $("#chat-form").serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				lastMsgId = $('#chat-modal .chat li').last().data("msgId");
				var msgDate = data.data[data.data.length - 1].created_at.split(' ')[0];
				var startIndex, isSeller;
				isSeller = $("#chat-modal .is-seller").val();
				
				if (data.data[data.data.length - 1].message_id != lastMsgId) {
					//to know last msg index in data.data array
					for (i = data.data.length - 1; i >= 0; i -= 1) {
						if (data.data[i].message_id == lastMsgId) {
							startIndex = i;
							break;
						}
					}
					for (i = startIndex + 1; i < data.data.length; i += 1) {
						//check msg date
						if (data.data[i].created_at.split(' ')[0] !== msgDate) {
							//update msg date
							msgDate = data.data[i].created_at.split(' ')[0];
							$("#chat-modal .chat").append('<div class="day">' + msgDate + '</div>');
						}
						data.data[i].time = new Date(data.data[i].created_at).toLocaleString('en-US', {
							hour: 'numeric',
							minute: 'numeric',
							hour12: true
						});
						
						if((isSeller === "1" && data.data[i].to_seller === "1") || (isSeller === "0" && data.data[i].to_seller === "0") ){
							template = $('#chat-other-template').html();
						} else{
							template = $('#chat-self-template').html();
						}
						
//						template = $('#chat-other-template').html();
						Mustache.parse(template);
						rendered = Mustache.render(template, data.data[i]);
						$("#chat-modal .chat").append(rendered);
					}
					$("#chat-modal .chat").stop().animate({
						scrollTop: $("#chat-modal .chat")[0].scrollHeight
					}, 300);
				}
				intervalId = setTimeout(checkLiveSessionMsg, 100);
			}
		});
	}

	var intervalId;
	$('#chat-modal').on('shown.bs.modal', function (e) {
		$("#chat-modal .chat").stop().animate({
			scrollTop: $("#chat-modal .chat")[0].scrollHeight
		}, 300);
		$("#chat-modal input[name='msg']").focus();
		var lastMsgId;
		intervalId = setTimeout(checkLiveSessionMsg);
	});

	$('#chat-modal').on('hide.bs.modal', function (e) {
		//		clearInterval(intervalId);
		clearTimeout(intervalId);
	});

	$("#chat-form").submit(function (e) {
		e.preventDefault();
		e.stopPropagation();

		$.ajax({
			type: "post",
			url: base_url + '/api/users_control/send_msg',
			dataType: "json",
			global: false, // this makes sure ajaxStart is not triggered
			data: $(this).serialize()
		}).done(function (data) {
			console.log(data.data);
			if (data.status === false) {} else {
				data.data.time = new Date(data.data.created_at).toLocaleString('en-US', {
					hour: 'numeric',
					minute: 'numeric',
					hour12: true
				});

				template = $('#chat-self-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, data.data);
				$("#chat-modal .chat").append(rendered);

				$("#chat-form input[name='msg']").val("");
				$("#chat-modal .chat").stop().animate({
					scrollTop: $("#chat-modal .chat")[0].scrollHeight
				}, 300);
			}
		});
	});


	//language
	$(".language-switch span").click(function (e) {
		var language = "en";
		if ($(this).hasClass("english")) {
			language = "en";
		} else if ($(this).hasClass("arabic")) {
			language = "ar";
		}
		$.ajax({
			type: "get",
			url: base_url + '/users_control_web/change_language?language=' + language,
			dataType: "json",
			complete: function () {
				location.reload();
			}
		});
	});

	//register
	$('#register-form').submit(function (e) {
		$('#register-modal .error-message').addClass("d-none");
		var pass1, pass2, data, phone, whatsup;
		phone = $(this).find(".phone").val();

		if (phone.length !== 9) {
			if (lang === "ar") {
				$('#register-modal .error-message').text("رقم الهاتف يجب أن يتكون من 9 أرقام");
			} else {
				$('#register-modal .error-message').text("Phone must be exactly 9 characters in length");
			}
			$('#register-modal .error-message').removeClass("d-none");
			$("#register-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}

		pass1 = $(this).find(".password").val();
		pass2 = $(this).find(".confirm_password").val();
		if (pass1 !== pass2) {
			if (lang === "ar") {
				$('#register-modal .error-message').text("الرجاء إدخال كلمة المرور بشكل صحيح");
			} else {
				$('#register-modal .error-message').text("Please retype password correctly");
			}
			$('#register-modal .error-message').removeClass("d-none");
			$("#register-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}

		whatsup = $(this).find(".whatsup").val();

		if (whatsup.length !== 9 && whatsup !== "") {
			if (lang === "ar") {
				$('#register-modal .error-message').text("رقم الواتساب يجب أن يتكون من 9 أرقام");
			} else {
				$('#register-modal .error-message').text("Whatsapp number must be exactly 9 characters in length");
			}
			$('#register-modal .error-message').removeClass("d-none");
			$("#register-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}
		e.preventDefault();
		e.stopImmediatePropagation();

		//save user data in verify modal to use for login
		$("#verify-modal").find(".phone").val($(this).find(".phone").val());
		$("#verify-modal").find(".password").val($(this).find(".password").val());

		$(this).find(".lang").val(lang);

		$.ajax({
			type: "post",
			url: base_url + '/users_control_web/register',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += '-' + errorMessage[node].innerHTML;
					} else {
						wholeMessage += '<br>';
					}
				}
				$('#register-modal .error-message').html(wholeMessage);
				$('#register-modal .error-message').removeClass("d-none");
				$("#register-modal").animate({
					scrollTop: $("body").offset().top
				}, 500);
			} else {
				$("#register-modal").modal("hide");
				setTimeout(function () {
					$("#verify-modal").modal("show");
				}, 500);
				//reset
				$('#register-form').trigger("reset");
			}
		});
	});

	//verify
	$('#verify-form').submit(function (e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		var data = $(this).serializeArray();
		data.push({
					name: "is_multi",
					value: 1
				});
		$.ajax({
			type: "post",
			url: base_url + '/api/users_control/verify',
			dataType: "json",
			data: $.param(data)
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += '-' + errorMessage[node].innerHTML;

					} else if (errorMessage[node].nodeName === '#text') {
						wholeMessage = data.message;
					} else {
						wholeMessage += '<br>';
					}
				}
				$('#verify-modal .error-message').html(wholeMessage);
				$('#verify-modal .error-message').removeClass("d-none");
			} else {
				//				if($(".profile-page").length > 0){
				//					setTimeout(function () {
				//					$("#notification-modal .text").html("Phone number changed successfully,<br>You will be logged out, please sign in with your new number")
				//					$("#notification-modal").modal("show");
				//				}, 500);
				//				} else{
				//			}
				//login directly
				$("#verify-modal").modal("hide");
				setTimeout(function () {
					if (lang === "ar") {
						$("#success-modal .text").html("تم إنشاء حسابك بنجاح");
					} else {
						$("#success-modal .text").html("You have successfully registered");
					}

					$("#success-modal").modal("show");
				}, 500);
				setTimeout(function () {
					$("#success-modal").modal("hide");

				}, 3000);
				$.ajax({
					type: "post",
					url: base_url + '/users_control_web/login',
					dataType: "json",
					data: {
						phone: $("#verify-modal").find(".phone").val(),
						password: $("#verify-modal").find(".password").val()
					}
				}).done(function (data) {
					if (data.status === false) {
						var errorMessage = $.parseHTML(data.message),
							node,
							wholeMessage = '';
						for (node in errorMessage) {
							if (errorMessage[node].nodeName === 'P') {
								wholeMessage += errorMessage[node].textContent;
							} else {
								if (node === "0") {
									wholeMessage = errorMessage[node].textContent;
								} else {
									wholeMessage += '<br>';
								}
							}
						}
						$('#verify-modal .error-message').html(wholeMessage);
						$('#verify-modal .error-message').removeClass("d-none");
					} else {
						window.location = base_url;
					}
				});
			}
		});
	});

	$('#login-form').submit(function (e) {
		var phone = $(this).find(".phone").val();

		if (phone.length !== 9) {
			if (lang === "ar") {
				$('#login-modal .error-message').text("رقم الهاتف يجب أن يتكون من 9 أرقام");
			} else {
				$('#login-modal .error-message').text("Phone must be exactly 9 characters in length");
			}
			$('#login-modal .error-message').removeClass("d-none");
			$("#login-modal").animate({
				scrollTop: $("body").offset().top
			}, 500);
			return false;
		}
		e.preventDefault();
		e.stopImmediatePropagation();

		$.ajax({
			type: "post",
			url: base_url + '/users_control_web/login',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						wholeMessage += errorMessage[node].textContent;
					} else {
						if (node === "0") {
							wholeMessage = errorMessage[node].textContent;
						} else {
							wholeMessage += '<br>';
						}
					}
				}
				$('#login-modal .error-message').html(wholeMessage);
				$('#login-modal .error-message').removeClass("d-none");
			} else {
				window.location = base_url;
			}
		});

	});

	//favorite icon hover
	$("#card-modal").on("mouseenter", ".card .fav .icon", function () {
		$(this).css("color", "#FF87A0");
	});

	$("#card-modal").on("mouseleave", ".card .fav .icon", function () {
		if ($(this).data("added") === 0) {
			$(this).css("color", "#999");
		} else if ($(this).data("added") === 1) {
			$(this).css("color", "#FF87A0");
		}
	});

	//add/remove from favorite
	$("#card-modal").on("click", ".card .fav .icon", function () {
		if (!logged) {
			$("#card-modal").modal("hide");
			setTimeout(function () {
				$("#login-modal").modal("show");
			}, 500);
			return;
		}
		var adId, url;
		adId = $(this).parents(".card").data("adId");
		if ($(this).data("added") === 0) {
			$(this).html('<i class="fas fa-heart fa-2x"></i>');
			$(this).css("color", "FF87A0");
			$(this).data("added", 1);
			$(this).attr("title", "Remove from favorites");
			url = base_url + '/api/items_control/set_as_favorite';
		} else if ($(this).data("added") === 1) {
			$(this).html('<i class="far fa-heart fa-2x"></i>');
			$(this).css("color", "#999");
			$(this).data("added", 0);
			$(this).attr("title", "Add to favorites");
			url = base_url + '/api/items_control/remove_from_favorite';
		}

		$.ajax({
			type: "post",
			url: url,
			dataType: "json",
			data: {
				ad_id: adId
			}
		}).done(function (data) {
			if (data.status === false) {} else {}
		});

	});

	$("#notification-modal .submit").click(function () {
		$("#notification-modal").modal("hide");
	});
	$("#notification-modal").on("hidden.bs.modal", function () {
		location.reload();
	});

	//report ad
	$.ajax({
		type: "get",
		url: base_url + '/api/items_control/get_report_messages',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {} else {
			for (i in data.data) {
				$("#report-form .report-select").append($('<option>', {
					value: data.data[i].report_message_id,
					text: data.data[i].msg
				}));
			}
			$("#report-form .report-select")[0].sumo.reload();
		}
	});

	$("#card-modal .report").click(function () {
		$("#report-form .ad-id").val($("#card-modal .card").data("adId"));
		$("#card-modal").modal("hide");
		setTimeout(function () {
			$("#report-modal").modal("show");
		}, 500);
	});

	$("#report-form").submit(function (e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		$.ajax({
			type: "post",
			url: base_url + '/api/items_control/report_item',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				$("#report-form").trigger("reset");
				$("#report-form .report-select")[0].sumo.unSelectAll();
				$("#report-modal").modal("hide");
				setTimeout(function () {
					if (lang === "ar") {
						$("#success-modal .text").html("تم التبليغ عن الإعلان");
					} else {
						$("#success-modal .text").html("Ad reported successfully");
					}

					$("#success-modal").modal("show");
				}, 500);
				setTimeout(function () {
					$("#success-modal").modal("hide");
				}, 3000);
			}
		});
	});

	$("#report-modal").on("hide.bs.modal", function () {
		$("#report-form").trigger("reset");
		$('#report-modal .report-select')[0].sumo.unSelectAll();
	});

	//footer content
	$.ajax({
		type: "get",
		url: base_url + '/api/data_control/get_about_info',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {} else {
			template = $('#footer-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, data.data);
			$(".page-footer").append(rendered);

			template = $('#social-fixed-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, data.data);
			$(".social-fixed .icons").append(rendered);
		}
	});
});
