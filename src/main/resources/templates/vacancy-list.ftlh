<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vacancies</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 40px 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        header {
            text-align: center;
            margin-bottom: 40px;
        }

        .vacancy-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }

        .card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            display: flex;
            flex-direction: column;
        }

        .category {
            color: #007bff;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
            margin-bottom: 8px;
        }

        .name {
            font-size: 18px;
            margin: 0 0 10px 0;
            color: #222;
        }

        .salary {
            font-size: 20px;
            color: #28a745;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .meta-info {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 8px;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .btn-apply {
            display: block;
            text-align: center;
            background: #222;
            color: white;
            padding: 10px;
            text-decoration: none;
            border-radius: 6px;
            margin-top: auto;
        }

        .status{color: blue; font-weight: bold;}

    </style>
</head>
<body>

<div class="container">
    <header>
        <h1>Available Vacancies</h1>
        <div class="subtitle">Become Ultimate Dungeon Master</div>
    </header>

    <div class="vacancy-grid">
        <#list vacancies as vacancy>
            <div class="card">
                <div class="category">${vacancy.category}</div>
                <h2 class="name">${vacancy.name}</h2>

                <div class="salary">
                    ${vacancy.salary?string.currency}
                </div>

                <div class="description">
                    ${vacancy.description!"Employer did't submit a description"}
                </div>

                <div class="meta-info">
                    <span class="experience">
                            Experience: from ${vacancy.expFrom!0} to ${vacancy.expTo!0} years
                    </span>
                    <br>
                    <span>
                        Status: <span class="status"> ${(vacancy.isActive)?string('Active', 'Occupied')}</span>
                    </span>
                </div>

                <div class="author">
                    <span>Company(representative): <strong>${vacancy.author}</strong></span>
                    <a href="#" class="btn-apply">Respond</a>
                </div>
            </div>
        <#else>
            <div class="empty-state">
                <h3>No vacancies</h3>
            </div>
        </#list>
    </div>
</div>

</body>
</html>