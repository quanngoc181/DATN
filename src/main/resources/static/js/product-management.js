$(function() {
	let productTable = $('#product-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/product-management/datatable'
		},
		"drawCallback" : function(settings) {
			// initSwitch();
		},
		"columns" : [ {
			data : 'productCode',
			name : 'productCode'
		}, {
			data : 'name',
			name : 'name'
		}, {
			data : 'cost',
			name : 'cost'
		} ]
	});
	
	$('.product-container').each(function(id, el) {
		new Sortable(el, {
			group: 'shared',
			animation: 200,
			onEnd: function (evt) {
				let element = $(evt.item);
				let productId = element.data('id');
				let categoryId = element.closest('.card').data('id');
				
				$.ajax({
					url : "/admin/product-management/change-category",
					method: 'post',
					data: {
						productId: productId,
						categoryId: categoryId
					},
					success : function() {
						notify('success', 'Thành công');
					},
					error : function(err) {
						notify('error', err.responseJSON.message);
					}
				});
			}
		});
	});
});