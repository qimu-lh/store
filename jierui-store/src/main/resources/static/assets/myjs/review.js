var aid = getUrlParam("aid");
console.log("aid=" + aid);
$(function () {
    showCartList_review();

    $("#complete-order").click(function () {
        if (confirm("您确定要提交订单并支付吗？")){
            $.ajax({
                "url": "/orders/" + aid + "/create",
                "type": "POST",
                "dataType": "json",
                "success": function (json) {
                    if (json.state == 2000) {
                        location.href="/web/checkout-complete.html";
                    } else {
                        alert(json.message);
                    }
                },
                "error": function () {
                    alert("请先完成支付！或出现未知错误联系管理员！");
                }
            });
        }
    })
})

function showCartList_review() {
    $.ajax({
        "url": "/carts/",
        "type": "GET",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            var totalPrice=0;
            console.log("count=" + list.length);
            $("#cart-list-review").empty();
            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var html='<tr>'
                    +'<td>'
                    +'<div class="product-item">'
                    +'<a class="product-thumb" href="">'
                    +'<img src="..#{image}" alt="Product">'
                    +'</a>'
                    +'<div class="product-info">'
                    +'<h4 class="product-title"><a href="">#{name}</a></h4>'
                    +'<span><em>单价:</em> ￥<span id="price-#{cid}">#{price}</span></span>'
                    +'</div>'
                    +'</div>'
                    +'</td>'
                    +'<td class="text-center text-lg text-medium">￥<span id="total-price-#{cid}">#{totalPrice}</span></td>'
                    +'<td class="text-center">'
                    +'<a class="btn btn-outline-primary btn-sm" href="cart.html">编辑</a>'
                    +'</td>'
                    +'</tr>';

                html = html.replace(/#{cid}/g, list[i].cid);
                html = html.replace(/#{name}/g, list[i].name);
                html = html.replace(/#{image}/g, list[i].image);
                html = html.replace(/#{price}/g, list[i].price);
                html = html.replace(/#{totalPrice}/g, list[i].price * list[i].num);

                $("#cart-list-review").append(html);
                var totalPrice01=(list[i].price * list[i].num);
                totalPrice=totalPrice+totalPrice01;
            }
            console.log(totalPrice);
            $("#totalPrice-review").text("￥"+totalPrice);
        }
    });
}
function getUrlParam(name) {//封装方法
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

