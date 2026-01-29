<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="<@spring.url '/css/dashboard.css'/>">
</head>
<body>

    <div class="container">
        <h2>Profile</h2>

        <div class="profile-header">
            <div class="user-avatar">
                <label for="avatarInput" style="cursor: pointer;" title="Change Image">
                    <#if user.avatar?has_content>
                        <img src="/images/${user.avatar}" alt="Avatar" id="avatarPreview">
                    <#else>
                        <img src="/images/gachi.jpg" alt="No Avatar" id="avatarPreview">
                    </#if>
                </label>
                <form id="avatarForm" action="/api/users/avatar" method="post" enctype="multipart/form-data">
                    <div style="margin-top: 10px; display: flex; flex-direction: column; width: 120px">
                        <input type="file" id="avatarInput" name="file" accept="image/*">
                        <button type="submit" class="btn-update">Update</button>
                    </div>
                </form>
            </div>

            <div class="user-info">
                <h3>${user.name! "Not Found"} ${user.surname! "Not Found"}</h3>
                <h3 style="font-weight: normal">Age: ${user.age! 0} years</h3>
            </div>

            <div class="actions">
                <div class="action"> <a href="/api/responses/" style="text-decoration: none;">Responses</a></div>
                <#if (user.accountType! "")?lower_case == "applicant">
                    <div class="action"> <a href="/api/resumes/form" style="text-decoration: none;">Create Resume</a></div>
                    <div class="action"> <a href="/api/vacancies/all" style="text-decoration: none;">Vacancies</a></div>
                <#elseif (user.accountType! "")?lower_case == "employer">
                    <div class="action"> <a href="/api/vacancies/form" style="text-decoration: none;">Create Vacancy</a></div>
                    <div class="action"> <a href="/api/resumes/all" style="text-decoration: none;">Resumes</a></div>
                <#else>
                    <p>Cannot identify Account Type</p>
                </#if>
                <div class="action"> <a href="/api/users/edit" style="text-decoration: none;">Edit</a></div>
            </div>
        </div>

        <#if (user.accountType! "")?lower_case == "applicant">
            <h2>Resumes:</h2>
            <div class="resume-list">
                <#list itemsList![] as items>
                    <div class="resume-card">
                        <h3>${items.name! "Resume's Title"}</h3>
                        <p>Category: ${items.category! ""}</p>
                        <p>Salary: ${items.salary! 0}</p>
                        <p>Status: ${items.isActive?string('Active', 'Inactive')}</p>
                        <div class="action"> <a href="/api/resumes/edit/${items.id}" style="text-decoration: none;">Edit</a></div>

                    </div>
                <#else>
                    <div class="empty-state">
                        <p>You haven't created any resumes yet.</p>
                    </div>
                </#list>
            </div>
        <#elseif (user.accountType! "")?lower_case == "employer">
            <h2>Vacancies:</h2>
            <div class="vacancy-list">
                <#list itemsList![] as items>
                    <div class="vacancy-card">
                        <h3>${items.name! "Vacancy's Title"}</h3>
                        <p>Category: ${items.category! ""}</p>
                        <p>Salary: ${items.salary! 0}</p>
                        <p>Status: ${items.isActive?string('Active', 'Inactive')}</p>
                        <p>Experience: ${items.expFrom! 0} to ${items.expTo! 0} years</p>
                        <div class="action"> <a href="/api/vacancies/edit/${items.id}" style="text-decoration: none;">Edit</a></div>
                    </div>
                <#else>
                    <div class="empty-state">
                        <p>You have no active vacancies.</p>
                        <a href="/api/vacancies/form">Post a new vacancy</a>
                    </div>
                </#list>
            </div>
        <#else>
            <p>Cannot identify Account Type</p>
        </#if>
    </div>

</body>
</html>