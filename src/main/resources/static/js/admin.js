$(function() {
	fetchInfo();
	initMonthPicker();

	var orderpieChartCanvas1 = $('#orderPieChart1').get(0).getContext('2d');
	var orderpieData1 = {
		labels: ['Chưa thanh toán', 'Đã thanh toán', 'Đang chờ', 'Đang giao', 'Đã hoàn thành', 'Tại quầy'],
		datasets: [{
			data: [0, 0, 0, 0, 0, 0],
			backgroundColor: ['#007bff', '#28a745', '#6c757d', '#dc3545', '#ffc107', '#17a2b8']
		}]
	}
	var orderpieOptions1 = { legend: { display: false } }
	var orderPieChart1 = new Chart(orderpieChartCanvas1, {
		type: 'doughnut',
		data: orderpieData1,
		options: orderpieOptions1
	});

	var orderpieChartCanvas2 = $('#orderPieChart2').get(0).getContext('2d');
	var orderpieData2 = {
		labels: ['Chưa thanh toán', 'Đã thanh toán', 'Đang chờ', 'Đang giao', 'Đã hoàn thành', 'Tại quầy'],
		datasets: [{
			data: [0, 0, 0, 0, 0, 0],
			backgroundColor: ['#007bff', '#28a745', '#6c757d', '#dc3545', '#ffc107', '#17a2b8']
		}]
	}
	var orderpieOptions2 = { legend: { display: false } }
	var orderPieChart2 = new Chart(orderpieChartCanvas2, {
		type: 'doughnut',
		data: orderpieData2,
		options: orderpieOptions2
	});

	$('#month-picker').on('pick.datepicker', function(e) {
		let month = +e.date.getMonth() + 1;
		let year = +e.date.getFullYear();

		$.ajax({
			url: "/admin/statistic",
			data: {
				month: month,
				year: year
			},
			success: function(data) {
				orderPieChart1.data.datasets[0].data = data.amountsOrder;
				orderPieChart1.update({ duration: 0 });

				orderPieChart2.data.datasets[0].data = data.valuesOrder;
				orderPieChart2.update({ duration: 0 });
			},
			error: function(err) {
				notify('error', err.responseJSON.message);
			}
		});
	});
	$('#month-picker').datepicker('setDate', new Date());

	let enabledAccount = +$('body').data('enabledaccount');
	let disabledAccount = +$('body').data('disabledaccount');
	let adminAccount = +$('body').data('adminaccount');
	let memberAccount = +$('body').data('memberaccount');
	let monthsAccount = $('body').data('monthsaccount');
	let amountsAccount = $('body').data('amountsaccount');
	let amountsOrder = $('body').data('amountsorder');
	let valuesOrder = $('body').data('valuesorder');

	var accountpieChartCanvas1 = $('#accountPieChart1').get(0).getContext('2d');
	var accountpieData1 = {
		labels: [
			'Enable',
			'Disable'
		],
		datasets: [
			{
				data: [enabledAccount, disabledAccount],
				backgroundColor: ['#007bff', '#28a745']
			}
		]
	}
	var accountpieOptions1 = {
		legend: {
			display: false
		}
	}
	var accountPieChart1 = new Chart(accountpieChartCanvas1, {
		type: 'doughnut',
		data: accountpieData1,
		options: accountpieOptions1
	});

	var accountpieChartCanvas2 = $('#accountPieChart2').get(0).getContext('2d');
	var accountpieData2 = {
		labels: [
			'Quản trị viên',
			'Thành viên'
		],
		datasets: [
			{
				data: [adminAccount, memberAccount],
				backgroundColor: ['#007bff', '#28a745']
			}
		]
	}
	var accountpieOptions2 = {
		legend: {
			display: false
		}
	}
	var accountPieChart2 = new Chart(accountpieChartCanvas2, {
		type: 'doughnut',
		data: accountpieData2,
		options: accountpieOptions2
	});

	var accountBarChart = $('#accountBarChart')[0].getContext('2d');
	new Chart(accountBarChart, {
		type: 'bar',
		data: {
			labels: monthsAccount,
			datasets: [{
				label: 'Tạo mới',
				backgroundColor: '#007bff',
				data: amountsAccount
			}]
		},
		options: {
			maintainAspectRatio: false,
			tooltips: {
				mode: 'nearest',
				intersect: false,
				axis: 'x'
			},
			hover: {
				mode: 'nearest',
				intersect: false,
				axis: 'x',
				animationDuration: 0
			},
			legend: { display: false },
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
	
	var orderBarChart1 = $('#orderBarChart1')[0].getContext('2d');
	new Chart(orderBarChart1, {
		type: 'bar',
		data: {
			labels: monthsAccount,
			datasets: [{
				label: 'Số đơn',
				backgroundColor: '#007bff',
				data: amountsOrder
			}]
		},
		options: {
			maintainAspectRatio: false,
			tooltips: {
				mode: 'nearest',
				intersect: false,
				axis: 'x'
			},
			hover: {
				mode: 'nearest',
				intersect: false,
				axis: 'x',
				animationDuration: 0
			},
			legend: { display: false },
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
	
	var orderBarChart2 = $('#orderBarChart2')[0].getContext('2d');
	new Chart(orderBarChart2, {
		type: 'bar',
		data: {
			labels: monthsAccount,
			datasets: [{
				label: 'Giá trị',
				backgroundColor: '#007bff',
				data: valuesOrder
			}]
		},
		options: {
			maintainAspectRatio: false,
			tooltips: {
				mode: 'nearest',
				intersect: false,
				axis: 'x'
			},
			hover: {
				mode: 'nearest',
				intersect: false,
				axis: 'x',
				animationDuration: 0
			},
			legend: { display: false },
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
});