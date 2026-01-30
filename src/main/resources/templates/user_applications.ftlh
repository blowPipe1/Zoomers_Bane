<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Responses</title>
    <link rel="stylesheet" href="<@spring.url '/css/user-applications.css'/>">
</head>
<body>

<div class="container">
    <div class="header">
        <h1>My Responses</h1>
        <a href="/api/vacancies/all" style="text-decoration: none; color: #6c757d; font-size: 14px;">← Back to List</a>
    </div>

    <#list applications as app>
        <div class="application-card">
            <div class="info-block">
                <a href="/vacancies/${app.vacancy.id}" class="vacancy-name">${app.vacancy.name}</a>
                <div class="resume-name">Resume: ${app.resume.name}</div>
            </div>

            <div class="status-block">
                <span class="status-badge ${app.confirmation?string('status-confirmed', 'status-pending')}">
                    ${app.confirmation?string('Confirmed', 'Pending')}
                </span>
            </div>

            <a href="#" class="btn-chat">Chat</a>
        </div>
    <#else>
        <div class="empty-state">
            <h3>You haven't Responded to any Vacancy yet</h3>
            <p>go get a Job!</p>
            <br>
            <a href="/api/vacancies/all" style="text-decoration: none; color: #6c757d; font-size: 14px;">← Back to List</a>
        </div>
    </#list>
</div>

</body>
</html>