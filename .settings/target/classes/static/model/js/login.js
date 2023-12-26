var encrypt=function(data){
	var newPassWord=CryptoJS.enc.Utf8.parse(data);
	var newkey=CryptoJS.enc.Utf8.parse(key);
	var newIv=CryptoJS.enc.Utf8.parse(iv);
	const encrypted = CryptoJS.AES.encrypt(newPassWord, newkey,{ iv: newIv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.ZeroPadding});
	return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
}

var app=new Vue({
	el:"#app",
	data:{
		username:"",
		password:"",
		message:"",
		showMessage:"这是一条显示信息"
	},
	methods:{
		login:function(event){
			var url=urlAdd+'/login';
			var FormData={};
			FormData.userName=this.username;
			FormData.password=this.password;
            this.$http.post(url,FormData,{emulateJSON:true}).then(function(res){
                if("S"==res.body.status){
                	window.location.href="index.html";
                }else{
                	console.log(res.body.message);
                }
            },function(res){
					
            });
        
		},
		show:function(){
			this.message="{{showMessage}}"
		}
	}
});
app.show();