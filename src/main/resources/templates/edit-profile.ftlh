<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "edit-profile.header"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/edit-profile.css'/>">
</head>
<body>

<div class = "container">
    <h2><@spring.message "edit-profile.header"/></h2>
    <form action="/api/users/update" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <@spring.bind "userDto"/>

        <div class="form-group">
            <label><@spring.message "common.name"/></label>
            <@spring.formInput "userDto.name" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.surname"/></label>
            <@spring.formInput "userDto.surname" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.age"/></label>
            <@spring.formInput "userDto.age", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.email"/></label>
            <@spring.formInput "userDto.email", "type='email'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.phone-number"/></label>
            <@spring.formInput "userDto.phoneNumber" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.account-type"/></label>

            <#assign accountTypes = {
            "applicant": springMacroRequestContext.getMessage("common.account-type.applicant"),
            "employer": springMacroRequestContext.getMessage("common.account-type.employer")
            }>

            <@spring.formSingleSelect "userDto.accountType", accountTypes, "class='form-control'" />
            <@spring.showErrors "<br/>", "error" />
        </div>
        <button type="submit" class="btn-submit"><@spring.message "common.save-btn"/></button>
    </form>
    <a href="/api/users/dashboard" class="back-link">♂ <@spring.message "edit.profile.back-to-profile"/> ♂</a>
</div>

</body>
</html>