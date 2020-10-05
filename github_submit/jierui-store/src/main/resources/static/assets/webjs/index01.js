$(function () {
    //展示登录后的信息
    getUserInfo();
    //注销登录
    $("#user-cancel").click(function () {
        $.ajax({
            "url": "/users/logout",
            "type": "post",
            "dataType": "json",
            "success": function (json) {
                if (json.state === 2000) {
                    location.href="http://localhost:8080/static/web/account-login.html";
                }else {
                    alert(json.message)
                }
            }
        })
    })

    //滚轮到底部，翻页
    var f=true;
    var currentPage=4;
    $(window).scroll(function() {
            var scrollTop = $(document).scrollTop();
            var scrollHeight = $(document).height();
            var windowHeight = $(window).height();
            if ((scrollHeight-scrollTop - windowHeight)<30) {
                if (f){
                    f=false;
                    var pageNum=4;
                    $.ajax({
                        "url": "/products/"+currentPage+"/pageHot/"+pageNum,
                        "type": "get",
                        "dataType": "json",
                        "success": function (json) {
                            var list = json.data;
                            console.log("count=" + list.length);
                            f=true;//重新检测底部
                            currentPage+=4;
                            for (var i = 0; i < list.length; i++) {
                                console.log(list[i].name);
                                //+ '<div class="col-md-7 text-row-2"><a href="product.html?id=#{id}">#{title}</a></div>'
                                var html='<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 margin-bottom-1x">'
                                    +'<div class="product-card">'
                                    +'<a class="product-thumb" href="">'
                                    +'<img src="../static/#{image}" alt="Product">'
                                    +'</a>'
                                    +'<h3 class="product-title"><a href="">#{name}</a></h3>'
                                    +'<h4 class="product-price">￥#{price}</h4>'
                                    +'<div class="product-buttons">'
                                    +'<div class="product-buttons">'
                                    +'<button class="btn btn-outline-secondary btn-sm btn-wishlist" data-toggle="tooltip" title="喜欢">'
                                    +'<i class="icon-heart"></i>'
                                    +'</button>'
                                    +'<a href="shop-single.html?pid=#{pid}" class="btn btn-outline-primary btn-sm">查看详情</a>'
                                    +'</div>'
                                    +'</div>'
                                    +'<h5 style="font-size: 2px;text-align: center;color: lightslategray">#{shop}</h5>'
                                    +'</div>'
                                    +'</div>';

                                html = html.replace(/#{pid}/g, list[i].pid);
                                html = html.replace(/#{image}/g, list[i].image);
                                html = html.replace(/#{name}/g, list[i].name);
                                html = html.replace(/#{price}/g, list[i].price);
                                html = html.replace(/#{shop}/g, list[i].shop);


                                $("#hot-list").append(html);
                            }
                        }
                    });
                }
            }
        });
})
function getUserInfo() {
    $.ajax({
        "url": "/users/info",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state === 2000) {
                $("#user-name").text(json.data.username);
                $("#user-login").attr("style","display:none");
                $("#user-reg").attr("style","display:none");
            }else {
                $("#user-cancel").attr("style","display:none");
            }
        },
        "error":function () {
            $("#user-cancel").attr("style","display:none");
        }
    })
}
//搜索商品
$('#inp-search').bind('keypress',function(event){
    if(event.keyCode == "13") {
        location.href="/static/web/search-results.html?str="+$("#inp-search").val();
    }
});