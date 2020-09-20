var str = getUrlParam("str");
console.log("str=" + str);

$(function () {
    showSearchResults();
})
function showSearchResults() {
    $.ajax({
        "url": "/products/"+str+"/search",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            $("#search-results").empty();
            if (list.length>0){
                for (var i = 0; i < list.length; i++) {
                    console.log(list[i].name);
                    var html='<div class="card mb-4">'
                        +'<div class="card-header"><span class="badge badge-pill badge-primary">Product</span></div>'
                        +'<a class="product-thumb" href="">'
                        +'<div class="card-body">'
                        +'<div class="d-flex"><a class="pr-4 hidden-xs-down search-products" href="shop-single-2.html">'
                        +'<img src="..#{image}" alt="Product"></a>'
                        +'<div>'
                        +'<h5><a class="navi-link" href="shop-single.html?pid=#{pid}">#{name}</a></h5>'
                        +'<h6>￥#{price}</h6>'
                        +'<p>#{information}</p>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>';

                    html = html.replace(/#{image}/g, list[i].image);
                    html = html.replace(/#{pid}/g, list[i].pid);
                    html = html.replace(/#{name}/g, list[i].name);
                    html = html.replace(/#{price}/g, list[i].price);
                    html = html.replace(/#{information}/g, list[i].information);

                    $("#search-results").append(html);
                }
            }else {
                var html='<div class="row align-items-center padding-bottom-3x">'
                    +'<div class="col-md-5">'
                    +'<img class="d-block w-270 m-auto" src="../assets/images/features/01.jpg" alt="Online Shopping">'
                    +'</div>'
                    +'<div class="col-md-7 text-md-left text-center">'
                    +'<div class="hidden-md-up"></div>'
                    +'<h2> (。・＿・。)ﾉ  I’m sorry~ ！</h2>'
                    +'<h2> Products NOT Find！</h2>'
                    +'<h4>1、请输入准确的搜索信息 (๑*◡*๑) </h4>'
                    +'<h4>2、若还搜索不到(T ^ T) 我们会努力增添商品种类！✧⁺⸜(●˙▾˙●)⸝⁺✧ </h4>'
                    +'</div>'
                    +'</div>';
                $("#search-results").append(html);
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