const Toast = Swal.mixin({
	toast : true,
	position : 'top-end',
	showConfirmButton : false,
	timer : 5000
});

$(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	$('[data-mask]').inputmask();
	
	initSwitch();
});

function notify(type, title) {
	Toast.fire({
		type : type,
		title : title
	});
}

function initSwitch() {
	$('.js-switch').each(function(index, element) {
		new Switchery(element, {
			size : 'small'
		});
	});
}