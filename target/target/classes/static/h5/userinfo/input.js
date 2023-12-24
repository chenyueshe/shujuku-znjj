var vm = new Vue({
	el: '.input',
	data: {
		cityName: [
			'呼和浩特市',
			'呼伦贝尔市',
			'包头市',
			'鄂尔多斯市',
			'巴彦淖尔市',
			'乌兰察布市',
			'赤峰市',
			'通辽市',
			'兴安盟',
			'乌海市',
		],
		selectInput: '',
		addtInput: '',
		loadingControl: false,
		dataOptionsBackControl: false,
		addPanelControl: false,
		dropDownCategory: '',
//		radioOptions: [{value:"",text:""}],
		
		radio: '',
		checkboxOptions: [],
		checkbox: [],
		addData: {},
		selectCondition: true,
		selectControl: false,
		selectResultIfr: false,
		tableData: [],
		tableDataLength: 0,
		currentPage:1,
		pageSize:10,
		page:0,
		queryParam:{
			value:''	
		},
		addParam:{
		},
		updateParam:{
		}
	},
	methods: {
		queryData: function() {
			this.loadingControl = true;
			var FormData = {}
			FormData=this.queryParam;
			FormData.startPage=this.currentPage;
			FormData.pageSize=this.pageSize;
			var url=urlAdd+'/queryuserinfoList';
			this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
				if("S"==res.body.status){
					
					this.tableData=res.body.data.list;
					this.tableDataLength=res.body.data.total;
					vm.loadingControl = false;
					vm.selectCondition = false;
					vm.selectControl = true;
					vm.selectResultIfr = true
				}else{
					
				}
			},function(res){
				
			});
		},
		prePage:function(){
			if(this.currentPage>1){
				this.currentPage--;
				this.queryData();
			}
			
		},
		nextPage:function(){
			if(this.currentPage<this.page){
				this.currentPage++;
				this.queryData();
			}
		},
		scrollLeftFun: function() {
			var tableMargin = document.querySelector('.dataOptions')
			//元素实际长度
			var tableMargin01 = tableMargin.scrollWidth;
			//卷入的长度
			var tableMargin02 = parseInt(tableMargin.scrollLeft);
			//元素可视区域长度
			var tableMargin03 = tableMargin.offsetWidth;
			if (tableMargin02 + tableMargin03 == tableMargin01) {
				var allUl = document.querySelectorAll('.dataOptionsControl');
				for (o = 0; o < allUl.length; o++) {
					allUl[o].style.display = 'none'
				}
				arguments[1].path[1].lastChild.style.display = 'flex';
				arguments[1].path[1].lastChild.children[0].style.display = 'flex';
				arguments[1].path[1].lastChild.classList.add('animate__animated', 'animate__bounceInRight')
			}
		},
		scrollRightFun: function() {
			// arguments[1].path[1].lastChild.style.display = 'none';
			// arguments[1].path[1].lastChild.classList.remove('animate__animated', 'animate__fadeIn')
			var allUl = document.querySelectorAll('.operationPanel');
			for (o = 0; o < allUl.length; o++) {
				allUl[o].style.display = 'none';
				// allUl[o].classList.remove('animate__animated', 'animate__bounceInRight')
			}
		},
			//更新
		modifyIt: function(data) {
			document.querySelector('.picker').style.display = 'block';
			this.updateParam=data;
		},
				//删除
		deleteIt: function(e) {
			arguments[0].path[1].parentNode.style.display = 'none';

		},
		showSelectCondition: function() {
			vm.selectCondition = true;
			vm.selectControl = false;
			vm.selectResultIfr = false
		},
		closePicker: function() {
			document.querySelector('.picker').style.display = 'none';
		},
		addPanelShow: function() {
			this.addPanelControl = true
			document.querySelector('.addPanel').classList.add('animate__animated', 'animate__bounce')
		},
		addPanelHide: function() {
			this.addPanelControl = false
			document.querySelector('.addPanel').classList.remove('animate__animated', 'animate__bounce')
		},
		radioCheck: function(e) {
			var allDiv = document.querySelectorAll('.radioBox>div>div');
			for (i = 0; i < allDiv.length; i++) {
				allDiv[i].classList.remove('radoiFocus')
			}
			e.target.classList.add('radoiFocus')
		},
		checkboxCheck: function(e) {
			if (e.target.classList.contains('radoiFocus')) {
				e.target.classList.remove('radoiFocus')
			} else {
				e.target.classList.add('radoiFocus')
			}
		},
		addDataInfo: function() {
			console.log(this.addParam)
			var FormData=this.addParam
			var url=urlAdd+'adduserinfo';
			 $.confirm("您确定要提交新增数据吗?", "确认提交?", function() {
				vm.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
					if("S"==res.body.status){
						$.toast("提交成功", function() {
							this.addPanelHide();
						});
					}else{
						
					}
				},function(res){
					
				});
			}, function() {
			  //取消操作
			});
		},
		updateDataInfo:function(){
			console.log(this.updateParam)
			var FormData=this.updateParam;
			var url=urlAdd+'/updateuserinfo';
			 $.confirm("您确定要提交更新数据吗?", "确认更新?", function() {
				vm.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
					if("S"==res.body.status){
						$.toast("提交成功", function() {
							this.closePicker();
							this.queryData();
						});
					}else{
						
					}
				},function(res){
					
				});
			}, function() {
			  //取消操作
			});
		},
		deleteDataInfo:function(){
			console.log(this.deleteParam)
			var FormData=this.deleteParam;
			var url=urlAdd+'deleteuserinfo';
			 $.confirm("您确定要提交删除数据吗?", "确认删除?", function() {
				vm.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
					if("S"==res.body.status){
						$.toast("提交成功", function() {
							this.queryData();
						});
					}else{
						
					}
				},function(res){
					
				});
			}, function() {
			  //取消操作
			});
		}
	},
	watch:{
		tableDataLength:{
			handler:function(newVal, oldVal){
				this.page=Math.ceil(newVal/this.pageSize)
			},
			immediate: true,
			deep: true
		}
	}

})

