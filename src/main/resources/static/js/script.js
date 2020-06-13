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
	
	$('.toggle-header').on('click', function() {
		$('.my-header .header-container').toggleClass('visible');
		$(this).find('i').toggleClass('fa-bars');
		$(this).find('i').toggleClass('fa-times');
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

function initDatePicker() {
  $('[data-toggle="datepicker"]').each(function (index, element) {
    let start = $(this).attr('min');
    let end = $(this).attr('max');

    $(this).datepicker({
      autoHide: true,
      zIndex: 2048,
      weekStart: 1,
      startDate: start ? start : null,
      endDate: end ? end : null,
      format: 'dd/mm/yyyy',
      offset: 10
    });
  });
}

function initScrollbar() {
	$('.js-custom-scrollbar').overlayScrollbars({ });
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

function confirmAction(callback, error, message = 'Tiếp tục hành động này?') {
	Swal.fire({
	  title: 'Xác nhận',
	  text: message,
	  type: 'info',
	  showCancelButton: true,
	  confirmButtonColor: '#3085d6',
	  cancelButtonColor: '#d33',
	  confirmButtonText: 'Tiếp tục'
	}).then((result) => {
	  if(result.value) callback();
	  else error();
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

function debounce(fn, threshold) {
    var timeout;
    threshold = threshold || 100;
    return function debounced() {
      clearTimeout(timeout);
      var args = arguments;
      var _this = this;
      function delayed() {
        fn.apply(_this, args);
      }
      timeout = setTimeout(delayed, threshold);
    };
}