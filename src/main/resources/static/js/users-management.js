$(function() {
	fetchInfo();
	
	let table = $('#users-datatable').DataTable({
		"serverSide" : true,
		"ajax" : {
			"url" : '/admin/users-management/datatable'
		},
		"drawCallback": function(settings) {
		    initSwitch();
	    },
		"columns" : [ {
			data : 'username',
			name : 'username'
		}, {
			data : 'isAdmin',
			name : 'isAdmin',
			orderable : false,
			width: "45px",
			className: "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				if (data)
					return `<div class="icheck-primary d-inline">
								<input type="radio" value="2" id="isAdmin" disabled checked>
								<label for="isAdmin"></label>
							</div>`;
				else return '';
			}
		}, {
			data : 'enabled',
			name : 'enabled',
			width: "80px",
			className: "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				if(!row.isAdmin) {
					if (data) return "<input type='checkbox' class='js-switch user-enabled' checked />";
					return "<input type='checkbox' class='js-switch user-enabled' />";
				} else return '';
			}
		}, {
			orderable : false,
			width: "45px",
			className: "dt-center dt-vertical-align",
			render : function(data, type, row, meta) {
				return '<span class="view-detail"></span>'
			}
		} ]
	});
	
	$(document).on('change', '.user-enabled', function() {
		let row = $(this).closest('tr');
		let username = table.row(row).data().username;
		let enabled = $(this).prop('checked');
		
		$.ajax({
			url : "/admin/users-management/change-status",
			method: 'post',
			data: {
				username: username,
				status: enabled
			},
			success : function() {
				notify('success', 'Thành công');
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	
	$(document).on('click', '.view-detail', function() {
		let row = $(this).closest('tr');
		let username = table.row(row).data().username;
		
		$.ajax({
			url : "/admin/users-management/view-detail",
			data: {
				username: username
			},
			success : function(data) {
				$('#user-detail').html(data);
				
				document.getElementById("user-detail").scrollIntoView();
			},
			error : function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
});