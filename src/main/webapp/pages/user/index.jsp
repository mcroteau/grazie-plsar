
<div class="inside-container">

	<a:if spec="${message != ''}">
		<p class="notify">${message}</p>
	</a:if>

	<h1>Users</h1>
	<a:if spec="${users.size() > 0}">
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
				<a:foreach items="${users}" var="userCredential" >
					<tr>
						<td>${userCredential.id}</td>
						<td>${userCredential.name}</td>
						<td>${userCredential.phone}</td>
						<td><a href="/users/edit/${userCredential.id}" title="Edit" class="button retro">Edit</a>
					</tr>
				</a:foreach>
			</tbody>
		</table>
	</a:if>
	<a:if spec="${users.size() == 0}">
		<p>No users created yet.</p>
	</a:if>
</div>