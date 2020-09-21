$(function () {
    $("#operator-btn-login").click(function () {
        $.ajax({
            "url": "/operators/login",
            "data": $("#operator-form-login").serialize(),
            "type": "post",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    alert("登录成功");
                    // 跳转到某个页面
                    location.href="/web/operator-orders.html";
                } else {
                    alert(json.message);
                }
            }
        });
    });
})