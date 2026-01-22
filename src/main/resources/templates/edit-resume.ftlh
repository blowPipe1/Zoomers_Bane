<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Edit Resume</title>
    <style>
        body{
            font-family: 'Segoe UI', Arial, sans-serif;
            max-width: 1200px; margin: 0 auto;
            padding: 40px 20px; display: flex;
            justify-content: center; background-color: #f4f7f6;
        }

        .container{
            background: white;
            max-width: 800px; width: 100%;
            border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            padding: 40px;
        }

        h2, h3 {
            text-align: center;
            color: #333;
        }

        h3 {
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
            margin-top: 30px;
            text-align: left;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 14px;
            color: #444;
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            background: #f8f9fa;
            box-sizing: border-box;
        }
        .error { color: #dc3545; font-size: 13px; margin-top: 5px; display: block; }

        .btn-submit {
            display: block;
            width: 100%;
            padding: 14px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 30px;
        }

        .btn-submit:hover {
            background-color: #0056b3;
        }

        .section-block {
            border: 1px dashed #ccc;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 8px;
        }
        .back-link {
            display: block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }

    </style>
</head>
<body>

<div class="container">
    <h2>Edit Resume</h2>

    <form action="/api/resumes/update/${resumeDto.id}" method="post">
        <@spring.bind "resumeDto"/>

        <div class="form-group">
            <label>Resume Title</label>
            <@spring.formInput "resumeDto.name" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Applicant Email</label>
            <@spring.formInput "resumeDto.applicantEmail" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Category</label>
            <@spring.formSingleSelect "resumeDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Salary</label>
            <@spring.formInput "resumeDto.salary", "type='number' step='0.01'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label>Status</label>
            <#assign statusOptions = {"true": "Active", "false": "Inactive"}>
            <@spring.formSingleSelect "resumeDto.isActive", statusOptions, "" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <h3>Education</h3>
        <#list resumeDto.education as edu>
            <div class="section-block">
                <input type="hidden"
                       name="education[${edu?index}].id"
                       value="${(edu.id)!''}">

                <div class="form-group">
                    <label>Institution</label>
                    <@spring.formInput "resumeDto.education[${edu?index}].institution" />
                </div>
                <div class="form-group">
                    <label>Program</label>
                    <@spring.formInput "resumeDto.education[${edu?index}].program" />
                </div>
                <div class="form-group">
                    <label>Degree</label>
                    <@spring.formInput "resumeDto.education[${edu?index}].degree" />
                </div>
                <div class="form-group">
                    <label>Date of Beginning</label>
                    <@spring.formInput "resumeDto.education[${edu?index}].startDate", "type='date'" />
                </div>
                <div class="form-group">
                    <label>Date of Completion</label>
                    <@spring.formInput "resumeDto.education[${edu?index}].endDate", "type='date'" />
                </div>
            </div>
        </#list>

        <h3>Work Experience</h3>
        <#list resumeDto.workExperience as work>
            <div class="section-block">
                <input type="hidden" name="workExperience[${work?index}].id" value="${(work.id)!''}">
                <div class="form-group">
                    <label>Company</label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].companyName" />
                </div>
                <div class="form-group">
                    <label>Position</label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].position" />
                </div>
                <div class="form-group">
                    <label>Years</label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].years", "type='number'" />
                </div>
                <div class="form-group">
                    <label>Responsibilities</label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].responsibilities" />
                </div>
            </div>
        </#list>

        <h3>Contacts</h3>
        <#list resumeDto.contactInfo as contact>
            <div class="section-block">
                <@spring.formHiddenInput "resumeDto.contactInfo[${contact?index}].id" "" />

                <div class="form-group">
                    <label>Type (Phone, Telegram, Email, etc)</label>
                    <@spring.formSingleSelect "resumeDto.contactInfo[${contact?index}].type", contactTypesMap, "class='form-control'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>

                <div class="form-group">
                    <label>Value</label>
                    <@spring.formInput "resumeDto.contactInfo[${contact?index}].value" "class='form-control' placeholder='Enter value...'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>
            </div>
        </#list>

        <button type="submit" class="btn-submit">Save Changes</button>
    </form>

    <a href="/api/users/dashboard" class="back-link">♂ Back to Profile ♂</a>
</div>

</body>
</html>