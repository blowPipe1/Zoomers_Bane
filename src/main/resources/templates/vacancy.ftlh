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
            <span><@spring.message "vacancy.owner"/>:
                <a href="/api/users/${creatorId}" style="color: blue; text-decoration: none;">${vacancy.author}</a>
            </span>
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
                <span>from ${vacancy.expFrom! 0} to ${vacancy.expTo! 0}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "item-list.created"/></label>
                <span>${vacancy.formattedCreatedDate}</span>
            </div>
            <div class="data-item">
                <label><@spring.message "item-list.updated"/></label>
                <span>${vacancy.formattedUpdateTime}</span>
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
                <div id="applySection"
                     data-vacancy-id="${vacancy.id}"
                     data-resumes='[<#list userResumes as r>{"id":${r.id}, "name":"${r.name}"}<#if r_has_next>,</#if></#list>]'
                     data-csrf-name="${_csrf.parameterName}"
                     data-csrf-token="${_csrf.token}"
                     data-msg-select="<@spring.message 'vacancy.apply.select-resume'/>"
                     data-msg-message="<@spring.message 'vacancy.apply.message'/>"
                     data-msg-placeholder="<@spring.message 'vacancy.apply.message.placeholder'/>"
                     data-msg-send="<@spring.message 'common.send-btn'/>">

                    <button id="initApplyBtn" class="btn-submit">
                        <@spring.message "vacancy.apply"/>
                    </button>
                </div>
            <#else>
                <a href="/api/resumes/form">
                    <div class="description-box" style="background: #fff3cd; border-color: #ffeeba;">
                        <@spring.message "vacancy.apply.no-resumes"/>
                    </div>
                </a>
            </#if>
        </div>
    </#if>
</div>

<script src="<@spring.url '/js/vacancy-apply.js'/>"></script>

</body>
</html>