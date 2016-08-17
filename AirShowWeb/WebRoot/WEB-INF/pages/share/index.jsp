<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>链接分享</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link rel="stylesheet" href="../Public/css/jquery.mobile-1.3.2.min.css"/>
    <link rel="stylesheet" href="../Public/css/style.css"/>
    <script src="../Public/js/jquery.min.js"></script>
    <script src="../Public/js/jquery.mobile-1.3.2.min.js"></script>
</head>

<body>
<div data-role="page" id="page1" style="background-color: #fbfbfb">

    <div class="ui-bar ui-bar-b noMarginAndPading">
        <h1 style="font-size:25px;padding:5px;">
            ${target.name}
        </h1>
        <h1 style="font-size:25px;padding:5px;">
            ${target.title}
        </h1>
    </div>

    <div data-role="mycontent" style="background-color: #fbfbfb; border: 0px;">
    	<c:if test="${target.path != null}">
			<img src="..${target.path}" style="width: 100%;height: 200px" />
		</c:if>
    </div>

    <div data-role="content" data-theme="f">
        <div style="text-align: center">
            <p style="text-align: left;" >${target.introduction}</p>
        </div>
    </div>

    <div data-role="footer" data-theme="b" data-position="fixed">
        <h4>©2014</h4>
    </div>

</div>

</body>
</html>