<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vacancies</title>
    <link rel="stylesheet" href="<@spring.url '/css/vacancy-list.css'/>">
</head>
<body>
<nav class="navbar">
    <div class="navbar-container">
        <a href="#" class="nav-brand">JobPortal</a>
        <div class="nav-menu">
            <a href="/api/users/dashboard" class="nav-link">Dashboard</a>
            <form action="/logout" method="post" class="nav-form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="nav-link nav-logout">Logout</button>
            </form>
        </div>
    </div>
</nav>

<div class="container">
    <h2>Vacancies</h2>
    <div class="sorting-panel">
        <form action="/api/vacancies/all" method="get" class="sort-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <label>Sort</label>
            <select name="sort" onchange="this.form.submit()">
                <option value="id" ${(RequestParameters.sort?? && RequestParameters.sort == 'id')?string('selected', '')}>Default</option>
                <option value="salary" ${(RequestParameters.sort?? && RequestParameters.sort == 'salary')?string('selected', '')}>By Salary</option>
                <option value="name" ${(RequestParameters.sort?? && RequestParameters.sort == 'name')?string('selected', '')}>By Title</option>
            </select>
            <select name="dir" onchange="this.form.submit()">
                <option value="asc" ${(RequestParameters.dir?? && RequestParameters.dir == 'asc')?string('selected', '')}>Asc</option>
                <option value="desc" ${(RequestParameters.dir?? && RequestParameters.dir == 'desc')?string('selected', '')}>Desc</option>
            </select>
        </form>
    </div>
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
                <div class="action"> <a href="/api/vacancies/${vacancy.id}" style="text-decoration: none;">Open</a></div>
            </div>
        </#list>
    </div>
</div>

</body>
</html>