$(function () {
    var socket;
    //判断当前浏览器是否支持websocket
    if (window.WebSocket) {
        socket = new WebSocket("ws://localhost:8088/home");
        //相当于channelReado, ev 收到服务器端回送的消息
        socket.onmessage = function (ev) {
            $("#temperature").text(ev.data);
            $("#humidity").text(parseInt(ev.data)+53);
        }
    }
    else{
            alert("当前浏览器不支持websocket")
        }

        $("#number").click(function () {
            send("建立连接");
        });

        $("#upData").click(function () {
        //$(".number").click(function () {
            /*为每个数据增添首部*/
            var uid = "X" + $("#client-uid").text();
            var username = "Y" + $("#client-username").text();
            var temperature = "A" + 11;
            var humidity = "B" + 11;
            var airMode = "C" + $("#airMode option:selected").val();
            var airTemperature = "D" + $("#airTemperature").val();
            var airWindSpeed = "E" + "大";
            var lamp1 = "F" + $("#lamp1 option:selected").val();
            var lamp2 = "G" + $("#lamp2 option:selected").val();
            var lamp3 = "H" + $("#lamp3").val();
            var lamp4 = "I" + $("#lamp4").val();
            var windowMode = "J" + $("#windowMode option:selected").val();
            var end = "K" + "end";
            /*首部+数据*/
            var message = uid + username + temperature + humidity + airMode + airTemperature
                + airWindSpeed + lamp1 + lamp2 + lamp3 + lamp4 + windowMode+end;
            console.log(message);
            if ("调温"==$("#airMode option:selected").val()) {
                $('#temperature').replaceWith("<label id='temperature-new' style=\"font-size: 30px\">" + $("#airTemperature").val() + "</label>");
                $('#temperature-new').replaceWith("<label id='temperature-new' style=\"font-size: 30px\">" + $("#airTemperature").val() + "</label>");
            }
            if ("调湿" == $("#airMode option:selected").val()) {
                $('#humidity').replaceWith("<label id='humidity-new' style=\"font-size: 30px\">" + 66 + "</label>");
                $('#humidity-new').replaceWith("<label id='humidity-new' style=\"font-size: 30px\">" + 66 + "</label>");
            }
            send(message)
        });

        //发送消息到服务器
        function send(message) {
            if (socket.readyState == WebSocket.OPEN) {
                //通过socket 发送消息
                socket.send(message)
            } else {
                alert("连接没有开启");
            }
        }

        //展示用户信息
        showUserInfo();
    });
function showUserInfo() {
    $.ajax({
        "url": "/users/get_by_uid",
        "type": "get",
        "dataType": "json",
        "success": function (json) {
            if (json.state == 2000) {
                $("#temperature").text(json.data.airTemperature);
                //$("#humidity").text(66);
                $("#client-uid").text(json.data.uid);
                $("#client-username").text(json.data.username);
                $("#airMode").val(json.data.airMode);
                $("#airTemperature").val(json.data.airTemperature);
                $("#airWindSpeed").val(json.data.airWindSpeed);
                $("#lamp1").val(json.data.lamp1);
                $("#lamp2").val(json.data.lamp2);
                $("#lamp3").val(json.data.lamp3);
                $("#lamp4").val(json.data.lamp4);
                $("#windowMode").val(json.data.windowMode);
            } else {
                alert(json.message);
            }
        }
    });
}
