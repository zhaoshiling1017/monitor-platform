/**
 * Created by lj on 16/8/31.
 */
function initChart(data) {
	//柱状图
	$(".chart canvas").remove();
	$(".chart").each(function(){
		var index = $(this).index('.chart');
		if(index == 0){
			$('<canvas id="myChart0" width="600" height="400"></canvas>').insertBefore(".chart .legend");		
		}else{
			$(this).append('<canvas id="myChart'+index+'" width="600" height="400"></canvas>');
		}
	})
    var ctx0 = document.getElementById("myChart0");
    var myChart0 = null;
    myChart = new Chart(ctx0, {
        type: 'bar',
        data: {
            labels: (function() {	
		        	    var data1=[];			
		        	    for (var i = 0; i <data.length; i++) {									
		        	      data1.push(data[i].date);														 
		        	    }		
		        	    return data1;												
		    	  	})(),
            datasets: [{
                label:'启动次数',
                data: (function() {	
	            	    var data1=[];	
	            	    for (var i = 0; i <data.length; i++) {									
	            	      data1.push(parseInt(data[i].count));														 
	            	    }
	            	    return data1;												
	        	  	})(),
                backgroundColor: '#3B8FF6',//'#FF921E','#7AC843'
                borderColor: '#3B8FF6',
                borderWidth: 1
            }]
        },
        options: {
            responsive: false,
            title: {
                display: true,
                text: '2016年流程启动统计表',
                fontSize: 16,
                padding: 15
            },
            legend: {
                display: false,
            },
            scales: {
                xAxes: [{
                    gridLines: {
                        display: true,
                    },
                    scaleLabel: {
                        display: true,
                        labelString: '月份',
                        fontColor: '#666'
                    },
                    type: "category",
                    stacked: true,
                    categoryPercentage: 0.3,
                    barPercentage: 1.0
                }],
                yAxes: [{
                    gridLines: {
                        display: true,
                    },
                    scaleLabel: {
                        display: true,
                        labelString: '启动次数',
                        fontColor: '#666'
                    },
                    ticks: {
                        autoSkip: false,
                        beginAtZero: true,
                        fixedStepSize: 1
                    },
                }],
            }
        }
    });
    //折线图
    var ctx1 = document.getElementById("myChart1");
    var myChart1 = new Chart(ctx1, {
        type: 'line',
        data: {
            labels: (function() {	
        	    var data1=[];			
        	    for (var i = 0; i <data.length; i++) {									
        	      data1.push(data[i].date);														 
        	    }		
        	    return data1;												
    	  	})(),
            datasets: [
                {
                    label: "启动次数",
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: "rgba(75,192,192,0.4)",
                    borderColor: "#7AC843",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "#7AC843",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "#7AC843",
                    pointHoverBorderColor: "rgba(220,220,220,1)",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: (function() {	
		    	        	    var data1=[];			
		    	        	    for (var i = 0; i <data.length; i++) {									
		    	        	      data1.push(parseInt(data[i].count));														 
		    	        	    }	
		    	        	    return data1;												
		    	    	  	})(),
                    spanGaps: false,
                }
            ]
        },
        options: {
            responsive: false,
            title: {
                display: true,
                text: '2016年流程启动统计表',
                fontSize: 16,
                padding: 15
            },
            legend: {
                display: false,
            },
            scales: {
                xAxes: [{
                    gridLines: {
                        display: false,
                    },
                    scaleLabel: {
                        display: true,
                        labelString: '月份',
                        fontColor: '#666'
                    },
                    type: "category",
                    stacked: true,
                    categoryPercentage: 0.5,
                }],
                yAxes: [{
                    gridLines: {
                        display: true,
                    },
                    scaleLabel: {
                        display: true,
                        labelString: '启动次数',
                        fontColor: '#666'
                    },
                    ticks: {
                        autoSkip: false,
                        beginAtZero: true,
                        fixedStepSize: 1
                    },
                }],
            }
        }
    });
    //饼状图
    var ctx2 = document.getElementById("myChart2");
    var myChart2 = new Chart(ctx2, {
        type: 'pie',
        animation: {
            animateScale:true
        },
        data: {
            labels:(function() {	
			    var data1=[];			
			    for (var i = 0; i <data.length; i++) {									
			      data1.push(data[i].date);														 
			    }
			    return data1;												
			})(),
            datasets: [
                {
                    data: (function() {	
	    	        	    var data1=[];			
	    	        	    for (var i = 0; i <data.length; i++) {									
	    	        	      data1.push(parseInt(data[i].count));														 
	    	        	    }		
	    	        	    return data1;												
	    	    	  	})(),
                    backgroundColor: [
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843",
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843",
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843",
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843"
                    ],
                    hoverBackgroundColor: [
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843",
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843",
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843",
                        "#3B8FF6",
                        "#FF921E",
                        "#7AC843"
                    ]
                }
            ]
        },
        options: {
            responsive: false,
            title: {
                display: true,
                text: '2016年流程启动统计表',
                fontSize: 16,
                padding: 15
            },
            legend: {
                display: true,
            }
        }
    });
}