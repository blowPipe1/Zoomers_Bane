<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message "common.vacancies"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/vacancy-list.css'/>">
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

    <h2><@spring.message "vacancy-list.body.header"/></h2>
    <div class="sorting-panel">
        <form action="/api/vacancies/all" method="get" class="sort-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <input type="text" name="name"
                   placeholder="<@spring.message "item-list.search.by-title"/>"
                   value="${RequestParameters.name!''}"
                   class="search-input">

            <div class="search-group">
                <label for="category"><@spring.message "item-list.search.by-category"/></label>
                <select name="category" id="category" class="form-control search-input" onchange="this.form.submit()">
                    <option value=""><@spring.message "item-list.sort.default"/></option>
                    <#list categories?keys as key>
                        <option value="${key}" ${(category?? && category == key)?string('selected', '')}>
                            ${categories[key]}
                        </option>
                    </#list>
                </select>
            </div>

            <label><@spring.message "item-list.sort"/></label>
            <select name="sort" onchange="this.form.submit()">
                <option value="id" ${(RequestParameters.sort?? && RequestParameters.sort == 'id')?string('selected', '')}><@spring.message "item-list.sort.default"/></option>
                <option value="salary" ${(RequestParameters.sort?? && RequestParameters.sort == 'salary')?string('selected', '')}><@spring.message "item-list.sort.by-salary"/></option>
                <option value="name" ${(RequestParameters.sort?? && RequestParameters.sort == 'name')?string('selected', '')}><@spring.message "item-list.sort.by-title"/></option>
                <option value="createdDate" ${(RequestParameters.sort?? && RequestParameters.sort == 'createdDate')?string('selected', '')}><@spring.message "item-list.sort.by-date"/></option>
                <option value="updateTime" ${(RequestParameters.sort?? && RequestParameters.sort == 'updateTime')?string('selected', '')}><@spring.message "item-list.sort.by-update-time"/></option>
                <option value="applications" ${(RequestParameters.sort?? && RequestParameters.sort == 'applications')?string('selected', '')}><@spring.message "item-list.sort.by-applications"/></option>
            </select>
            <select name="dir" onchange="this.form.submit()">
                <option value="asc" ${(RequestParameters.dir?? && RequestParameters.dir == 'asc')?string('selected', '')}><@spring.message "item-list.sort.asc"/></option>
                <option value="desc" ${(RequestParameters.dir?? && RequestParameters.dir == 'desc')?string('selected', '')}><@spring.message "item-list.sort.des"/></option>
            </select>
        </form>
    </div>
    <div class="vacancy-list">
        <#list vacancies as vacancy>
            <a href="/api/vacancies/${vacancy.id}" class="vacancy-card-wrapper">
                <div class="vacancy-card">
                    <div class="vacancy-info">
                        <h3>${vacancy.name! "N/A"}</h3>
                        <p><span><@spring.message "common.category"/>: </span> ${vacancy.category! "N/A"}</p>
                        <p><span><@spring.message "common.salary"/>: </span> ${vacancy.salary! 0}</p>
                        <p><span><@spring.message "vacancy-list.owner"/>: </span> ${vacancy.author! "N/A"}</p>
                        <p><span><@spring.message "item-list.created"/>: </span> ${vacancy.formattedCreatedDate}</p>
                        <p><span><@spring.message "item-list.updated"/>: </span> ${vacancy.formattedUpdateTime}</p>
                        <p><span><@spring.message "common.applications"/>: </span> ${vacancy.applications! 0}</p>
                    </div>
                    <div class="meta-info">
                        <p><span><@spring.message "common.experience"/>: </span> ${vacancy.expFrom! 0} - ${vacancy.expTo! 0}</p>
                        <p><@spring.message "common.status"/>:
                            <@spring.message vacancy.isActive?string('common.status-value.active', 'common.status-value.inactive')/> </p>
                    </div>
                </div>
            </a>
            <#else>
                <p style="text-align: center"><@spring.message "vacancy-list.content.notfound"/></p>
        </#list>
    </div>
    <div class="pagination">
        <#if totalPages gt 1>
            <ul class="pagination-list" style="display: flex; justify-content: center; list-style: none; gap: 10px; margin-top: 20px;">
                <#if currentPage gt 0>
                    <li>
                        <a href="?page=${currentPage - 1}&sort=${sort}&dir=${dir}&name=${name!''}&category=${category!''}">&laquo; <@spring.message "common.previous"/></a>
                    </li>
                </#if>
                <#list 0..totalPages-1 as i>
                    <li>
                        <a href="?page=${i}&sort=${sort}&dir=${dir}&name=${name!''}&category=${category!''}"
                           style="${(i == currentPage)?string('font-weight: bold; text-decoration: underline;', '')}">
                            ${i + 1}
                        </a>
                    </li>
                </#list>
                <#if currentPage lt totalPages - 1>
                    <li>
                        <a href="?page=${currentPage + 1}&sort=${sort}&dir=${dir}&name=${name!''}&category=${category!''}"><@spring.message "common.next"/> &raquo;</a>
                    </li>
                </#if>
            </ul>
        </#if>
    </div>
</div>
<script src="<@spring.url '/js/vacancy-filter.js'/>"></script>

</body>
</html>