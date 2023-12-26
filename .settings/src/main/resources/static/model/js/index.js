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
		info: ''
	},
	methods: {
		queryUserInfo: function() {
			var query = window.location.search.substring(1);
			var userId = query.split("=")[1];
			var FormData = {};
			FormData.userId = userId;
			var url = urlAdd + '/survey/queryUserInfo';
			this.$http.post(url, FormData, {
				emulateJSON: true
			}).then(function(res) {
				if ("S" == res.body.status) {
					this.name = res.body.data.user_name;
					this.mainListMenu = res.body.list;
					this.dashboardUrl = "part/index.html";
				} else {
					this.$message.error(res.body.message);
				}
			}, function(res) {

			});

		},
		handleOpen: function(key, keyPath) {
			console.log(key, keyPath);
		},
		handleClose: function(key, keyPath) {
			console.log(key, keyPath);
		},
		handleTabsEdit: function(targetName, action) {
			//	    	if (action === 'add') {
			//	            let newTabName = ++this.tabIndex + '';
			//	            this.editableTabs.push({
			//	              title: 'New Tab',
			//	              name: newTabName,
			//	              content: 'New Tab content'
			//	            });
			//	            this.editableTabsValue = newTabName;
			//	          }
			if (action === 'remove') {
				let tabs = this.editableTabs;
				let activeName = this.editableTabsValue;
				if (activeName === targetName) {
					//2020年8月27日降级了箭头函数
					tabs.forEach(function(tab, index) {
						if (tab.name === targetName) {
							let nextTab = tabs[index + 1] || tabs[index - 1];
							if (nextTab) {
								activeName = nextTab.name;
							}
						}
					});
				}

				this.editableTabsValue = activeName;
				//2020年8月27日降级了箭头函数
				this.editableTabs = tabs.filter(function(tab) {
					tab.name !== targetName
				});
			}
		},
		addAction: function(url,menuName) {
			window.parent.frames['right_frame'].window.location.href = url;
			//this.editableKeys.includes(cells.url)
			if (this.editableKeys.indexOf(url) != '-1') {
				return;
			} else {
				this.editableKeys.push(url)
			}
			var newTabName = menuName;
			this.editableTabs.push({
				title: newTabName,
				name: url,
				content: url
			});
			this.editableTabsValue = newTabName;

		},
		handleTabclick: function(tab, event) {
			console.log(tab)
			var url = event.target.getAttribute('id').replace("tab-module/", "module/");
			window.parent.frames['right_frame'].window.location.href = url;
			this.activeIndex = url;
		},
		showorHide: function() {
			this.show2 = !this.show2
			if (this.caret == 'el-icon-caret-bottom') {
				this.caret = 'el-icon-caret-top'
				this.layuiStyle = {
					'position': 'absolute',
					'left': '237px'
				}
			} else {
				this.caret = 'el-icon-caret-bottom'

				this.layuiStyle = {
					'position': 'absolute',
					'width': '100%',
					//'height': '600px'
					'left':'0px'
				}
			}
		},
		jump: function() {
			window.location.href = "index.html";
		},
		handleMessage: function(event) {
			if (event.data.message) {
				this.show2 = !this.show2
				if (this.caret == 'el-icon-caret-bottom') {
					this.caret = 'el-icon-caret-top'
					this.layuiStyle = {
						'position': 'absolute',
						'left': '237px'
					}
				} else {
					this.caret = 'el-icon-caret-bottom'

					this.layuiStyle = {
						'position': 'static',
						'width': '100%',
						'height': '600px'
					}
				}
			}
		}
	},
	created:function(){
		window.addEventListener('message',function(e){
			this.dashboardUrl = e.data
			window.parent.frames['right_frame'].window.location.href = this.dashboardUrl;
        },false);
	},
	updated: function() {
		if (layui.element == undefined) {
			return;
		}
		layui.element.init();
	}

})
vue.queryUserInfo();
