<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<stargzr:if spec="${message != ''}">
		<p class="notify">${message}</p>
	</stargzr:if>

	<stargzr:if spec="${!userCredential.activated}">
		<div class="notify" style="margin-top:20px;font-size:17px;font-weight:300;line-height: 1.2em;">
			If you have done this already, You will be redirected to Stripe.com
			to complete the activation process. If you are still seeing this and
			you have completed the onboarding process on Stripe.com,
			then contact one of us. croteau.mike@gmail.com.

			<form action="/stripe/activate/${userCredential.id}" method="post">
				<input type="submit" value="Activate!" class="button green"
					   style="margin:10px 0px;
					   			font-family: Roboto !important;
    							font-size:17px;
    							padding:23px 26px;"/>
				<br/>
					${userCredential.stripeAccountId}&nbsp;:&nbsp;${userCredential.guid}&nbsp;:&nbsp;${userCredential.uuid}
			</form>
		</div>
	</stargzr:if>

	<h1 class="left-float">Edit Profile</h1>

	<a href="/${userCredential.guid}" class="href-dotted-black"
							style="float:left;margin-left:20px;">What the Tipper Sees!</a>

	<br class="clear"/>

	<form action="/users/update/${userCredential.id}" method="post" enctype="multipart/form-data">

		<stargzr:if spec="${userCredential.imageUri != null &&
						userCredential.imageUri != '' &&
							userCredential.imageUri != 'null'}">
			<img src="${userCredential.imageUri}" style="width:230px;padding:3px; border:solid 1px #deeaea"/>
		</stargzr:if>

		<label>Profile Image</label>
		<input type="file" name="media"/>

		<label>Name</label>
		<input type="text" name="name" placeholder="" value="${userCredential.name}" style="width:90%"/>

		<label>Cell Phone</label>
		<span class="tiny">The application uses your cell phone to send notifications.</span>
		<br class="clear"/>
		<input type="text" name="phone" placeholder="9079878652" value="${userCredential.phone}"/>

		<label>Thank Em!</label>
		<span class="tiny">Simply say thanks, this will be displayed after the person makes a tip!</span>
		<textarea name="description">${userCredential.description}</textarea>

		<div class="button-wrapper">
			<input type="submit" value="Update Profile" class="button green"
				   style="width:100%;
				   			margin-bottom:10px;
							font-family: Roboto !important;
							font-size:17px;
							padding:23px 26px;"/>
		</div>
	</form>


	<a href="/signout" class="href-dotted">Signout</a>


