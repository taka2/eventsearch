<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Calendar,eventsearch.*" %>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <title>イベント検索</title>
    <style type = "text/css">
      body {
         background-color: #EEE;
         color: #222;
         font-family: Helvetica;
         font-size: 14px;
         margin: 0;
         padding: 0;
      }
      .form_header {float: left; width: 180px; height: 25px; padding: 0px; margin: 0em}
      .form_input {height: 30px; padding: 0px; margin: 0em}
      .button {margin-left: 30px; margin-right: 30px; height: 25px; width: 350px}
      #message {color: red}
    </style>
  </head>
  <h1>イベント検索</h1>
  <body>
    <form name = "form1" action = "/search/" method = "GET">
      
      <div class = "form_header">対象月：</div>
      <div class = "form_input">
      <select name = "target_month">
        <option value = "">むこう三ヶ月</option>
        <%
          Calendar[] monthsList = SearchHelper.getMonthList(6);
          for(Calendar cal : monthsList)
          {
        %>
          <option value = "<%= SearchHelper.formatYYYYMM(cal.getTime()) %>">
            <%= SearchHelper.formatYYYYMM(cal.getTime()) %>
          </option>
        <%
          }
        %>
      </select>
      </div>
      <span STYLE="clear: both;">

      <div class = "form_header">対象曜日：</div>
      <div class = "form_input">
      平日<input type = "radio" name = "target_day_of_week" value = "1">
      土日<input type = "radio" name = "target_day_of_week" value = "2">
      全て<input type = "radio" name = "target_day_of_week" value = "3" checked>
      </div>
      <span STYLE="clear: both;">

      <div class = "form_header">時間：</div>
      <div class = "form_input">
      <input type = "text" name = "target_time_from" value = "00:00" size = "5" maxlength = "5"> ～ <input type = "text" name = "target_time_to" value = "23:59" size = "5" maxlength = "5">
      </div>
      <span STYLE="clear: both;">

      <div class = "form_header">住所（部分一致）：</div>
      <div class = "form_input">
      <input type = "text" name = "address">
      </div>
      <span STYLE="clear: both;">

      <div class = "form_header">キーワード：</div>
      <div class = "form_input">
      <input type = "text" name = "keyword">
      </div>
      <span STYLE="clear: both;">

      <div class = "form_header">空席があるイベントのみ：</div>
      <div class = "form_input">
      <input type = "checkbox" name = "only_exists_vacant_seat" checked>
      </div>
      <span STYLE="clear: both;">

      <input type = "submit" value = "検索" class = "button">
    </form>
    <div id = "message">
    <%
      String message = (String)request.getAttribute("message");
      if(message != null)
      {
        out.println(message);
      }
    %>
    </div>

    <jsp:include page = "footer.jsp"/>
  </body>
</html>
