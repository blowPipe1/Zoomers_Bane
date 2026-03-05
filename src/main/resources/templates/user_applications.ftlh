<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "user-applications.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/user-applications.css'/>">
</head>
<body>

<div class="container">
    <div class="header">
        <h1><@spring.message "user-applications.title"/></h1>
        <a href="/api/users/dashboard" style="text-decoration: none; color: #6c757d; font-size: 14px;"><@spring.message "common.dashboard"/></a>
    </div>

    <#list applications as app>
        <div class="application-card">
            <div class="info-block">
                <a href="/vacancies/${app.vacancy.id}" class="vacancy-name">${app.vacancy.name}</a>
                <div class="resume-name"><@spring.message "common.resume-title"/>: ${app.resume.name}</div>
            </div>

            <div class="status-block">
                <p><@spring.message "common.status"/>:
                    <@spring.message app.confirmation?string('user-applications.status.confirmed', 'user-applications.status.pending')/> </p>
            </div>

            <a href="#" class="btn-chat"><@spring.message "user-applications.chat"/></a>
        </div>
    <#else>
        <div class="empty-state">
            <h3><@spring.message "user-applications.empty"/></h3>
            <br>
        </div>
    </#list>
</div>

</body>
</html>