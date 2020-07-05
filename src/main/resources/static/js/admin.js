$(function() {
	fetchInfo();
	
	let enabledAccount = +$('body').data('enabledaccount');
	let disabledAccount = +$('body').data('disabledaccount');
	let adminAccount = +$('body').data('adminaccount');
	let memberAccount = +$('body').data('memberaccount');
	let monthsAccount = $('body').data('monthsaccount');
	let amountsAccount = $('body').data('amountsaccount');

	var pieChartCanvas1 = $('#pieChart1').get(0).getContext('2d')
	var pieData1 = {
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
	var pieOptions1 = {
		legend: {
			display: false
		}
	}
	var pieChart1 = new Chart(pieChartCanvas1, {
		type: 'doughnut',
		data: pieData1,
		options: pieOptions1
	});

	var pieChartCanvas2 = $('#pieChart2').get(0).getContext('2d')
	var pieData2 = {
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
	var pieOptions2 = {
		legend: {
			display: false
		}
	}
	var pieChart2 = new Chart(pieChartCanvas2, {
		type: 'doughnut',
		data: pieData2,
		options: pieOptions2
	});

	var accountChart = $('#accountChart')[0].getContext('2d');
	return new Chart(accountChart, {
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
});