$(function() {
	let categoryTable = $('#category-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/category-management/datatable'
		},
		"drawCallback" : function(settings) {
			// initSwitch();
		},
		"columns" : [ {
			data : 'categoryCode',
			name : 'categoryCode'
		}, {
			data : 'name',
			name : 'name'
		}, {
			orderable : false,
			width: "45px",
			className: "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="view-detail"></span>'
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

	$('#add-category').on('click', function() {
		$('#add-category-modal').modal('show');
	});
	
	$(document).on('click', '.edit-row', function() {
		let row = $(this).closest('tr');
		let data = categoryTable.row(row).data();
		$('#edit-modal-id').val(data.id);
		$('#edit-modal-name').val(data.name);
		$('#edit-category-modal').modal('show');
	});

	$(document).on('click', '.delete-row', function() {
		let row = $(this).closest('tr');
		let id = categoryTable.row(row).data().id;
		
		confirmDelete(function() {
			$.ajax({
				url : "/admin/category-management/delete",
				method : 'post',
				data : {
					id : id
				},
				success : function(data) {
					categoryTable.ajax.reload();
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		}, "Thao tác này sẽ xóa toàn bộ sản phẩm thuộc danh mục");
	});
	
	$(document).on('click', '.view-detail', function() {
		let row = $(this).closest('tr');
		let id = categoryTable.row(row).data().id;
		
		$.ajax({
			url : "/admin/category-management/view-detail",
			data: {
				id: id
			},
			success : function(data) {
				$('#category-detail').html(data);
				
				document.getElementById("category-detail").scrollIntoView();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});