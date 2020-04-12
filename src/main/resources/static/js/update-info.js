$(function() {
	$('.avatar-overlay').on('click', function() {
		$("#avatar-input").click();
	});

	$("#avatar-input").on('change', function() {
		$("#avatar-form").submit();
	});

	$('#add-address').on('click', function() {
		$.ajax({
			url : "/user/receive-address/count",
			success : function(data) {
				console.log(data);
				if (data >= 4) {
					notify('error', 'Tối đa 4 địa chỉ nhận hàng');
					return;
				}
				$('#add-address-modal').modal('show');
			},
			error : function() {

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
				notify('success', err.responseJSON.message);
			}
		});
	});
});