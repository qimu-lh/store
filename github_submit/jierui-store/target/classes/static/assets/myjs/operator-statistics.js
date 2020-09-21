$(function () {
    showOperatorProductsList();
    showOperatorProductsSales();
})
function showOperatorProductsList() {
    $.ajax({
        "url": "/products/",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);

            $("#operator-products-list").empty();

            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var html='<tr>'
                    +'<td><span class="text-medium">#{name}</span></td>'
                    +'<td><span class="text-medium">#{num}</span></td>'
                    +'</tr>';

                html = html.replace(/#{name}/g, list[i].name);
                html = html.replace(/#{num}/g, list[i].num);

                $("#operator-products-list").append(html);
            }
        }
    });
}
function showOperatorProductsSales() {
    $.ajax({
        "url": "/orders/operator_get_sales",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            $("#operator-products-sales").text(json.data);
        }
    });
}