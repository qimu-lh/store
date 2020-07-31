var pid = getUrlParam("pid");
console.log("pid=" + pid);

$(function () {
    showGoodsDetails();

    //购物数量加1
    $("#numUp").click(function () {
        var n = parseInt($("#num").val());
        $("#num").val(n + 1);
    })
    //购物数量-1
    $("#numDown").click(function () {
        var n = parseInt($("#num").val());
        if (n == 1) {
            return;
        }
        $("#num").val(n - 1);
    })

    $("#btn-add-to-cart").click(function () {
        addToCart();
    });
})

function addToCart() {
    //$("#num option:selected").val()
    $.ajax({
        "url": "/carts/add_to_cart",
        "data": "pid=" + pid + "&num=" + $("#num").val(),
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                setTimeout(function () {
                    location.href="/web/cart.html";
                },2000)
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您尚未登录，或登录已过期！请重新登录！");
        }
    });
}

function showGoodsDetails() {
    $.ajax({
        "url": "/products/" + pid + "/details",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var products = json.data;
            $("#products-name").html(products.name);
            $("#products-information").html(products.information);
            $("#products-price").html("￥"+products.price);
            for (var i = 1; i <= 5; i++) {
                $("#products-image-" + i + "-big").attr("src", ".." + products.image);
                $("#products-image-" + i + "-md").attr("src", ".." + products.image);
                $("#products-image-" + i + "-sm").attr("src", ".." + products.image);
            }
        }
    });
}
function getUrlParam(name) {//封装方法
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}
