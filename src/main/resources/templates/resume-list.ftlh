<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumes</title>
    <style>
        body {
            font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
            background-color: #f4f7f6;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 1100px;
            margin: 0 auto;
        }

        header {
            text-align: center;
            margin-bottom: 40px;
        }

        h1 { color: #2c3e50; margin-bottom: 10px; }
        .subtitle { color: #7f8c8d; font-size: 1.1rem; }

        .resume-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
            gap: 25px;
        }

        .card {
            background: #fff;
            border-radius: 12px;
            padding: 24px;
            display: flex;
            flex-direction: column;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            transition: transform 0.2s, box-shadow 0.2s;
            border: 1px solid #eef0f2;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 20px rgba(0,0,0,0.1);
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 15px;
        }

        .name {
            font-size: 1.25rem;
            font-weight: 700;
            color: #1a1a1a;
            margin: 0;
        }

        .category-tag {
            background: #e8f0fe;
            color: #1967d2;
            padding: 4px 10px;
            border-radius: 6px;
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
        }

        .salary {
            font-size: 1.15rem;
            color: #27ae60;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .section-title {
            font-size: 0.75rem;
            color: #95a5a6;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 8px;
            margin-top: 15px;
        }

        .info-block {
            padding-left: 12px;
            border-left: 2px solid #3498db;
            margin-bottom: 10px;
        }

        .info-main { font-size: 0.95rem; font-weight: 600; display: block; color: #2c3e50; }
        .info-sub { font-size: 0.85rem; color: #7f8c8d; }

        .card-footer {
            margin-top: auto;
            padding-top: 20px;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .btn-view {
            background-color: #2c3e50;
            color: white;
            text-align: center;
            padding: 12px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            transition: background 0.2s;
        }

        .btn-view:hover { background-color: #34495e; }

        .contacts-preview {
            display: flex;
            justify-content: center;
            gap: 10px;
            color: #95a5a6;
            font-size: 0.8rem;
        }

        .empty-state {
            text-align: center;
            grid-column: 1 / -1;
            padding: 50px;
            background: white;
            border-radius: 12px;
            color: #95a5a6;
        }
    </style>
</head>
<body>

<div class="container">
    <header>
        <h1>Resumes</h1>
        <div class="subtitle">Find yourself an employee</div>
    </header>

    <div class="resume-grid">
        <#list resumes as resume>
            <div class="card">
                <div class="card-header">
                    <h2 class="name">${resume.name!"Not Submitted"}</h2>
                    <span class="category-tag">${resume.category!"Category"}</span>
                </div>

                <div class="salary">
                    <#if resume.salary??>
                        ${resume.salary?string.currency}
                    <#else>
                        Not Submitted
                    </#if>
                </div>

                <div class="section-title">Experience</div>
                <#if resume.workExperience?has_content>
                    <div class="info-block">
                        <span class="info-main">${resume.workExperience[0].position}</span>
                        <span class="info-sub">${resume.workExperience[0].companyName} • ${resume.workExperience[0].years} г.</span>
                    </div>
                <#else>
                    <span class="info-sub">None</span>
                </#if>

                <div class="section-title">Education</div>
                <#if resume.education?has_content>
                    <div class="info-block" style="border-color: #9b59b6;">
                        <span class="info-main">${resume.education[0].degree}</span>
                        <span class="info-sub">${resume.education[0].institution}</span>
                    </div>
                <#else>
                    <span class="info-sub">Not Submitted</span>
                </#if>

                <div class="card-footer">
                    <a href="/resumes/${resume.applicant}" class="btn-view">Open</a>

                    <div class="contacts-preview">
                        <#if resume.contactInfo?has_content>
                            <#list resume.contactInfo as contact>
                                <span>${contact.type!"Contact"}</span>

                                <#if contact_index == 1><#break></#if>
                            </#list>
                        <#else>
                            <span style="color: #bdc3c7;">No contacts provided</span>
                        </#if>
                    </div>
                </div>
            </div>
        <#else>
            <div class="empty-state">
                <p>Nothing's in the Dungeon.</p>
            </div>
        </#list>
    </div>
</div>

</body>
</html>