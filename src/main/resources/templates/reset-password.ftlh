<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html>
<head>
    <title>New password</title>
    <link rel="stylesheet" href="<@spring.url '/css/password-reset.css'/>">
</head>
<body>
<div class="container">
    <h2>Please Enter new Password</h2>
    <form action="/passwords/reset-password" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="token" value="${token}">
        <div class="form-group">
            <label>Inter new password:</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit" class="btn-submit">Update</button>
    </form>
</div>

</body>
</html>