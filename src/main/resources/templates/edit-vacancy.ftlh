<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Edit Vacancy</title>
    <link rel="stylesheet" href="<@spring.url '/css/edit-item.css'/>">
</head>
<body>
<div class = "container">
    <h2>Edit Vacancy</h2>

    <form action="/api/vacancies/update/${vacancyDto.id}" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <@spring.bind "vacancyDto"/>

        <div class="form-group">
            <label>Name</label>
            <@spring.formInput "vacancyDto.name" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Description</label>
            <@spring.formInput "vacancyDto.description" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Category</label>
            <@spring.formSingleSelect "vacancyDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Salary</label>
            <@spring.formInput "vacancyDto.salary", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Experience(Starting)</label>
            <@spring.formInput "vacancyDto.expFrom", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Experience(Limit)</label>
            <@spring.formInput "vacancyDto.expTo", "type='number'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Статус</label>
            <#assign options = {
            "true": "Active",
            "false": "Inactive"
            }>

            <@spring.formSingleSelect "vacancyDto.isActive", options, "class='form-select'" />

            <@spring.showErrors "<br/>", "error" />
        </div>

        <button type="submit" class="btn-submit">Save Changes</button>
    </form>

    <a href="/api/users/dashboard" class="back-link">♂ Back to Profile ♂</a>
</div>

</body>
</html>