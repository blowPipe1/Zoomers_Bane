<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
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

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            background: #f8f9fa;
            box-sizing: border-box;
        }

        .error {
            color: #dc3545;
            font-size: 13px;
            margin-top: 5px;
            display: block;
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
        }

        .back-link {
            display: block;
            text-align: left;
            margin-top: 20px;
            text-decoration: none;
            font-size: 14px;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class = "container">
    <h2>Edit Profile</h2>

    <form action="/api/users/update" method="post">
        <@spring.bind "userDto"/>

        <div class="form-group">
            <label>Name</label>
            <@spring.formInput "userDto.name" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Surname</label>
            <@spring.formInput "userDto.surname" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Age</label>
            <@spring.formInput "userDto.age", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Email</label>
            <@spring.formInput "userDto.email", "type='email'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Password</label>
            <@spring.formPasswordInput "userDto.password" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Phone Number</label>
            <@spring.formInput "userDto.phoneNumber" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Account Type</label>
            <#assign accountTypes = {
            "applicant": "Applicant",
            "employer": "Employer"
            }>
            <@spring.formSingleSelect "userDto.accountType", accountTypes />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <button type="submit" class="btn-submit">Save Changes</button>
    </form>

    <a href="/api/users/dashboard" class="back-link">♂ Back to Profile ♂</a>
</div>

</body>
</html>