<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Vacancy</title>
    <link rel="stylesheet" href="<@spring.url '/css/item-create.css'/>">
</head>
<body>
<div class="container">
    <h2>Create vacancy</h2>

    <form action="/api/vacancies/create" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label>Vacancy Title</label>
            <@spring.formInput "vacancyDto.name" "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Description</label>
            <@spring.formInput "vacancyDto.description" "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Category</label>
            <@spring.formSingleSelect "vacancyDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Salary</label>
            <@spring.formInput "vacancyDto.salary" "class='form-control' type='number' step='0.01'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Experience(Starting)</label>
            <@spring.formInput "vacancyDto.expFrom" "class='form-control' type='number'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Experience(Limit)</label>
            <@spring.formInput "vacancyDto.expTo" "class='form-control' type='number'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>
        <hr>


        <@spring.formHiddenInput "vacancyDto.isActive" "true" />

        <button type="submit" class="btn-submit">Save Vacancy</button>
    </form>
</div>
</body>
</html>