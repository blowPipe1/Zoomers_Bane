<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "vacancy-create.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/item-create.css'/>">
</head>
<body>
<div class="container">
    <h2><@spring.message "vacancy-create.title"/></h2>

    <form action="/api/vacancies/create" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label><@spring.message "common.vacancy-title"/></label>
            <@spring.formInput "vacancyDto.name" "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.description"/></label>
            <@spring.formInput "vacancyDto.description" "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.category"/></label>
            <@spring.formSingleSelect "vacancyDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.salary"/></label>
            <@spring.formInput "vacancyDto.salary" "class='form-control' type='number' step='0.01'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "edit-vacancy.exp-from"/></label>
            <@spring.formInput "vacancyDto.expFrom" "class='form-control' type='number'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "edit-vacancy.exp-to"/></label>
            <@spring.formInput "vacancyDto.expTo" "class='form-control' type='number'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>
        <hr>

        <@spring.formHiddenInput "vacancyDto.isActive" "true" />

        <button type="submit" class="btn-submit"><@spring.message "common.save-btn"/></button>
    </form>
</div>
</body>
</html>