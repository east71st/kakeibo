<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="model.Kakeibo,model.DisplayData,model.Himoku,java.util.Date,java.util.List,java.util.Map,java.text.SimpleDateFormat"%>

<%
List<Kakeibo> kakeiboList = (List<Kakeibo>) request.getAttribute("kakeiboList");
Map<Integer, Himoku> himokuMap = (Map<Integer, Himoku>) session.getAttribute("himokuMap");
DisplayData updateData = (DisplayData) session.getAttribute("updateData");
SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
String today = dateFormat.format(new Date());
%>

<!DOCTYPE html>
<html lang="ja">

<jsp:include page="WEB-INF/jsp/head.jsp" />

<body>

	<header>

		<jsp:include page="WEB-INF/jsp/nav.jsp" />
	</header>

	<div id="main">

		<section id="console">
			<form action="UpdateServlet" method="post">
				<table>
					<tr>
						<td>期間</td>
						<td><input type="date" name="hidukeFirst" id="hidukeFirst"
							required>～</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="date" name="hidukeLast" id="hidukeLast"
							required></td>
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
						<td rowspan="4"><textarea rows="4" cols="15"
								name="meisaiSelect" id="meisaiSelect"></textarea></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="表示"></td>
					</tr>
				</table>
			</form>

		</section>

		<section id="tableSection">
			<div id="tableArea">
				<table id="kakeiboTable">
					<thead id="thead"></thead>
					<tbody id="tbody"></tbody>
				</table>
			</div>
		</section>
	</div>

	<footer>
		<%if (updateData != null && updateData.getErrorMsg().length() != 0) {%>
		<div id="errorDisplay">${updateData.getErrorMsg()}</div>
		<%}%>
	</footer>
</body>

<script>

	let select = document.getElementById('himokuSelectId');
	let option;
	'<%for (Integer i : himokuMap.keySet()) {%>'
	
		option = document.createElement('option');
		option.value = '<%=himokuMap.get(i).getId()%>';
		option.text = '<%=himokuMap.get(i).getHimokumei()%>';
		select.appendChild(option);
	'<%}%>'
	
	'<%if (updateData != null) {%>'
	
		document.getElementById('hidukeFirst').value = '<%=updateData.getHidukeFirst()%>';
		document.getElementById('hidukeLast').value = '<%=updateData.getHidukeLast()%>';
		document.getElementById('himokuSelectId').value = '<%=updateData.getHimokuSelectId()%>'
		document.getElementById('meisaiSelect').value = '<%=updateData.getMeisaiSelect()%>'
	'<%} else {%>'
	
		document.getElementById('hidukeFirst').value = '<%=today%>';
		document.getElementById('hidukeLast').value = '<%=today%>';
		document.getElementById('himokuSelectId').value = '0';
		document.getElementById('meisaiSelect').value = '';
	'<%}%>'
	
	'<%if (kakeiboList != null) {%>'
	
		let header = ['ID', '日付', '費目', '内容', '収入', '支出','',''];
		let colWidth = ['50px','150px','200px','500px','200px','200px','40px','40px'];
	
		let thead = document.getElementById('thead');
		let tr = document.createElement('tr');
		tr.className = 'headRow';
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
		let td;
		let input;
		'<%for (Kakeibo kakeibo : kakeiboList) {%>'
	
			tr = document.createElement('tr');
			tr.className = 'bodyRow';
	
			th = document.createElement('th');
			th.className = 'fixdCol numberCol';
			th.innerHTML = '<%=String.format("%d", kakeibo.getId())%>';
			tr.appendChild(th);
	
			td = document.createElement('td');
			td.className = 'innerBoxCol';
			input = document.createElement('input');
			input.type = 'date';
			input.style.width = '100%'
			input.style.boxSizing = 'border-box';
			input.style.textAlign = 'center';
			input.setAttribute('required', '');
			td.style.textAlign = 'center';
			input.value = '<%=kakeibo.getHiduke()%>';
			td.appendChild(input);
			tr.appendChild(td);
	
			td = document.createElement('td');
			td.className = 'innerBoxCol';
			select = document.createElement('select');
			select.style.width = '100%'
			select.style.boxSizing = 'border-box';
			'<%for (Integer i : himokuMap.keySet()) {%>'
				option = document.createElement('option');
				option.value = '<%=himokuMap.get(i).getId()%>';
				option.text = '<%=himokuMap.get(i).getHimokumei()%>';
				select.appendChild(option);
			'<%}%>'
			td.appendChild(select);
			tr.appendChild(td);
			select.value = '<%=Integer.valueOf(kakeibo.getHimokuId()).toString()%>';	
			
			td = document.createElement('td');
			td.className = 'textCol';
			td.setAttribute('contenteditable','true');
			td.innerHTML = '<%=kakeibo.getMeisai()%>';
			tr.appendChild(td);
	
			td = document.createElement('td');
			td.className = 'numberCol';
			td.setAttribute('contenteditable','true');
			td.innerHTML = '<%=String.format("%,d", kakeibo.getNyukingaku())%>';
			tr.appendChild(td);
	
			td = document.createElement('td');
			td.className = 'numberCol';
			td.setAttribute('contenteditable','true');
			td.innerHTML = '<%=String.format("%,d", kakeibo.getShukingaku())%>';
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
	
			td = document.createElement('td');
			td.className = 'innerBoxCol';
			button = document.createElement('button');
			button.className ='delete';
			button.onclick = clicked;
			button.style.width = '100%'
			button.style.boxSizing = 'border-box';
			button.style.textAlign = 'center';
			button.innerHTML = '削除';
			td.appendChild(button);
			tr.appendChild(td);
			
			tbody.appendChild(tr);
		'<%}%>'
		
		document.getElementById('tableArea').scrollTop = '<%=updateData.getTableScrollTop()%>'
		
		function clicked(e) {
				
			const url = 'http://localhost:8080/kakeibo/UpdateServlet';
			const fd = new FormData();
		
			fd.append('hidukeFirst', document.getElementById('hidukeFirst').value);
		    fd.append('hidukeLast',document.getElementById('hidukeLast').value);
		    fd.append('himokuSelectId',document.getElementById('himokuSelectId').value);
		    fd.append('meisaiSelect',document.getElementById('meisaiSelect').value);
			fd.append('tableScrollTop',document.getElementById('tableArea').scrollTop);
						   
			let trChildren = e.target.parentElement.parentElement.children
		  
			fd.append('id', trChildren[0].textContent);
			fd.append('hiduke',trChildren[1].firstElementChild.value);
			fd.append('himokuId',trChildren[2].firstElementChild.value);
			fd.append('meisai',trChildren[3].textContent);
			fd.append('nyukingaku',trChildren[4].textContent);
			fd.append('shukingaku',trChildren[5].textContent);
			
			fd.append('option',e.target.className);
	
		    fetch(url,{
			    method: 'POST',
			    body: fd
			}).then((response) => {
		        if(!response.ok) {
		            console.log('error!');
		        }
		        console.log('ok!');
		        location.reload();
		        return response;
		    }).then((data)  => {
		        console.log(data);
		    }).catch((error) => {
		        console.log(error);
		    });
		}
	
	'<%}%>'
</script>

</html>