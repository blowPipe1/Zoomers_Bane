<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Vacancy</title>
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
        h3 {
            margin-top: 0;
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
            transition: opacity 0.3s;
        }

        hr{
            margin-bottom: 35px;
        }

        .error-message {
            color: #d9534f;
            font-size: 12px;
            margin-top: 5px;
            display: block;
        }

    </style>
</head>
<body>
<div class="container">
    <h2>Create vacancy</h2>

    <form action="/api/vacancies/create" method="post">
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