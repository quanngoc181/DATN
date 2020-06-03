$(function() {
	getCartNumber();
	
	$('.avatar-overlay').on('click', function() {
		$("#avatar-input").click();
	});

	$("#avatar-input").on('change', function() {
		$("#avatar-form").submit();
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

	$('.set-default-address').on('click', function() {
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
	
	$('.delete-address').on('click', function() {
		let $this = $(this);
		let id = $this.closest('.card').data('id');

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
	
	$('.edit-address').on('click', function() {
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
});