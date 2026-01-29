<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New user Registration</title>
    <link rel="stylesheet" href="<@spring.url '/css/registration.css'/>">
</head>
<body>

<div class="container">
        <h2>Create account</h2>
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
    <a href="login" class="login-link">♂ Log In ♂</a>
</div>

</body>
</html>