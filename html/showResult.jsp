<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="eventsearch.*" %>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <title>イベント検索結果</title>
    <style type = "text/css">
      body, td {
         background-color: #EEE;
         color: #222;
         font-family: Helvetica;
         font-size: 14px;
         margin: 0;
         padding: 0;
      }
      .button {margin-left: 30px; margin-right: 30px; height: 25px; width: 350px}
    </style>
  </head>
  <h1>
    イベント検索結果
  </h1>
  <body>
    <h2>
      検索結果&nbsp;&nbsp;<a href = "/search/?<%= request.getQueryString() %>&output_format=rss"><img src = "/s03c.gif"></a>
    </h2>
    <a href = "/index_old.jsp"><< 戻る</a>
    <table border = "1">
      <tr>
        <th>ソース</th>
        <th>タイトル</th>
        <th>開始日時</th>
        <th>終了日時</th>
        <th>会場</th>
        <th>住所</th>
      </tr>
    <% SearchResult[] resultArray = (SearchResult[])request.getAttribute("resultArray");
       for(SearchResult result : resultArray) 
       { %>
       <tr>
         <td><%= SearchHelper.convertEventSourceCodeToName(result.getEventSource()) %></td>
         <td><a href = "<%= result.getEventUrl() %>"><%= SearchHelper.omitString(result.getTitle(), 30) %></a></td>
         <td><%= SearchHelper.formatYYYYMMDDHHMMSS(result.getStartedAt().getTime()) %></td>
         <td>
             <% if(result.getEndedAt() == null) { %>
                 &nbsp;
             <% } else { %>
                 <%= SearchHelper.formatYYYYMMDDHHMMSS(result.getEndedAt().getTime()) %>
             <% } %>
         </td>
         <% if(result.getPlace() == null) { %>
           <td>&nbsp;</td>
         <% } else { %>
           <td><%= result.getPlace() %></td>
         <% } %>
         <% if(result.getAddress() == null) { %>
           <td>&nbsp;</td>
         <% } else { %>
           <td><a href = "http://maps.google.co.jp/maps?ie=UTF8&ll=<%= result.getLat() %>,<%= result.getLon() %>&spn=0.5,0.5&z=16"><%= result.getAddress() %></a></td>
         <% } %>
       </tr>
    <% } %>
    </table>
    <a href = "/index_old.jsp"><< 戻る</a>

    <jsp:include page = "footer.jsp"/>
  </body>
</html>
