/*jslint browser: true*/
/*global $, alert,console,lang, Mustache, base_url, logged*/

$(function () {
	$(".header-account-logged  ul").css("min-width", $(".header-account-logged").width());
	$(window).resize(function () {
		$(".header-account-logged  ul").css("min-width", $(".header-account-logged").width());

	});
	//global variables
	var typesData, adImgs, mixer, category_id, category_name, i;
	adImgs = [];
	mixer = mixitup('.products');
	category_id = $(".category-page").data("categoryId");
	if (!category_id) {
		//home page
		category_id = 0;
	}
	category_name = $(".category-page").data("categoryName");
	//    Loading screen
	$(window).on('load', function () {
		$(".loading-overlay .spinner").fadeOut(500, function () {
			$(this).parent().fadeOut(500, function () {
				$("body").removeAttr('style');
				$(this).remove();
			});
		});
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

	function resetPostAd() {
		$("#place-ad-form").trigger("reset");
		$("#ad-modal a.select").css("color", "#6c757d");
		if (lang === "ar") {
			$("#ad-modal .categories-nav .select").text("اختر فئة");
			$("#ad-modal .locations-nav .select").text("مكان القطعة");
			$("#ad-modal .types-nav .select").text("اختر النوع");
		} else {
			$("#ad-modal .categories-nav .select").text("Select Category");
			$("#ad-modal .locations-nav .select").text("Item's Location");
			$("#ad-modal .types-nav .select").text("Select type");
		}
		$("#ad-modal .ajax-file-upload-container").empty();
		$("#ad-modal .types-nav").parent(".form-group").addClass("d-none");
		adImgs = [];
		$("#ad-modal .featured .warning").addClass("d-none");
		$('#ad-modal .error-message').addClass("d-none");
		$("#ad-modal .template").each(function () {
			$(this).addClass("d-none");
		});
		$('#ad-modal .period-select')[0].sumo.unSelectAll();
		$('#ad-modal .schedules-select')[0].sumo.unSelectAll();
		$('#ad-modal .educations-select')[0].sumo.unSelectAll();
	}

	function resetEditAd() {
		$("#edit-ad-form").trigger("reset");
		$("#edit-ad-modal a.select").css("color", "#6c757d");
		if (lang === "ar") {
			$("#edit-ad-modal .locations-nav .select").text("مكان القطعة");
			$("#edit-ad-modal .types-nav .select").text("اختر النوع");
		} else {
			$("#edit-ad-modal .locations-nav .select").text("Item's Location");
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
		$('#edit-ad-modal .period-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .schedules-select')[0].sumo.unSelectAll();
		$('#edit-ad-modal .educations-select')[0].sumo.unSelectAll();
	}

	function resetFilter() {
		$("#filter-form").trigger("reset");
		$("#filter-modal a.select").css("color", "#6c757d");
		if (lang === "ar") {
			$("#filter-modal .categories-nav .select").text("اختر فئة");
			$("#filter-modal .locations-nav .select").text("مكان القطعة");
		} else {
			$("#filter-modal .categories-nav .select").text("Select Category");
			$("#filter-modal .locations-nav .select").text("Item's Location");
		}
		$("#filter-modal .type-select").parents(".form-group").addClass("d-none");
		$("#filter-modal .model-select").parents(".form-group").addClass("d-none");
		$('#filter-modal .error-message').addClass("d-none");
		$("#filter-modal .template").each(function () {
			$(this).addClass("d-none");
		});
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
			console.log(category_id);
			console.log(data);
			var i, template, rendered, adData, sliderDefaultImg, sliderImgCount = 0,
				sideImgCount = 0,
				sideDefaultImg;

			sliderDefaultImg = {
				image: "assets/images/banner-772x250.jpg"
			};
			sideDefaultImg = {
				image: "assets/images/af-coinbase-2.jpg"
			};

			if (data.data.length !== 0) {
				for (i in data.data) {
					//					if (data.data[i].is_main === "1") {
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
	}).fail(function (response) {
		console.log(response);
		alert("fail");
	});

	//get categories and subcategories
	$.ajax({
		type: "get",
		url: base_url + '/api/categories_control/get_nested_categories',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {
			//console.log(data);
			alert("error status false");
		} else {
			var i, template, rendered, catData;
			catData = {
				categories: data.data
			};
			template = $('#ad-modal-categories-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, catData);
			$("#ad-modal .categories-nav .main-categories, #filter-modal .categories-nav .main-categories").append(rendered);
		}
	}).fail(function (response) {
		alert("fail");
	});

	//get cities and areas - types - educations - schedules
	$.ajax({
		type: "get",
		url: base_url + '/api/items_control/get_data_lists',
		dataType: "json"
	}).done(function (data) {
		if (data.status === false) {
			//console.log(data);
			alert("error status false");
		} else {
			//			console.log(data);
			var i, j, template, rendered, locData, arr1 = [],
				arr2 = [];
			//locations
			locData = {
				cities: data.data.nested_locations
			};
			template = $('#ad-modal-cities-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, locData);
			$("#ad-modal .locations-nav .cities,#edit-ad-modal .locations-nav .cities, #filter-modal .locations-nav .cities").append(rendered);

			//register locations
			for (i in locData.cities) {
				$('#register-modal .city-select, #edit-user-info-modal .city-select').append($('<option>', {
					value: locData.cities[i].city_id,
					text: locData.cities[i].city_name
				}));
			}
			$('#register-modal .city-select')[0].sumo.reload();
			//			
			//types
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
			//			console.log(typesData.types);
			template = $('#ad-modal-types-template').html();
			Mustache.parse(template);
			rendered = Mustache.render(template, typesData);
			//			$("#ad-modal .types-nav .types, #filter-modal .types-nav .types").append(rendered);
			$("#ad-modal .types-nav .types, #edit-ad-modal .types-nav .types").append(rendered);

			//if type with no models don't open a menu and remove arrow 
			//			$("#ad-modal .types-nav ul.dropdown-menu, #filter-modal .types-nav ul.dropdown-menu").each(function () {
			$("#ad-modal .types-nav ul.dropdown-menu, #edit-ad-modal .types-nav ul.dropdown-menu").each(function () {
				if ($(this).html().trim() === "") {
					$(this).siblings(".dropdown-item").removeClass("dropdown-toggle");
					$(this).remove();
				}
			});

			//filter modal types
			for (i in typesData.types) {
				//				$('#filter-modal .type-select').append($('<option>', {
				//					value: typesData.types[i].type_id,
				//					text: typesData.types[i].name
				//				}));

				$('#filter-modal .type-select').append('<option class="d-none" value="' + typesData.types[i].type_id + '" data-template-id="' + typesData.types[i].tamplate_id + '">' + typesData.types[i].name + '</option>');

			}

			$('#filter-modal select.type-select')[0].sumo.reload();

			$('#filter-modal .type-select').change(function () {
				//				console.log(typesData.types);
				var typeId, i, j;
				typeId = $(this).find('option:selected').val();
				//				console.log(typeId);
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
			$('select').each(function () {
				if ($(this).attr("multiple") !== "multiple") {
					var selected;
					selected = $(this).find('option:selected');
					if (selected.hasClass("placeholder")) {
						$(this).siblings(".CaptionCont").find("span").css("color", "#6c757d");
					} else {
						$(this).siblings(".CaptionCont").find("span").css("color", "#495057");
					}
				}
			});

			$('select').change(function () {
				if ($(this).attr("multiple") !== "multiple") {
					var selected;
					selected = $(this).find('option:selected');
					if (selected.hasClass("placeholder")) {
						$(this).siblings(".CaptionCont").find("span").css("color", "#6c757d");
					} else {
						$(this).siblings(".CaptionCont").find("span").css("color", "#495057");
					}
				}
			});
		}

	}).fail(function (response) {
		alert("fail");
	});


	//filter modal manufacture date
	//	var i
	//	, yearsArr=[];
	//	for(i = 1980; i<= 2018; i++){
	//		yearsArr.push(i);
	//	}
	for (i = 1980; i <= 2018; i += 1) {
		$("#filter-modal .manufacture-date-select").append($('<option>', {
			value: i,
			text: i
		}));
	}

	$('select.manufacture-date-select')[0].sumo.reload();

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
				alert("error status false");
			} else {
				console.log(data);
				var adData, negotiable, automatic, status, furniture, type, i, template, rendered, templateId;
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
						automatic = "لا";
					} else {
						automatic = "No";
					}

				} else {
					if (lang === "ar") {
						automatic = "نعم";
					} else {
						automatic = "Yes";
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

				$("#card-modal .templates [class*='-val']").each(function () {
					if ($(this).text() === " ") {
						$(this).text(" -");
					}
				});

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
		}).fail(function (response) {
			alert("fail");
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
		$("#ad-modal .categories-nav a.select").css("color", "#495057");
		var templateId, subId, has_types = 0;
		templateId = $(this).data("templateId");
		subId = $(this).data("categoryId");
		//		$("#ad-modal .template").addClass("d-none");
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
			$("#ad-modal .types-nav .select").text("اختر النوع");
		} else {
			$("#ad-modal .types-nav .select").text("Select type");
		}
		$("#place-ad-form .type-model-id").val("");
		$("#place-ad-form .type-id").val("");
		$("#place-ad-form .category-id").val(subId);

		//change select placeholder
		$("#ad-modal .categories-nav .select").text($(this).text());

		$('#ad-modal .schedules-select')[0].sumo.selectItem(0);
		$('#ad-modal .educations-select')[0].sumo.selectItem(0);
	});

	//view template fields when change category in filter modal
	$("#filter-modal .categories-nav").on("click", ".last-subcategory", function () {
		$("#filter-modal .categories-nav a.select").css("color", "#495057");

		var templateId, subId, has_types = 0;
		templateId = $(this).data("templateId");
		subId = $(this).data("categoryId");
		//		$("#filter-modal .template").addClass("d-none");
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
			$("#filter-modal .type-select .placeholder").text("اختر النوع");
		} else {
			$("#filter-modal .type-select .placeholder").text("Select type");
		}

		$("#filter-form .category-id").val(subId);

		//change select placeholder
		$("#filter-modal .categories-nav .select").text($(this).text());

		$('#filter-modal .manufacture-date-select')[0].sumo.unSelectAll();
		$('#filter-modal .type-select')[0].sumo.selectItem(0);
		$('#filter-modal .model-select')[0].sumo.unSelectAll();
		$('#filter-modal .automatic-select')[0].sumo.selectItem(0);
		$('#filter-modal .status-select')[0].sumo.selectItem(0);
		$('#filter-modal .schedules-select')[0].sumo.unSelectAll();
		$('#filter-modal .educations-select')[0].sumo.unSelectAll();
	});

	//locations select
	$("#ad-modal .locations-nav,#edit-ad-modal .locations-nav, #filter-modal .locations-nav").on("click", ".location", function () {
		$("#ad-modal .locations-nav a.select").css("color", "#495057");
		$("#filter-modal .locations-nav a.select").css("color", "#495057");
		var locationId;
		locationId = $(this).data("locationId");
		if ($(this).parents("#ad-modal").length > 0) {
			$("#place-ad-form .location-id").val(locationId);
			//change select placeholder
			$("#ad-modal .locations-nav .select").text($(this).text());
		} else if ($(this).parents("#edit-ad-modal").length > 0) {
			$("#edit-ad-form .location-id").val(locationId);
			//change select placeholder
			$("#edit-ad-modal .locations-nav .select").text($(this).text());
		} else if ($(this).parents("#filter-modal").length > 0) {
			$("#filter-form .location-id").val(locationId);
			//change select placeholder
			$("#filter-modal .locations-nav .select").text($(this).text());
		}
	});

	//types select
	$("#ad-modal .types-nav,#edit-ad-modal .types-nav, #filter-modal .types-nav").on("click", ".type-item", function () {
		$("#ad-modal .types-nav a.select").css("color", "#495057");
		$("#filter-modal .types-nav a.select").css("color", "#495057");
		var typeId;
		if ($(this).parents("#ad-modal").length > 0) {
			if ($(this).find("ul").length > 0) {
				//has children models
			} else {
				//has no children models
				typeId = $(this).data("typeId");
				$("#place-ad-form .type-model-id").val("");
				$("#place-ad-form .type-id").val(typeId);
			}
			//		console.log("type: " + $("#place-ad-form .type-id").val());
			//		console.log("model: " + $("#place-ad-form .type-model-id").val());
			//change select placeholder
			$("#ad-modal .types-nav .select").text($(this).text());
		} else if ($(this).parents("#edit-ad-modal").length > 0) {
			if ($(this).find("ul").length > 0) {
				//has children models
			} else {
				//has no children models
				typeId = $(this).data("typeId");
				$("#edit-ad-form .type-model-id").val("");
				$("#edit-ad-form .type-id").val(typeId);
			}
			//change select placeholder
			$("#edit-ad-modal .types-nav .select").text($(this).text());
		} else if ($(this).parents("#filter-modal").length > 0) {
			if ($(this).find("ul").length > 0) {
				//has children models
			} else {
				//has no children models
				typeId = $(this).data("typeId");
				$("#filter-form .type-model-id").val("");
				$("#filter-form .type-id").val(typeId);
			}
			//change select placeholder
			$("#filter-modal .types-nav .select").text($(this).text());
		}
	});

	//model select
	$("#ad-modal .types-nav,#edit-ad-modal .types-nav, #filter-modal .types-nav").on("click", ".model", function () {
		$("#ad-modal .types-nav a.select").css("color", "#495057");
		$("#filter-modal .types-nav a.select").css("color", "#495057");
		var typeId, typeModelId;
		typeId = $(this).data("typeId");
		typeModelId = $(this).data("typeModelId");
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
	});

	//featured ad
	$("#ad-modal .featured").click(function () {
		if ($(this).find("input").is(':checked')) {
			$(this).find(".warning").removeClass("d-none");
		} else {
			$(this).find(".warning").addClass("d-none");
		}
	});

	var registerImg = [];
	//upload register image
	$("#fileuploader-register").uploadFile({
		url: base_url + '/api/items_control/item_images_upload',
		multiple: false,
		dragDrop: false,
		fileName: "image",
		acceptFiles: "image/*",
		maxFileSize: 10000 * 1024,
		showDelete: true,
		showPreview: true,
		previewHeight: "100px",
		previewWidth: "100px",
		uploadStr: "Upload Image",
		returnType: "json",
		onSuccess: function (files, data, xhr, pd) {
			console.log(data.data);
			registerImg.push(data.data);
			console.log("reg");
			console.log(registerImg[0]);
		},
		onError: function (files, status, errMsg, pd) {
			console.log("upload failed");
		},
		deleteCallback: function (data, pd) {
			//			console.log(data.data);
			var arr;
			arr = [data.data];
			$.post(base_url + '/api/items_control/delete_images', {
					images: arr
				},
				function (resp, textStatus, jqXHR) {
					alert("File Deleted");
					deleted = data.data;
					registerImg = [];
				});
		}
	});


	//upload ad main image
	$("#fileuploader-ad").uploadFile({
		url: base_url + '/api/items_control/item_images_upload',
		multiple: true,
		dragDrop: true,
		fileName: "image",
		acceptFiles: "image/*",
		maxFileSize: 10000 * 1024,
		//see docs for localization(lang)
		showDelete: true,
		//				statusBarWidth:600,
		dragdropWidth: "100%",
		showPreview: true,
		previewHeight: "100px",
		previewWidth: "100px",
		uploadStr: "Upload Images",
		returnType: "json",
		onSuccess: function (files, data, xhr, pd) {
			//			console.log(data);
			//			adImgs.push(data.data.slice(12));
			adImgs.push(data.data);
			//			console.log(adImgs);
			//			$("#ad-modal .main-image").val(data.data.slice(12));
		},
		onError: function (files, status, errMsg, pd) {
			//console.log("upload failed");
		},
		deleteCallback: function (data, pd) {
			//			console.log(data.data);
			var arr;
			arr = [data.data];
			//				for (var i = 0; i < data.data.length; i++) {
			$.post(base_url + '/api/items_control/delete_images', {
					images: arr
				},
				function (resp, textStatus, jqXHR) {
					//Show Message    
					//					alert("File Deleted");
					var i, deleted;
					//					deleted = data.data.slice(12);
					deleted = data.data;
					for (i in adImgs) {
						if (adImgs[i] === deleted) {
							adImgs.splice(i, 1);
						}
					}
					//					console.log(adImgs);
				});
			//				}
		}
	});

	//place ad form submit
	$("#place-ad-form").submit(function (evnt) {
		evnt.preventDefault();
		evnt.stopImmediatePropagation();

		//console.log(adImgs);
		var i, data, upladed_imgs = [],
			main_img = "",
			secondary_imgs = [];
		//copy adimgs into uploaded_imgs
		for (i in adImgs) {
			upladed_imgs.push(adImgs[i]);
		}
		main_img = upladed_imgs[0];
		upladed_imgs.splice(0, 1);
		secondary_imgs = upladed_imgs;
		secondary_imgs = JSON.stringify(secondary_imgs);

		data = $(this).serializeArray(); // convert form to array
		data.push({
			name: "main_image",
			value: main_img
		}, {
			name: "images",
			value: secondary_imgs
		});
		//		console.log($(this).serializeArray());
		//		console.log($(this).serialize());
		//		console.log(data);
		//		console.log($.param(data));
		$.ajax({
			type: "post",
			url: base_url + '/api/items_control/post_new_item',
			dataType: "json",
			data: $.param(data)
			//				$(this).serialize()
		}).done(function (data) {
			if (data.status === false) {
				//				console.log(data);
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						//console.log(errorMessage[node].innerHTML);
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
				//				console.log(data);
				//				if ($("#ad-modal .featured input").is(':checked')) {
				//					$("#ad-modal").modal("hide");
				//					setTimeout(function () {
				//						$("#pay-modal").modal("show");
				//					}, 500);
				//				} else {
				$("#ad-modal").modal("hide");
				setTimeout(function () {
					$("#success-modal").modal("show");
				}, 500);
				setTimeout(function () {
					$("#success-modal").modal("hide");
				}, 2000);
				//				}
				//reset
				resetPostAd();
			}
		}).fail(function (response) {
			//console.log(response);
			alert("fail");
		});
	});

	//	$(".place-ad").css("width", $(".right-col").width());

	$(window).scroll(function () {
		//		if ($(this).scrollTop() > $(".products").offset().top && $(this).scrollTop() < ($(".products .row").offset().top + $(".products").innerHeight())) {
		if ($(this).scrollTop() > $(".products").offset().top) {
			//			$(".categories").addClass("slider-fixed");	
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
			//			$(".categories").removeClass("slider-fixed");	
			//			setTimeout(function () {
			//			$(".categories").css({
			//				position: "absolute"
			//			});
			//			$(".category-slider img").css("width", "60%");
			//			if (lang === "ar") {
			//				$(".category-slider h6").css("font-size", ".8rem");
			//			} else {
			//				$(".category-slider h6").css("font-size", ".7rem");
			//			}

			$(".categories, .category-slider img, .category-slider h6").removeAttr("style");
		}
		if ($(window).innerWidth() > 768) {
			if ($(".right-col").length > 0 && $(this).scrollTop() > ($(".right-col").offset().top - $(".categories").height())) {
				$(".place-ad").css({
					//				width: $(".right-col").width(),
					top: $(".categories").height(),
					right: "20px",
					"z-index": 11,
					position: "fixed"
					//				width: $(".right-col").width()
				});
			} else {
				$(".place-ad").removeAttr("style");
				//			$(".place-ad").css("width", $(".right-col").width());
			}
		} else {
			$(".place-ad").removeAttr("style");
		}
	});

	//remove aside ads
	$("aside.banners .close").click(function () {
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

	//view subcategories when click on category slider
	$(".category").click(function () {
		category_id = $(this).data("categoryId");
		category_name = $(this).find(".name").text().trim();
		$.ajax({
			type: "get",
			url: base_url + '/api/categories_control/get_subcategories?category_id=' + category_id,
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {
				//console.log("error status false");
			} else {
				var subData, i, template, rendered, string, sub = [],
					all;
				$(".main .sub-categories .row").empty();
				if (data.data.length > 1) {
					if (lang === "ar") {
						all = "الكل";
					} else {
						all = 'All';
					}
					subData = {
						sub: data.data,
						catId: category_id
					};
					template = $('#sub-categories-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, subData);
					$(".main .sub-categories .row").append(rendered);
					$(".main .sub-categories").removeClass("d-none");
					//	smooth scroll
					$("html, body").animate({
						scrollTop: $(".main").offset().top
					}, 700);
					$(".main .sub-categories").css("background-color", "lightyellow");
					setTimeout(function () {
						$(".main .sub-categories").removeAttr("style");
					}, 1100);
				} else {
					window.location = base_url + "/home_control/load_ads_by_category_page?category_id=" + category_id + "&category_name=" + category_name;
				}
			}
		}).fail(function (response) {
			alert("fail");
		});

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

	//search
	$('input[type="search"]').keypress(function (e) {
		var key, data, query = 0;
		key = e.which;
		query = $(this).val();

		if (key === 13) {
			data = {
				query: $(this).val()
			};
			//			if ($(this).parents(".category-page").length > 0) {
			//				window.location = base_url + '/search_control?query=' + query + '&category_id=' + category_id ;
			//			} else {
			//				window.location = base_url + '/search_control?query=' + query;
			//			}
			window.location = base_url + '/search_control?query=' + query;
			//			$.ajax({
			//				type: "get",
			//				//				url: base_url + '/api/ads_control/search?query=' + query,
			//				url: base_url + '/api/ads_control/search',
			//				dataType: "json",
			//				data: data
			//			}).done(function (data) {
			//				if (data.status === false) {
			//					//					console.log("error status false");
			//					alert("error status false");
			//				} else {
			//					alert("success status true");
			//				}
			//			}).fail(function (response) {
			//				alert("fail");
			//			});
		}
	});

	//filter
	$('#filter-form').submit(function (e) {
		e.preventDefault();
		e.stopPropagation();
		var data, data1, query = 0,
			typeModelArr = [],
			schedulesArr = [],
			educationArr = [],
			manufactureDateArr = [];
		data = $(this).serializeArray();

		if ($('#filter-modal .manufacture-date-select option:selected').length > 0) {
			$('#filter-modal .manufacture-date-select option:selected').each(function (i) {
				manufactureDateArr.push($(this).val());
				$('#filter-modal .manufacture-date-select')[0].sumo.unSelectItem(i);
			});
			manufactureDateArr = JSON.stringify(manufactureDateArr);
			data.push({
				name: "manufacture_date",
				value: manufactureDateArr
			});
		}

		if ($('#filter-modal .model-select option:selected').length > 0) {
			$('#filter-modal .model-select option:selected').each(function (i) {
				typeModelArr.push($(this).val());
				$('#filter-modal .model-select')[0].sumo.unSelectItem(i);
			});
			typeModelArr = JSON.stringify(typeModelArr);
			data.push({
				name: "type_model_id",
				value: typeModelArr
			});
		}

		if ($('#filter-modal .schedules-select option:selected').length > 0) {
			$('#filter-modal .schedules-select option:selected').each(function (i) {
				schedulesArr.push($(this).val());
				$('#filter-modal .schedules-select')[0].sumo.unSelectItem(i);
			});
			schedulesArr = JSON.stringify(schedulesArr);
			data.push({
				name: "schedules_id",
				value: schedulesArr
			});
		}

		if ($('#filter-modal .educations-select option:selected').length > 0) {
			$('#filter-modal .educations-select option:selected').each(function (i) {
				educationArr.push($(this).val());
				$('#filter-modal .educations-select')[0].sumo.unSelectItem(i);
			});
			educationArr = JSON.stringify(educationArr);
			data.push({
				name: "education_id",
				value: educationArr
			});
		}

		//		console.log($(this).serialize());
		//		console.log($(this).serializeArray());
		//		//
		//		console.log(data);
		//		console.log($.param(data));

		query = $(this).find('input.search').val();
		if (query !== "") {
			data.push({
				name: "query",
				value: query
			});
		}
		data1 = $.param(data);
		//		console.log($.param(data));
		//		window.location = base_url + '/search_control?' + $.param(data);
		$.ajax({
			type: "get",
			url: base_url + '/api/items_control/search',
			dataType: "json",
			data: data1
		}).done(function (data) {
			if (data.status === false) {
				console.log(data);
				alert("error status false");
			} else {
				console.log(data);
				window.location = base_url + '/search_control?' + data1;
			}
		}).fail(function (response) {
			alert("fail");
		});
	});

	//chat with seller
	$("#card-modal button.chat").click(function () {
		//		$("#card-modal").modal("hide");
		//		setTimeout(function () {
		//			$("#chat-modal").modal("show");
		//		}, 500);
	});

	//language
	//	$(".language-switch span").click(function (e) {
	////		e.preventDefault();
	//		var language = "en";
	//		if ($(this).hasClass("english")) {
	//			language = "en";
	//		} else if ($(this).hasClass("arabic")) {
	//			language = "ar";
	//		}
	//		$.ajax({
	//			type: "get",
	//			url: base_url + '/users_control_web/change_language?language=' + language,
	//			dataType: "json"
	//		}).done(function (data) {
	//			if (data.status === false) {
	//				location.reload();
	//				console.log(data);
	////				return false;
	//			} else {
	//			}
	//		}).fail(function (response) {
	//			alert("fail");
	//		});
	//	});


	//register
	$('#register-form').submit(function (e) {
		var pass1, pass2, data;
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

		e.preventDefault();
		e.stopImmediatePropagation();

		$("#verify-modal").find(".phone").val($(this).find(".phone").val());
		$(this).find(".lang").val(lang);

		data = $(this).serializeArray();
		data.push({
			name: "image",
			value: registerImg[0]
		})
		console.log(data);
		$.ajax({
			type: "post",
			url: base_url + '/users_control_web/register',
			dataType: "json",
			//			data: $(this).serialize()
			data: $.param(data)
		}).done(function (data) {
			if (data.status === false) {
				console.log(data);
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						//console.log(errorMessage[node].innerHTML);
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
		}).fail(function (response) {
			alert("fail");
		});
	});

	//verify
	$('#verify-form').submit(function (e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		$.ajax({
			type: "post",
			url: base_url + '/api/users_control/verify',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {
				console.log(data);
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				console.log(errorMessage);
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						console.log(errorMessage[node]);
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
				console.log(data);

				$("#verify-modal").modal("hide");
				//				if($(".profile-page").length > 0){
				//					setTimeout(function () {
				//					$("#notification-modal .text").html("Phone number changed successfully,<br>You will be logged out, please sign in with your new number")
				//					$("#notification-modal").modal("show");
				//				}, 500);
				//				} else{
				setTimeout(function () {
					$("#success-modal .text").html("You have registered successfully,<br>You can now sign in");
					$("#success-modal").modal("show");
				}, 500);
				setTimeout(function () {
					$("#success-modal").modal("hide");
					setTimeout(function () {
						$("#login-modal input[name='phone']").val($("#verify-form input[name='phone']").val());
						$("#login-modal").modal("show");
					}, 500);
				}, 3000);

				//			}
			}
		}).fail(function (response) {
			alert("fail");
		});
	});

	//login
	function login() {

	}

	$('#login-form').submit(function (e) {
		e.preventDefault();
		e.stopImmediatePropagation();

		$.ajax({
			type: "post",
			url: base_url + '/users_control_web/login',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {
				console.log(data);
				var errorMessage = $.parseHTML(data.message),
					node,
					wholeMessage = '';
				for (node in errorMessage) {
					if (errorMessage[node].nodeName === 'P') {
						//console.log(errorMessage[node].innerHTML);
						wholeMessage += '-' + errorMessage[node].innerHTML;
					} else {
						wholeMessage += '<br>';
					}
				}
				$('#login-modal .error-message').html(wholeMessage);
				$('#login-modal .error-message').removeClass("d-none");
			} else {
				console.log(data);
				window.location = base_url;
				//				$("#verify-modal").modal("hide");
				//				setTimeout(function () {
				//					$("#success-modal").modal("show");
				//				}, 500);
				//				setTimeout(function () {
				//					$("#success-modal").modal("hide");
				//				}, 2000);
				//				//				}
				//				//reset
				//				resetPostAd();
			}
		}).fail(function (response) {
			alert("fail");
		});

	});

	//add/remove from favorite
	//	if ($(".card .fav .icon").data("added") === 0) {
	//		$(".card .fav .icon").html('<i class="far fa-heart fa-2x"></i>');
	//		$(".card .fav .icon").css("color", "#999");
	//	} else if ($(".card .fav .icon").data("added") === 1) {
	//		$(".card .fav .icon").html('<i class="fas fa-heart fa-2x"></i>');
	//		$(".card .fav .icon").css("color", "#FF87A0");
	//	}

	//add item to favorite
	//	$(".card .fav .icon").mouseenter(function () {
	$("#card-modal").on("mouseenter", ".card .fav .icon", function () {
		$(this).css("color", "#FF87A0");
	});
	//	$(".card .fav .icon").mouseleave(function () {
	$("#card-modal").on("mouseleave", ".card .fav .icon", function () {
		if ($(this).data("added") === 0) {
			$(this).css("color", "#999");
		} else if ($(this).data("added") === 1) {
			$(this).css("color", "#FF87A0");
		}
	});



	//	$(".card .fav .icon").click(function () {
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
			//			$(this).siblings(".text").text("Add to favorites");
			$(this).attr("title", "Remove from favorites");
			url = base_url + '/api/items_control/set_as_favorite';
		} else if ($(this).data("added") === 1) {
			$(this).html('<i class="far fa-heart fa-2x"></i>');
			$(this).css("color", "#999");
			$(this).data("added", 0);
			//			$(this).siblings(".text").text("Remove from favorites");
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
			if (data.status === false) {
				console.log(data);
			} else {
				console.log(data);
			}
		}).fail(function (response) {
			alert("fail");
		});

	});


	$("#notification-modal .submit").click(function () {
		window.location = base_url;
	});
});
