<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>
<h2>Hello World!</h2>
<form action="${pageContext.request.contextPath }/doupload.action" method="post" enctype="multipart/form-data">
    <h2>文件上传</h2>
    文件:<input type="file" name="uploadFile"/><br/><br/>
    <input type="submit" value="上传"/>
</form>

<form action="${pageContext.request.contextPath }/doupload2.action" method="post" enctype="multipart/form-data">
    <h2>文件上传</h2>
    文件:<input type="file" name="uploadFile2"/><br/>
    <input type="file" name="uploadFile2"/><br/>
    <input type="file" name="uploadFile2"/><br/><br/>
    <input type="submit" value="上传2"/>
</form>

<a href="${pageContext.request.contextPath }/download.action?line.jpg">下载</a>
<button onclick="requestJson()">requestJson</button>
<button onclick="responseJson()">responseJson</button>
</body>
<script>

    function requestJson(){

        var jsonData = {
            "name" : "手机",
            "price" : "999"
        };
        $.ajax({
            type:'post',
            url:'${pageContext.request.contextPath }/requestJson.action',
            contentType:'application/json;charset=utf-8',//指定为json类型
            //数据格式是json串，商品信息
            data:JSON.stringify(jsonData),
            success:function(data){//返回json结果
                alert(data.name);
            }
        });
    }

    //请求key/value，输出是json
    function responseJson(){

        $.ajax({
            type:'post',
            url:'${pageContext.request.contextPath }/responseJson.action',
            //请求是key/value这里不需要指定contentType，因为默认就 是key/value类型
            //数据格式是key/value，商品信息
            data:'name=手机&price=999',
            success:function(data){//返回json结果
                alert(data.name);
            }
        });
    }
</script>
</html>
