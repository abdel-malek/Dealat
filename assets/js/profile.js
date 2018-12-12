/*jslint browser: true*/
/*global $, alert, console,lang, Mustache, base_url, user_id*/

//constants
//Ad Status: 
//pending = 1
//accepted = 2
//expired = 3
//hidden = 4
//rejected = 5
//deleted = 6

$(function () {
	if ($(".profile-page").length > 0) {

		var userRateValue = 2;
		var i, template, rendered;
		//get my ads
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_my_items',
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {} else {
				var adData, negotiable, status, type, i, template, rendered, statusId1, statusId2;
				for (i in data.data) {
					if (data.data[i].is_negotiable === "0") {
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

					if (!data.data[i].main_image) {
						data.data[i].main_image = 'assets/images/default_ad/' + data.data[i].tamplate_id + '.png';
					}

					if (data.data[i].expired_after <= 0 && data.data[i].status === "2") {
						data.data[i].status = "3";
					}

					if (data.data[i].status === "1") {
						if (lang === "ar") {
							status = "قيد الانتظار";
						} else {
							status = "Pending";
						}

					} else if (data.data[i].status === "2") {
						if (lang === "ar") {
							status = "موافق عليه";
						} else {
							status = "Accepted";
						}
					} else if (data.data[i].status === "3") {
						if (lang === "ar") {
							status = "منتهي";
						} else {
							status = "Expired";
						}
					} else if (data.data[i].status === "4") {
						if (lang === "ar") {
							status = "مخفي";
						} else {
							status = "Hidden";
						}
					} else if (data.data[i].status === "5") {
						if (lang === "ar") {
							status = "مرفوض";
						} else {
							status = "Rejected";
						}
					}

					//add commas to price
					data.data[i].price = new Intl.NumberFormat().format(data.data[i].price);

					//convert emojis shortnames to native unicode
					data.data[i].title = emojione.shortnameToUnicode(data.data[i].title);

					if (data.data[i].publish_date) {
						adData = {
							ad: data.data[i],
							publish: data.data[i].publish_date.split(' ')[0],
							expiry: data.data[i].expiry_date.split(' ')[0],
							negotiable: negotiable,
							status: status
						};
					} else {
						adData = {
							ad: data.data[i],
							negotiable: negotiable,
							status: status
						};
					}

					if (data.data[i].status === "5") {
						adData.reject_note = data.data[i].reject_note;
					}
					template = $('#user-ads-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, adData);
					$(".profile-page .user-ads .row.first").append(rendered);

					//remove edit button if ad expired
					//					if (data.data[i].status === "3") {
					//						$(".profile-page .user-ads .card[data-ad-id=" + data.data[i].ad_id + "] .edit-ad").addClass("d-none");
					//					}
				}
				$(".profile-page .user-ads .card").each(function () {
					statusId1 = $(this).data("statusId");
					var temp = $(this).data("templateId");
					if (temp === 8) {
						$(this).find(".price").addClass("d-none");
					}
					$(this).find(".status-icon").each(function () {
						statusId2 = $(this).data("statusId");
						if (statusId1 === statusId2) {
							$(this).removeClass("d-none");
							return false;
						}
					});
				});

				var mixer2 = mixitup('#user-ads .main', {
					selectors: {
						control: '[data-mixitup-control]'
					}
				});
			}
		});

		$("#user-ads .filter-dropdown .filter-item").click(function () {
			$(this).closest(".dropdown-menu").siblings(".dropdown-toggle").text($(this).text());
		});
		//get my fav ads
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_my_favorites',
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {} else {
				var adData, negotiable, type, i, template, rendered, templateId;

				for (i in data.data) {
					if (data.data[i].is_negotiable === "0") {
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

					//add commas to price
					data.data[i].price = new Intl.NumberFormat().format(data.data[i].price);

					//convert emojis shortnames to native unicode
					data.data[i].title = emojione.shortnameToUnicode(data.data[i].title);
					data.data[i].description = emojione.shortnameToUnicode(data.data[i].description);

					adData = {
						ad: data.data[i],
						date: data.data[i].publish_date.split(' ')[0],
						negotiable: negotiable
					};

					template = $('#user-fav-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, adData);
					$(".profile-page .favorites .row.first").append(rendered);
				}
			}
		});

		//get user info
		var userInfo;
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_my_info',
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {} else {
				userInfo = data.data;
				if (!userInfo.personal_image) {
					//if image is null
					userInfo.personal_image = '/assets/images/user2.jpg';
				}
				template = $('#user-info-template').html();
				Mustache.parse(template);
				rendered = Mustache.render(template, userInfo);
				$(".profile-page .user-details .row.first").append(rendered);

				$(".profile-page .rating .rate-group").each(function () {
					if ($(this).data("value") <= userRateValue) {
						$(this).children("label").css("color", "#FFCC36");
					} else {
						$(this).children("label").css("color", "#ddd");
					}
				});

				//fill edit user info modal with data
				$("#edit-user-info-form input[name='name']").val(data.data.name);
				$("#edit-user-info-form input[name='location_id']").val(data.data.city_id);
				$("#edit-user-info-form .city-select").val(data.data.city_id);
				$("#edit-user-info-form .gender-select").val(data.data.user_gender);
				$("#edit-user-info-form input[name='email']").val(data.data.email);
				$("#edit-user-info-form input[name='phone']").val(data.data.phone);
				$("#edit-user-info-form input[name='whatsup_number']").val(data.data.whatsup_number);
				$("#edit-user-info-form input[name='birthday']").val(data.data.birthday);
			}
		});

		var registerImg = [];
		var uploadPersonal, deleteString;
		if (lang === "ar") {
			uploadPersonal = "اختر صورة";
			deleteString = "حذف";
		} else {
			uploadPersonal = "Upload Image";
			deleteString = "Delete";
		}

		//upload register image
		$("#fileuploader-register").uploadFile({
			url: base_url + '/api/users_control/upload_personal_image',
			multiple: false,
			dragDrop: false,
			fileName: "image",
			acceptFiles: "image/*",
			maxFileCount: 1,
			maxFileSize: 10000 * 1024,
			showDelete: true,
			showPreview: true,
			previewHeight: "100px",
			previewWidth: "100px",
			deleteStr: deleteString,
			uploadStr: uploadPersonal,
			returnType: "json",
			onSuccess: function (files, data, xhr, pd) {
				registerImg.push(data.data);
			},
			onError: function (files, status, errMsg, pd) {},
			deleteCallback: function (data, pd) {
				var arr;
				arr = [data.data];
				$.post(base_url + '/api/items_control/delete_images', {
						images: arr
					},
					function (resp, textStatus, jqXHR) {
						deleted = data.data;
						registerImg = [];
					});
			}
		});

		//edit user info
		$(".profile-page").on("click", ".edit-user-info", function () {
			$('#edit-user-info-modal .city-select')[0].sumo.reload();
			$('#edit-user-info-modal .gender-select')[0].sumo.reload();
			$("#edit-user-info-modal").modal("show");
		});

		$("#edit-user-info-form").submit(function (e) {
			var newData, i, data, newPhone, whatsup;
			whatsup = $(this).find(".whatsup").val();

			if (whatsup.length !== 9 && whatsup !== "") {
				if (lang === "ar") {
					$('#edit-user-info-modal .error-message').text("رقم الهاتف يجب أن يتكون من 9 أرقام");
				} else {
					$('#edit-user-info-modal .error-message').text("Phone must be exactly 9 characters in length");
				}
				$('#edit-user-info-modal .error-message').removeClass("d-none");
				$("#edit-user-info-modal").animate({
					scrollTop: $("body").offset().top
				}, 500);
				return false;
			}
			newData = $(this).serializeArray();

			e.preventDefault();
			e.stopPropagation();
			data = $(this).serializeArray();
			for (i in data) {
				//send -1 for empty values
				if (data[i].value === "") {
					data[i].value = "-1";
				}
			}
			data.push({
				name: "image",
				value: registerImg[0]
			});
			$.ajax({
				type: "post",
				url: base_url + '/api/users_control/edit_user_info',
				dataType: "json",
				data: $.param(data)
			}).done(function (data) {
				if (data.status === false) {} else {
					//					for (i in newData) {
					//						if (newData[i].name === "phone") {
					//							if (newData[i].value !== userInfo.phone) {
					//								newPhone = newData[i].value;
					//								$("#verify-modal").find(".phone").val(newPhone);
					//								$("#edit-user-info-modal").modal("hide");
					//								setTimeout(function () {
					//									$("#verify-modal").modal("show");
					//								}, 500);
					//							} else{
					//								$("#edit-user-info-modal").modal("hide");
					//								setTimeout(function () {
					//									$("#success-modal .text").html("Data Updated successfully")
					//									$("#success-modal").modal("show");
					//								}, 500);
					//								setTimeout(function () {
					//									$("#success-modal").modal("hide");
					//									location.reload();
					//								}, 3000);
					//							}
					//						}
					//					}

					$("#edit-user-info-modal").modal("hide");
					setTimeout(function () {
						if (lang === "ar") {
							$("#success-modal .text").html("تم تحديث معلوماتك بنجاح");
						} else {
							$("#success-modal .text").html("Data Updated successfully");
						}

						$("#success-modal").modal("show");
					}, 500);
					setTimeout(function () {
						$("#success-modal").modal("hide");
						location.reload();
					}, 3000);
				}
			});
		});

		//delete ad
		$(".profile-page .user-ads").on("click", ".delete-ad", function () {
			var adId, adStatus;
			adId = $(this).parents(".card").data("adId");
			$("#delete-modal .ad-id").val(adId);
			$("#delete-modal").modal("show");
		});

		$("#delete-ad-form").submit(function (e) {
			e.preventDefault();
			e.stopPropagation();
			$.ajax({
				type: "post",
				url: base_url + '/api/items_control/change_status',
				dataType: "json",
				data: {
					ad_id: $(this).find(".ad-id").val(),
					status: 6 //for delete
				}
			}).done(function (data) {
				if (data.status === false) {} else {
					$(".profile-page .user-ads .card[data-ad-id=\"" + data.data + "\"]").parent(".col-12").remove();

					$("#delete-modal").modal("hide");
					setTimeout(function () {
						if (lang === "ar") {
							$("#success-modal .text").html("تم حذف الإعلان بنجاح");
						} else {
							$("#success-modal .text").html("Advertisement deleted successfully");
						}
						$("#success-modal").modal("show");
					}, 500);
					setTimeout(function () {
						$("#success-modal").modal("hide");
					}, 3000);
				}
			});
		});

		var uploadEdit, uploadMainEdit, uploadVideoEdit, editAdImgs = [],
			tempEditAdImgs = [],
			editMainImg = [],
			tempEditMainImg = [],
			editAdVideo = [];
		if (lang === "ar") {
			uploadEdit = "اختر صور إضافية";
			uploadMainEdit = "اختر صورة الإعلان الرئيسية";
			uploadVideoEdit = "اختر فيديو";
		} else {
			uploadEdit = "Upload more images";
			uploadMainEdit = "Upload main ad image";
			uploadVideoEdit = "Upload video";
		}

		uploadobjEditMain = $("#fileuploader-edit-ad-main").uploadFile({
			url: base_url + '/api/items_control/item_images_upload',
			multiple: false,
			dragDrop: false,
			fileName: "image",
			acceptFiles: "image/*",
			maxFileSize: 10000 * 1024,
			maxFileCount: 1,
			showDelete: true,
			dragdropWidth: "100%",
			showPreview: true,
			showProgress: true,
			showFileSize: false,
			previewHeight: "100px",
			previewWidth: "100px",
			deleteStr: deleteString,
			uploadStr: uploadMainEdit,
			returnType: "json",
			onSuccess: function (files, data, xhr, pd) {
				editMainImg.push(data.data);
				$("#edit-ad-modal .ad-images .main-img").remove();
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
						for (i in editMainImg) {
							if (editMainImg[i] === deleted) {
								editMainImg.splice(i, 1);
							}
						}
					});
			}
		});

		uploadobjEditOther = $("#fileuploader-edit-ad").uploadFile({
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
			showProgress: true,
			showFileSize: false,
			previewHeight: "100px",
			previewWidth: "100px",
			deleteStr: deleteString,
			uploadStr: uploadEdit,
			returnType: "json",
			onSuccess: function (files, data, xhr, pd) {
				editAdImgs.push(data.data);
				tempEditAdImgs.push(data.data);
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
						for (i in editAdImgs) {
							if (editAdImgs[i] === deleted) {
								editAdImgs.splice(i, 1);
							}
						}
					});
			}
		});

		//video
		uploadobjEditVideo = $("#fileuploader-edit-ad-video").uploadFile({
			url: base_url + '/api/items_control/item_video_upload',
			multiple: false,
			dragDrop: false,
			fileName: "video",
			acceptFiles: "video/*",
			maxFileSize: 20000 * 1024,
			maxFileCount: 1,
			showDelete: true,
			dragdropWidth: "100%",
			showPreview: false,
			showProgress: true,
			showFileSize: false,
			previewHeight: "100px",
			previewWidth: "100px",
			deleteStr: deleteString,
			uploadStr: uploadVideoEdit,
			returnType: "json",
			onSuccess: function (files, data, xhr, pd) {
				editAdVideo.push(data.data);
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
						for (i in editAdVideo) {
							if (editAdVideo[i] === deleted) {
								editAdVideo.splice(i, 1);
							}
						}
					});
			}
		});

		//open edit user ad modal
		$(".profile-page .user-ads").on("click", ".edit-ad", function () {
			var templateId, adId, catId, adStatus;
			adId = $(this).parents(".card").data("adId");
			templateId = $(this).parents(".card").data("templateId");
			$("#edit-ad-form .template-id").val(templateId);
			catId = $(this).parents(".card").data("categoryId");
			adStatus = $(this).parents(".card").data("statusId");
			if (adStatus == 3) {
				adStatus = 2;
			}
			$("#edit-ad-modal .ad-status").val(adStatus);

			$.ajax({
				type: "get",
				url: base_url + '/api/items_control/get_item_details',
				dataType: "json",
				data: {
					ad_id: adId,
					template_id: templateId
				}
			}).done(function (data) {
				if (data.status === false) {} else {
					//remove upload video except for properties category
					if (templateId === 2) {
						$("#edit-ad-modal #fileuploader-edit-ad-video").removeClass("d-none");
					} else {
						$("#edit-ad-modal #fileuploader-edit-ad-video").addClass("d-none");
					}

					//if category is job remove price and negotiable inputs
					if (templateId === 8) {
						$("#edit-ad-modal input[name='price']").closest(".form-group").addClass("d-none");
						$("#edit-ad-modal input[name='price']").removeAttr("required");
						$("#edit-ad-modal input[name='price']").val("0");
						$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").addClass("d-none");
					} else {
						$("#edit-ad-modal input[name='price']").closest(".form-group").removeClass("d-none");
						$("#edit-ad-modal input[name='price']").attr("required", true);
						$("#edit-ad-modal input[name='price']").val("");
						$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").removeClass("d-none");
					}

					//convert emojis shortnames to native unicode
					data.data.title = emojione.shortnameToUnicode(data.data.title);
					data.data.description = emojione.shortnameToUnicode(data.data.description);

					$("#edit-ad-modal input[name='ad_id']").val(data.data.ad_id);
					$("#edit-ad-modal input[name='title']").val(data.data.title);
					$("#edit-ad-modal select[name='show_period']").val(data.data.show_period);
					$("#edit-ad-modal .period-select")[0].sumo.reload();
					$("#edit-ad-modal input[name='price']").val(new Intl.NumberFormat().format(data.data.price));
					$("#edit-ad-modal select[name='city_id']").val(data.data.city_id).change();
					$("#edit-ad-modal select[name='city_id']")[0].sumo.reload();

					if (data.data.location_id) {
						$("#edit-ad-modal select[name='location_id']").val(data.data.location_id);
						$("#edit-ad-modal select[name='location_id']")[0].sumo.reload();
					}
					$("#edit-ad-modal select[name='location_id']")[0].sumo.enable();

					if (data.data.is_negotiable === "1") {
						$("#edit-ad-modal input[name='is_negotiable']").prop("checked", true);
					}

					$("#edit-ad-modal textarea[name='description']").val(data.data.description);

					if (data.data.type_id) {
						$("#edit-ad-modal select[name='type_id']").val(data.data.type_id).change();
						$("#edit-ad-modal select[name='type_id']")[0].sumo.reload();
						if (data.data.type_model_id) {
							$("#edit-ad-modal select[name='type_model_id']").closest(".form-group").removeClass("d-none");
							$("#edit-ad-modal select[name='type_model_id']").val(data.data.type_model_id);
							$("#edit-ad-modal select[name='type_model_id']")[0].sumo.reload();
						}
					}

					if (data.data.manufacture_date) {
						$("#edit-ad-modal input[name='manufacture_date']").val(data.data.manufacture_date);
					}
					if (data.data.kilometer) {
						$("#edit-ad-modal input[name='kilometer']").val(new Intl.NumberFormat().format(data.data.kilometer));
					}
					if (data.data.engine_capacity) {
						$("#edit-ad-modal select[name='engine_capacity']").val(data.data.engine_capacity);
						$("#edit-ad-modal select[name='engine_capacity']")[0].sumo.reload();
					} else {
						$("#edit-ad-modal select[name='engine_capacity']")[0].sumo.unSelectAll();
					}
					if (data.data.is_automatic) {
						$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_automatic']").val(data.data.is_automatic);
						$("#edit-ad-modal select[name='is_automatic']")[0].sumo.reload();
					}
					if (data.data.is_new) {
						$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").val(data.data.is_new);
						$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']")[0].sumo.reload();
						//change all status values from all categories because when submit it takes the last value
						$('#edit-ad-modal .status-select').each(function () {
							$(this).val(data.data.is_new);
						});
					}
					if (data.data.space) {
						$("#edit-ad-modal input[name='space']").val(new Intl.NumberFormat().format(data.data.space));
					}
					if (data.data.rooms_num) {
						$("#edit-ad-modal input[name='rooms_num']").val(data.data.rooms_num);
					}
					if (data.data.floor) {
						$("#edit-ad-modal input[name='floor']").val(data.data.floor);
					}
					if (data.data.floors_number) {
						$("#edit-ad-modal input[name='floors_number']").val(data.data.floors_number);
					}
					if (data.data.property_state_id) {
						$("#edit-ad-modal select[name='property_state_id']").val(data.data.property_state_id);
						$("#edit-ad-modal select[name='property_state_id']")[0].sumo.reload();
					}
					if (data.data.with_furniture === "1") {
						$("#edit-ad-modal input[name='with_furniture']").prop("checked", true);
					}
					if (data.data.size) {
						$("#edit-ad-modal input[name='size']").val(data.data.size);
					}
					if (data.data.schedule_id) {
						$("#edit-ad-modal select[name='schedule_id']").val(data.data.schedule_id);
						$("#edit-ad-modal select[name='schedule_id']")[0].sumo.reload();
					}
					if (data.data.education_id) {
						$("#edit-ad-modal select[name='education_id']").val(data.data.education_id);
						$("#edit-ad-modal select[name='education_id']")[0].sumo.reload();
					}
					if (data.data.certificate_id) {
						$("#edit-ad-modal select[name='certificate_id']").val(data.data.certificate_id);
						$("#edit-ad-modal select[name='certificate_id']")[0].sumo.reload();
					}
					if (data.data.experience) {
						$("#edit-ad-modal input[name='experience']").val(data.data.experience);
					}
					if (data.data.gender) {
						$("#edit-ad-modal select[name='gender']").val(data.data.gender);
						$("#edit-ad-modal select[name='gender']")[0].sumo.reload();
					} else {
						$("#edit-ad-modal select[name='gender']")[0].sumo.unSelectAll();
					}
					if (data.data.salary) {
						$("#edit-ad-modal input[name='salary']").val(new Intl.NumberFormat().format(data.data.salary));
					}

					if (data.data.ad_visible_phone === "1") {
						$("#edit-ad-modal input[name='ad_visible_phone']").prop("checked", true);
					}

					editAdImgs = [];

					for (i in data.data.images) {
						editAdImgs.push(data.data.images[i].image);
					}

					tempEditMainImg = [];
					if (data.data.main_image) {
						tempEditMainImg.push(data.data.main_image);
					}

					$("#edit-ad-modal .ad-images").empty();
					template = $('#ad-edit-images-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, data.data);
					$("#edit-ad-modal .ad-images").append(rendered);

					if (data.data.images.length === 0) {
						$("#edit-ad-modal .ad-images .secondary-imgs").remove();
					}
				}
			});

			var subId, has_types = 0;

			subId = $(this).parents(".card").data("categoryId");

			//put all fields as shown
			$("#edit-ad-modal .field").each(function () {
				$(this).removeClass("d-none");
			});

			//hide fields from template according to hiddenFields array
			var hideArr = [];
			for (i in hiddenFields) {
				if (hiddenFields[i].categoryId == subId) {
					hideArr = hiddenFields[i].hidden;
					for (j in hideArr) {
						$("#edit-ad-modal .field." + hideArr[j]).addClass("d-none");
					}
				}
			}

			$("#edit-ad-modal .template").each(function () {
				$(this).addClass("d-none");

				if ($(this).data("templateId") === templateId) {
					$(this).removeClass("d-none");
				}
			});

			//only display selected template types
			$("#edit-ad-modal .type-select option").each(function () {
				$(this).addClass("d-none");
				if ($(this).data("categoryId") === subId) {
					has_types = 1;
					$(this).removeClass("d-none");
				}
			});

			$('#edit-ad-modal select.type-select')[0].sumo.reload();
			//display types select only if category is vehicles, mobiles or electronics
			if (has_types !== 0) {
				$("#edit-ad-modal .type-select").parents(".form-group").removeClass("d-none");
				$("#edit-ad-modal .model-select").parents(".form-group").addClass("d-none");
			} else {
				$("#edit-ad-modal .type-select").parents(".form-group").addClass("d-none");
				$("#edit-ad-modal .model-select").parents(".form-group").addClass("d-none");
			}

			//vehicles
			if (templateId === 1) {
				if ($.inArray("manufacture_date", hideArr) === -1) {
					$("#edit-ad-modal input[name='manufacture_date']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='manufacture_date']").removeAttr("required");
				}
				if ($.inArray("kilometer", hideArr) === -1) {
					$("#edit-ad-modal input[name='kilometer']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='kilometer']").removeAttr("required");
				}
				if ($.inArray("is_automatic", hideArr) === -1) {
					$("#edit-ad-modal select[name='is_automatic']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='is_automatic']").removeAttr("required");
				}
				if ($.inArray("type_name", hideArr) === -1 && has_types !== 0) {
					$("#edit-ad-modal select[name='type_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='type_id']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal input[name='manufacture_date']").removeAttr("required");
				$("#edit-ad-modal input[name='kilometer']").removeAttr("required");
				$("#edit-ad-modal select[name='is_automatic']").removeAttr("required");
				$("#edit-ad-modal select[name='type_id']").removeAttr("required");
			}

			if (templateId === 1 || templateId === 3 || templateId === 4 || templateId === 5 || templateId === 6 || templateId === 7 || templateId === 9) {
				if ($.inArray("is_new", hideArr) === -1) {
					$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").attr("required", true);
				} else {
					$("#edit-ad-modal .template[data-template-id=" + templateId + "] select[name='is_new']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal select[name='is_new']").each(function () {
					$(this).removeAttr("required");
				});
			}

			//only show upload video in properties
			if (templateId === 2) {
				$("#edit-ad-modal #fileuploader-ad-video").removeClass("d-none");

				if ($.inArray("space", hideArr) === -1) {
					$("#edit-ad-modal input[name='space']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='space']").removeAttr("required");
				}
				if ($.inArray("rooms_num", hideArr) === -1) {
					$("#edit-ad-modal input[name='rooms_num']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='rooms_num']").removeAttr("required");
				}
				if ($.inArray("floor", hideArr) === -1) {
					$("#edit-ad-modal input[name='floor']").attr("required", true);
				} else {
					$("#edit-ad-modal input[name='floor']").removeAttr("required");
				}
				if ($.inArray("property_state_name", hideArr) === -1) {
					$("#edit-ad-modal select[name='property_state_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='property_state_id']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal #fileuploader-ad-video").addClass("d-none");

				$("#edit-ad-modal input[name='space']").removeAttr("required");
				$("#edit-ad-modal input[name='rooms_num']").removeAttr("required");
				$("#edit-ad-modal input[name='floor']").removeAttr("required");
				$("#edit-ad-modal select[name='property_state_id']").removeAttr("required");
			}

			//if category is job remove price and negotiable inputs
			if (templateId === 8) {
				$("#edit-ad-modal input[name='price']").closest(".form-group").addClass("d-none");
				$("#edit-ad-modal input[name='price']").removeAttr("required");
				$("#edit-ad-modal input[name='price']").val("0");
				$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").addClass("d-none");

				if ($.inArray("education_name", hideArr) === -1) {
					$("#edit-ad-modal select[name='education_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='education_id']").removeAttr("required");
				}
				if ($.inArray("gender", hideArr) === -1) {
					$("#edit-ad-modal select[name='gender']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='gender']").removeAttr("required");
				}
				if ($.inArray("certificate_name", hideArr) === -1) {
					$("#edit-ad-modal select[name='certificate_id']").attr("required", true);
				} else {
					$("#edit-ad-modal select[name='certificate_id']").removeAttr("required");
				}
			} else {
				$("#edit-ad-modal input[name='price']").closest(".form-group").removeClass("d-none");
				$("#edit-ad-modal input[name='price']").attr("required", true);
				$("#edit-ad-modal input[name='price']").val("");
				$("#edit-ad-modal input[name='is_negotiable']").closest(".form-group").removeClass("d-none");

				$("#edit-ad-modal select[name='education_id']").removeAttr("required");
				$("#edit-ad-modal select[name='gender']").removeAttr("required");
				$("#edit-ad-modal select[name='certificate_id']").removeAttr("required");
			}


			$("#edit-ad-modal").modal("show");
		});

		$("#edit-ad-modal").on("hide.bs.modal", function () {
			//delete images if user close modal without editing the ad
			var deleteImgs = [];

			if (editMainImg.length > 0) {
				for (i in editMainImg) {
					deleteImgs.push(editMainImg[i]);
				}
			}

			if (tempEditAdImgs.length > 0) {
				for (i in tempEditAdImgs) {
					deleteImgs.push(tempEditAdImgs[i]);
				}
			}

			if (tempEditAdImgs.length > 0 || editMainImg.length > 0) {
				$.ajax({
					type: "post",
					url: base_url + '/api/items_control/delete_images',
					dataType: "json",
					data: {
						images: JSON.stringify(deleteImgs)
					}
				});
			}
			//delete video if user close modal without editing the ad
			if (editAdVideo.length > 0) {
				var deleteVid = [];
				for (i in editAdVideo) {
					deleteVid.push(editAdVideo[i]);
				}
				$.ajax({
					type: "post",
					url: base_url + '/api/items_control/delete_vedios',
					dataType: "json",
					data: {
						videos: JSON.stringify(deleteVid)
					}
				});
			}
			editMainImg = [];
			editAdImgs = [];
			editAdVideo = [];
			deleteImgArr = [];
			deleteMainImgArr = [];
			deleteVideoArr = [];
			tempEditAdImgs = [];
		});

		var deleteImgArr = [],
			deleteMainImgArr = [],
			deleteVideoArr = [];
		$("#edit-ad-modal .ad-images").on("click", ".delete", function () {
			var url = $(this).parents(".image-wrapper").data("url");
			if ($(this).parents(".image-wrapper").hasClass("main-image")) {
				deleteMainImgArr.push(url);
				deleteImgArr.push(url);
				tempEditMainImg = [];
			} else if ($(this).parents(".image-wrapper").hasClass("video")) {
				deleteVideoArr.push(url);
			} else {
				deleteImgArr.push(url);
				for (i in editAdImgs) {
					if (editAdImgs[i] == url) {
						editAdImgs.splice(i, 1);
					}
				}
			}
			$(this).parents(".image-wrapper").remove();

		});

		//change all is_new select values if one select is changed
		$('#edit-ad-modal').on("change", ".status-select", function () {
			var newVal = $(this).val();
			$('#edit-ad-modal .status-select').each(function () {
				$(this).val(newVal);
			});
		});

		var editAdData;
		//submit edit user ad
		$("#edit-ad-form").submit(function (e) {
			e.preventDefault();
			e.stopPropagation();

			var i,
				secondary_imgs = [],
				adStatus;

			adStatus = $(this).find(".ad-status").val();
			editAdData = $(this).serializeArray();

			// convert native unicode emoji to their shortnames upon form submission.
			var title = $(this).find('input[name="title"]').val();
			title = emojione.toShort(title);

			editAdData.push({
				name: "title",
				value: title
			});
			var desc = $(this).find('textarea[name="description"]').val();
			desc = emojione.toShort(desc);

			editAdData.push({
				name: "description",
				value: desc
			});

			if (adStatus) {
				editAdData.push({
					name: "edit_status",
					value: adStatus
				});
			}

			if (editMainImg.length > 0) {
				editAdData.push({
					name: "main_image",
					value: editMainImg[0]
				});
			}

			if (deleteMainImgArr.length > 0 && editMainImg.length === 0 && editAdImgs.length === 0) {
				//in case no images added
				var templateId = $("#edit-ad-form .template-id").val();
				if (templateId == 1) {
					if (lang === "ar") {
						$('#edit-ad-modal .error-message').html("الرجاء إرفاق صورة واحدة للإعلان على الأقل ثم حاول مجدداً");
					} else {
						$('#edit-ad-modal .error-message').html("Please upload at least one image and try again");
					}
					$('#edit-ad-modal .error-message').removeClass("d-none");
					$("#edit-ad-modal").animate({
						scrollTop: $("body").offset().top
					}, 500);
					return false;
				}

				//to keep default image
				editAdData.push({
					name: "main_image",
					value: "-1"
				});
			}

			if (tempEditMainImg.length === 0 && editMainImg.length === 0 && tempEditAdImgs.length === 0 && editAdImgs.length === 0) {
				//in case no images added
				var templateId = $("#edit-ad-form .template-id").val();
				if (templateId == 1) {
					if (lang === "ar") {
						$('#edit-ad-modal .error-message').html("الرجاء إرفاق صورة واحدة للإعلان على الأقل ثم حاول مجدداً");
					} else {
						$('#edit-ad-modal .error-message').html("Please upload at least one image and try again");
					}
					$('#edit-ad-modal .error-message').removeClass("d-none");
					$("#edit-ad-modal").animate({
						scrollTop: $("body").offset().top
					}, 500);
					return false;
				}
			}

			if (tempEditMainImg.length === 0 && editMainImg.length === 0) {
				//there is no main image
				if (editAdImgs.length > 0) {
					if (lang === "ar") {
						$('#edit-ad-modal .error-message').html("الرجاء رفع صورة رئيسية للإعلان");
					} else {
						$('#edit-ad-modal .error-message').html("Please upload main ad image");
					}
					$('#edit-ad-modal .error-message').removeClass("d-none");
					$("#edit-ad-modal").animate({
						scrollTop: $("body").offset().top
					}, 500);
					return false;
				}
			}

			if (editAdImgs.length > 0) {
				//copy adimgs into uploaded_imgs
				for (i in editAdImgs) {
					secondary_imgs.push(editAdImgs[i]);
				}
				secondary_imgs = JSON.stringify(secondary_imgs);
				editAdData.push({
					name: "images",
					value: secondary_imgs
				});
			}

			if (editAdVideo.length > 0) {
				editAdData.push({
					name: "main_video",
					value: editAdVideo[0]
				});
			}

			if (deleteImgArr.length > 0) {
				deleteImgArr = JSON.stringify(deleteImgArr);

				editAdData.push({
					name: "deleted_images",
					value: deleteImgArr
				});
			}

			if (deleteVideoArr.length > 0) {
				deleteVideoArr = JSON.stringify(deleteVideoArr);

				editAdData.push({
					name: "deleted_videos",
					value: deleteVideoArr
				}, {
					name: "main_video",
					value: "-1"
				});
			}

			for (i in editAdData) {
				//send -1 for empty values
				if (editAdData[i].value === "") {
					editAdData[i].value = "-1";
				}
			}

			//remove commas from numbers before sending the request
			var numbersWithComma = ["price", "kilometer", "space", "salary"];
			for (i in editAdData) {
				if (numbersWithComma.indexOf(editAdData[i].name) > -1) {
					editAdData[i].value = editAdData[i].value.replace(/,/g, '');
				}
			}

			$("#confirm-edit-modal").modal("show");
		});

		$("#confirm-edit-modal").on("hidden.bs.modal", function () {
			$("body").addClass("modal-open");
		});

		$("#confirm-edit-modal .submit").click(function () {
			$.ajax({
				type: "post",
				url: base_url + '/api/items_control/edit',
				dataType: "json",
				data: $.param(editAdData)
			}).done(function (data) {
				if (data.status === false) {} else {
					//reset edit arrays
					editMainImg = [];
					editAdImgs = [];
					editAdVideo = [];
					deleteImgArr = [];
					deleteMainImgArr = [];
					deleteVideoArr = [];
					tempEditAdImgs = [];

					$("#confirm-edit-modal").modal("hide");
					$("#edit-ad-modal").modal("hide");
					setTimeout(function () {
						if (lang === "ar") {
							$("#notification-modal .text").html("تم تعديل إعلانك بنجاج وهو الآن بانتظار الموافقة عليه");
						} else {
							$("#notification-modal .text").html("Your ad has been edited successfully and it is now pending waiting for admin approval");
						}

						$("#notification-modal").modal("show");
					}, 500);
				}
			});
		});

		var sound_notify_path = site_url + 'admin_assets/definite.mp3';
		//get chat sessions
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_my_chat_sessions',
			dataType: "json",
			data: $(this).serialize()
		}).done(function (data) {
			if (data.status === false) {} else {
				var sessionData, sessionImage, username;
				for (i in data.data) {
					sessionData = [];
					if (data.data[i].seller_id == user_id) {
						sessionImage = data.data[i].user_pic;
						username = data.data[i].user_name;
					} else {
						sessionImage = data.data[i].seller_pic;
						username = data.data[i].seller_name;
					}
					if (!sessionImage) {
						sessionImage = '/assets/images/user1.jpg';
					}

					//convert emojis shortnames to native unicode
					data.data[i].ad_title = emojione.shortnameToUnicode(data.data[i].ad_title);

					sessionData = {
						image: sessionImage,
						username: username,
						details: data.data[i]
					};

					template = $('#chat-sessions-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, sessionData);
					$(".profile-page .chats ul.sessions").append(rendered);

					//check for new msgs
					notSeenMsgs = 0;
					var newMsgSessions = [];
					for (i in data.data) {
						if ((user_id == data.data[i].seller_id && data.data[i].seller_seen == 0) || (user_id == data.data[i].user_id && data.data[i].user_seen == 0)) {
							newMsgSessions.push(data.data[i].chat_session_id);
							notSeenMsgs += 1;
						}
					}

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
			}
		});


		//open a chat session
		$(".profile-page .chats").on("click", ".session", function () {
			var adId, templateId, sessionId, sellerId, isSeller;
			adId = $(this).data("adId");
			templateId = $(this).data("templateId");
			sessionId = $(this).data("sessionId");
			sellerId = $(this).data("sellerId");

			if (user_id == sellerId) {
				isSeller = 1;
			} else {
				isSeller = 0;
			}

			//this value not for send
			$("#chat-modal .is-seller").val(isSeller);

			$("#chat-modal .chat-header .ad-name").text($(this).data("adname"));
			$("#chat-modal .chat-header .user-name").text($(this).data("username"));

			$("#chat-form .ad-id").val(adId);
			$("#chat-form .template-id").val(templateId);
			$("#chat-form .chat-session-id").val(sessionId);

			$.ajax({
				type: "get",
				url: base_url + '/api/users_control/get_chat_messages',
				dataType: "json",
				data: {
					ad_id: adId,
					chat_session_id: sessionId
				}
			}).done(function (data) {
				if (data.status === false) {} else {
					$("#chat-modal .chat").empty();
					var msgDate = data.data[0].created_at.split(' ')[0];
					$("#chat-modal .chat").append('<div class="day">' + msgDate + '</div>');

					if (isSeller) {
						//then I am the ad seller and a user chatted with me
						for (i in data.data) {
							if (data.data[i].to_seller === "1") {
								// message from other to me
								template = $('#chat-other-template').html();
							} else {
								template = $('#chat-self-template').html();
							}

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
							Mustache.parse(template);
							rendered = Mustache.render(template, data.data[i]);
							$("#chat-modal .chat").append(rendered);
						}
					} else {
						//then I chatted with ad seller(I started chat)
						for (i in data.data) {
							if (data.data[i].to_seller === "1") {
								// message from me to seller
								template = $('#chat-self-template').html();
							} else {
								template = $('#chat-other-template').html();
							}
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
							Mustache.parse(template);
							rendered = Mustache.render(template, data.data[i]);
							$("#chat-modal .chat").append(rendered);
						}
					}
					$("#chat-modal").modal("show");
//					$("#chat-modal .chat").stop().animate({
//						scrollTop: $("#chat-modal .chat")[0].scrollHeight
//					}, 1000);
//					$("#chat-modal .chat").stop().scrollTop($("#chat-modal .chat")[0].scrollHeight);
				}
			});
		});

		//delete chat session
		$(".profile-page .chats").on("click", ".delete-chat", function (e) {
			e.stopPropagation();
			var chatId = $(this).parents(".session").data("sessionId");
			var btn = $(this);

			$.ajax({
				type: "post",
				url: base_url + '/api/users_control/delete_chat',
				dataType: "json",
				data: {
					chat_id: chatId
				}
			}).done(function (data) {
				if (data.status) {
					btn.parents(".session").next("hr").remove();
					btn.parents(".session").remove();
					if (lang === "ar") {
						$("#success-modal .text").html("تم حذف الدردشة بنجاح");
					} else {
						$("#success-modal .text").html("Chat deleted successfully");
					}
					$("#success-modal").modal("show");
					setTimeout(function () {
						$("#success-modal").modal("hide");
					}, 2000);
				}
			});
		});

		//get saved bookmarks
		$.ajax({
			type: "get",
			url: base_url + '/api/users_control/get_my_bookmarks',
			dataType: "json"
		}).done(function (data) {
			if (data.status === false) {} else {
				for (i in data.data) {
					data.data[i].query = $.parseJSON(data.data[i].query);
					data.data[i].filter = $.param(data.data[i].query);

					//add commas to price
					var numbersWithComma = ["price_min", "kilometers_min", "space_min", "salary_min", "price_max", "kilometers_max", "space_max", "salary_max"];

					for (var k in numbersWithComma) {
						if (data.data[i].query[numbersWithComma[k]]) {
							data.data[i].query[numbersWithComma[k]] = new Intl.NumberFormat().format(data.data[i].query[numbersWithComma[k]]);
						}
					}

					template = $('#saved-bookmarks-template').html();
					Mustache.parse(template);
					rendered = Mustache.render(template, data.data[i]);
					$(".profile-page .bookmarks .bookmarks-list").append(rendered);
				}
			}
		});

		//delete bookmark
		$(".bookmarks").on("click", ".remove", function () {

			var id, btn;
			btn = $(this);
			id = $(this).parents(".bookmark").data("bookmarkId");
			$.ajax({
				type: "post",
				url: base_url + '/api/users_control/delete_bookmark',
				dataType: "json",
				data: {
					user_bookmark_id: id
				}
			}).done(function (data) {
				if (data.status === false) {} else {
					btn.parents(".bookmark").next("hr").remove();
					btn.parents(".bookmark").remove();
					if (lang === "ar") {
						$("#success-modal .text").html("تم حذف البحث بنجاح");
					} else {
						$("#success-modal .text").html("Saved search deleted successfully");
					}
					$("#success-modal").modal("show");
					setTimeout(function () {
						$("#success-modal").modal("hide");
					}, 2000);
				}
			});
		});

		//show bookmark
		$(".bookmarks").on("click", ".show", function () {
			var filter = $(this).parents(".bookmark").data("filter");

			$.ajax({
				type: "get",
				url: base_url + '/api/items_control/search',
				dataType: "json",
				data: filter
			}).done(function (data) {
				if (data.status === false) {} else {
					window.location = base_url + '/search_control?' + filter;
				}
			});
		});

		//delete account
		$("#edit-user-info-modal").on("click", ".delete-account", function () {
			$("#edit-user-info-modal").modal("hide");
			setTimeout(function () {
				$("#delete-account-modal").modal("show");
			}, 500);

		});

		$("#delete-account-modal .submit").on("click", function () {
			$.ajax({
				type: "post",
				url: base_url + '/api/users_control/delete_my_account',
				dataType: "json"
			}).done(function (data) {
				if (data.status === false) {} else {
					window.location = base_url;
				}
			});
		});

		$(".profile-page #chat-modal .ad-name").click(function () {
			$("#card-modal .card").remove();
			//copied from openCardModal function except for ajax data
			$.ajax({
				type: "get",
				url: base_url + '/api/items_control/get_item_details',
				dataType: "json",
				global: false, // this makes sure ajaxStart is not triggered
				data: {
					ad_id: $("#chat-form .ad-id").val(),
					template_id: $("#chat-form .template-id").val()
				},
				beforeSend: function () {
					ajaxLoadTimeout = setTimeout(function () {
						$(".loading-overlay1").fadeIn("fast");
					}, 100);
				},
				complete: function () {
					clearTimeout(ajaxLoadTimeout);
					$(".loading-overlay1").fadeOut("fast");
				}
			}).done(function (data) {
				if (data.status === false) {} else {
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

					if (data.data.gender === "1") {
						if (lang === "ar") {
							data.data.gender = "ذكر";
						} else {
							data.data.gender = "Male";
						}

					} else if (data.data.gender === "2") {
						if (lang === "ar") {
							data.data.gender = "أنثى";
						} else {
							data.data.gender = "Female";
						}
					}

					if (!data.data.main_image && !data.data.main_video) {
						data.data.main_image = 'assets/images/default_ad/' + data.data.tamplate_id + '.png';
					}

					//add commas to price
					if (data.data.price) {
						data.data.price = new Intl.NumberFormat().format(data.data.price);
					}
					if (data.data.kilometer) {
						data.data.kilometer = new Intl.NumberFormat().format(data.data.kilometer);
					}
					if (data.data.space) {
						data.data.space = new Intl.NumberFormat().format(data.data.space);
					}
					if (data.data.salary) {
						data.data.salary = new Intl.NumberFormat().format(data.data.salary);
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

					$('.card-img-slider').slickLightbox({
						src: 'src',
						itemSelector: '.card-img-top img'
					});

					if (adData.ad.seller_phone) {
						$("#card-modal .seller-phone").val(adData.ad.seller_phone);
					}

					if (data.data.seller_id === user_id) {
						$("#card-modal .chat, #card-modal .report, #card-modal .fav").addClass("d-none");
					} else {
						$("#card-modal .chat, #card-modal .report, #card-modal .fav").removeClass("d-none");
						if (data.data.is_admin === 1) {
							$("#card-modal .chat").addClass("d-none");
						}
					}

					if (data.data.is_admin === 1) {
						$("#card-modal .seller").addClass("d-none");
					} else {
						$("#card-modal .seller").removeClass("d-none");
					}

					//category job then remove price circle
					if (data.data.tamplate_id === "8") {
						$("#card-modal .price").addClass("d-none");
						$("#card-modal .negotiable").addClass("d-none");
					} else {
						$("#card-modal .price").removeClass("d-none");
						$("#card-modal .negotiable").removeClass("d-none");
					}

					if (!logged) {
						$("#card-modal .fav .icon").data("added", 0);
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
						$(".card .fav .icon").css("color", "#bbb");
					} else if ($(".card .fav .icon").data("added") === 1) {
						$(".card .fav .icon").html('<i class="fas fa-heart fa-2x"></i>');
						$(".card .fav .icon").css("color", "#FF87A0");
					}
					$("#chat-modal").modal("hide");
					setTimeout(function () {
						$("#card-modal").modal("show");
						setTimeout(function () {
							$(".card-img-slider").slick("refresh");
						}, 200);
					}, 500);


				}
			});


		});

	}
});
