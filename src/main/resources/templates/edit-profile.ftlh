<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="<@spring.url '/css/edit-profile.css'/>">
</head>
<body>

<div class = "container">
    <h2>Edit Profile</h2>

    <form action="/api/users/update" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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