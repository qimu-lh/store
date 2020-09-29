$(function () {
    //页面加载时运行的代码
    //判断是否有自动登录的内容
/*    if ($.cookie("isAutoLogin") === "true") {
        //如果是自动登录，将cookie中的信息自动填写到用户名和密码框中
        $("#auto").prop("checked", true);
        $("#username").val($.cookie("username"));
        $("#password").val($.cookie("password"));
    }*/
    $("#btn-login").click(function () {
        if ($("#username").val()===""){
            alert("用户名不能为空！");
        }else if ($("#password").val()===""){
            alert("密码不能为空！")
        }else {
            $.ajax({
                "url": "/users/login",
                "data": $("#form-login").serialize(),
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state === 2000) {
                        //rememberPwd();
                        alert("登录成功");
                        // 跳转到某个页面
                        location.href="http://localhost:8080/products/hot";
                        window.event.returnValue=false; //return false是阻止游览器对事件的默认处理
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
    })
})