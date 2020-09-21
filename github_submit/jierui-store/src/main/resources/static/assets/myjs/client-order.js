$(function () {
    showOrderList();
})
function showOrderList() {
    $.ajax({
        "url": "/orders/user",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            console.log("count=" + list.length);
            $("#order-list").empty();

            for (var i = 0; i < list.length; i++) {
                console.log(list[i].name);
                var html = '<tr>'
                    + '<td><a onclick="findByOid(#{oid})" class="text-medium navi-link" href="#" data-toggle="modal" data-target="#orderDetails">#{oid}</a></td>'
                    + '<td>#{orderTime}</td>'
                    + '<td><span class="text-info">#{state}</span></td>'
                    + '<td><span class="text-medium">#{totalPrice}</span></td>'
                    + '</tr>';

                html = html.replace(/#{oid}/g, list[i].oid);
                html = html.replace(/#{orderTime}/g,getMyDate(list[i].orderTime));
                html = html.replace(/#{state}/g, list[i].state);
                html = html.replace(/#{totalPrice}/g, list[i].totalPrice);

                $("#order-list").append(html);
            }
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