<div id="edit-userCredential-form">

	<a:if spec="${message != ''}">
		<div class="notify">
			${message}
		</div>
	</a:if>
		
	<h1>Update Password</h1>
	
	<form action="/userCredential/update_password/${userCredential.id}" class="form" modelAttribute="userCredential" method="post">
		
		<input type="hidden" name="id" value="${userCredential.id}"/>

		<div class="form-group">
		  	<label for="password">Password</label>
		  	<input type="text" name="password" id="password" placeholder="******" value="">
		</div>
		
		<div class="form-group">
			<input type="submit" class="button" id="update" value="Update"/>
		</div>
		
	</form>
	
</div>
	
		
