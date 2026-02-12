<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "resume-create.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/item-create.css'/>">
</head>
<body>
<div class="container">
    <h2><@spring.message "resume-create.title"/></h2>
    <form action="/api/resumes/create-resume" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <h3><@spring.message "resume-create.body.header"/></h3>
        <div class="form-group">
            <label><@spring.message "common.resume-title"/></label>
            <@spring.formInput "resumeDto.name" "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.salary"/></label>
            <@spring.formInput "resumeDto.salary" "class='form-control' type='number' step='0.01'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.category"/></label>
            <@spring.formSingleSelect "resumeDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br>" "error-message" />
        </div>
        <hr>

        <h3><@spring.message "common.education"/></h3>
        <div class="education-entry">
            <div class="form-group">
                <label><@spring.message "common.education.institution"/></label>
                <@spring.formInput "resumeDto.education[0].institution" "class='form-control'" />

                <label><@spring.message "common.education.degree"/></label>
                <@spring.formInput "resumeDto.education[0].degree" "class='form-control'" />

                <label><@spring.message "common.education.program"/></label>
                <@spring.formInput "resumeDto.education[0].program" "class='form-control'" />

                <label><@spring.message "common.education.start-date"/></label>
                <@spring.formInput "resumeDto.education[0].startDate", "class='form-control'", "date" />

                <label><@spring.message "common.education.end-date"/></label>
                <@spring.formInput "resumeDto.education[0].endDate", "class='form-control'", "date" />
            </div>
        </div>
        <hr>

        <h3><@spring.message "common.experience"/></h3>
        <div class="work-entry">
            <div class="form-group">
                <label><@spring.message "common.experience.company"/></label>
                <@spring.formInput "resumeDto.workExperience[0].companyName" "class='form-control'" />

                <label><@spring.message "common.years"/></label>
                <@spring.formInput "resumeDto.workExperience[0].years" "class='form-control' type='number'" />

                <label><@spring.message "common.experience.position"/></label>
                <@spring.formInput "resumeDto.workExperience[0].position" "class='form-control'" />

                <label><@spring.message "common.experience.responsibilities"/></label>
                <@spring.formInput "resumeDto.workExperience[0].responsibilities" "class='form-control'" />
            </div>
        </div>
        <hr>

        <h3><@spring.message "common.contacts"/></h3>
        <#list resumeDto.contactInfo as contact>
            <div class="contact-entry">
                <div class="form-group">
                    <label><@spring.message "common.contacts.type"/></label>
                    <@spring.formSingleSelect "resumeDto.contactInfo[${contact_index}].type", contactTypesMap, "class='form-control'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>

                <div class="form-group">
                    <label><@spring.message "common.contacts.value"/></label>
                    <@spring.formInput "resumeDto.contactInfo[${contact_index}].value" "class='form-control'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>

                <@spring.formHiddenInput "resumeDto.contactInfo[${contact_index}].resume" "Main Contact" />
            </div>
        </#list>
        <hr>

        <@spring.formHiddenInput "resumeDto.isActive" "true" />

        <button type="submit" class="btn-submit"><@spring.message "common.save-btn"/></button>
    </form>
</div>
</body>
</html>