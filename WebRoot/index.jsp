<%@ page language="java" import="java.util.*,com.lg.domain.*"  pageEncoding="utf-8"%>
<%@page import="com.lg.dao.MapService"%>
<%@page import="com.lg.domain.Map"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
//从数据库中取出经纬度数据
  //out.println("lnglatXY = [116.496574, 39.992706]; //已知点坐标");
%>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>逆地理编码</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=a796472d437364bb795b0632634250f1&plugin=AMap.Geocoder"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body onload="regeocoder()">
<div id="container"></div>
<div id="tip">
    <b>经纬度 116.396574, 39.992706 的地理编码结果:</b>
    <span id="result"></span>
</div>
<script type="text/javascript">
    var map = new AMap.Map("container", {
        resizeEnable: true,
		zoom: 18
		
    }),
    <%
    //从数据库中取出数据
    MapService mapService=new MapService();
    Map map=mapService.getMapInfo();
    System.out.println(map.getLongitude()+""+map.getLatitude()+"");
    %>
    <%
    //
    out.println("lnglatXY = [+"+map.getLongitude()+","+ map.getLatitude()+"]; //已知点坐标");
    %>  
    //lnglatXY = [116.496574, 39.992706]; //已知点坐标
    function regeocoder() {  //逆地理编码
        var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: "all"
        });        
        geocoder.getAddress(lnglatXY, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
                geocoder_CallBack(result);
            }
        });        
        var marker = new AMap.Marker({  //加点
            map: map,
            position: lnglatXY
        });
        map.setFitView();
    }
    function geocoder_CallBack(data) {
        var address = data.regeocode.formattedAddress; //返回地址描述
        document.getElementById("result").innerHTML = address;
    }
</script>
</body>
</html>			
