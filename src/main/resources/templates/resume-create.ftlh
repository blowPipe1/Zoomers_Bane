<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Resume</title>
    <link rel="stylesheet" href="<@spring.url '/css/item-create.css'/>">
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