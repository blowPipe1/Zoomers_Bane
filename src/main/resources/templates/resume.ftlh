<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Resume: ${resume.name}</title>
    <link rel="stylesheet" href="<@spring.url '/css/resume.css'/>">
</head>
<body>

<div class="container">
    <div class="profile-header">
        <div class="user-info">
            <h1>${resume.name}</h1>
            <p><strong>Category:</strong> ${resume.category}</p>
            <span class="status-badge ${resume.isActive?string('status-active', 'status-inactive')}">
                ${resume.isActive?string('Active', 'Inactive')}
            </span>
        </div>
    </div>

    <div class="section">
        <div class="info-grid">
            <div class="data-item">
                <label>Applicant(Email)</label>
                <span>${resume.applicantEmail}</span>
            </div>
            <div class="data-item">
                <label>Expected Salary</label>
                <span>${resume.salary?string.currency}</span>
            </div>
        </div>
    </div>

    <#if resume.contactInfo?has_content>
        <div class="section">
            <div class="section-title">Contact Information</div>
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
            <div class="section-title">Work Experience</div>
            <#list resume.workExperience as work>
                <div class="entry-card">
                    <div class="entry-header">
                        <span>${work.position} — ${work.companyName}</span>
                        <span>Years: ${work.years}</span>
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
            <div class="section-title">Education</div>
            <#list resume.education as edu>
                <div class="entry-card">
                    <div class="entry-header">
                        <span><strong>Degree:</strong> ${edu.degree}</span>
                        <span>${edu.startDate} — ${(edu.endDate)!"Still on it"}</span>
                    </div>
                    <div><strong>Institution:</strong> ${edu.institution}</div>
                    <div><strong>Program:</strong> ${edu.program}</div>
                </div>
            </#list>
        </div>
    </#if>

    <div class="actions">
        <a href="/api/resumes/all" class="action" style="text-decoration: none; color: inherit;">
            Back to list
        </a>
    </div>
</div>

</body>
</html>