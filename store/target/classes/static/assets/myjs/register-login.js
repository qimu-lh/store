$(function () {
    $("#btn-reg").click(function () {
        if ($("#reg-username").val()===""){
            alert("用户名不能为空！")
        }else if ($("#reg-newPassword").val()!==$("#reg-confirmPassword").val()){
            alert("前后密码不一致！")
        }else {
                $.ajax({
                    "url": "/users/reg",
                    "data": $("#form-reg").serialize(),
                    "type": "post",
                    "dataType": "json",
                    "success": function (json) {
                        if (json.state === 2000) {
                            alert("注册成功");
                            // 跳转到某个页面
                        } else {
                            alert(json.message);
                        }
                    }
                });
        }
    });
    $("#btn-login").click(function () {
        $.ajax({
            "url": "/users/login",
            "data": $("#form-login").serialize(),
            "type": "post",
            "dataType": "json",
            "success": function (json) {
                if (json.state === 2000) {
                    alert("登录成功");
                    // 跳转到某个页面
                    location.href="/web/account-profile.html";
                } else {
                    alert(json.message);
                }
            }
        });
    });
});