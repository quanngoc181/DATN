const Toast = Swal.mixin({
	toast : true,
	position : 'top-end',
	showConfirmButton : false,
	timer : 5000
});

$(function() {
	let url = window.location.pathname;
	let _this = $('.sidebar a[href="'+url+'"]');
	_this.addClass('active');
	_this.closest('.nav-item.has-treeview').addClass('menu-open');
	_this.closest('.nav-item.has-treeview').children('.nav-link').addClass('active');
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

	$('[data-mask]').inputmask();
	
	bsCustomFileInput.init();
	
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

function confirmDelete(callback, message = 'Bạn có chắc xóa muốn xóa?') {
	Swal.fire({
	  title: 'Xác nhận xóa',
	  text: message,
	  type: 'warning',
	  showCancelButton: true,
	  confirmButtonColor: '#3085d6',
	  cancelButtonColor: '#d33',
	  confirmButtonText: 'Xóa!'
	}).then((result) => {
	  if(result.value) callback();
	});
}

function objectifyForm(selector) {
	let formArray = $(selector).serializeArray();
	
    let obj = {};
    for (var i = 0; i < formArray.length; i++) {
      obj[formArray[i]['name']] = formArray[i]['value'];
    }
    return obj;
}

function readURL(input, target) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    
    reader.onload = function(e) {
      $(target).attr('src', e.target.result);
    }
    
    reader.readAsDataURL(input.files[0]);
  }
}