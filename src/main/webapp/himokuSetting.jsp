<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="model.Himoku,model.HimokuSettingData,java.util.Date,java.util.Map,java.text.SimpleDateFormat"%>
<%
HimokuSettingData himokuSettingData = (HimokuSettingData) session.getAttribute("himokuSettingData");
Map<Integer, Himoku> himokuMap = (Map<Integer, Himoku>) session.getAttribute("himokuMap");
%>

<!-- 費目データの設定用画面の表示 -->
<!DOCTYPE html>
<html lang="ja">

<head>
	<jsp:include page="WEB-INF/jsp/head.jsp" />
</head>

<body>

	<header>
		<!-- メニューバーの表示 -->
		<jsp:include page="WEB-INF/jsp/menu.jsp" />
	</header>
	
	<div id="main">
		<!-- コンソールの表示 -->
		<section id="console">
			<form action="HimokuServlet" method="post">
				<table>
					<tr>
						<td>費目名</td>
						<td><input type="text" name="himokumei" id="himokumei" size="15" required></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="新規登録"></td>
					</tr>
				</table>
				<input type="hidden" name="id" id="id" value="0">
				<input type="hidden" name="option" value="create">
			</form>
		</section>
		
		<!-- 費目データテーブルの表示 -->
		<section id="tableSection">
			<div id="tableArea">
				<table>
					<thead id="thead"></thead>
					<tbody id="tbody"></tbody>
				</table>
			</div>
		</section>
	</div>
	
	<footer>
		<!-- 入力エラーの表示 -->
		<% if (himokuSettingData != null && himokuSettingData.getErrorMsg().length() != 0) { %>
			<div id="errorDisplay">
				${himokuSettingData.getErrorMsg()}
			</div>
		<% } %>
	</footer>	
</body>

<script>
	//費目データテーブルのデータ表示
	'<% if (himokuMap != null) { %>'
		
		let header = ['ID', '費目名',''];
		let colWidth =['50px','200px','40px'];
		
		let thead = document.getElementById('thead');
		
		let tr = document.createElement('tr');
		tr.className = 'headRow';
		let th;
		for (let i in header) {
			let th = document.createElement('th');
			if (i == 0) {
				th.className = 'fixdRowCol';
			}else{
				th.className = 'fixdRow';
			}
			th.setAttribute('width',colWidth[i]);
			th.innerHTML = header[i];
			tr.appendChild(th);
		}
		thead.appendChild(tr);
		
		let tbody = document.getElementById('tbody');
		
		'<% for (int i : himokuMap.keySet()) { %>'
			
			tr = document.createElement('tr');
			tr.className = 'bodyRow';
			
			th = document.createElement('th');
			th.className = 'fixdCol numberCol';
			th.setAttribute('contenteditable','true');
			th.innerHTML = '<%=himokuMap.get(i).getId()%>';
			tr.appendChild(th);
			
			td = document.createElement('td');
			td.className = 'textCol';
			td.setAttribute('contenteditable','true');
			td.innerHTML = '<%=himokuMap.get(i).getHimokumei()%>';
			tr.appendChild(td);
			
			td = document.createElement('td');
			td.className = 'innerBoxCol';
			button = document.createElement('button');
			button.className ='update';
			button.onclick = clicked;
			button.style.width = '100%'
			button.style.boxSizing = 'border-box';
			button.style.textAlign = 'center';
			button.innerHTML = '更新';
			td.appendChild(button);
			tr.appendChild(td);
					
			tbody.appendChild(tr);
		'<% } %>'
		
		//修正する費目データをコントロールにポスト
		function clicked(e) {
				
			const url = 'http://localhost:8080/kakeibo/HimokuServlet';
			const fd = new FormData();

			let trChildren = e.target.parentElement.parentElement.children
		  
			fd.append('id', trChildren[0].textContent);
			fd.append('himokumei',trChildren[1].textContent);
			fd.append('option',e.target.className);
	
		    fetch(url,{
			    method: 'POST',
			    body: fd
			}).then((response) => {
		        if(!response.ok) {
		            console.log('error!');
		        } 
		        console.log('ok!');
// 		        location.reload();
		        return response;
		    }).then((data)  => {
		        console.log(data);
		    }).catch((error) => {
		        console.log(error);
		    });
		}
		
	'<% } %>'

</script>

</html>