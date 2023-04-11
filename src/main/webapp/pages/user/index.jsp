<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="inside-container">

	<stargzr:if spec="${message != ''}">
		<p class="notify">${message}</p>
	</stargzr:if>

	<h1>Users</h1>
	<stargzr:if spec="${users.size() > 0}">
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Phone</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<stargzr:foreach items="${users}" var="userCredential" >
					<tr>
						<td>${userCredential.id}</td>
						<td>${userCredential.name}</td>
						<td>${userCredential.phone}</td>
						<td><a href="/users/edit/${userCredential.id}" title="Edit" class="button retro">Edit</a>
					</tr>
				</stargzr:foreach>
			</tbody>
		</table>
	</stargzr:if>
	<stargzr:if spec="${users.size() == 0}">
		<p>No users created yet.</p>
	</stargzr:if>
</div>