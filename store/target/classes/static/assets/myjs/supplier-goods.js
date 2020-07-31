$(function () {
    $("#btn-product-add").click(function () {
        $.ajax({
            "url": "/products/add",
            "data": $("#form-product-add").serialize(),
            "type": "post",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    alert("商品添加成功");
                    location.href="/web/supplier-inventory.html";
                } else {
                    alert(json.message);
                }
            }
        });
    })
})