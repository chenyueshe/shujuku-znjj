var vue=new Vue({
	el:"#dashboard",
	data:{
		list:[],
		taskValue:0,
		dialogTableVisible:false,
		dialogFormVisible: false,
		formLabelWidth: '120px',
		show:true,
		startPage:1,
		pageSize:10,
		resultShow:false
		
	},
	filters:{
		formatDate1:function(time){
		  if(time==null || time=="") return "";
	        let date = new Date(Date.parse(time));
	        let Y = date.getFullYear() + '-';
	        let M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) + '-' : date.getMonth() + 1 + '-';
	        let D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
	        let h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
	        let m = date.getMinutes()  < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
	        let s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
	        return Y + M + D + h+m+s;
		}
	},
	methods:{
		queryTaskInfoList:function(){
			//发送 post 请求
			var FormData={};
			FormData.startPage=this.startPage;
			FormData.pageSize=this.pageSize;
			var url=urlAdd+'/survey/queryTaskInfoList';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.taskValue=res.body.data.total;
				}else{
					this.$message.error(res.body.message);
				}
			},function(res){
				alert(res);
			});
		},
		queryTVSurveyResult:function(){
			//发送 post 请求
			var FormData={};
			var url=urlAdd+'/tv/queryTVSurveyResult';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					option1.series.data=res.body.list;
					myChart1.setOption({
						 legend: {
						        orient: 'vertical',
						        left: 10,
						        data: ['互联网电视已收到调研问卷', '互联网电视未收到调研问卷']
						    },
						    series: [
						        {
						            name: '访问来源',
						            type: 'pie',
						            radius: ['50%', '70%'],
						            avoidLabelOverlap: false,
						            label: {
						                show: false,
						                position: 'center'
						            },
						            emphasis: {
						                label: {
						                    show: true,
						                    fontSize: '30',
						                    fontWeight: 'bold'
						                }
						            },
						            labelLine: {
						                show: false
						            },
						            data: res.body.list
						        }
						    ]
					});
				}else{
					this.$message.error(res.body.message);
				}
			},function(res){
				alert(res);
			});
		},
		queryPhoneSurveyResult:function(){
			//发送 post 请求
			var FormData={};
			var url=urlAdd+'/phone/queryPhoneSurveyResult';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					myChart.setOption({
						 legend: {
						        orient: 'vertical',
						        left: 10,
						        data: ['手机营业厅已收到调研问卷', '手机营业厅未收到调研问卷']
						    },
						    series: [
						        {
						            name: '访问来源',
						            type: 'pie',
						            radius: ['50%', '70%'],
						            avoidLabelOverlap: false,
						            label: {
						                show: false,
						                position: 'center'
						            },
						            emphasis: {
						                label: {
						                    show: true,
						                    fontSize: '30',
						                    fontWeight: 'bold'
						                }
						            },
						            labelLine: {
						                show: false
						            },
						            data: res.body.list
						        }
						    ]
					});
				}else{
					this.$message.error(res.body.message);
				}
			},function(res){
				alert(res);
			});
		}
	}
})
vue.queryTaskInfoList();
vue.queryPhoneSurveyResult();
vue.queryTVSurveyResult();

