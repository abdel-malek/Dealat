
/*jslint browser: true*/
/*global $, alert,console,lang, Mustache, base_url, logged, user_id, hiddenFields*/

$(function () {
var generatedQR = $("#qr-form .qr_gen_code").val();
	
	$("#qr-form input[name=\"secret_code\"]").keyup(function () {
		if ($(this).val().length === 6) {
			$("#qr-form .submit").removeAttr("disabled");
		} else {
			$("#qr-form .submit").attr("disabled", true);
		}
	});


	$("#qr-form").submit(function (e) {
		e.preventDefault();

		var data = $(this).serializeArray();
		data.push({
			name: "gen_code",
			value: generatedQR
		});
		$.ajax({
			type: "post",
			url: base_url + '/api/QR_users_control/login_by_qr_code',
			dataType: "json",
			data: $.param(data)
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
				$('.qr-page .error-message').html(wholeMessage);
				$('.qr-page .error-message').removeClass("d-none");
				
			} else {
				if(data.data.cms_logged === 1){
					window.location = base_url + "/admin/dashboard";
				}else{
					window.location = base_url;
				}	
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
	
});