<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "resume.header.title"/>: ${resume.name}</title>
    <link rel="stylesheet" href="<@spring.url '/css/resume.css'/>">
</head>
<body>

<div class="container">
    <div class="profile-header">
        <div class="user-info">
            <h1>${resume.name}</h1>
            <p><strong><@spring.message "common.category"/>: </strong> ${resume.category}</p>
            <p><@spring.message "common.status"/>:
                <@spring.message resume.isActive?string('common.status-value.active', 'common.status-value.inactive')/> </p>
        </div>
    </div>

    <div class="section">
        <div class="info-grid">
            <div class="data-item">
                <label><@spring.message "resume.applicant"/></label>
                <span>${resume.applicantEmail}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "resume.salary"/></label>
                <span>${resume.salary}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "item-list.created"/></label>
                <span>${resume.formattedCreatedDate}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "item-list.updated"/></label>
                <span>${resume.formattedUpdateTime}</span>
            </div>
        </div>
    </div>

    <#if resume.contactInfo?has_content>
        <div class="section">
            <div class="section-title"><@spring.message "resume.contacts"/></div>
            <div class="contact-list">
                <#list resume.contactInfo as contact>
                    <div class="contact-tag">
                        <strong>${contact.type}:</strong> ${contact.value}
                    </div>
                </#list>
            </div>
        </div>
    </#if>

    <#if resume.workExperience?has_content>
        <div class="section">
            <div class="section-title"><@spring.message "common.experience"/></div>
            <#list resume.workExperience as work>
                <div class="entry-card">
                    <div class="entry-header">
                        <span>${work.position} — ${work.companyName}</span>
                        <span>${work.years} (<@spring.message "common.years"/>)</span>
                    </div>
                    <div style="color: #555; white-space: pre-line;">
                        ${work.responsibilities}
                    </div>
                </div>
            </#list>
        </div>
    </#if>

    <#if resume.education?has_content>
        <div class="section">
            <div class="section-title"><@spring.message "common.education"/></div>
            <#list resume.education as edu>
                <div class="entry-card">
                    <div class="entry-header">
                        <span><strong><@spring.message "common.education.degree"/>: </strong> ${edu.degree}</span>
                        <span>${edu.formattedStartDate} — ${(edu.formattedEndDate)!"Still on it"}</span>
                    </div>
                    <div><strong><@spring.message "common.education.institution"/>: </strong> ${edu.institution}</div>
                    <div><strong><@spring.message "common.education.program"/>: </strong> ${edu.program}</div>
                </div>
            </#list>
        </div>
    </#if>

    <div class="actions">
        <a href="/api/resumes/all" class="action" style="text-decoration: none; color: inherit;">
            <@spring.message "common.back-to-list"/>
        </a>
    </div>
</div>

</body>
</html>