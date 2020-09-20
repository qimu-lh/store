//删除单个购物车
function deleteCart(obj) {
    var cid=$(obj).parent().prev().prev().attr("id");
    if (confirm("您确定要删除此购物车吗？")){
        $.ajax({
            "url": "/carts/" + cid + "/delete_cart",
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state === 2000) {
                    location.href="http://localhost:8080/carts/";
                } else {
                    alert(json.message);
                    location.href="http://localhost:8080/carts/";
                }
            },
            "error": function () {
                alert("您的登录信息已过期，请重新登录！");
            }
        });
    }
}
//删除所有购物车
$("#btn-delete-allCarts").click(function () {
    if (confirm("您确定要删除所有购物车吗？")){
        $.ajax({
            "url": "/carts/deleteAllCarts",
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state === 2000) {
                    location.href="http://localhost:8080/carts/";
                } else {
                    alert(json.message);
                    location.href="http://localhost:8080/carts/";
                }
            },
            "error": function () {
                alert("您的登录信息已过期，请重新登录！");
            }
        });
    }
})
/*单选计算总价*/
$(function() {
    $(".selectSingle").click(function() {
        calcTotal();
    });
    //开始时计算价格
    calcTotal();
});
/*店铺选择*/
$(".selectShop").on("click",function () {
    $(this).parent().parent().parent().find('.selectSingle').prop("checked", $(this).prop("checked"));
    //console.log(this.parentNode.parentNode.parentNode);
    calcTotal();
});
/*全选全不选*/
function checkAll(ckbtn) {
    $(".selectSingle").prop("checked", $(ckbtn).prop("checked"));
    $(".selectShop").prop("checked", $(ckbtn).prop("checked"));
    //为商品总价赋值
    calcTotal();
}
//计算总价格的方法
function calcTotal() {
	//选中商品的总价
	var vselectTotal = 0;

	//循环遍历所有tr
	for (var i = 0; i < $(".cart-list tr").length; i++) {
		//计算每个商品的价格小计开始
		//取出1行
		var $tr = $($(".cart-list tr")[i]);
		//小计金额
		var vtotal=parseFloat($tr.find(".singleTotalPrice").text());
		//检查是否选中
		if ($tr.find(".selectSingle").prop("checked")) {
			//计总价
			vselectTotal += vtotal;
		}
		//将选中的价格赋值
		$("#totalPrice-cart").text(vselectTotal);
	}
}
/*
* 商品数量 加
* */
$(".add").on("click",function () {
    var cid=$(this).parent().attr("id");
    addNum(cid);
});
/*
* 商品数量 减
* */
$(".reduce").on("click",function () {
    var cid=$(this).parent().attr("id");
    subNum(cid);
});

function addNum(cid) {
    $.ajax({
        "url": "/carts/" + cid + "/add_num",
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state === 2000) {
                var price = parseInt($("#price-" + cid).html());
                $("#inp-num-" + cid).val(json.data);
                $("#total-price-" + cid).html(json.data * price);
            } else {
                alert(json.message);
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
            if (json.state === 2000) {
                var price = parseInt($("#price-" + cid).html());
                $("#inp-num-" + cid).val(json.data);
                $("#total-price-" + cid).html(json.data * price);
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}