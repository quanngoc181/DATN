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
});