<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Events</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/resources/css/test.css" rel="stylesheet"/>
    <link href="/resources/css/bootstrapTheme.css" rel="stylesheet">

    <link rel="stylesheet" href="/resources/css/kendoLib/kendo.common.min.css"/>
    <link rel="stylesheet" href="/resources/css/kendoLib/kendo.rtl.css"/>
    <link rel="stylesheet" href="/resources/css/kendoLib/kendo.silver.css"/>
    <link rel="stylesheet" href="/resources/css/kendoLib/kengo.modile.css"/>


    <script src="/resources/js/lib/jquery-2.1.4.min.js"></script>
    <script src="/resources/js/lib/angular.min.js"></script>
    <script src="/resources/js/lib/angular-route.min.js"></script>

    <script src="/resources/js/lib/bootstrap.js"></script>
    <script src="/resources/js/lib/angular-touch.js"></script>

    <script src="/resources/js/eventsModule/app.js"></script>
    <script src="/resources/js/eventsModule/controllers.js"></script>
    <script src="/resources/js/eventsModule/services.js"></script>
    <script src="/resources/js/eventsModule/filters.js"></script>

    <script src="/resources/js/lib/kendo.js"></script>
</head>
<body ng-app="events" >
<jsp:include page="/WEB-INF/views/jspf/nav.jsp" flush="true"/>
<div class="container" style="margin-top: 60px">
    <div class="row">
        <div ng-view></div>
    </div>
</div>
</body>
</html>

