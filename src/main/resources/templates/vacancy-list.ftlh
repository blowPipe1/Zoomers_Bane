<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vacancies</title>
    <style>
        body {
            font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 1100px;
            margin: 0 auto;
        }

        h2 {
            text-align: center;
            margin-top: 0;
            font-size: 24px;
            margin-bottom: 30px;
        }

        .vacancy-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .vacancy-card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            display: flex;
            flex-direction: column;
        }

        .btn-view{
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
            margin-top: auto;
        }

        .vacancy-info, .meta-info{
            gap: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f2f5;
            margin-bottom: 30px;
        }

        .meta-info{
            margin-top: auto;
        }

        .vacancy-card h3{
            margin-bottom: 4px;
            margin-top: 4px;
        }
        .vacancy-card p{
            margin-bottom: 4px;
            margin-top: 4px;
        }

        .vacancy-card span{
            font-weight: lighter;
        }

    </style>
</head>
<body>

<div class="container">
    <h2>Vacancies</h2>

    <div class="vacancy-list">
        <#list vacancies as vacancy>
            <div class="vacancy-card">
                <div class="vacancy-info">
                    <h3>${vacancy.name! "N/A"}</h3>
                    <p><span>Category: </span> ${vacancy.category! "N/A"}</p>
                    <p><span>Salary: </span> ${vacancy.salary! 0}</p>
                    <p><span>Company(Owner): </span> ${vacancy.author! "N/A"}</p>
                </div>
                <div class="meta-info">
                    <p><span>Experience: </span>from ${vacancy.expFrom! 0} to ${vacancy.expTo! 0}</p>
                    <p><span>Status: </span> ${vacancy.isActive?string('Active', 'Inactive')}</p>
                </div>
                <button type="submit" class="btn-view">Open</button>
            </div>
        </#list>
    </div>
</div>

</body>
</html>