<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>

</head>
<body>

<div class="registration-container">
    <div class="form-card">
        <h2>Profile</h2>

        <div class="profile-header">
            <div class="avatar-placeholder"></div>
            <div class="user-avatar">
                <#if user.avatar?has_content>
                    <img src="/images/${user.avatar}" alt="Avatar" width="100">
                <#else>
                    <img src="/images/gachi.jpg" alt="No Avatar" width="100">
                </#if>
            </div>
            <div class="user-info">
                <h3>${user.name! "Not Found"} ${user.surname! "Not Found"}</h3>
                <p>Age: ${user.age! 0}</p>
                <a href="/api/users/edit" class="btn-edit" style="text-decoration: none; display: inline-block;">Edit</a>
            </div>
            <div class="actions">
                <button class="btn-create">Create Resume</button>
                <div class="notifications">
                    Responses
                </div>
            </div>
        </div>

        <#if (user.accountType! "") == "applicant">
        <h4>Resumes:</h4>
        <div class="resume-list">
            <#list itemsList![] as items>
                <div class="resume-card">
                    <h5>${items.name! "Resume's Title"}</h5>
                    <p>Category: ${items.category! ""}</p>
                    <p>Salary: ${items.salary! 0}</p>
                    <p>Status: ${items.isActive?string('Active', 'Inactive')}</p>
                    <button class="btn-update">Update</button>
                    <button class="btn-edit-resume">Edit</button>
                </div>
            </#list>
        </div>
        <#elseif (user.accountType! "") == "employer">
            <h4>Vacancies:</h4>
            <div class="vacancy-list">
                <#list itemsList![] as items>
                    <div class="vacancy-card">
                        <h5>${items.name! "Vacancy's Title"}</h5>
                        <p>Category: ${items.category! ""}</p>
                        <p>Salary: ${items.salary! 0}</p>
                        <p>Status: ${items.isActive?string('Active', 'Inactive')}</p>
                        <button class="btn-update">Update</button>
                        <button class="btn-edit-resume">Edit</button>
                    </div>
                </#list>
            </div>
        <#else>
            <p>Can's identify Account Type</p>
        </#if>
    </div>
</div>

</body>
</html>