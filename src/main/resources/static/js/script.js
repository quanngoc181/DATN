const Toast = Swal.mixin({
	toast: true,
	position: 'top-end',
	showConfirmButton: false,
	timer: 5000
});

$(function(){
	$('[data-mask]').inputmask();
});

function notify(type, title) {
	Toast.fire({
		type: type,
		title: title
	});
}