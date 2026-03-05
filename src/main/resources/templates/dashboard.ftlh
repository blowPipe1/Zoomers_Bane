<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message "common.profile"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/dashboard.css'/>">
</head>
<body>
    <div class="container">
        <div style="text-align: right; margin-bottom: 20px;">
            <span><@spring.message "common.language.select"/>:</span>
            <a href="?lang=ru" style="margin-left: 10px;">RU</a>
            <a href="?lang=en" style="margin-left: 10px;">EN</a>
        </div>

        <h2><@spring.message "common.profile"/></h2>
        <div class="profile-header">
            <div class="user-avatar-container">
                <form id="avatarForm" action="/api/users/avatar" method="post" enctype="multipart/form-data" class="avatar-form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="avatar-wrapper">
                        <label for="avatarInput" class="avatar-label" title="<@spring.message 'dashboard.profile-header.change-image'/>">
                            <#if user.avatar?has_content>
                                <img src="/images/${user.avatar}" alt="Avatar" id="avatarPreview">
                            <#else>
                                <img src="/images/gachi.jpg" alt="No Avatar" id="avatarPreview">
                            </#if>
                        </label>
                        <input type="file" id="avatarInput" name="file" accept="image/*" onchange="this.form.submit()" style="display: none;">
                    </div>
                </form>
            </div>

            <div class="user-info">
                <h3>${user.name! "Not Found"} ${user.surname! "Not Found"}</h3>
                <h3 style="font-weight: normal"><@spring.message "common.age"/>: ${user.age! 0} <@spring.message "common.years"/></h3>
            </div>

            <div class="actions">
                <a href="/api/responses/">
                    <div class="action"><@spring.message "dashboard.actions.responses"/></div>
                </a>
                <#if (user.accountType! "")?lower_case == "applicant">
                    <a href="/api/resumes/form">
                        <div class="action"><@spring.message "dashboard.actions.create-resume"/></div>
                    </a>
                    <a href="/api/vacancies/all">
                        <div class="action"><@spring.message "common.vacancies"/></div>
                    </a>
                <#elseif (user.accountType! "")?lower_case == "employer">
                    <a href="/api/vacancies/form">
                        <div class="action"><@spring.message "dashboard.actions.create-vacancy"/></div>
                    </a>
                    <a href="/api/resumes/all">
                        <div class="action"><@spring.message "common.resumes"/></div>
                    </a>
                </#if>
                <a href="/api/users/edit">
                    <div class="action"><@spring.message "common.edit-btn"/></div>
                </a>
                <form action="/logout" method="post" class="nav-form">
                    <div class="action">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn-reset" style="color: inherit"><@spring.message "common.logout"/></button>
                    </div>
                </form>
            </div>
        </div>

        <#if (user.accountType! "")?lower_case == "applicant">
            <h2><@spring.message "common.resumes"/>:</h2>
            <div class="resume-list">
                <#list itemsList![] as items>
                    <div class="resume-card">
                        <h3>${items.name! ""}</h3>
                        <p><@spring.message "item-list.updated"/>: ${items.formattedUpdateTime}</p>
                        <div style="display: flex; flex-direction: row; gap: 10px; width: 100%; box-sizing: border-box;" >
                            <a href="/api/resumes/edit/${items.id}" style="flex: 1;"><div class="card-action" style="text-align: center;"> <@spring.message "common.edit-btn"/> </div></a>
                            <a href="javascript:location.reload();" style="flex: 1;"><div class="card-action" style="text-align: center;"> <@spring.message "common.update-btn"/> </div></a>
                        </div>

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
                        <div style="display: flex; flex-direction: row; gap: 10px; width: 100%; box-sizing: border-box;" >
                            <a href="/api/vacancies/edit/${items.id}" style="flex: 1;"><div class="card-action" style="text-align: center;"> <@spring.message "common.edit-btn"/> </div></a>
                            <a href="javascript:location.reload();" style="flex: 1;"><div class="card-action" style="text-align: center;"> <@spring.message "common.update-btn"/> </div></a>
                        </div>
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