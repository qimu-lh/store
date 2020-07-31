$(function () {
    showCartList();
    $("#btn-delete-allCarts").click(function () {
        if (confirm("您确定要删除所有购物车吗？")){
            $.ajax({
                "url": "/carts/deleteAllCarts",
                "type": "POST",
                "dataType": "json",
                "success": function (json) {
                    if (json.state == 2000) {
                        showCartList();
                    } else {
                        alert(json.message);
                        showCartList();
                    }
                },
                "error": function () {
                    alert("您的登录信息已过期，请重新登录！");
                }
            });
        }
    })
})
function showCartList() {
    $.ajax({
        "url": "/carts/",
        "type": "GET",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            $("#cart-list").empty();
            for (var i = 0; i < list.length; i++) {
                console.log(list[i].title);
                var html='<tr>'
                    +'<td>'
                    +'<div class="product-item">'
                    +'<a class="product-thumb" href="">'
                    +'<img src="..#{image}" alt="Product">'
                    +'</a>'
                    +'<div class="product-info">'
                    +'<h4 class="product-title"><a href="">#{name}</a></h4>'
                    +'<span><em>单价（￥）:</em><span id="price-#{cid}">#{price}</span></span>'
                    +'</div>'
                    +'</div>'
                    +'</td>'
                    +'<td class="text-center">'
                    +'<button onclick="subNum(#{cid})" class="btn btn-sm btn-secondary" type="button"><label style="font-size: 200%">-</label></button>'
                    +'<input id="inp-num-#{cid}" type="text" style="width: 15%;display: inline" readonly="readonly" class="num-text form-control" value="#{num}">'
                    +'<button onclick="addNum(#{cid})" class="btn btn-sm btn-secondary" type="button"><label style="font-size: 150%">+</label></button>'
                    +'</td>'
                    +'<td class="text-center text-lg text-medium">￥<span id="total-price-#{cid}">#{totalPrice}</span></td>'
                    +'<td class="text-center">'
                    +'<a onclick="deleteCart(#{cid})" class="remove-from-cart" href="#" data-toggle="tooltip" title="删除此商品"><i class="icon-cross"></i></a>'
                    +'</td>'
                    +'</tr>';

                html = html.replace(/#{cid}/g, list[i].cid);
                html = html.replace(/#{name}/g, list[i].name);
                html = html.replace(/#{image}/g, list[i].image);
                html = html.replace(/#{price}/g, list[i].price);
                html = html.replace(/#{num}/g, list[i].num);
                html = html.replace(/#{totalPrice}/g, list[i].price * list[i].num);

                $("#cart-list").append(html);
            }
        }
    });
}

function addNum(cid) {
    $.ajax({
        "url": "/carts/" + cid + "/add_num",
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                var price = parseInt($("#price-" + cid).html());
                $("#inp-num-" + cid).val(json.data);
                $("#total-price-" + cid).html(json.data * price);
            } else {
                alert(json.message);
                showCartList();
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}
function subNum(cid) {
    $.ajax({
        "url": "/carts/" + cid + "/sub_num",
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                var price = parseInt($("#price-" + cid).html());
                $("#inp-num-" + cid).val(json.data);
                $("#total-price-" + cid).html(json.data * price);
            } else {
                alert(json.message);
                showCartList();
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}
function deleteCart(cid) {
    if (confirm("您确定要删除此购物车吗？")){
        $.ajax({
            "url": "/carts/" + cid + "/delete_cart",
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    showCartList();
                } else {
                    alert(json.message);
                    showCartList();
                }
            },
            "error": function () {
                alert("您的登录信息已过期，请重新登录！");
            }
        });
    }
}