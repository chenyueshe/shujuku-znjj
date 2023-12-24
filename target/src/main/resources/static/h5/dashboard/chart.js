var vm = new Vue({
	el: '#charts',
	data: {
		chartData: [{
			src: '../chartIframe/1.html',
			name: '场景A场景A场景A场景A场景A场景A场景A场景A场景A场景A场景A',
		}, {
			src: '../chartIframe/2.html',
			name: '场景B',
		}, {
			src: '../chartIframe/3.html',
			name: '场景C',
		}, {
			src: '../chartIframe/3.html',
			name: '场景D',
		}, {
			src: '../chartIframe/3.html',
			name: '场景E',
		}, {
			src: '../chartIframe/3.html',
			name: '场景F',
		}, {
			src: '../chartIframe/3.html',
			name: '场景G',
		}],
		dataOptionsBackControl: false,
		loadingControl: false,
		chartsDataStep: 0,
		sceneSelect: false,
		num: 0,
		sceneTitle: '场景选择',
		jumpText: '',
		jumpUrl: '../chartIframe/1.html'
	},
	methods: {
		loading: function() {
			//加载动画开始
			this.loadingControl = true;
			//传递场景名称
			// 加载动画结束
			setTimeout(function() {
				vm.loadingControl = false;
			}, 1000)
		},
		queryCharsData: function() {},
		//场景选择下拉菜单
		sceneDropDown: function() {
			if (this.num == 0) {
				this.num = 1;
				this.sceneSelect = true;
				document.querySelector('.sceneSelect').classList.add('animate__animated',
					'animate__bounceInDown')
			} else {
				this.num = 0;
				this.sceneSelect = false;
			}
		},
		//场景名称选择
		selectName: function(e, opt1, opt2) {
			var allLi = document.querySelectorAll('.sceneSelect ul li');
			for (i = 0; i < allLi.length; i++) {
				allLi[i].classList.remove('selectNameColor', 'animate__animated', 'animate__bounce')
			}
			e.currentTarget.classList.add('selectNameColor', 'animate__animated', 'animate__bounce')
			this.jumpUrl0 = opt1;
			this.jumpText0 = opt2;
			chartsDisplayText.innerHTML = opt2;

		},
		//场景名称置入
		insertName: function() {
			this.loading()
			this.sceneTitle = this.jumpText0;
			this.jumpUrl = this.jumpUrl0;
			this.sceneSelect = false;
		}
	},
	created: function() {

	},
	mounted: function() {
		setTimeout(function() {
			vm.queryCharsData()
		}, 1000)
	},
})
