<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <style>
        .registration-container {
            max-width: 1200px;
            margin: 0 auto;
            font-family: 'Segoe UI', Arial, sans-serif;
        }

        .form-card {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }


        .profile-header {
            display: flex;
            align-items: center;
            gap: 20px;
            padding-bottom: 30px;
            border-bottom: 2px solid #f0f2f5;
            margin-bottom: 30px;
        }

        .user-avatar img {
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #007bff;
        }

        .btn-edit {
            background: #f0f2f5;
            color: #222;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
            margin-top: 10px;
            transition: background 0.3s;
        }

        .btn-edit:hover {
            background: #e0e0e0;
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

        .resume-card h5, .vacancy-card h5 {
            font-size: 18px;
            margin: 0 0 10px 0;
            color: #222;
        }

        .resume-card p:nth-of-type(1), .vacancy-card p:nth-of-type(1) {
            color: #007bff;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
            margin-bottom: 8px;
            order: -1;
        }

        .resume-card p:nth-of-type(2), .vacancy-card p:nth-of-type(2) {
            font-size: 20px;
            color: #28a745;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .resume-card p:nth-of-type(3), .vacancy-card p:nth-of-type(3) {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 8px;
            font-size: 14px;
            margin-bottom: 15px;
            color: #666;
        }

        .btn-update, .btn-edit-resume, .btn-create {
            display: inline-block;
            text-align: center;
            background: #222;
            color: white;
            padding: 10px;
            text-decoration: none;
            border-radius: 6px;
            border: none;
            cursor: pointer;
            margin-top: 5px;
            font-size: 14px;
            transition: opacity 0.3s;
        }

        .btn-edit-resume {
            background: #6c757d;
        }

        .btn-update:hover, .btn-edit-resume:hover {
            opacity: 0.8;
        }

        .btn-create {
            background: #007bff;
            margin-bottom: 10px;
        }
    </style>

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