<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message "dashboard.title.profile"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/dashboard.css'/>">
</head>
<body>
    <div class="container">
        <h2><@spring.message "dashboard.body.header.profile"/></h2>
        <div class="profile-header">
            <div class="user-avatar">
                <label for="avatarInput" style="cursor: pointer;" title="<@spring.message"dashboard.profile-header.change-image"/>">
                    <#if user.avatar?has_content>
                        <img src="/images/${user.avatar}" alt="Avatar" id="avatarPreview">
                    <#else>
                        <img src="/images/gachi.jpg" alt="No Avatar" id="avatarPreview">
                    </#if>
                </label>
                <form id="avatarForm" action="/api/users/avatar" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div style="margin-top: 10px; display: flex; flex-direction: column; width: 120px">
                        <input type="file" id="avatarInput" name="file" accept="image/*" title="<@spring.message"dashboard.profile-header.change-image-input"/>">
                        <button type="submit" class="btn-update"><@spring.message "dashboard.profile-header.avatarForm.button"/></button>
                    </div>
                </form>
            </div>

            <div class="user-info">
                <h3>${user.name! "Not Found"} ${user.surname! "Not Found"}</h3>
                <h3 style="font-weight: normal"><@spring.message "dashboard.user-info.age"/>: ${user.age! 0} <@spring.message "dashboard.years"/></h3>
            </div>

            <div class="actions">
                <div class="action"> <a href="/api/responses/" style="text-decoration: none;"><@spring.message "dashboard.actions.responses"/></a></div>
                <#if (user.accountType! "")?lower_case == "applicant">
                    <div class="action"> <a href="/api/resumes/form" style="text-decoration: none;"><@spring.message "dashboard.actions.create-resume"/></a></div>
                    <div class="action"> <a href="/api/vacancies/all" style="text-decoration: none;"><@spring.message "dashboard.actions.vacancies"/></a></div>
                <#elseif (user.accountType! "")?lower_case == "employer">
                    <div class="action"> <a href="/api/vacancies/form" style="text-decoration: none;"><@spring.message "dashboard.actions.create-vacancy"/></a></div>
                    <div class="action"> <a href="/api/resumes/all" style="text-decoration: none;"><@spring.message "dashboard.actions.resumes"/></a></div>
                </#if>
                <div class="action"> <a href="/api/users/edit" style="text-decoration: none;"><@spring.message "dashboard.actions.edit-profile"/></a></div>
            </div>
        </div>

        <#if (user.accountType! "")?lower_case == "applicant">
            <h2><@spring.message "dashboard.item-list.header.resumes"/>:</h2>
            <div class="resume-list">
                <#list itemsList![] as items>
                    <div class="resume-card">
                        <h3>${items.name! ""}</h3>
                        <p><@spring.message "dashboard.item-card.category"/>: ${items.category! ""}</p>
                        <p><@spring.message "dashboard.item-card.salary"/>: ${items.salary! 0}</p>
                        <p><@spring.message "dashboard.item-card.status"/>: ${items.isActive?string('Active', 'Inactive')}</p>
                        <div class="action"> <a href="/api/resumes/edit/${items.id}" style="text-decoration: none;"><@spring.message "dashboard.item-card.action.edit"/></a></div>

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
                        <p><@spring.message "dashboard.item-card.category"/>: ${items.category! ""}</p>
                        <p><@spring.message "dashboard.item-card.salary"/>: ${items.salary! 0}</p>
                        <p><@spring.message "dashboard.item-card.status"/>: ${items.isActive?string('Active', 'Inactive')}</p>
                        <p><@spring.message "dashboard.item-card.experience"/>: ${items.expFrom! 0} - ${items.expTo! 0} <@spring.message "dashboard.years"/></p>
                        <div class="action"> <a href="/api/vacancies/edit/${items.id}" style="text-decoration: none;"><@spring.message "dashboard.item-card.action.edit"/></a></div>
                    </div>
                <#else>
                    <div class="empty-state">
                        <p><@spring.message "dashboard.item-list.empty.vacancy"/></p>
                    </div>
                </#list>
            </div>
        <#else>
            <p><@spring.message "dashboard.not-found"/></p>
        </#if>
    </div>

</body>
</html>