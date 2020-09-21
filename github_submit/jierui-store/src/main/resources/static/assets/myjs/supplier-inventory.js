$(function () {
    showSupplierProductsList();
})
function showSupplierProductsList() {
    $.ajax({
        "url": "/products/",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);

            $("#supplier-products-list").empty();

            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var html='<tr>'
                    +'<td><span class="text-medium">#{name}</span></td>'
                    +'<td><span class="text-medium">#{num}</span></td>'
                    +'<td><a onclick="deleteProduct(#{pid})" class="btn btn-outline-danger" href="#">删除商品</a></td>'
                    +'</tr>';

                html = html.replace(/#{name}/g, list[i].name);
                html = html.replace(/#{num}/g, list[i].num);
                html = html.replace(/#{pid}/g, list[i].pid);

                $("#supplier-products-list").append(html);
            }
        }
    });
}
function deleteProduct(pid) {
    if (confirm("您确定要删除此商品吗？")){
        $.ajax({
            "url": "/products/" + pid + "/delete_by_pid",
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 2000) {
                    showSupplierProductsList();
                } else {
                    alert(json.message);
                }
            },
            "error": function () {
                alert("您的登录信息已过期，请重新登录！");
            }
        });
    }
}