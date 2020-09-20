$(function () {
    showSupplierOrderList();
})
function showSupplierOrderList() {
    $.ajax({
        "url": "/orders/",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            $("#supplier-order-list").empty();

            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                if (list[i].state === "In Progress") {
                    var html = '<tr>'
                        + '<td><a onclick="findByOid(#{oid})" class="text-medium navi-link" href="#" data-toggle="modal" data-target="#orderDetails">#{oid}</a></td>'
                        + '<td>#{orderTime}</td>'
                        + '<td><span class="text-info">#{state}</span></td>'
                        + '<td><span class="text-medium">#{totalPrice}</span></td>'
                        + '<td>'
                        + '<a onclick="acceptOrder(#{oid})" class="btn btn-success" href="#">接受</a>'
                        + '<a onclick="refuseOrder(#{oid})" class="btn btn-danger" href="#">拒绝</a>'
                        + '</td>'
                        + '</tr>';

                    html = html.replace(/#{oid}/g, list[i].oid);
                    html = html.replace(/#{orderTime}/g, getMyDate(list[i].orderTime));
                    html = html.replace(/#{state}/g, list[i].state);
                    html = html.replace(/#{totalPrice}/g, list[i].totalPrice);

                    $("#supplier-order-list").append(html);
                }else {
                    var html = '<tr>'
                        + '<td><a onclick="findByOid(#{oid})" class="text-medium navi-link" href="#" data-toggle="modal" data-target="#orderDetails">#{oid}</a></td>'
                        + '<td>#{orderTime}</td>'
                        + '<td><span class="text-info">#{state}</span></td>'
                        + '<td><span class="text-medium">#{totalPrice}</span></td>'
                        + '<td>'
                        + '<a class="btn btn-success" href="#" disabled>接受</a>'
                        + '<a class="btn btn-danger" href="#" disabled>拒绝</a>'
                        + '</td>'
                        + '</tr>';

                    html = html.replace(/#{oid}/g, list[i].oid);
                    html = html.replace(/#{orderTime}/g, getMyDate(list[i].orderTime));
                    html = html.replace(/#{state}/g, list[i].state);
                    html = html.replace(/#{totalPrice}/g, list[i].totalPrice);

                    $("#supplier-order-list").append(html);
                }
            }
        }
    });
}
//接受订单
function acceptOrder(oid) {
    var state="Delivered";
    $.ajax({
        "url": "/orders/" + oid +"/"+state+ "/supplier_set_state",
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                showSupplierOrderList();
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}
//拒绝订单
function refuseOrder(oid) {
    var state="Canceled";
    $.ajax({
        "url": "/orders/" + oid +"/"+state+ "/operator_set_state",
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                showSupplierOrderList();
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}

//将时间戳转换为可视时间
function getMyDate(str){
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+
            getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
};
//补0操作
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}