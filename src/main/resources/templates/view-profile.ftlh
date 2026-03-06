<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${user.name! "Not Found"} ${user.surname! "Not Found"}<@spring.message "common.profile.other"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/dashboard.css'/>">
</head>
<body>
<div class="container">
    <div style="text-align: right; margin-bottom: 20px;">
        <span><@spring.message "common.language.select"/>:</span>
        <a href="?lang=ru" style="margin-left: 10px;">RU</a>
        <a href="?lang=en" style="margin-left: 10px;">EN</a>
    </div>

    <h2>${user.name! "Not Found"} ${user.surname! "Not Found"} <@spring.message "common.profile.other"/></h2>
    <div class="profile-header">
        <div class="user-avatar-container">
            <div class="avatar-wrapper-other">
                <#if user.avatar?has_content>
                    <img src="/images/${user.avatar}" alt="Avatar" id="avatarPreview">
                <#else>
                    <img src="/images/gachi.jpg" alt="No Avatar" id="avatarPreview">
                </#if>
            </div>
        </div>

        <div class="user-info">
            <h3>${user.name! "Not Found"} ${user.surname! "Not Found"}</h3>
            <h3 style="font-weight: normal"><@spring.message "common.age"/>: ${user.age! 0} <@spring.message "common.years"/></h3>
        </div>

    </div>

    <#if (user.accountType! "")?lower_case == "applicant">
        <h2><@spring.message "common.resumes"/>:</h2>
        <div class="resume-list">
            <#list itemsList![] as items>
                <div class="resume-card">
                    <h3>${items.name! ""}</h3>
                    <p><@spring.message "item-list.updated"/>: ${items.formattedUpdateTime}</p>
                    <a href="/api/resumes/${items.id}" style="flex: 1;"><div class="card-action" style="text-align: center;"> <@spring.message "common.open-btn"/> </div></a>
                </div>
            <#else>
                <div class="empty-state">
                    <p><@spring.message "dashboard.item-list.empty.resume"/></p>
                </div>
            </#list>
        </div>
    <#elseif (user.accountType! "")?lower_case == "employer">
        <h2><@spring.message "dashboard.item-list.header.vacancies"/>:</h2>
        <div class="vacancy-list">
            <#list itemsList![] as items>
                <div class="vacancy-card">
                    <h3>${items.name! "Vacancy's Title"}</h3>
                    <p><@spring.message "item-list.updated"/>: ${items.formattedUpdateTime}</p>
                    <a href="/api/vacancies/${items.id}" style="flex: 1;"><div class="card-action" style="text-align: center;"> <@spring.message "common.open-btn"/> </div></a>
                </div>
            <#else>
                <div class="empty-state">
                    <p><@spring.message "dashboard.item-list.empty.vacancy"/></p>
                </div>
            </#list>
        </div>
    <#else>
        <p><@spring.message "common.not-found"/></p>
    </#if>
    <div class="pagination">
        <#if totalPages gt 1>
            <ul class="pagination-list" style="display: flex; justify-content: center; list-style: none; gap: 10px; margin-top: 20px;">
                <#if currentPage gt 0>
                    <li>
                        <a href="?page=${currentPage - 1}">&laquo; <@spring.message "common.previous"/></a>
                    </li>
                </#if>
                <#list 0..totalPages-1 as i>
                    <li>
                        <a href="?page=${i}"
                           class="${(i == currentPage)?string('active-page', '')}">
                            ${i + 1}
                        </a>
                    </li>
                </#list>
                <#if currentPage lt totalPages - 1>
                    <li>
                        <a href="?page=${currentPage + 1}"><@spring.message "common.next"/> &raquo;</a>
                    </li>
                </#if>
            </ul>
        </#if>
    </div>
</div>

</body>
</html>