$(function () {
    /**
     * 获得验证码
     */
    $("#btn-getCode").click(function () {
        var email=$("#email01").val();
        if (email==""||!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            alert("格式不正确！请重新输入！");
            $("#email01").focus();
        }else {
            $.ajax({
                "url": "/users/get_code",
                "data": $("#form-recovery").serialize(),
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state == 2000) {
                        alert("验证码发送成功！请注意查收！")
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
    });
    /**
     * 重置密码
     */
    $("#btn-reset-password").click(function () {
        if ($("#reset-newPassword").val()!==$("#reset-confirmPassword").val()){
            alert("前后密码不一致！");
            $("#reset-confirmPassword").focus();
        }else {
            $.ajax({
                "url": "/users/reset_password",
                "data": $("#form-recovery").serialize(),
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state == 2000) {
                        alert("重置密码成功！");
                        location.href="/web/account-login.html";
                    } else {
                        alert(json.message);
                    }
                }
            });
        }
    });
})