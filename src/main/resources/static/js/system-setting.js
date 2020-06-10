$(function() {
	fetchInfo();
	
	$('#cost-setting-submit').on('click', function(e) {
		e.preventDefault();
		
		$.ajax({
			url : "/admin/system-setting/save-cost",
			type: 'post',
			data: objectifyForm('#cost-setting-form'),
			success : function(data) {
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('#other-setting-submit').on('click', function(e) {
		e.preventDefault();
		
		$.ajax({
			url : "/admin/system-setting/save-other",
			type: 'post',
			data: objectifyForm('#other-setting-form'),
			success : function(data) {
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$('#company-setting-submit').on('click', function(e) {
		e.preventDefault();
		
		$.ajax({
			url : "/admin/system-setting/save-company",
			type: 'post',
			data: objectifyForm('#company-setting-form'),
			success : function(data) {
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});