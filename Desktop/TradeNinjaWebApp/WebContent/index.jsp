<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <jsp:include page="/retrieveTeamInfo"/> --%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Trade Ninjas Trading Portal</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Brandon & Kevin">

	<!--     Latest compiled and minified CSS -->
<!--     	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"> -->
	<!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>
	    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid" style="background-color: #1E656D">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#" style="color: inherit; color: #F1F3CE;">Trade Ninjas Trading Portal
          	<span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
          </a>
         </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
            <li><a href="#">Reports</a></li>
            <li><a href="#">Analytics</a></li>
            <li><a href="#">Export</a></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">Dashboard</h1>
		
		<!-- Market instrument icons -->	
          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4><i>0005</i></h4>
<!--               <span class="text-muted">Something else</span> -->
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4><i>0386</i></h4>
<!--               <span class="text-muted">Something else</span> -->
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4><i>0388</i></h4>
<!--               <span class="text-muted">Something else</span> -->
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4><i>3988</i></h4>
<!--               <span class="text-muted">Something else</span> -->
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4><i>0001</i></h4>
<!--               <span class="text-muted">Something else</span> -->
            </div>
          </div>
      	<form action="${pageContext.request.contextPath}/retrieveTeamInfo" method="POST">
      		<!-- Indicates a successful or positive action -->
			<button type="submit" class="btn btn-success col-sm-offset-4" onclick="<%=request.getAttribute("Output")%>">Start
				<span class="glyphicon glyphicon-play" aria-hidden="true"></span>
			</button>
			<!-- Indicates a dangerous or potentially negative action -->
			<button type="button" class="btn btn-danger">Stop
				<span class="glyphicon glyphicon-stop" aria-hidden="true"></span>
			</button>
		</form>
		
		<h2 class="sub-header">$$$</h2>
         <div class="table-responsive">
           <table class="table table-striped">
           		<thead>
	                <tr>
	                  <th>Cash</th>
	                  <th>Profit</th>
	                  <th>Cash Reserve</th>
	                </tr>
              </thead>
              <tbody>
              <tr>
               	<%if(request.getAttribute("cash") != null){%>
                  	<td><%=request.getAttribute("cash")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
					<%if(request.getAttribute("profit") != null){%>
                  	<td><%=request.getAttribute("profit")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
					<%if(request.getAttribute("cashReserve") != null){%>
                  	<td><%=request.getAttribute("cashReserve")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
              </tr>
              </tbody>
           </table>
         </div>
		
          <h2 class="sub-header">Market Data</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>Instrument Symbol</th>
                  <th>Holding</th>
                  <th>Reserved Amount</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>0005</td>
                  <%if(request.getAttribute("holdingList0005") != null){%>
                  	<td><%=request.getAttribute("holdingList0005")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                  <%if(request.getAttribute("reserveList0005") != null){%>
                  	<td><%=request.getAttribute("reserveList0005")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                </tr>
                <tr>
                  <td>0386</td>
                  <%if(request.getAttribute("holdingList0386") != null){%>
                  	<td><%=request.getAttribute("holdingList0386")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                  <%if(request.getAttribute("reserveList0386") != null){%>
                  	<td><%=request.getAttribute("reserveList0386")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                </tr>
                <tr>
                  <td>0388</td>
                  <%if(request.getAttribute("holdingList0388") != null){%>
                  	<td><%=request.getAttribute("holdingList0388")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                  <%if(request.getAttribute("reserveList0388") != null){%>
                  	<td><%=request.getAttribute("reserveList0388")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                </tr>
                <tr>
                  <td>3988</td>
                  <%if(request.getAttribute("holdingList3988") != null){%>
                  	<td><%=request.getAttribute("holdingList3988")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                  <%if(request.getAttribute("reserveList3988") != null){%>
                  	<td><%=request.getAttribute("reserveList3988")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                </tr>
                 <tr>
                  <td>0001</td>
                  <%if(request.getAttribute("holdingList0001") != null){%>
                  	<td><%=request.getAttribute("holdingList0001")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                  <%if(request.getAttribute("reserveList0001") != null){%>
                  	<td><%=request.getAttribute("reserveList0001")%></td>
                  <%}else{%>
                	<td>0</td>
					<% }%>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	</body>
</html>