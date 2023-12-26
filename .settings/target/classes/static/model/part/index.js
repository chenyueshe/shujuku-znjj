var vue=new Vue({
	el: "#index",
	data: function() {
		return {
			//整体数据
			batch_chuli:'',
			batch_shenhe:'',
			bactch_duanlianjie:'',
			blackCount:'',
			backTotal:'',
			taskTotal:'',
			phoneTotal:'',
			backEndTotal:'',
			//个人数据
			batch_chuli1:'',
			batch_shenhe1:'',
			bactch_duanlianjie1:'',
			blackCount1:'',
			backTotal1:'',
			taskTotal1:'',
			phoneTotal1:'',
			backEndTotal1:''
		}
	},
	methods: {
		queryReportInfo:function(){
			//黑名单1
			var FormData={};
			var url=urlAdd+'/survey/querySurveyInfo';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.blackCount = res.body.data.blackCount
				}else{
					this.$message.error(res.body.message);
				}
			},function(res){
					
			});
			//黑名单2
			var FormData={total:''};
			var url=urlAdd+'/survey/querySurveyInfo';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.blackCount1 = res.body.data.blackCount
				}else{
					this.$message.error(res.body.message);
				}
			},function(res){
					
			});
			//审核1
			var FormData={};
			FormData.startPage=1;
			FormData.pageSize=10;
			var url=urlAdd+'/survey/queryTaskInfoList';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.list=res.body.data.list;
					this.batch_shenhe=res.body.data.total;
				}else{
					alert(res.body.message);
				}
			},function(res){
				alert(res);
			});
			//审核2
			var FormData={total:''};
			FormData.startPage=1;
			FormData.pageSize=10;
			var url=urlAdd+'/survey/queryTaskInfoList';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.list=res.body.data.list;
					this.batch_shenhe1=res.body.data.total;
				}else{
					alert(res.body.message);
				}
			},function(res){
				alert(res);
			});
			//其他1
			var FormData={};
			var url=urlAdd+'/survey/querySurveyReportInfo';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.batch_chuli = res.body.data.batch_chuli
					this.bactch_duanlianjie = res.body.data.bactch_duanlianjie
					this.backTotal = res.body.data.backTotal
					this.taskTotal = res.body.data.taskTotal
					this.phoneTotal = res.body.data.phoneTotal
					this.backEndTotal = res.body.data.backEndTotal
				}else{
					alert(res.body.message);
				}
			},function(res){
				alert(res);
			});
			//其他2
			var FormData={total:''};
			var url=urlAdd+'/survey/querySurveyReportInfo';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.batch_chuli1 = res.body.data.batch_chuli
					this.bactch_duanlianjie1 = res.body.data.bactch_duanlianjie
					this.backTotal1 = res.body.data.backTotal
					this.taskTotal1 = res.body.data.taskTotal
					this.phoneTotal1 = res.body.data.phoneTotal
					this.backEndTotal1 = res.body.data.backEndTotal
				}else{
					alert(res.body.message);
				}
			},function(res){
				alert(res);
			});
		},
		linkPoint:function(num){
			if(num==2){
				window.top.postMessage('module/task/querytask.html', '*');	
			}else if(num==1 || num==3 || num==4 || num==5 || num==6 || num==7){
				window.top.postMessage('module/user/batchlist.html', '*');
			}
			else{
				window.top.postMessage('module/user/blackcustomlist.html', '*');
			}
		}
	},
	beforeMount: function() {
		
	}
})
vue.queryReportInfo();