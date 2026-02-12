<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "edit-vacancy.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/edit-item.css'/>">
</head>
<body>
<div class = "container">
    <h2><@spring.message "edit-vacancy.title"/></h2>

    <form action="/api/vacancies/update/${vacancyDto.id}" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <@spring.bind "vacancyDto"/>

        <div class="form-group">
            <label><@spring.message "common.vacancy-title"/></label>
            <@spring.formInput "vacancyDto.name" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "edit-vacancy.description"/></label>
            <@spring.formInput "vacancyDto.description" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.category"/></label>
            <@spring.formSingleSelect "vacancyDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.salary"/></label>
            <@spring.formInput "vacancyDto.salary", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "edit-vacancy.exp-from"/> (<@spring.message "common.years"/>)</label>
            <@spring.formInput "vacancyDto.expFrom", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "edit-vacancy.exp-to"/> (<@spring.message "common.years"/>)</label>
            <@spring.formInput "vacancyDto.expTo", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.status"/></label>
            <#assign statusOptions = {
            "true": springMacroRequestContext.getMessage("common.status-value.active"),
            "false": springMacroRequestContext.getMessage("common.status-value.inactive")
            }>
            <@spring.formSingleSelect "vacancyDto.isActive", statusOptions, "class='form-select'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <button type="submit" class="btn-submit"><@spring.message "common.save-btn"/></button>
    </form>

    <a href="/api/users/dashboard" class="back-link">♂ <@spring.message "edit-item.back-to-profile"/> ♂</a>
</div>

</body>
</html>