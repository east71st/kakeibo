<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav>
	<ul class="menuItems">
		<li class="menuItem"><a href="http://localhost:8080/kakeibo/InputServlet">入力</a></li>
		<li class="menuItem"><a href="http://localhost:8080/kakeibo/DisplayServlet">表示</a></li>
		<li class="menuItem"><a href="http://localhost:8080/kakeibo/UpdateServlet">修正</a></li>
		<li class="menuItem">
			<a href="#">設定</a>
			<ul class="dropdownLists">
				<li class="dropdownList"><a href="http://localhost:8080/kakeibo/HimokuServlet">費目設定</a></li>
				<li class="dropdownList"><a href="http://localhost:8080/kakeibo/#">その他</a></li>
			</ul>
		</li>
	</ul>
</nav>