$(function () {
    showHotList();
})
function showHotList() {
    $.ajax({
        "url": "/products/hot",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);

            $("#hot-list").empty();

            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                    //+ '<div class="col-md-7 text-row-2"><a href="product.html?id=#{id}">#{title}</a></div>'
                var html='<div class="col-md-3">'
                    +'<div class="product-card">'
                    +'<a class="product-thumb" href="">'
                    +'<img src="..#{image}" alt="Product">'
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
                    +'</div>'
                    +'</div>';

                html = html.replace(/#{pid}/g, list[i].pid);
                html = html.replace(/#{image}/g, list[i].image);
                html = html.replace(/#{name}/g, list[i].name);
                html = html.replace(/#{price}/g, list[i].price);

                $("#hot-list").append(html);
            }
        }
    });
}
$('#inp-search').bind('keypress',function(event){
    if(event.keyCode == "13") {
        location.href="/web/search-results.html?str="+$("#inp-search").val();
    }
});
