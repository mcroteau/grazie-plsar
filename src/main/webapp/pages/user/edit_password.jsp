<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="edit-userCredential-form">

	<stargzr:if spec="${message != ''}">
		<div class="notify">
			${message}
		</div>
	</stargzr:if>
		
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
	
		
