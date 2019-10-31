$(function() {
	zylVerCode();//初始化生成随机数
	
	  var countdown = 60;
       $(function () {
            $("#getCode").bind("click", function () {
                settime();
            });
        })
        function settime(e) {
            if (countdown == 0) {
                countdown = 60;
                $("#getCode").val("获取验证码");
                $("#getCode").removeAttr("disabled");
                return false;
            } else {
                countdown--;
                $("#getCode").val(countdown + "秒后重新获取");
                $("#getCode").attr("disabled", "disabled");
            }
            setTimeout(function () {
                settime();
            }, 1000);
        }

});


//生成随机数
function zylVerCode(len){
    len = len || 4;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';//默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
    var maxPos = $chars.length;
    var zylCode = '';
    for (i = 0; i < len; i++) {
        zylCode += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    $(".zylVerCode").html(zylCode);
}
