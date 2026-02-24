<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "resume-create.title"/></title>
    <link rel="stylesheet" href="<@spring.url '/css/item-create.css'/>">
</head>
<body>

<script>
    const contactTypes = [
        <#list contactTypesMap?keys as key>
        { value: "${key}", label: "${contactTypesMap[key]}" }<#if key_has_next>,</#if>
        </#list>
    ];
    const i18n = {
        institution: "${springMacroRequestContext.getMessage('common.education.institution')?js_string}",
        degree:      "${springMacroRequestContext.getMessage('common.education.degree')?js_string}",
        program:     "${springMacroRequestContext.getMessage('common.education.program')?js_string}",
        startDate:   "${springMacroRequestContext.getMessage('common.education.start-date')?js_string}",
        endDate:     "${springMacroRequestContext.getMessage('common.education.end-date')?js_string}",
        deleteBtn:   "${springMacroRequestContext.getMessage('common.delete-btn')?js_string}",
        company:     "${springMacroRequestContext.getMessage('common.experience.company')?js_string}",
        experience:  "${springMacroRequestContext.getMessage('common.experience')?js_string}",
        years:  "${springMacroRequestContext.getMessage('common.years')?js_string}",
        position:  "${springMacroRequestContext.getMessage('common.experience.position')?js_string}",
        responsibilities:  "${springMacroRequestContext.getMessage('common.experience.responsibilities')?js_string}",
        type:  "${springMacroRequestContext.getMessage('common.contacts.type')?js_string}",
        value:  "${springMacroRequestContext.getMessage('common.contacts.value')?js_string}"
    };
</script>

<div class="container">
    <h2><@spring.message "resume-create.title"/></h2>
    <form id="resumeForm" action="/api/resumes/create-resume" method="post">
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
        <div id="education-container">
        </div>
        <button type="button" onclick="addEducation()"> + <@spring.message "common.add-btn"/></button>
        <hr>

        <h3><@spring.message "common.experience"/></h3>
        <div id="experience-container">
        </div>
        <button type="button" onclick="addExperience()"> + <@spring.message "common.add-btn"/></button>
        <hr>

        <h3><@spring.message "common.contacts"/></h3>
        <div id="contacts-container">
        </div>
        <button type="button" class="btn-add" onclick="addContact()"> + <@spring.message "common.add-btn"/></button>
        <hr>

        <@spring.formHiddenInput "resumeDto.isActive" "true" />

        <button type="submit" class="btn-submit"><@spring.message "common.save-btn"/></button>
    </form>
</div>
<script src="<@spring.url '/js/resume-create.js'/>"></script>
</body>
</html>