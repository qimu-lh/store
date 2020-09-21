$(function () {
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