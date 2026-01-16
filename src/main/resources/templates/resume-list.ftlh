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

    </style>
</head>
<body>

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
                <button type="submit" class="btn-view">Open</button>
            </div>
        </#list>

    </div>
</div>

</body>
</html>