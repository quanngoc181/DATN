$(function() {
	let discountTable = $('#discount-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/discount-management/datatable'
		},
		"drawCallback" : function(settings) {
			// initSwitch();
		},
		"columns" : [ {
			data : 'description',
			name : 'description'
		}, {
			data : 'amount',
			name : 'amount'
		}, {
			data : 'unit',
			name : 'unit'
		}, {
			data : 'startDate',
			name : 'startDate',
			render : function(data, type, row, meta) {
				return moment(data).format('HH:mm DD/MM/YYYY');
			}
		}, {
			data : 'endDate',
			name : 'endDate',
			render : function(data, type, row, meta) {
				return moment(data).format('HH:mm DD/MM/YYYY');
			}
		}, {
			orderable : false,
			width : "45px",
			className : "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="edit-row"></span>'
			}
		}, {
			orderable : false,
			width : "45px",
			className : "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="delete-row"></span>'
			}
		} ]
	});
	
	$('#add-discount').on('click', function() {
		$.ajax({
			url : "/admin/discount-management/add",
			data: { },
			success : function(data) {
				$('#add-discount-container').html(data);
				initDatePicker();
				$('[data-mask]').inputmask();
				document.getElementById("add-discount-container").scrollIntoView();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '#add-discount-submit', function(e) {
		e.preventDefault();
		let data = objectifyForm('#add-discount-form');
		let products = '';
		$('input[name="products"]').each(function(index, element) {
			if($(this).prop('checked')) {
				products += $(this).val() + ';';
			}
		});
		if(products.length != 0) products = products.slice(0, -1);
		data.products = products;
		$.ajax({
			url : "/admin/discount-management/add",
			method : 'post',
			data : data,
			success : function(data) {
				$('#add-discount-container').html('');
				discountTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('change', '.card-title input', function() {
		let checked = $(this).prop('checked');
		if(checked) {
			$(this).closest('.card').find('.card-body input').prop('checked', true);
		} else {
			$(this).closest('.card').find('.card-body input').prop('checked', false);
		}
	});
	
	$(document).on('change', '.card-body input', function() {
		let checked = $(this).prop('checked');
		if(!checked) {
			$(this).closest('.card').find('.card-title input').prop('checked', false);
		}
	});
	
	$(document).on('click', '.delete-row', function() {
		let row = $(this).closest('tr');
		let id = discountTable.row(row).data().id;
		
		confirmDelete(function() {
			$.ajax({
				url : "/admin/discount-management/delete",
				method : 'post',
				data : {
					id : id
				},
				success : function(data) {
					discountTable.ajax.reload();
					notify('success', 'Thành công');
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		});
	});
	
	$(document).on('click', '.edit-row', function() {
		let row = $(this).closest('tr');
		let id = discountTable.row(row).data().id;
		
		$.ajax({
			url : "/admin/discount-management/edit",
			data: { id: id },
			success : function(data) {
				$('#add-discount-container').html(data);
				initDatePicker();
				$('[data-mask]').inputmask();
				document.getElementById("add-discount-container").scrollIntoView();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '#edit-discount-submit', function(e) {
		e.preventDefault();
		let data = objectifyForm('#edit-discount-form');
		let products = '';
		$('input[name="products"]').each(function(index, element) {
			if($(this).prop('checked')) {
				products += $(this).val() + ';';
			}
		});
		if(products.length != 0) products = products.slice(0, -1);
		data.products = products;
		$.ajax({
			url : "/admin/discount-management/edit",
			method : 'post',
			data : data,
			success : function(data) {
				$('#add-discount-container').html('');
				discountTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});