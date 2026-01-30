<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Vacancy: ${vacancy.name}</title>
    <link rel="stylesheet" href="<@spring.url '/css/vacancy.css'/>">
</head>
<body>

<div class="container">
    <div class="profile-header">
        <div class="user-info">
            <h1>${vacancy.name}</h1>
            <p><strong>Company(Owner):</strong> ${vacancy.author}</p>
            <span class="status-badge ${vacancy.isActive?string('status-active', 'status-inactive')}">
                ${vacancy.isActive?string('Active', 'Inactive')}
            </span>
        </div>
    </div>

    <div class="section">
        <div class="info-grid">
            <div class="data-item">
                <label>Category</label>
                <span>${vacancy.category}</span>
            </div>
            <div class="data-item">
                <label>Salary</label>
                <span>${vacancy.salary?string.currency}</span>
            </div>
            <div class="data-item">
                <label>Required Experience(Years)</label>
                <span>from ${vacancy.expFrom!"N/A"} to ${vacancy.expTo!"N/A"}</span>
            </div>
        </div>
    </div>

    <div class="section">
        <div class="section-title">Description</div>
        <div class="description-box">
            ${vacancy.description}
        </div>
    </div>

    <div class="actions">
        <a href="/api/vacancies/all" class="action">
            Back to list
        </a>
    </div>

    <#if vacancy.isActive>
        <div class="section" style="margin-top: 40px;">
            <div class="section-title">Apply for this position</div>

            <#if userResumes?? && (userResumes?size > 0)>
                <div class="respond-form">
                    <form action="/api/responses/apply" method="post">
                        <input type="hidden" name="vacancyId" value="${vacancy.id}">

                        <div class="form-group">
                            <label for="resumeId">Select your resume:</label>
                            <select name="resumeId" id="resumeId" class="form-control" required>
                                <#list userResumes as resume>
                                    <option value="${resume.id}">${resume.id} ${resume.name}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="message">Cover letter (optional):</label>
                            <textarea name="message" id="message" class="form-control"
                                      placeholder="Tell the employer why you are a good fit..."></textarea>
                        </div>

                        <button type="submit" class="btn-submit">Send Application</button>
                    </form>
                </div>
            <#else>
                <div class="description-box" style="background: #fff3cd; border-color: #ffeeba;">
                    You need to <a href="api/resumes/form">create a resume</a> before you can apply.
                </div>
            </#if>
        </div>
    </#if>
</div>

</body>
</html>