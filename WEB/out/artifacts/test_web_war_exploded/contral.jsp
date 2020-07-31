<%@ page import="cn.d.util.JDBCUtils" %>
<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %><%--
  Created by IntelliJ IDEA.
  User: 邓文校
  Date: 2020/4/25
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html"%>
<%@page pageEncoding="GB2312"%>
<%@page import="java.sql.*" %>  <%--导入java.sql包--%>
<html>
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户界面</title>
    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style>
        .box{
            margin-top: -150px;
            float: right;
        }
        .p1{
            margin-left: 60px;
            margin-top: 20px;
            /*width: fit-content;
            height: fit-content;*/
        }
        .p3{
            height: 40px;
            font-size: 20px;
            color: #985f0d;
        }
        .p4{
            height: 40px;
            color: #2e6da4;
            font-size: 20px;
        }
        #text1{
            height: 40px;
            font-size: 20px;
            color: #2e6da4;
        }
        #text3{
            height: 70px;
        }
        #wen{
            height: 35px;
            font-size: 20px;
            color: #761c19;
        }
        #shi{
            height: 35px;
            font-size: 20px;
            color: #761c19;
        }
    </style>
    <script>
        function sql12() {
            var s;
            s=$("#kill12").find("option:selected").text();
            alert("模式："+s);
        }
        function sql13() {
            var s;
            s=$("#kill13").find("option:selected").text();
            alert("风速："+s);
        }
        function sql() {
            var s;
            s=$("#kill").find("option:selected").text();
            alert("门窗磁："+s);
        }
        function sql1() {
            var s;
            s=$("#kill1").find("option:selected").text();
            alert("灯光1："+s);
        }
        function sql2() {
            var s;
            s=$("#kill2").find("option:selected").text();
            alert("灯光2："+s);
        }
        function enter(){
            var b="${user.name}";
            var a=document.getElementById('enter1').value;
            var c=document.getElementById('a1').value;
            var d=document.getElementById('a2').value;
            var s1=$("#kill").find("option:selected").text();
            var s2=$("#kill1").find("option:selected").text();
            var s3=$("#kill2").find("option:selected").text();
            var s4=document.getElementById('enter2').value;
            var s5=document.getElementById('enter3').value;
            var s6=$("#kill12").find("option:selected").text();
            var s7=$("#kill13").find("option:selected").text();
// Enter.update();
            window.location.href = '${pageContext.request.contextPath}/send/a1?userName='+b;
            window.location.href = '${pageContext.request.contextPath}/updateInformationServlet?userName='+b+'&temp='+a+'&wendu='+c+'&shidu='+d+'&vent='+s1+'&deng1='+s2+'&deng2='+s3+'&deng3='+s4+'&deng4='+s5+'&moshi='+s6+'&fengsu='+s7;
        }
    </script>

<%--    <script type="text/javascript">--%>
<%--        function a1() {--%>
<%--            $.ajax({--%>

<%--                url:"http://localhost:8080/${pageContext.request.contextPath}/updateInformationServlet/a1",--%>
<%--                data:{'name':$("#txtName").val()},--%>
<%--                success:function (data,status) {--%>
<%--                   // console.log(data);--%>
<%--                    alert(data);--%>
<%--                    alert(status);--%>
<%--                }--%>
<%--            });--%>
<%--        }--%>
<%--    </script>--%>

</head>
<body>
<div style="margin: 0 auto;">
    <div class="p1" style="background-size: 20px;font-size: 30px;width: 200px;background-color: #2aabd2">
        用户名：${user.name}
    </div>
    <div class="box" style="width: 850px;height: 600px;"></div>
    <script src="js/echarts.min.js"></script>
    <script>
        var myChart = echarts.init(document.querySelector(".box"));
        var option = {
            tooltip: {
                formatter: "{a} <br/>{c} {b}"
            },
            toolbox: {
                show: true,
                feature: {
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series : [
                {
                    name: '温度',
                    type: 'gauge',
                    z: 3,
                    min: 0,
                    max: 220,
                    splitNumber: 11,
                    radius: '50%',
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            width: 10
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length: 15,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto'
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 20,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    axisLabel: {
                        backgroundColor: 'auto',
                        borderRadius: 2,
                        color: '#eee',
                        padding: 3,
                        textShadowBlur: 2,
                        textShadowOffsetX: 1,
                        textShadowOffsetY: 1,
                        textShadowColor: '#222'
                    },
                    title: {
                        // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 20,
                        fontStyle: 'italic'
                    },
                    detail: {
                        // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        formatter: function (value) {
                            value = (value + '').split('.');
                            value.length < 2 && (value.push('00'));
                            return ('00' + value[0]).slice(-2)
                                + '.' + (value[1] + '00').slice(0, 2);
                        },
                        fontWeight: 'bolder',
                        borderRadius: 3,
                        backgroundColor: '#444',
                        borderColor: '#aaa',
                        shadowBlur: 5,
                        shadowColor: '#333',
                        shadowOffsetX: 0,
                        shadowOffsetY: 3,
                        borderWidth: 2,
                        textBorderColor: '#000',
                        textBorderWidth: 2,
                        textShadowBlur: 2,
                        textShadowColor: '#fff',
                        textShadowOffsetX: 0,
                        textShadowOffsetY: 0,
                        fontFamily: 'Arial',
                        width: 60,
                        color: '#eee',
                        rich: {}
                    },
                    data: [{value: 40, name: '温度 °C'}]
                },
                {
                    name: '湿度',
                    type: 'gauge',
                    center: ['20%', '55%'],    // 默认全局居中
                    radius: '35%',
                    min: 0,
                    max: 7,
                    endAngle: 45,
                    splitNumber: 7,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            width: 8
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length: 12,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto'
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 20,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width: 5
                    },
                    title: {
                        offsetCenter: [0, '-30%'],
                    },
                    detail: {
                        // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder'
                    },
                    data: [{value: 40, name: '湿度 %rh'}]
                },
            ]
        };
        setInterval(function (){
            option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
            option.series[1].data[0].value = (Math.random()*7).toFixed(2) - 0;
            /*option.series[2].data[0].value = (Math.random()*2).toFixed(2) - 0;
            option.series[3].data[0].value = (Math.random()*2).toFixed(2) - 0;*/
            myChart.setOption(option,true);
        },2000);
        myChart.setOption(option);
    </script>
    <%--
        <br style="text-decoration:none;font-size:20px;background-size: 20px;background-color: #2aabd2;">
    --%>
    <div class="p1">
        <%--input type="text" id="xy" name="xy" maxlength="11">--%>
        <%--<script>
            function xieyi() {
                var array=new Array();
                array=document.getElementById("xy").value.split("");
                var m1=array[0].concat(array[1]);
                var m2=array[7].concat(array[8]);
                var m3=array[9].concat(array[10]);
                document.getElementById("enter1").value=m1;
                document.getElementById("enter2").value=m2;
                document.getElementById("enter3").value=m3;
                var obj=document.getElementById("kill12");
                for(var i=0;i<obj.length;i++){
                    if(obj[i].value==array[2])
                    {
                        obj[i].selected=true;
                    }
                }
                var obj1=document.getElementById("kill13");
                for(var i=0;i<obj1.length;i++){
                    if(obj1[i].value==array[3])
                    {
                        obj1[i].selected=true;
                    }
                }
                var obj2=document.getElementById("kill");
                for(var i=0;i<obj2.length;i++){
                    if(obj2[i].value==array[4])
                    {
                        obj2[i].selected=true;
                    }
                }
                var obj3=document.getElementById("kill1");
                for(var i=0;i<obj3.length;i++){
                    if(obj3[i].value==array[5])
                    {
                        obj3[i].selected=true;
                    }
                }
                var obj4=document.getElementById("kill2");
                for(var i=0;i<obj4.length;i++){
                    if(obj4[i].value==array[6])
                    {
                        obj4[i].selected=true;
                    }
                }
            }
        </script>
        <input type="button" value="传入数据" onclick="xieyi()"/><br/>--%>
        </br>

<%--            <div>--%>
<%--                用户名：--%>
<%--                <input type="text" id="txtName" onblur="a1()">--%>
<%--            </div>--%>

        <div id="text1">
            空调温度：
            <input id="enter1" type="text" name="name" style="width: 212px">
        </div>
        <div id="text12">
            <p style="display: inline" class="p4">空调模式</p>
            <select id="kill12" name="type" onchange="sql12()">
                <option value="0">cold</option>
                <option value="1">normal</option>
                <option value="2">hot</option>
            </select>
        </div>
        <div id="text13">
            <p style="display: inline" class="p4">空调风速</p>
            <select id="kill13" name="type" onchange="sql13()">
                <option value="0">high</option>
                <option value="1">proper</option>
                <option value="2">low</option>
            </select>
        </div>
        <div id="text2">
            <p style="display: inline"class="p3">门窗磁：</p>
            <select id="kill" name="type" onchange="sql()"style="width: 66px">
                <option value="0">close</option>
                <option value="1">open</option>
            </select>
        </div>
        <div id="text3">
            <p style="display: inline"class="p3">&nbsp;灯&nbsp;光&nbsp;1&nbsp;:</p>
            <select id="kill1" name="type1" onchange="sql1()"style="width: 66px">
                <option value="0">close</option>
                <option value="1">open</option>
            </select><br/>
            <p style="display: inline"class="p3">&nbsp;灯&nbsp;光&nbsp;2&nbsp;:</p>
            <select id="kill2" name="type2" onchange="sql2()"style="width: 66px">
                <option value="0">close</option>
                <option value="1">open</option>
            </select>
            <p>
                <div style="display: inline">
            <p style="display: inline;font-size: 20px;height: 40px;color: #761c19">灯光3</p>
            <div id="kill3" name="type3" onchange="sql3()" style="display: inline">
                <input id="enter2" type="text" name="name">
            </div>
        </div>
        </p>
        <p>
            <div style="display: inline">
        <p style="display: inline;font-size: 20px;height: 40px;color: #761c19">灯光4</p>
        <div id="kill4" name="type4" onchange="sql4()" style="display: inline">
            <input id="enter3" type="text" name="name">
        </div>
    </div>
    </p>
    <input type="submit" value="Enter" name="submit" onclick="enter();number();number1()" >
    <div id="wen" class="p2" style="visibility: hidden">
        温度传感器：
        <input type="text" id="a1" style="width: 130px">
        <script>
            function sum (m,n){
                var num = Math.floor(Math.random()*(m - n) + n);
                document.getElementById("a1").value=num;
            }
            sum(1,100);
        </script>
        <script>
            function number() {
                var  a=parseInt(Math.random()*100);
                document.getElementById("a1").value=a;
            }
            number(1,100);
        </script>
        <%--<button onclick="number()" name="button1" >刷新</button>--%>
    </div>
    <div id="shi" class="p3" style="visibility: hidden">
        湿度传感器：
        <input type="text" id="a2" style="width: 130px">
        <script>
            function sum (m,n){
                var num = Math.floor(Math.random()*(m - n) + n);
                document.getElementById("a2").value=num;
            }
            sum(1,100);
        </script>
        <script>
            function number1() {
                var  a1=parseInt(Math.random()*100);
                document.getElementById("a2").value=a1;
            }
            number(1,100);
        </script>
        <%--<button onclick="number1()" name="button2">刷新</button>--%>
    </div>
</div>
</div>
</div>

</body>
</html>