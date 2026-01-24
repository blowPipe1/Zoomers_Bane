<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login</title>
    <style>
        body{
            font-family: 'Segoe UI', Arial, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
        }

        .container{
            background: white;
            max-width: 600px;
            width: 100%;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            padding: 40px;
        }

        h2 {
            text-align: center;
            margin-top: 0;
            font-size: 24px;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 14px;
            color: #444;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            background: #f8f9fa;
            box-sizing: border-box;
        }

        .btn-submit {
            display: block;
            width: 100%;
            padding: 14px;
            text-align: center;
            text-decoration: none;
            border-radius: 6px;
            border: none;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 20px;
            transition: opacity 0.3s;
            background-color: #007bff;
            color: white;
        }

        .error-message {
            color: #d9534f;
            font-size: 12px;
            margin-top: 5px;
            display: block;
        }

        .register-link {
            display: block;
            text-align: left;
            margin-top: 20px;
            text-decoration: none;
            font-size: 14px;
            color: #007bff;
        }

        .register-link:hover {
            text-decoration: underline;
        }
    </style>
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