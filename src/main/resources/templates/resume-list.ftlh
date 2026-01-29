<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumes</title>
    <link rel="stylesheet" href="<@spring.url '/css/resume-list.css'/>">
</head>
<body>
<nav class="navbar">
    <div class="navbar-container">
        <a href="#" class="nav-brand">JobPortal</a>
        <div class="nav-menu">
            <a href="/api/users/dashboard" class="nav-link">Dashboard</a>
            <a href="/logout" class="nav-link" style="color: #dc3545;">Logout</a>
        </div>
    </div>
</nav>
<div class="container">
        <h2>Resumes</h2>
    <div class="sorting-panel">
        <form action="/api/resumes/all" method="get" class="sort-form">
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
    <div class="resume-list">
        <#list resumes as resume>
            <div class="resume-card">
                <div class="resume-info" style="margin-bottom: 10px">
                    <h3>${resume.name! "N/A"}</h3>
                    <p><span>Category: </span> ${resume.category! "N/A"}</p>
                    <p><span>Salary: </span> ${resume.salary! 0}</p>
                    <p><span>Status: </span> ${resume.isActive?string('Active', 'Inactive')}</p>
                </div>

                <div class="experience"  style="margin-bottom: 10px">
                    <h3>Experience:</h3>
                    <#if resume.workExperience?has_content>
                        <p><span>Company: </span> ${resume.workExperience[0].companyName! "N/A"}</p>
                        <p><span>Position: </span> ${resume.workExperience[0].position! "N/A"} • ${resume.workExperience[0].years! 0} year(s)</p>
                    <#else>
                        <p>N/A</p>
                    </#if>
                </div>

                <div class="education"  style="margin-bottom: 10px">
                    <h3>Education:</h3>
                    <#if resume.education?has_content>
                        <p><span>Institution: </span> ${resume.education[0].institution! "N/A"}</p>
                        <p><span>Program: </span> ${resume.education[0].program! "N/A"}</p>
                        <p><span>Degree: </span> ${resume.education[0].degree! "N/A"}</p>
                        <p><span>Period: </span> ${resume.education[0].startDate! 'N/A'} to ${resume.education[0].endDate! 'N/A'}</p>
                    <#else>
                        <p>N/A</p>
                    </#if>
                </div>
                <div class="contacts"  style="margin-bottom: 10px">
                    <#if resume.contactInfo?has_content>
                        <#list resume.contactInfo as contact>
                            <p><span>${contact.type!"Contact"}: </span>${contact.value!"N/A"}</p>
                            <#if contact_index == 1><#break></#if>
                        </#list>
                    <#else>
                        <p>Contacts Not Provided</p>
                    </#if>
                    </div>
                <div class="action"> <a href="/api/resumes/${resume.id}" style="text-decoration: none;">Open</a></div>
            </div>
        </#list>

    </div>
</div>

</body>
</html>