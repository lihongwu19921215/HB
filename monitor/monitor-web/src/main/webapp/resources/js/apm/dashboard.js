$(function() {
	$('.spinner').ace_spinner({
		value : 60,
		min : 0,
		max : 100,
		step : 5,
		on_sides : true,
		icon_up : 'ace-icon fa fa-plus bigger-110',
		icon_down : 'ace-icon fa fa-minus bigger-110',
		btn_up_class : 'btn-success',
		btn_down_class : 'btn-danger'
	});
	var line = echarts.init($('#line')[0]);
	option = null;
	option = {
		title : {
			text : 'ECharts 入门示例'
		},
		tooltip : {},
		legend : {
			data : [ '销量' ]
		},
		xAxis : {
			data : [ "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" ]
		},
		yAxis : {},
		series : [ {
			name : '销量',
			type : 'bar',
			data : [ 5, 20, 36, 10, 10, 20 ],
		} ]
	};
	if (option && typeof option === "object") {
		var startTime = +new Date();
		line.setOption(option, true);
		var endTime = +new Date();
		var updateTime = endTime - startTime;
		console.log("Time used:", updateTime);
	}

	var gauge = echarts.init($('#gauge')[0]);
	gaugeOption = {
		tooltip : {
			formatter : "{a} <br/>{c} {b}"
		},
		toolbox : {
			show : true,
			feature : {
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		series : [ {
			name : '速度',
			type : 'gauge',
			z : 3,
			min : 0,
			max : 220,
			splitNumber : 11,
			radius : '50%',
			axisLine : { // 坐标轴线
				lineStyle : { // 属性lineStyle控制线条样式
					width : 10
				}
			},
			axisTick : { // 坐标轴小标记
				length : 15, // 属性length控制线长
				lineStyle : { // 属性lineStyle控制线条样式
					color : 'auto'
				}
			},
			splitLine : { // 分隔线
				length : 20, // 属性length控制线长
				lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
					color : 'auto'
				}
			},
			title : {
				textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					fontWeight : 'bolder',
					fontSize : 20,
					fontStyle : 'italic'
				}
			},
			detail : {
				textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					fontWeight : 'bolder'
				}
			},
			data : [ {
				value : 40,
				name : 'km/h'
			} ]
		}, {
			name : '转速',
			type : 'gauge',
			center : [ '20%', '55%' ], // 默认全局居中
			radius : '35%',
			min : 0,
			max : 7,
			endAngle : 45,
			splitNumber : 7,
			axisLine : { // 坐标轴线
				lineStyle : { // 属性lineStyle控制线条样式
					width : 8
				}
			},
			axisTick : { // 坐标轴小标记
				length : 12, // 属性length控制线长
				lineStyle : { // 属性lineStyle控制线条样式
					color : 'auto'
				}
			},
			splitLine : { // 分隔线
				length : 20, // 属性length控制线长
				lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
					color : 'auto'
				}
			},
			pointer : {
				width : 5
			},
			title : {
				offsetCenter : [ 0, '-30%' ], // x, y，单位px
			},
			detail : {
				textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					fontWeight : 'bolder'
				}
			},
			data : [ {
				value : 1.5,
				name : 'x1000 r/min'
			} ]
		}, {
			name : '油表',
			type : 'gauge',
			center : [ '77%', '50%' ], // 默认全局居中
			radius : '25%',
			min : 0,
			max : 2,
			startAngle : 135,
			endAngle : 45,
			splitNumber : 2,
			axisLine : { // 坐标轴线
				lineStyle : { // 属性lineStyle控制线条样式
					width : 8
				}
			},
			axisTick : { // 坐标轴小标记
				splitNumber : 5,
				length : 10, // 属性length控制线长
				lineStyle : { // 属性lineStyle控制线条样式
					color : 'auto'
				}
			},
			axisLabel : {
				formatter : function(v) {
					switch (v + '') {
					case '0':
						return 'E';
					case '1':
						return 'Gas';
					case '2':
						return 'F';
					}
				}
			},
			splitLine : { // 分隔线
				length : 15, // 属性length控制线长
				lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
					color : 'auto'
				}
			},
			pointer : {
				width : 2
			},
			title : {
				show : false
			},
			detail : {
				show : false
			},
			data : [ {
				value : 0.5,
				name : 'gas'
			} ]
		}, {
			name : '水表',
			type : 'gauge',
			center : [ '77%', '50%' ], // 默认全局居中
			radius : '25%',
			min : 0,
			max : 2,
			startAngle : 315,
			endAngle : 225,
			splitNumber : 2,
			axisLine : { // 坐标轴线
				lineStyle : { // 属性lineStyle控制线条样式
					width : 8
				}
			},
			axisTick : { // 坐标轴小标记
				show : false
			},
			axisLabel : {
				formatter : function(v) {
					switch (v + '') {
					case '0':
						return 'H';
					case '1':
						return 'Water';
					case '2':
						return 'C';
					}
				}
			},
			splitLine : { // 分隔线
				length : 15, // 属性length控制线长
				lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
					color : 'auto'
				}
			},
			pointer : {
				width : 2
			},
			title : {
				show : false
			},
			detail : {
				show : false
			},
			data : [ {
				value : 0.5,
				name : 'gas'
			} ]
		} ]
	};
	gauge.setOption(gaugeOption);

	setInterval(function() {
		gaugeOption.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
		gaugeOption.series[1].data[0].value = (Math.random() * 7).toFixed(2) - 0;
		gaugeOption.series[2].data[0].value = (Math.random() * 2).toFixed(2) - 0;
		gaugeOption.series[3].data[0].value = (Math.random() * 2).toFixed(2) - 0;
		gauge.setOption(gaugeOption, true);
	}, 2000);
})
