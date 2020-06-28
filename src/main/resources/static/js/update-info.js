$(function() {
	getCartNumber();
	
	$('.avatar-overlay').on('click', function() {
		$("#avatar-input").click();
	});

	$("#avatar-input").on('change', function() {
		$("#avatar-form").submit();
	});
	
	$('#update-basic-submit').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/user/update-info/basic",
			method: 'post',
			data: objectifyForm('#update-basic-form'),
			success : function(data) {
				notify('success', "Thành công");
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('#update-contact-submit').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/user/update-info/contact",
			method: 'post',
			data: objectifyForm('#update-contact-form'),
			success : function(data) {
				notify('success', "Thành công");
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});

	$('#add-address').on('click', function() {
		$.ajax({
			url : "/user/receive-address/add",
			success : function(data) {
				$('#add-address-modal .modal-content').html(data);
				$('#add-address-modal').modal('show');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '#add-address-submit', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/user/receive-address/add",
			method: 'post',
			data: objectifyForm('#add-address-form'),
			success : function(data) {
				$('#address-container').append(data);
				$('#add-address-modal').modal('hide');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});

	$(document).on('click', '.set-default-address', function() {
		let $this = $(this);
		let id = $this.closest('.card').data('id');

		$.ajax({
			url : "/user/receive-address/set-default",
			method: 'post',
			data : {
				id: id
			},
			success : function() {
				$('.receive-address-item > .card').removeClass('card-primary');
				$this.closest('.card').addClass('card-primary');
				notify('success', 'Thành công');
			},
			error : function() {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.delete-address', function() {
		let $this = $(this);
		let id = $this.closest('.card').data('id');

		confirmDelete(function() {
			$.ajax({
				url : "/user/receive-address/delete",
				method: 'post',
				data : {
					id: id
				},
				success : function() {
					$this.closest('.receive-address-item').remove();
					notify('success', 'Thành công');
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		});
	});
	
	$(document).on('click', '.edit-address', function() {
		let $this = $(this);
		let id = $this.closest('.card').data('id');

		$.ajax({
			url : "/user/receive-address/edit",
			data : {
				id: id
			},
			success : function(data) {
				$('#add-address-modal .modal-content').html(data);
				$('#add-address-modal').modal('show');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '#edit-address-submit', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/user/receive-address/edit",
			method: 'post',
			data: objectifyForm('#edit-address-form'),
			success : function(data) {
				let id = $(data).find('.card').data('id');
				$('#address-container').find('[data-id=' + id + ']').closest('.receive-address-item').replaceWith(data);
				$('#add-address-modal').modal('hide');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});