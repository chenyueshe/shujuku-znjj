var vue = new Vue({
	el: "#index",
	data: {
		name: '',
		mainListMenu: [],
		activeIndex: "",
		opened: "",
		editableTabs: [],
		editableTabsValue: "",
		editableKeys: [],
		dashboardUrl: "",
		show2: true,
		caret: 'el-icon-caret-top',
		layuiStyle: {
			'left': '237px'
		},
		info: '',
		saveParam:{},
	},
	methods: {
		 saveInfo:function(){
		        var saveParam=this.saveParam;
          		//发送 post 请求
				var FormData=saveParam;
				var url=urlAdd+'/adduserinfo';
				vue.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
					if("S"==res.body.status){
						vue.$message({
				             message: res.body.message,
				             type: 'success'
				        });
				       window.location.href="login.html";
					}else{
						vue.$message.error(res.body.message);
					}
				},function(res){
					vue.$message.error(res.body.message);
				});
		 }
	}
	
	

})

