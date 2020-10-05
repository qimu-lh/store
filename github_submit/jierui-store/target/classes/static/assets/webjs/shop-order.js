$(function () {
    showOrders();
    $("#btn-add-logistics").click(function () {
        var oid=$("#logisticsTestOid").text();
        var logistics=$("#logisticsNum").val();
        $.ajax({
            "url": "/orders/"+logistics+"/addLogistics/"+oid,
            "type": "POST",
            "dataType": "json",
            "success": function (json) {
                if (json.state === 2000) {
                    alert("添加物流号成功，审核通过！")
                    var tid="#tLogistics"+oid;
                    var bid="#bState"+oid;
                    $(tid).text(logistics);
                    $(bid).text("审核通过");
                    $(bid).attr({"disabled":"disabled"})
                } else {
                    alert(json.message);
                }
            },
            "error": function () {
                alert("出现未知错误！");
            }
        });
    })
})
function showOrders() {
    $.ajax({
        "url": "/orders/",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            var list = json.data;
            $("tbody").empty();
            if (list.length>0) {
                for (var i = 0; i < list.length; i++) {
                    var html='<tr>' +
                        '<td>#{oid}</td>' +
                        '<td>#{recvName}</td>' +
                        '<td>#{orderTime}</td>' +
                        '<td id="tLogistics#{oid}">#{logistics}</td>' +
                        '<td>' +
                        '<button id="bState#{oid}" onclick="addLogistics(this)" #{disabled} type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#exampleModal01">#{state}</button>' +
                        '</td>' +
                        '</tr>';
                    html = html.replace(/#{oid}/g, list[i].oid);
                    html = html.replace(/#{recvName}/g, list[i].recvName);
                    html = html.replace(/#{orderTime}/g, renderTime(list[i].orderTime));
                    html = html.replace(/#{logistics}/g, list[i].logistics);
                    html = html.replace(/#{state}/g, (list[i].state===0)?"审核":"审核通过");
                    html = html.replace(/#{disabled}/g, (list[i].state===0)?'':'disabled="disabled"');

                    $("tbody").append(html);
                }
            }
        }
    });
}
function addLogistics(obj) {
    var oid = $(obj).parent().prev().prev().prev().prev().text();
    $("#logisticsTestOid").text(oid);
}
function renderTime(date) {
    var dateee = new Date(date).toJSON();
    return new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
}