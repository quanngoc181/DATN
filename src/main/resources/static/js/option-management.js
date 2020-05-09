$(function() {
	let optionTable = $('#option-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/option-management/datatable'
		},
		"drawCallback" : function(settings) {
			// initSwitch();
		},
		"columns" : [ {
			data : 'name',
			name : 'name'
		}, {
			orderable : false,
			width : "45px",
			className : "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="add-row add-item"></span>'
			}
		}, {
			orderable : false,
			width : "45px",
			className : "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="edit-row edit-option"></span>'
			}
		}, {
			orderable : false,
			width : "45px",
			className : "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="delete-row delete-option"></span>'
			}
		} ]
	});
	
	let itemTable = $('#item-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/option-management/item-datatable'
		},
		"drawCallback" : function(settings) {
			// initSwitch();
		},
		"columns" : [ {
			data : 'optionName',
			name : 'optionName'
		}, {
			data : 'name',
			name : 'name'
		}, {
			data : 'cost',
			name : 'cost'
		}, {
			orderable : false,
			width : "45px",
			className : "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="edit-row edit-item"></span>'
			}
		}, {
			orderable : false,
			width : "45px",
			className : "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="delete-row delete-item"></span>'
			}
		} ]
	});
	
	$('#add-option').on('click', function() {
		$('#add-option-name').val('');
		$('#add-option-modal').modal('show');
	});
	
	$('#add-option-submit').on('click', function(e) {
		e.preventDefault();
		let value = $('#add-option-name').val().trim();
		if(value.length == 0) return;
		
		$.ajax({
			url : "/admin/option-management/add",
			method: 'post',
			data: {
				name: value
			},
			success : function() {
				$('#add-option-modal').modal('hide');
				optionTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.delete-option', function() {
		let row = $(this).closest('tr');
		let id = optionTable.row(row).data().id;
		
		confirmDelete(function() {
			$.ajax({
				url : "/admin/option-management/delete",
				method : 'post',
				data : {
					id : id
				},
				success : function(data) {
					optionTable.ajax.reload();
					itemTable.ajax.reload();
					notify('success', 'Thành công');
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		});
	});
	
	$(document).on('click', '.edit-option', function() {
		let row = $(this).closest('tr');
		let id = optionTable.row(row).data().id;
		let name = optionTable.row(row).data().name;
		
		$('#edit-option-id').val(id);
		$('#edit-option-name').val(name);
		$('#edit-option-modal').modal('show');
	});
	
	$('#edit-option-submit').on('click', function(e) {
		e.preventDefault();
		let id = $('#edit-option-id').val();
		let value = $('#edit-option-name').val().trim();
		if(value.length == 0) return;
		
		$.ajax({
			url : "/admin/option-management/edit",
			method: 'post',
			data: {
				id: id,
				name: value
			},
			success : function() {
				$('#edit-option-modal').modal('hide');
				optionTable.ajax.reload();
				itemTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.add-item', function() {
		$('#add-item-form')[0].reset();
		let row = $(this).closest('tr');
		let id = optionTable.row(row).data().id;
		$('#add-item-optionId').val(id);
		$('#add-item-modal').modal('show');
	});
	
	$('#add-item-submit').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/admin/option-management/add-item",
			method: 'post',
			data: objectifyForm('#add-item-form'),
			success : function() {
				$('#add-item-modal').modal('hide');
				optionTable.ajax.reload();
				itemTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.delete-item', function() {
		let row = $(this).closest('tr');
		let id = itemTable.row(row).data().id;
		
		confirmDelete(function() {
			$.ajax({
				url : "/admin/option-management/delete-item",
				method : 'post',
				data : {
					id : id
				},
				success : function(data) {
					itemTable.ajax.reload();
					notify('success', 'Thành công');
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		});
	});
	
	$(document).on('click', '.edit-item', function() {
		let row = $(this).closest('tr');
		let id = itemTable.row(row).data().id;
		let name = itemTable.row(row).data().name;
		let cost = itemTable.row(row).data().cost;
		let optionName = itemTable.row(row).data().optionName;
		
		$('#edit-item-id').val(id);
		$('#edit-item-name').val(name);
		$('#edit-item-cost').val(cost);
		$('#edit-item-optionName').val(optionName);
		$('#edit-item-modal').modal('show');
	});
	
	$('#edit-item-submit').on('click', function(e) {
		e.preventDefault();
		$.ajax({
			url : "/admin/option-management/edit-item",
			method: 'post',
			data: objectifyForm('#edit-item-form'),
			success : function() {
				$('#edit-item-modal').modal('hide');
				itemTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});