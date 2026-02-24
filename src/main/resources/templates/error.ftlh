<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "error.title"/> | ${status!}</title>
    <style>

        body{
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f4f7f6;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .error-container{
            background: white;
            max-width: 500px;
            width: 100%;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            padding: 40px;
            text-align: center;
        }
        .error-code {
            font-size: 96px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 10px;
        }
        h2 {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }
        p {
            color: #666;
            margin-bottom: 30px;
        }
        .back-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }
        .back-link:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="error-container">
    <div class="error-code">${status!"Error"}</div>

    <h2><@spring.message errorKey!"error.default"/></h2>

    <p>${message!""}</p>

    <a href="/api/users/dashboard" class="back-link">
        <@spring.message "edit-item.back-to-profile"/>
    </a>
</div>

</body>