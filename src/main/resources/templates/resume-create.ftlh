<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Resume</title>
    <style>
        body{
            font-family: 'Segoe UI', Arial, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
        }

        .container{
            background: white;
            max-width: 600px;
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
        h3 {
            margin-top: 0;
            margin-bottom: 30px;
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

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            background: #f8f9fa;
            box-sizing: border-box;
        }

        .btn-submit {
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
            margin-top: 20px;
            transition: opacity 0.3s;
        }

        hr{
            margin-bottom: 35px;
        }

        .error-message {
            color: #d9534f;
            font-size: 12px;
            margin-top: 5px;
            display: block;
        }

    </style>
</head>
<body>
<div class="container">
    <h2>Create Resume</h2>


    <form action="/api/resumes/create-resume" method="post">
        <h3>General Information</h3>
        <div class="form-group">
            <label>Resume Title</label>
            <@spring.formInput "resumeDto.name" "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Salary</label>
            <@spring.formInput "resumeDto.salary" "class='form-control' type='number' step='0.01'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label>Category</label>
            <@spring.formSingleSelect "resumeDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>
        <hr>

        <h3>Education (First Entry)</h3>
        <div class="education-entry">
            <div class="form-group">
                <label>Institution</label>
                <@spring.formInput "resumeDto.education[0].institution" "class='form-control'" />

                <label>Degree</label>
                <@spring.formInput "resumeDto.education[0].degree" "class='form-control'" />

                <label>Program</label>
                <@spring.formInput "resumeDto.education[0].program" "class='form-control'" />

                <label>Start Date</label>
                <@spring.formInput "resumeDto.education[0].startDate", "class='form-control'", "date" />

                <label>End Date</label>
                <@spring.formInput "resumeDto.education[0].endDate", "class='form-control'", "date" />
            </div>
        </div>
        <hr>

        <h3>Work Experience (First Entry)</h3>
        <div class="work-entry">
            <div class="form-group">
                <label>Company</label>
                <@spring.formInput "resumeDto.workExperience[0].companyName" "class='form-control'" />

                <label>Years</label>
                <@spring.formInput "resumeDto.workExperience[0].years" "class='form-control' type='number'" />

                <label>Position</label>
                <@spring.formInput "resumeDto.workExperience[0].position" "class='form-control'" />

                <label>Responsibilities</label>
                <@spring.formInput "resumeDto.workExperience[0].responsibilities" "class='form-control'" />
            </div>
        </div>
        <hr>

        <h3>Contact Info</h3>
        <#list resumeDto.contactInfo as contact>
            <div class="contact-entry">
                <div class="form-group">
                    <label>Contact Type</label>
                    <@spring.formSingleSelect "resumeDto.contactInfo[${contact_index}].type", contactTypesMap, "class='form-control'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>

                <div class="form-group">
                    <label>Contact Details</label>
                    <@spring.formInput "resumeDto.contactInfo[${contact_index}].value" "class='form-control' placeholder='Enter value...'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>

                <@spring.formHiddenInput "resumeDto.contactInfo[${contact_index}].resume" "Main Contact" />
            </div>
        </#list>
        <hr>

        <@spring.formHiddenInput "resumeDto.isActive" "true" />

        <button type="submit" class="btn-submit">Save Resume</button>
    </form>
</div>
</body>
</html>