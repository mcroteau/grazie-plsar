
<div id="reset-password-form">

	<a:if spec="${message != ''}">
		<div class="span12">
			<div class="alert alert-info">${message}</div>
		</div>
	</a:if>

	<a:if spec="${not empty error}">
		<div>
			<div class="alert alert-danger">${error}</div>
		</div>
	</a:if>


	<h1>Reset Password</h1>

	<form action="/userCredential/reset/${userCredential.id}" modelAttribute="userCredential" method="post" class="pure-form pure-form-stacked">

		<input type="hidden" name="id" value="${userCredential.id}"/>

		<div class="form-group">
		  	<label for="password">${userCredential.username}</label>
		</div>

        <br/>
		<div class="form-group">
		  	<label for="password">New Password</label>
		  	<input type="text" name="password" class="form-control" id="password" placeholder="Password" value="">
		</div>

        <br/>

		<div class="form-group">
			<input type="submit" class="button retro" id="reset-password" value="Reset Password"/>
		</div>

	</form>

</div>


