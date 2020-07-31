$(function () {
    $("#supplier-btn-login").click(function () {
        $.ajax({
            "url": "/suppliers/login",
            "data": $("#supplier-form-login").serialize(),
            "type": "post",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    alert("登录成功");
                    // 跳转到某个页面
                    location.href="/web/supplier-orders.html";
                } else {
                    alert(json.message);
                }
            }
        });
    });
})