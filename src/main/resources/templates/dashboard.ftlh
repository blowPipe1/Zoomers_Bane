<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <style>
        .profile-header {
            display: flex;
            align-items: center;
            gap: 20px;
            padding-bottom: 30px;
            border-bottom: 2px solid #f0f2f5;
            margin-bottom: 30px;
        }

        .user-avatar img {
            object-fit: cover;
            border: 3px solid #007bff;
            width: 120px;
            height: 120px;
        }

        .resume-list, .vacancy-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .resume-card, .vacancy-card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            display: flex;
            flex-direction: column;
        }

        .btn-update, .btn-edit-resume {
            display: inline-block;
            text-align: center;
            padding: 10px;
            text-decoration: none;
            cursor: pointer;
            margin-top: 5px;
        }


        body{
            font-family: 'Segoe UI', Arial, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
        }

        .container{
            background: white;
            max-width: 1100px;
            width: 100%;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            padding: 40px;
        }

        h2 {
            text-align: center;
            margin-top: 0;
            font-size: 24px;
            margin-bottom: 30px;
        }

        .profile-header a, .profile-header p{
            margin: 5px 0;
        }


        .actions{
            display: grid;
            margin-left: auto;
            margin-right: 0;
            flex-direction: row;
            gap: 4px;

        }

        .action{
            background: #fff;
            border-radius: 12px;
            padding: 7px 70px;
            display: flex;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            transition: transform 0.2s, box-shadow 0.2s;
            border: 1px solid #eef0f2;
            justify-content: center;
        }


    </style>

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
                <div class="action"> <a href="#" style="text-decoration: none;">Responses</a></div>
                <#if (user.accountType! "")?lower_case == "applicant">
                    <div class="action"> <a href="/api/resumes/form" style="text-decoration: none;">Create Resume</a></div>
                <#elseif (user.accountType! "")?lower_case == "employer">
                    <div class="action"> <a href="/api/vacancies/form" style="text-decoration: none;">Create Vacancy</a></div>
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
                        <button class="btn-update">Update</button>
                        <a>edit-vacancy</a>
                    </div>
                <#else>
                    <div class="empty-state">
                        <p>You haven't created any resumes yet.</p>
                        <a href="/api/resumes/form">Create your first resume</a>
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
                        <button class="btn-update">Update</button>
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