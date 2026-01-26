<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Vacancy: ${vacancy.name}</title>
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
            max-width: 900px;
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
            display: inline-block;
            margin-top: 10px;
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
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .data-item label {
            display: block;
            font-size: 13px;
            color: #6c757d;
            text-transform: uppercase;
            margin-bottom: 5px;
        }

        .data-item span {
            font-size: 16px;
            font-weight: 500;
        }

        .description-box {
            background: #fdfdfd;
            border: 1px solid #f0f0f0;
            border-radius: 8px;
            padding: 20px;
            line-height: 1.6;
            color: #444;
            white-space: pre-line;
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

        .respond-form {
            margin-top: 30px;
            padding: 25px;
            background: #f1f4f9;
            border-radius: 12px;
            border: 1px solid #e0e6ed;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            font-size: 14px;
            font-weight: bold;
            margin-bottom: 8px;
            color: #444;
        }

        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-family: inherit;
            box-sizing: border-box; /* Важно для правильной ширины */
        }

        textarea.form-control {
            resize: vertical;
            min-height: 100px;
        }

        .btn-submit {
            display: block;
            width: 100%;
            padding: 14px;
            text-align: center;
            text-decoration: none;
            border-radius: 6px;
            border: none;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 20px;
            transition: opacity 0.3s;
            background-color: #007bff;
            color: white;
        }
    </style>
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