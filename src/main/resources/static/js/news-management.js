$(function() {
	fetchInfo();
	
	let newsTable = $('#news-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/news-management/datatable'
		},
		"drawCallback" : function(settings) {
			// initSwitch();
		},
		"columns" : [{
			data : 'createAt',
			name : 'createAt',
			width : "100px",
			render : function(data, type, row, meta) {
				return moment(data).format('HH:mm DD/MM/YYYY');
			}
		}, {
			data : 'updateAt',
			name : 'updateAt',
			width : "100px",
			render : function(data, type, row, meta) {
				if(data == null) return "";
				return moment(data).format('HH:mm DD/MM/YYYY');
			}
		}, {
			data : 'title',
			name : 'title'
		}, {
			data : 'description',
			name : 'description'
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
	
	$('#add-news').on('click', function() {
		$.ajax({
			url : "/admin/news-management/add",
			data : {},
			success : function(data) {
				$('#news-detail').html(data);
				$('#content').summernote();
				document.getElementById("news-detail").scrollIntoView();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '#add-news-submit', function(e) {
		e.preventDefault();
		
		let form = objectifyForm('#add-news-form');
		form.content = $('#content').summernote('code');
		
		$.ajax({
			url : "/admin/news-management/add",
			method: 'post',
			data : form,
			success : function(data) {
				$('#news-detail').html('');
				newsTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.delete-row', function() {
		let row = $(this).closest('tr');
		let id = newsTable.row(row).data().id;
		
		confirmDelete(function() {
			$.ajax({
				url : "/admin/news-management/delete",
				method : 'post',
				data : { id : id },
				success : function(data) {
					newsTable.ajax.reload();
				},
				error : function(err) {
					notify('error', err.responseJSON.message);
				}
			});
		});
	});
	
	$(document).on('click', '.edit-row', function() {
		let row = $(this).closest('tr');
		let id = newsTable.row(row).data().id;
		
		$.ajax({
			url : "/admin/news-management/edit",
			data : { id: id },
			success : function(data) {
				$('#news-detail').html(data);
				$('#content').summernote();
				document.getElementById("news-detail").scrollIntoView();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '#edit-news-submit', function(e) {
		e.preventDefault();
		
		let form = objectifyForm('#edit-news-form');
		form.content = $('#content').summernote('code');
		
		$.ajax({
			url : "/admin/news-management/edit",
			method: 'post',
			data : form,
			success : function(data) {
				$('#news-detail').html('');
				newsTable.ajax.reload();
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});