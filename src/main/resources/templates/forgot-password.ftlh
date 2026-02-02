<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Password Reset</title>
    <link rel="stylesheet" href="<@spring.url '/css/password-reset.css'/>">
</head>
<body>
<div class="container">
    <h2>Forgot Your Password?</h2>
    <p>Enter your email to receive reset link</p>

    <#if error??>
        <div class="error">${error}</div>
    </#if>

    <#if message??>
        <div class="success">${message}</div>
    </#if>

    <form action="/passwords/forgot-password" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label for="email">Email:</label><br>
            <input type="email" id="email" name="email" required
                   placeholder="example@mail.com" value="${email!''}">
        </div>

        <button type="submit" class="btn-submit">Send Link</button>
    </form>

    <p><a href="/login">Back to Login</a></p>
</div>

</body>
</html>