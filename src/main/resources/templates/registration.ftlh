<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message "registration.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/registration.css'/>">
</head>
<body>

<div class="container">
    <div style="text-align: right; margin-bottom: 20px;">
        <span><@spring.message "common.language.select"/>:</span>
        <a href="?lang=ru" style="margin-left: 10px; text-decoration: none;">RU</a>
        <a href="?lang=en" style="margin-left: 10px; text-decoration: none;">EN</a>
    </div>

        <h2><@spring.message "registration.body.header"/></h2>
        <form action="/register" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label for="name"><@spring.message "common.name"/></label>
                <@spring.formInput "userDto.name" "class='form-control'" />
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-group">
                <label for="surname"><@spring.message "common.surname"/></label>
                <@spring.formInput "userDto.surname" "class='form-control'" />
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="age"><@spring.message "common.age"/></label>
                    <@spring.formInput "userDto.age" "class='form-control' type='number'" />
                    <@spring.showErrors "<br>" "error-message" />
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
            </div>


            <div class="form-group">
                <#assign emailPlaceholder = springMacroRequestContext.getMessage("common.email.placeholder")>
                <label for="email"><@spring.message "common.email"/></label>
                <@spring.formInput "userDto.email", "class='form-control' type='email' placeholder='${emailPlaceholder}'"/>
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-group">
                <#assign phonePlaceholder = springMacroRequestContext.getMessage("common.phone-number.placeholder")>
                <label for="phoneNumber"><@spring.message "common.phone-number"/></label>
                <@spring.formInput "userDto.phoneNumber", "class='form-control' placeholder='${phonePlaceholder}'"/>
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <div class="form-group">
                <#assign passPlaceholder = springMacroRequestContext.getMessage("common.password.placeholder")>
                <label for="password"><@spring.message "common.password"/></label>
                <@spring.formPasswordInput "userDto.password", "class='form-control' placeholder='${passPlaceholder}'"/>
                <@spring.showErrors "<br>" "error-message" />
            </div>

            <@spring.formHiddenInput "userDto.avatar" "" />

            <button type="submit" class="btn-submit"><@spring.message "registration.btn"/></button>
        </form>
    <a href="login" class="login-link">♂ <@spring.message "login.btn"/> ♂</a>
</div>

</body>
</html>