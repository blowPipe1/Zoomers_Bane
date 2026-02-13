<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><@spring.message "vacancy.header.title"/>: ${vacancy.name}</title>
    <link rel="stylesheet" href="<@spring.url '/css/vacancy.css'/>">
</head>
<body>

<div class="container">
    <div class="profile-header">
        <div class="user-info">
            <h1>${vacancy.name}</h1>
            <p><strong><@spring.message "vacancy.owner"/>: </strong> ${vacancy.author}</p>
            <p><@spring.message "common.status"/>:
                <@spring.message vacancy.isActive?string('common.status-value.active', 'common.status-value.inactive')/> </p>
        </div>
    </div>

    <div class="section">
        <div class="info-grid">
            <div class="data-item">
                <label><@spring.message "common.category"/></label>
                <span>${vacancy.category}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "vacancy.salary"/></label>
                <span>${vacancy.salary}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "vacancy.experience"/>(<@spring.message "common.years"/>)</label>
                <span>from ${vacancy.expFrom!"N/A"} to ${vacancy.expTo!"N/A"}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "item-list.created"/></label>
                <span>${vacancy.createdDate!'N/A'}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "item-list.updated"/></label>
                <span>${vacancy.updateTime!'N/A'}</span>
            </div>
        </div>
    </div>

    <div class="section">
        <div class="section-title"><@spring.message "common.description"/></div>
        <div class="description-box">
            ${vacancy.description}
        </div>
    </div>

    <div class="actions">
        <a href="/api/vacancies/all" class="action">
            <@spring.message "common.back-to-list"/>
        </a>
    </div>

    <#if vacancy.isActive>
        <div class="section" style="margin-top: 40px;">
            <div class="section-title"><@spring.message "vacancy.apply"/></div>

            <#if userResumes?? && (userResumes?size > 0)>
                <div class="respond-form">
                    <form action="/api/responses/apply" method="post">
                        <input type="hidden" name="vacancyId" value="${vacancy.id}">

                        <div class="form-group">
                            <label for="resumeId"><@spring.message "vacancy.apply.select-resume"/>:</label>
                            <select name="resumeId" id="resumeId" class="form-control" required>
                                <#list userResumes as resume>
                                    <option value="${resume.id}">${resume.name}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="message"><@spring.message "vacancy.apply.message"/>:</label>
                            <textarea name="message" id="message" class="form-control"
                                      placeholder="<@spring.message "vacancy.apply.message.placeholder"/>"></textarea>
                        </div>

                        <button type="submit" class="btn-submit"><@spring.message "common.send-btn"/></button>
                    </form>
                </div>
            <#else>
            <a href="api/resumes/form">
                <div class="description-box" style="background: #fff3cd; border-color: #ffeeba;">
                    <@spring.message "vacancy.apply.no-resumes"/>
                </div>
            </a>

            </#if>
        </div>
    </#if>
</div>

</body>
</html>