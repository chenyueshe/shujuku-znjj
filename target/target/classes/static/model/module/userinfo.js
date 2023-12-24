var vue=new Vue({
	el:"#userinfo",
	data:{
		phone:"",
		fileList:[],
		cityList:cityJson,
		busndeptList:[],
		gridList:[],
		microgridList:[],
		queryParam:{},
		saveParam:{},
		updateParam:{},
		labelPosition:"left",
		tableData:[],
		FormData:{},
		value:false,
		currentPage:1,
		total:0,
		startPage:1,
		pageSize:10,
		formLabelWidth:"120px",
		uploadUrl:urlAdd+"/uploaduserinfoURL",
		saveFormVisible:false,
		updateFormVisible:false,
		tableDataLoading:false,
		pickerOptions: {
			//2020年8月27日降级了函数声明方式
	          disabledDate:function(time) {
	            return time.getTime() < Date.now();
	          },
	          shortcuts: [{
	            text: '今天',
	            //2020年8月27日降级了函数声明方式
	            onClick:function(picker) {
	              picker.$emit('pick', new Date());
	            }
	          }]
	        }
	},
	methods:{
		queryInfoList:function(){
			//发送 post 请求
			var FormData=this.queryParam;
			
			FormData.startPage=this.startPage;
			FormData.pageSize=this.pageSize;
			var url=urlAdd+'/queryuserinfoList';
			this.tableDataLoading=true;
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.tableData=res.body.data.list;
					this.total=res.body.data.total;
					this.tableDataLoading=false;
				}else{
					this.$message.error(res.body.message);
				}
			},function(res){
				this.$message.error(res.body.message);
			});
		},
		handleSizeChange:function(val){
			this.pageSize=val;
			this.queryInfoList();
		},
		handleCurrentChange:function(val){
			this.startPage=val;
			this.queryInfoList();
		},
		submitUpload:function() {//文件上传
	        var FormData={};
	        var url=urlAdd+'/uploaduserinfoFile';
	        this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					this.$message({
			             message: res.body.message,
			             type: 'success'
			        });
			        this.queryInfoList();
				}else{
					this.$message.error(res.body.message);
				}
			},function(res){
				this.$message.error(res.body.message);
			});
	    },
	    handleRemove:function(file, fileList) {
	    	console.log(file, fileList);
	    },
	    handlePreview:function(file) {
	    	console.log(file);
	    },
	    onSuccess:function(response,file,filelist){
	    	if(response.status=="S"){
	    		this.$refs.upload.clearFiles();
		    	this.queryInfoList();
		    	 this.$message({
		             message: '恭喜你，文件上传成功',
		             type: 'success'
		         });
	    	}else{
	    		this.$refs.upload.clearFiles();
		    	this.queryInfoList();
	    		this.$message.error(response.message);
	    	}
	    	
	    },
	    formatDate:function(row, column, cellValue, index) {
	        if(cellValue==null || cellValue=="") return "";
	        let date = new Date(Date.parse(cellValue));
	        let Y = date.getFullYear() + '-';
	        let M = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) + '-' : date.getMonth() + 1 + '-';
	        let D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
	        let h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
	        let m = date.getMinutes()  < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
	        let s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
	        return Y + M + D ;
	      },
	      formatType:function(row, column, cellValue, index){
	    	  if(cellValue==null || cellValue=="") return "";
	    	  
	    	  if(cellValue=="out"){
	    		  return "IOP"
	    	  }else if(cellValue=="inner"){
	    		  return "用户导入"
	    	  }
	      },
	      deleteInfo:function(id){
	    	  this.$confirm('此操作将此${name}数据删除, 是否继续?', '提示', {
		            confirmButtonText: '确定',
		            cancelButtonText: '取消',
		            type: 'warning'
		            //2020年8月27日降级了箭头函数
		          }).then(function(){
		        	//发送 post 请求
						var FormData={};
						FormData.id=id;
						var url=urlAdd+'/deleteuserinfo';
						vue.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
							if("S"==res.body.status){
								this.$message({
						             message: res.body.message,
						             type: 'success'
						         });
								this.queryInfoList();
							}else{
								this.$message.error(res.body.message);
							}
						},function(res){
							this.$message.error(res.body.message);
						});
		          });	
	    	  
	      },
	      editInfo:function(row){
	      	this.updateParam=row;
	      	this.updateFormVisible=true;
	      },
	      saveInfo:function(){
	        var saveParam=this.saveParam;
	      	this.$confirm('此操作将此确认, 是否继续?', '提示', {
		            confirmButtonText: '确定',
		            cancelButtonText: '取消',
		            type: 'warning'
		            //2020年8月27日降级了箭头函数
		          }).then(function(){
		          		//发送 post 请求
						var FormData=saveParam;
						var url=urlAdd+'/adduserinfo';
						vue.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
							if("S"==res.body.status){
								vue.$message({
						             message: res.body.message,
						             type: 'success'
						        });
						        vue.saveFormVisible=false;
						        vue.queryInfoList();
							}else{
								vue.$message.error(res.body.message);
							}
						},function(res){
							vue.$message.error(res.body.message);
						});
		          })
	      },
	      updateInfo:function(){
	      	var updateParam=this.updateParam;
	      	this.$confirm('此操作将此${name}数据更新, 是否继续?', '提示', {
		            confirmButtonText: '确定',
		            cancelButtonText: '取消',
		            type: 'warning'
		            //2020年8月27日降级了箭头函数
		          }).then(function(){
		          	//发送 post 请求
					var FormData=updateParam;
					var url=urlAdd+'/updateuserinfo';
					vue.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
						if("S"==res.body.status){
							vue.$message({
					             message: res.body.message,
					             type: 'success'
					        });
					        vue.updateFormVisible=false;
					        vue.queryInfoList();
						}else{
							vue.$message.error(res.body.message);
						}
					},function(res){
						vue.$message.error(res.body.message);
					});
		          })
	      	
	      },
	      downloadInfo:function(){
	      	var FormData=this.queryParam;
	      	
	      	var elemIF = document.createElement('iframe')
	        //elemIF.src = urlAdd+"/downloadkhyjReport?startDate="+FormData.startDate+"&endDate="+FormData.endDate
	        elemIF.src = urlAdd+"/downloadkhyjReport?"
	        elemIF.style.display = 'none'
	        document.body.appendChild(elemIF)
	      },
	      download:function (data,row) {
			if (!data) {
				return;
			}
			let url = window.URL.createObjectURL(new Blob([data]));
			let link = document.createElement('a');
			link.href = url;
			// 获取文件名
			// download 属性定义了下载链接的地址而不是跳转路径
			link.setAttribute('download', row);
			document.body.appendChild(link);
			try{
				link.click();
			}catch( e){
				console.log(e)
			}
			
		}
	   
	}
});
vue.queryInfoList();


