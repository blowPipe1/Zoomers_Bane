<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Resume: ${resume.name}</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
            background-color: #f8f9fa;
        }

        .container {
            background: white;
            max-width: 800px;
            width: 100%;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border: 1px solid #e0e0e0;
            padding: 40px;
            margin: 0 auto;
        }

        .profile-header {
            display: flex;
            align-items: center;
            gap: 20px;
            padding-bottom: 30px;
            border-bottom: 2px solid #f0f2f5;
            margin-bottom: 30px;
        }

        .user-info h1 {
            margin: 0;
            font-size: 28px;
            color: #333;
        }

        .status-badge {
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: bold;
        }

        .status-active { background: #d4edda; color: #155724; }
        .status-inactive { background: #f8d7da; color: #721c24; }

        .section {
            margin-bottom: 30px;
        }

        .section-title {
            font-size: 20px;
            font-weight: bold;
            border-bottom: 1px solid #e0e0e0;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .data-item label {
            display: block;
            font-size: 13px;
            color: #6c757d;
            text-transform: uppercase;
        }

        .data-item span {
            font-size: 16px;
            font-weight: 500;
        }

        .entry-card {
            background: #fdfdfd;
            border: 1px solid #f0f0f0;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
        }

        .entry-header {
            display: flex;
            justify-content: space-between;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .contact-tag {
            display: inline-block;
            background: #eef0f2;
            padding: 5px 15px;
            border-radius: 5px;
            margin-right: 10px;
            margin-bottom: 10px;
        }
        .actions {
            display: grid;
            margin-left: auto;
            margin-right: 0;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 10px;
            margin-top: 30px;
        }
        .action {
            background: #fff;
            border-radius: 12px;
            padding: 10px 20px;
            display: flex;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            transition: transform 0.2s, box-shadow 0.2s;
            border: 1px solid #eef0f2;
            justify-content: center;
            text-decoration: none;
            color: #333;
            font-weight: 500;
        }

        .action:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0,0,0,0.1);
        }
    </style>
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