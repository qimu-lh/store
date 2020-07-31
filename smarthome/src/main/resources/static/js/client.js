$(function () {
    var socket;
    //判断当前浏览器是否支持websocket
    if (window.WebSocket) {
        //go on
        socket = new WebSocket("ws://localhost:8088/client");
        //相当于channelReado, ev 收到服务器端回送的消息
        socket.onmessage = function (ev) {
            if ("制热" == $("#airMode option:selected").val() || "制冷" == $("#airMode option:selected").val()) {
                var rt = $("#temperature");
                rt.text(ev.data);
                cp.value = ev.data;
                cp.el.style.setProperty('--progress01-value', ev.data / 40);
            }
            if ("调湿" == $("#airMode option:selected").val()) {
                var rt = $("#humidity");
                rt.text(ev.data);
                (function initAndSetupTheSliders() {
                    [].forEach.call(document.getElementsByClassName("container"), function (el) {
                        var inputs = [].slice.call(el.querySelectorAll('.range-slider input'));
                        inputs.forEach(function (input) {
                            input.setAttribute('value', ev.data);
                            updateSlider(input);
                            input.addEventListener('input', function (element) {
                                updateSlider(input);
                            });
                            input.addEventListener('change', function (element) {
                                updateSlider(input);
                            });
                        });
                    });
                })();
            }

        }
    } else {
        alert("当前浏览器不支持websocket")
    }

    /*手动模式提交*/
    $("#submit").click(function () {
        /*为每个数据增添首部*/
        var uid = "X" + $("#client-uid").text();
        var username = "Y" + $("#client-username").text();
        var temperature = "A" + $("#temperature").text();
        var humidity = "B" + $("#humidity").text();
        var airMode = "C" + $("#airMode option:selected").val();
        var airTemperature = "D" + $("#airTemperature").val();
        var airWindSpeed = "E" + $("#airWindSpeed option:selected").val();
        var lamp1 = "F" + $("#lamp1 option:selected").val();
        var lamp2 = "G" + $("#lamp2 option:selected").val();
        var lamp3 = "H" + $("#lamp3").val();
        var lamp4 = "I" + $("#lamp4").val();
        var windowMode = "J" + $("#windowMode option:selected").val();
        var adjustPattern ="K"+"manual";
        /*首部+数据*/
        var message = uid + username + temperature + humidity + airMode + airTemperature
            + airWindSpeed + lamp1 + lamp2 + lamp3 + lamp4 + windowMode+adjustPattern;
        console.log(message);
        alert("确定要提交数据吗？");
        send(message);
    });
    //智能模式提交
/*    $("#submit-smart").click(function () {
        /!*为每个数据增添首部*!/
        var uid = "X" + $("#client-uid").text();
        var username = "Y" + $("#client-username").text();
        var temperature = "A" + $("#temperature").text();
        var humidity = "B" + $("#humidity").text();
        var airMode = "C" + $("#airMode option:selected").val();
        var airTemperature = "D" + $("#airTemperature").val();
        var airWindSpeed = "E" + $("#airWindSpeed option:selected").val();
        var lamp1 = "F" + $("#lamp1 option:selected").val();
        var lamp2 = "G" + $("#lamp2 option:selected").val();
        var lamp3 = "H" + $("#lamp3").val();
        var lamp4 = "I" + $("#lamp4").val();
        var windowMode = "J" + $("#windowMode option:selected").val();
        var adjustPattern ="K"+"smart";
        /!*首部+数据*!/
        var message = uid + username + temperature + humidity + airMode + airTemperature
            + airWindSpeed + lamp1 + lamp2 + lamp3 + lamp4 + windowMode+adjustPattern;
        console.log(message);
        alert("确定要启动智能模式吗？");
        send(message);
    });*/

    //发送消息到服务器
    function send(message) {
        /*       if(!window.socket) { //先判断socket是否创建好
                   alert("警告：连接没有开启")
                   return;
               }*/
        if (socket.readyState == WebSocket.OPEN) {
            //通过socket 发送消息
            socket.send(message)
        } else {
            alert("连接没有开启");
        }
    }

})
