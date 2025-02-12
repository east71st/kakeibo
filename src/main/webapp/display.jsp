<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Kakeibo,model.ConsoleData,model.Himoku,java.lang.String,java.util.Date,java.util.List,java.util.Map,java.time.LocalDate,java.time.format.DateTimeFormatter"%>
    
<%
List<Kakeibo> kakeiboList = (List<Kakeibo>) request.getAttribute("kakeiboList");
Map<Integer,Himoku> himokuMap = (Map<Integer,Himoku>) session.getAttribute("himokuMap");
ConsoleData consoleData = (ConsoleData) session.getAttribute("DisplayConsoleData");
String firstDay = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
String lastDay = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE);
%>

<!-- 家計簿データの表示用画面の表示 -->
<!DOCTYPE html>
<html lang="ja">

<head>
	<jsp:include page="WEB-INF/jsp/head.jsp" />
	<script src="js/jquery-3.7.1.min.js"></script>
</head>

<body>

	<header>
		<!-- メニューバーの表示 -->
		<jsp:include page="WEB-INF/jsp/menu.jsp"/>
	</header>
	
	<div id="main">
		<!-- コンソールの表示 -->
		<section id="console">
			<form action="DisplayServlet" method="post">
				<table>
					<tr>
						<td>期間</td>
						<td>
							<input type="date" name="hidukeFirst" id="hidukeFirst" required>～
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="date" name="hidukeLast" id="hidukeLast" required>
						</td>
					</tr>
					<tr>
						<td>費目</td>
						<td>
							<select name="himokuSelectId" id="himokuSelectId">
								<option value="0">全て</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>明細</td>
						<td rowspan="4">
							<textarea rows="4" cols="15" name="meisaiSelect" id="meisaiSelect"></textarea>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="表示">
						</td>
					</tr>
				</table>
			</form>
		</section>
		
		<!-- 家計簿データテーブルの表示 -->
		<section id="tableSection">
			<div id="tableArea">
			
				<table id="kakeiboTable">
		
					<% if (kakeiboList != null) { %>
					
						<thead>
							<tr class="headRow">
								<th class="fixdRowCol" width="50px">ID</th>
								<th class="fixdRow" width="150px">日付</th>
								<th class="fixdRow" width="200px">費目</th>
								<th class="fixdRow" width="500px">明細</th>
								<th class="fixdRow" width="200px">収入</th>
								<th class="fixdRow" width="200px">支出</th>
							</tr>
						</thead>
						
						<tbody>
							<%
							int sumNyukingaku = 0;
							int sumShukingaku = 0;
							for (Kakeibo kakeibo : kakeiboList) { 
								sumNyukingaku += kakeibo.getNyukingaku();
								sumShukingaku += kakeibo.getShukingaku();
							%>
								<tr class="bodyRow">
									<th class="fixdCol numberCol"><%= String.format("%d",kakeibo.getId()) %></th>
									<td class="dateCol"><%= kakeibo.getHiduke() %></td>
									<td class="textCol"><%= himokuMap.get(kakeibo.getHimokuId()).getHimokumei() %></td>
									<td class="textCol"><%= kakeibo.getMeisai() %></td>
									<td class="numberCol"><%= String.format("%,d",kakeibo.getNyukingaku()) %></td>
									<td class="numberCol"><%= String.format("%,d",kakeibo.getShukingaku()) %></td>
								</tr>
							<% } %>
							
							<tr class="bottomRow">
								<th class="fixdCol numberCol"></th>
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
		<!-- 入力エラーの表示 -->
		<% if (consoleData != null && consoleData.getErrorMsg().length() != 0) { %>
			<div id="errorDisplay">
				${consoleData.getErrorMsg()};
			</div>
		<% } %>
	</footer>

	<script>
		//コンソールの初期データの表示
		//費目データのドロップダウンリスト
		let select = document.getElementById('himokuSelectId');
		let option;
		'<% for (Integer i : himokuMap.keySet()) { %>'
			
			option = document.createElement('option');
			option.value = '<%= himokuMap.get(i).getId() %>';
			option.text = '<%= himokuMap.get(i).getHimokumei() %>';
			select.appendChild(option);
		'<% } %>'
		
		'<% if (consoleData != null) { %>'
			//再表示時のデータ表示
			document.getElementById('hidukeFirst').value = '<%= consoleData.getHidukeFirst() %>';
			document.getElementById('hidukeLast').value = '<%= consoleData.getHidukeLast() %>';
			document.getElementById('himokuSelectId').value = '<%= consoleData.getHimokuSelectId() %>'
			document.getElementById('meisaiSelect').value = '<%= consoleData.getMeisaiSelect() %>'
		'<% } else { %>'
			//初期表示時のデータ表示
			document.getElementById('hidukeFirst').value = '<%= firstDay %>';
			document.getElementById('hidukeLast').value = '<%= lastDay %>';
			document.getElementById('himokuSelectId').value = '0';
			document.getElementById('meisaiSelect').value = '';
		'<% } %>'
	</script>
</body>
</html>