<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message "common.resumes"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/resume-list.css'/>">
</head>
<body>
<nav class="navbar">
    <div class="navbar-container">
        <a href="#" class="nav-brand"><@spring.message "common.job-portal"/></a>
        <div class="nav-menu">
            <a href="/api/users/dashboard" class="nav-link"><@spring.message "common.dashboard"/></a>
            <form action="/logout" method="post" class="nav-form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="nav-link nav-logout"><@spring.message "common.logout"/></button>
            </form>
            <span><@spring.message "common.language.select"/>:</span>
            <a href="?lang=ru" style="margin-left: 10px; text-decoration: none;">RU</a>
            <a href="?lang=en" style="margin-left: 10px; text-decoration: none;">EN</a>
        </div>

    </div>
</nav>
<div class="container">

        <h2><@spring.message "resume-list.body.header"/></h2>
    <div class="sorting-panel">
        <form action="/api/resumes/all" method="get" class="sort-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input type="text" name="name"
                   placeholder="<@spring.message "item-list.search.by-title"/>"
                   value="${name!''}"
                   class="search-input">

            <label><@spring.message "item-list.sort"/></label>
            <select name="sort" onchange="this.form.submit()">
                <option value="id" ${(RequestParameters.sort?? && RequestParameters.sort == 'id')?string('selected', '')}><@spring.message "item-list.sort.default"/></option>
                <option value="salary" ${(RequestParameters.sort?? && RequestParameters.sort == 'salary')?string('selected', '')}><@spring.message "item-list.sort.by-salary"/></option>
                <option value="name" ${(RequestParameters.sort?? && RequestParameters.sort == 'name')?string('selected', '')}><@spring.message "item-list.sort.by-title"/></option>
                <option value="createdDate" ${(RequestParameters.sort?? && RequestParameters.sort == 'createdDate')?string('selected', '')}><@spring.message "item-list.sort.by-date"/></option>
            </select>
            <select name="dir" onchange="this.form.submit()">
                <option value="asc" ${(RequestParameters.dir?? && RequestParameters.dir == 'asc')?string('selected', '')}><@spring.message "item-list.sort.asc"/></option>
                <option value="desc" ${(RequestParameters.dir?? && RequestParameters.dir == 'desc')?string('selected', '')}><@spring.message "item-list.sort.des"/></option>
            </select>
        </form>
    </div>
    <div class="resume-list">
        <#list resumes as resume>
            <a href="/api/resumes/${resume.id}" class="resume-card-link">
                <div class="resume-card">
                    <div class="resume-info">
                        <h3>${resume.name! "N/A"}</h3>
                        <p><span><@spring.message "common.category"/>: </span> ${resume.category! "N/A"}</p>
                        <p><span><@spring.message "common.salary"/>: </span> ${resume.salary! 0}</p>
                        <p><@spring.message "common.status"/>:
                            <@spring.message resume.isActive?string('common.status-value.active', 'common.status-value.inactive')/> </p>
                        <p><span><@spring.message "item-list.created"/>: </span> ${resume.createdDate! "N/A"}</p>
                        <p><span><@spring.message "item-list.updated"/>: </span> ${resume.updateTime! "N/A"}</p>
                    </div>

                    <div class="experience">
                        <h3><@spring.message "common.experience"/>:</h3>
                        <#if resume.workExperience?has_content>
                            <p><span><@spring.message "common.experience.company"/>: </span> ${resume.workExperience[0].companyName! "N/A"}</p>
                            <p><span><@spring.message "common.experience.position"/>: </span> ${resume.workExperience[0].position! "N/A"}</p>
                        <#else>
                            <p><@spring.message "common.not-found"/></p>
                        </#if>
                    </div>

                    <div class="education">
                        <h3><@spring.message "common.education"/>:</h3>
                        <#if resume.education?has_content>
                            <p><span><@spring.message "common.education.institution"/>: </span> ${resume.education[0].institution! "N/A"}</p>
                            <p><span><@spring.message "common.education.degree"/>: </span> ${resume.education[0].degree! "N/A"}</p>
                        <#else>
                            <p><@spring.message "common.not-found"/></p>
                        </#if>
                    </div>

                    <div class="contacts">
                        <#if resume.contactInfo?has_content>
                            <p><span>${resume.contactInfo[0].type!"Contact"}: </span>${resume.contactInfo[0].value!"N/A"}</p>
                        <#else>
                            <p><@spring.message "resume-list.contacts.notfound"/></p>
                        </#if>
                    </div>
                </div>
            </a>
        </#list>
    </div>

    <div class="pagination">
        <#if totalPages gt 1>
            <ul class="pagination-list" style="display: flex; justify-content: center; list-style: none; gap: 10px; margin-top: 20px;">
                <#if currentPage gt 0>
                    <li>
                        <a href="?page=${currentPage - 1}&sort=${sort}&dir=${dir}&name=${name!''}">&laquo; <@spring.message "common.previous"/></a>
                    </li>
                </#if>
                <#list 0..totalPages-1 as i>
                    <li>
                        <a href="?page=${i}&sort=${sort}&dir=${dir}"
                           class="${(i == currentPage)?string('active-page', '')}">
                            ${i + 1}
                        </a>
                    </li>
                </#list>
                <#if currentPage lt totalPages - 1>
                    <li>
                        <a href="?page=${currentPage + 1}&sort=${sort}&dir=${dir}&name=${name!''}"><@spring.message "common.next"/> &raquo;</a>
                    </li>
                </#if>
            </ul>
        </#if>
    </div>
</div>
<script src="<@spring.url '/js/resume-filter.js'/>"></script>
</body>
</html>