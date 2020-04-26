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
});