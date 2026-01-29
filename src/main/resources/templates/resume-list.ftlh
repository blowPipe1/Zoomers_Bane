<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumes</title>
    <style>
        body {
            font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 1100px;
            margin: 0 auto;
        }

        h2 {
            text-align: center;
            margin-top: 0;
            font-size: 24px;
            margin-bottom: 30px;
        }

        .resume-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .resume-card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            display: flex;
            flex-direction: column;
        }

        .resume-card h3{
            margin-bottom: 2px;
            margin-top: 2px;
        }
        .resume-card p{
            margin-bottom: 2px;
            margin-top: 2px;
        }

        .resume-card span{
            font-weight: lighter;
        }

        .resume-info, .experience, .education, .contacts{
            gap: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f2f5;
            margin-bottom: 30px;
        }

        .experience, .education, .contacts{
            margin-top: auto;
        }

        .btn-view{
            display: block;
            width: 100%;
            padding: 14px;
            text-align: center;
            text-decoration: none;
            border-radius: 6px;
            border: none;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: auto;
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

        .navbar {
            background-color: #ffffff;
            padding: 15px 0;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            position: sticky;
            top: 0;
            z-index: 1000;
            margin-bottom: 30px;
        }

        .navbar-container {
            max-width: 100%;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
        }

        .nav-link {
            text-decoration: none;
            color: #333;
            font-weight: 600;
            font-size: 16px;
            padding: 8px 16px;
            border-radius: 6px;
            transition: background 0.3s;
        }

        .nav-link:hover {
            background-color: #f0f2f5;
            color: #007bff;
        }

        .nav-brand {
            font-size: 20px;
            font-weight: bold;
            color: #007bff;
            text-decoration: none;
        }
    </style>
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