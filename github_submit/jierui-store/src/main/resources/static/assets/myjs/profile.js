$(function () {
    /**
     * 显示用户信息；更改用户信息
     */
    showUserInfo();

    $("#btn-change-info").click(function () {
        $.ajax({
            "url": "/users/change_info",
            "data": $("#form-change-info").serialize(),
            "type": "post",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    // 两秒后刷新界面
                    setTimeout(function(){
                        location.href = "/web/account-profile.html";
                    },2000);
                } else {
                    alert(json.message);
                }
            },
            "error": function () {
                alert("您的登录信息已经过期！请重新登录！");
                location.href = "/web/account-login.html";
            }
        });
    });
    /**
     * 更改密码
     */
    $("#btn-change-password").click(function () {
        if ($("#pro-newPassword").val()!==$("#pro-confirmPassword").val()){
            alert("前后密码不一致！")
        }else {
            $.ajax({
                "url": "/users/change_password",
                "data": $("#form-change-password").serialize(),
                "type": "post",
                "dataType": "json",
                "success": function (json) {
                    if (json.state == 2000) {
                        alert("修改密码成功！")
                        location.href = "/web/account-profile.html";
                    } else {
                        alert(json.message);
                    }
                },
                "error": function () {
                    alert("您的登录信息已经过期！请重新登录！");
                    location.href = "/web/account-login.html";
                }
            });
        }

    });
})
function showUserInfo() {
    $.ajax({
        "url": "/users/get_by_uid",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                $("#inp-username").val(json.data.username);
                $("#inp-email").val(json.data.email);
                $("#inp-phone").val(json.data.phone);

                var radio = json.data.gender == 0 ? $("#gender-female") : $("#gender-male");
                radio.attr("checked", "checked");
            } else {
                alert(json.message);
            }
        }
    });
}