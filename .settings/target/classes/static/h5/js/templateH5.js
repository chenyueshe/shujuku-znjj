var vm = new Vue({
	el: '#templateH5',
	data: {
		iframeSrc: 'dashboard/chart.html',
		slideMenuClass: ['sideMenu'],
		leftMenu: [{
			name: '列表0列表0列表0列表0列表0列表0列表0列表0列表0列表0',
			icon: ['icon', 'iconfont', 'icon-chajian'],
			children: [{
				name: '子列表1列表1子列表1列表1子列表1列表1子列表1列表1',
				url: 'templates/input.html'
			}, {
				name: '新增',
				url: 'templates/input.html'
			}, {
				name: '子列表2',
				url: ''
			}]
		}, {
			name: '列表1',
			icon: ['icon', 'iconfont', 'icon-chuli'],
			children: [{
				name: '子列表1',
				url: ''
			}, {
				name: '新增',
				url: 'templates/input.html'
			}, {
				name: '子列表2',
				url: ''
			}]
		}, {
			name: '列表2',
			icon: ['icon', 'iconfont', 'icon-yingyong'],
			// children: ['子列表0', '子列表1', '子列表2', ]
		}],
	},
	methods: {
		sideMenuShow: function() {
			this.slideMenuClass = ['sideMenu'];
			document.querySelector('.mask').style.display = "block";
			document.querySelector('.mask').classList.add('animate__animated', 'animate__fadeIn')
			document.querySelector('.sideMenu').style.display = "block";
			this.slideMenuClass.push('animate__animated', 'animate__fadeInLeft')
		},
		closeSideMenu: function() {
			this.slideMenuClass = ['sideMenu'];
			document.querySelector('.mask').style.display = "none";
			this.slideMenuClass.push('animate__animated', 'animate__fadeOutLeft')
		},
		menuOpen: function(num) {
			var allMenuOpen = document.querySelectorAll('.slideContent ul>li')
			for (i = 0; i < allMenuOpen.length; i++) {
				allMenuOpen[i].style.height = '1.2rem'
			}
			if (document.querySelectorAll('.menuOpen')[num].parentNode.style.height == '1.2rem') {
				document.querySelectorAll('.menuOpen')[num].parentNode.style.height = 'auto'
			} else {
				document.querySelectorAll('.menuOpen')[num].parentNode.style.height = '1.2rem'
			}
		},
		iframeSrcChange: function(num, num1) {
			this.iframeSrc = this.leftMenu[num].children[num1].url;
			this.closeSideMenu()
		}
	},
	created: function() {}
})
