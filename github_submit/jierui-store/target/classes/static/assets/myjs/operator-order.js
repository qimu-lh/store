$(function () {
    showOperatorOrderList();
})
function showOperatorOrderList() {
    $.ajax({
        "url": "/orders/",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            $("#operator-order-list").empty();
            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                if (list[i].state==="In Progress"||list[i].state==="Delayed"){
                    var html = '<tr>'
                        + '<td><a onclick="findByOid(#{oid})" class="text-medium navi-link" href="#" data-toggle="modal" data-target="#orderDetails">#{oid}</a></td>'
                        + '<td>#{orderTime}</td>'
                        + '<td><span class="text-info">#{state}</span></td>'
                        + '<td><span class="text-medium">#{totalPrice}</span></td>'
                        + '<td>'
                        + '<a onclick="acceptOrder(#{oid})" class="btn btn-success" href="#">接受</a>'
                        + '<a onclick="refuseOrder(#{oid})" class="btn btn-danger" href="#">拒绝</a>'
                        + '</td>'
                        + '<td><a onclick="notifyCustomer(#{oid})" class="btn btn-info" href="#">通知客户</a></td>'
                        + '</tr>';

                    html = html.replace(/#{oid}/g, list[i].oid);
                    html = html.replace(/#{orderTime}/g,getMyDate(list[i].orderTime));
                    html = html.replace(/#{state}/g, list[i].state);
                    html = html.replace(/#{totalPrice}/g, list[i].totalPrice);

                    $("#operator-order-list").append(html);
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
                        + '<td><a onclick="notifyCustomer(#{oid})" class="btn btn-info" href="#">通知客户</a></td>'
                        + '</tr>';

                    html = html.replace(/#{oid}/g, list[i].oid);
                    html = html.replace(/#{orderTime}/g,getMyDate(list[i].orderTime));
                    html = html.replace(/#{state}/g, list[i].state);
                    html = html.replace(/#{totalPrice}/g, list[i].totalPrice);

                    $("#operator-order-list").append(html);
                }

            }
        }
    });
}
//接受订单
function acceptOrder(oid) {
    var state="In Progress";
    $.ajax({
        "url": "/orders/" + oid +"/"+state+ "/operator_set_state",
        "type": "POST",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                showOperatorOrderList();
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
                showOperatorOrderList();
            } else {
                alert(json.message);
            }
        },
        "error": function () {
            alert("您的登录信息已过期，请重新登录！");
        }
    });
}
function notifyCustomer(oid) {
    $.ajax({
        "url": "/orders/" + oid +"/send_order_information",
        "type": "GET",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                alert("通知邮件发送成功！")
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