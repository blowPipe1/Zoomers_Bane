<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login</title>
    <link rel="stylesheet" href="<@spring.url '/css/login.css'/>">
</head>
<body>

<div class="container">
    <h2>Log In to your account</h2>
    <#if RequestParameters.error??>
        <span class="error-message">Invalid username or password.</span>
    </#if>
    <form action="/process_login" method="post">
        <div class="form-group">
            <label for="username">Email</label>
            <input type="email" id="username" name="username" class="form-group input" placeholder="boi@nextdoor.com" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-group input" placeholder="ilikefistin" required>
        </div>

        <button type="submit" class="btn-submit">Log In</button>
    </form>
    <a href="/register-form" class="register-link">New User? ♂ Register here ♂</a>
</div>
</body>
</html>