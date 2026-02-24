<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "edit-resume.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/edit-item.css'/>">
</head>
<body>

<div class="container">
    <h2><@spring.message "edit-resume.title"/></h2>

    <form action="/api/resumes/update/${resumeDto.id}" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <@spring.formHiddenInput "resumeDto.id" "" />
        <@spring.bind "resumeDto"/>

        <div class="form-group">
            <label><@spring.message "common.resume-title"/></label>
            <@spring.formInput "resumeDto.name" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.email"/></label>
            <@spring.formInput "resumeDto.applicantEmail" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.category"/></label>
            <@spring.formSingleSelect "resumeDto.category", categories![], "class='form-control'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.salary"/></label>
            <@spring.formInput "resumeDto.salary", "type='number' step='0.01'" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <div class="form-group">
            <label><@spring.message "common.status"/></label>
            <#assign statusOptions = {
            "true": springMacroRequestContext.getMessage("common.status-value.active"),
            "false": springMacroRequestContext.getMessage("common.status-value.inactive")
            }>
            <@spring.formSingleSelect "resumeDto.isActive", statusOptions, "" />
            <@spring.showErrors "<br/>", "error" />
        </div>

        <h3><@spring.message "common.education"/></h3>
        <#list resumeDto.education as edu>
            <div class="section-block">
                <input type="hidden"
                       name="education[${edu?index}].id"
                       value="${(edu.id)!''}">

                <div class="form-group">
                    <label><@spring.message "common.education.institution"/></label>
                    <@spring.formInput "resumeDto.education[${edu?index}].institution" />
                </div>
                <div class="form-group">
                    <label><@spring.message "common.education.program"/></label>
                    <@spring.formInput "resumeDto.education[${edu?index}].program" />
                </div>
                <div class="form-group">
                    <label><@spring.message "common.education.degree"/></label>
                    <@spring.formInput "resumeDto.education[${edu?index}].degree" />
                </div>
                <div class="form-group">
                    <label><@spring.message "common.education.start-date"/></label>
                    <@spring.formInput "resumeDto.education[${edu?index}].startDate", "type='date'" />
                    <@spring.showErrors "<br/>", "error" />
                </div>
                <div class="form-group">
                    <label><@spring.message "common.education.end-date"/></label>
                    <@spring.formInput "resumeDto.education[${edu?index}].endDate", "type='date'" />
                    <@spring.showErrors "<br/>", "error" />
                </div>
            </div>
        </#list>

        <h3><@spring.message "common.experience"/></h3>
        <#list resumeDto.workExperience as work>
            <div class="section-block">
                <input type="hidden" name="workExperience[${work?index}].id" value="${(work.id)!''}">
                <div class="form-group">
                    <label><@spring.message "common.experience.company"/></label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].companyName" />
                </div>
                <div class="form-group">
                    <label><@spring.message "common.experience.position"/></label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].position" />
                </div>
                <div class="form-group">
                    <label><@spring.message "common.years"/></label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].years", "type='number'" />
                </div>
                <div class="form-group">
                    <label><@spring.message "common.experience.responsibilities"/></label>
                    <@spring.formInput "resumeDto.workExperience[${work?index}].responsibilities" />
                </div>
            </div>
        </#list>

        <h3><@spring.message "common.contacts"/></h3>
        <#list resumeDto.contactInfo as contact>
            <div class="section-block">
                <@spring.formHiddenInput "resumeDto.contactInfo[${contact?index}].id" "" />

                <div class="form-group">
                    <label><@spring.message "common.contacts.type"/></label>
                    <@spring.formSingleSelect "resumeDto.contactInfo[${contact?index}].type", contactTypesMap, "class='form-control'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>

                <div class="form-group">
                    <label><@spring.message "common.contacts.value"/></label>
                    <@spring.formInput "resumeDto.contactInfo[${contact?index}].value" "class='form-control' placeholder='Enter value...'" />
                    <@spring.showErrors "<br>" "error-message" />
                </div>
            </div>
        </#list>

        <button type="submit" class="btn-submit"><@spring.message "common.save-btn"/></button>
    </form>

    <a href="/api/users/dashboard" class="back-link">♂ <@spring.message "edit-item.back-to-profile"/> ♂</a>
</div>

</body>
</html>