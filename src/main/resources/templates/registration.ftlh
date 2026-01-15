<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New user Registration</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #f0f2f5;
            font-family: 'Segoe UI', Arial, sans-serif;
            color: #222;
        }

        .registration-container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
        }

        .form-card {
            background: white;
            max-width: 500px;
            width: 100%;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
        }

        h2 {
            text-align: center;
            margin-top: 0;
            color: #222;
            font-size: 24px;
            margin-bottom: 30px;
        }

        .subtitle {
            color: #666;
            margin-bottom: 25px;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-row {
            display: flex;
            gap: 20px;
            margin-bottom: 15px;
        }

        .form-row .form-group {
            flex: 1;
            margin-bottom: 0;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 14px;
            color: #444;
        }

        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 14px;
        }

        .form-control:focus {
            outline: none;
            border-color: #007bff;
        }

        .error-message {
            color: #d9534f;
            font-size: 12px;
            margin-top: 5px;
            display: block;
        }

        .btn-submit {
            display: block;
            width: 100%;
            background: #222;
            color: white;
            padding: 14px;
            text-align: center;
            text-decoration: none;
            border-radius: 6px;
            border: none;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: opacity 0.3s;
            margin-top: 20px;
        }

        .btn-submit:hover {
            opacity: 0.9;
        }

        .footer-link {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
            color: #666;
        }

        .footer-link a {
            text-decoration: none;
            font-weight: bold;
        }

        .footer-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="registration-container">
    <div class="form-card">
        <h2>Create account</h2>
        <p class="subtitle">Please fill required fields</p>

        <form action="/register" method="post">
            <div class="form-group">
                <label for="name">Name</label>
                <@spring.formInput "userDto.name" "class='form-control' placeholder='Enter your Name'" />
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-group">
                <label for="surname">Last Name</label>
                <@spring.formInput "userDto.surname" "class='form-control' placeholder='Enter your Last Name'" />
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="age">Age</label>
                    <@spring.formInput "userDto.age" "class='form-control' type='number'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>

                <div class="form-group">
                    <label for="accountType">Account Type</label>
                    <@spring.formSingleSelect "userDto.accountType", {
                    "applicant": "Applicant",
                    "employer": "Employer"
                    }, "class='form-control'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <@spring.formInput "userDto.email" "class='form-control' type='email' placeholder='email@example.com'" />
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-group">
                <label for="phoneNumber">Phone Number</label>
                <@spring.formInput "userDto.phoneNumber" "class='form-control' placeholder='+7 (999) 000-00-00'" />
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <@spring.formPasswordInput "userDto.password" "class='form-control' placeholder='Min 7 characters'" />
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <@spring.formHiddenInput "userDto.avatar" "" />

            <button type="submit" class="btn-submit">Register</button>
        </form>

        <div class="footer-link">
            Registered? <a href="#">Log In</a>
        </div>
    </div>
</div>

</body>
</html>