<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="model.InputData,model.Himoku,model.Kakeibo,java.util.Date,java.util.List,java.util.Map,java.text.SimpleDateFormat"%>

<%
InputData inputData = (InputData) request.getAttribute("inputData");
List<Kakeibo> inputDataList = (List<Kakeibo>) session.getAttribute("inputDataList");
Map<Integer,Himoku> himokuMap = (Map<Integer,Himoku>) session.getAttribute("himokuMap");
SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
String today = dateFormat.format(new Date());
%>

<!DOCTYPE html>
<html lang="ja">

<jsp:include page="WEB-INF/jsp/head.jsp" />

<body>

	<header>

		<jsp:include page="WEB-INF/jsp/nav.jsp"/>
	</header>
	
	<div id="main">
		
		<section id="console">
			<form action="InputServlet" method="post">
				<table>
					<tr>
						<td>日付</td>
						<td><input type="date" name="hiduke" id="hiduke" required></td>
					</tr>
					<tr>
						<td>費目</td>
						<td><select name="himokuId" id="himokuId" required></select></td>
					</tr>
					<tr>
						<td>明細</td>
						<td rowspan="4"><textarea rows="4" cols="15" name="meisai" id="meisai"></textarea></td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td>金額</td>
						<td><input type="number" name="kingaku" id="kingaku" min=0></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="登録"></td>
					</tr>
				</table>
			</form>
		</section>

		<section id="tableSection">
			<div id="tableArea">
			
				<table id="kakeiboTable">
		
					<% if (inputDataList != null) { %>
					
						<thead>
							<tr class="headRow">
								<th class="fixdRow" width="150px">日付</th>
								<th class="fixdRow" width="200px">費目</th>
								<th class="fixdRow" width="500px">内容</th>
								<th class="fixdRow" width="200px">収入</th>
								<th class="fixdRow" width="200px">支出</th>
							</tr>
						</thead>
						
						<tbody>
							<%
							int sumNyukingaku = 0;
							int sumShukingaku = 0;
							for (Kakeibo kakeibo : inputDataList) { 
								sumNyukingaku += kakeibo.getNyukingaku();
								sumShukingaku += kakeibo.getShukingaku();
							%>
								<tr class="bodyRow">
									<td class="dateCol"><%= dateFormat.format(kakeibo.getHiduke()) %></td>
									<td class="textCol"><%= himokuMap.get(kakeibo.getHimokuId()).getHimokumei() %></td>
									<td class="textCol"><%= kakeibo.getMeisai() %></td>
									<td class="numberCol"><%= String.format("%,d",kakeibo.getNyukingaku()) %></td>
									<td class="numberCol"><%= String.format("%,d",kakeibo.getShukingaku()) %></td>
								</tr>
							<% } %>
							
							<tr class="bottomRow">
								<td class="dateCol"><strong>合計</strong></td>
								<td class="textCol"></td>
								<td class="textCol"></td>
								<td class="numberCol"><strong><%= String.format("%,d",sumNyukingaku) %></strong></td>
								<td class="numberCol"><strong><%= String.format("%,d",sumShukingaku) %></strong></td>
							</tr>
						</tbody>
					<% } %>
				</table>
			</div>
		</section>
	
	</div>
	
	<footer>
		<% if (inputData != null && inputData.getErrorMsg().length() != 0) { %>
			<div id="errorDisplay">
				${inputData.getErrorMsg()}
			</div>
		<% } %>	
	</footer>	
</body>
	
<script>
	
	let select = document.getElementById('himokuId');
	let option;
	'<% for (Integer i : himokuMap.keySet()) { %>'
		
		option = document.createElement('option');
		option.value = '<%= himokuMap.get(i).getId() %>';
		option.text = '<%= himokuMap.get(i).getHimokumei() %>';
		select.appendChild(option);
	'<% } %>'
	
	'<% if (inputData != null && inputData.getErrorMsg().length() != 0) { %>'

		document.getElementById("hiduke").value = '<%= inputData.getInputHiduke() %>';
		document.getElementById("himokuId").value = '<%= inputData.getInputHimokuId() %>';
		document.getElementById("meisai").value = '<%= inputData.getInputMeisai() %>';
		document.getElementById("kingaku").value = '<%= inputData.getInputKingaku() %>';
	'<% } else if (inputData != null) { %>'

		document.getElementById("hiduke").value = '<%= inputData.getInputHiduke() %>';
		document.getElementById("himokuId").value = '<%= inputData.getInputHimokuId() %>';
	'<% } else { %>'

		document.getElementById("hiduke").value = '<%= today %>';
	'<% } %>'
</script>

</html>