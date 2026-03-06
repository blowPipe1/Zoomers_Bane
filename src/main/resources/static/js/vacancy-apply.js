document.addEventListener('DOMContentLoaded', function() {
    const container = document.getElementById('applySection');
    const btn = document.getElementById('initApplyBtn');

    if (!container || !btn) return;

    btn.addEventListener('click', function() {
        const vacancyId = container.getAttribute('data-vacancy-id');
        const resumes = JSON.parse(container.getAttribute('data-resumes'));

        const csrfName = container.getAttribute('data-csrf-name');
        const csrfToken = container.getAttribute('data-csrf-token');

        const labels = {
            select: container.getAttribute('data-msg-select'),
            message: container.getAttribute('data-msg-message'),
            placeholder: container.getAttribute('data-msg-placeholder'),
            send: container.getAttribute('data-msg-send')
        };

        const optionsHtml = resumes.map(r =>
            `<option value="${r.id}">${r.name}</option>`
        ).join('');

        const formHtml = `
            <div class="respond-form" style="margin-top: 20px;">
                <form action="/api/responses/apply" method="post">
                    <input type="hidden" name="${csrfName}" value="${csrfToken}">
                    
                    <input type="hidden" name="vacancyId" value="${vacancyId}">

                    <div class="form-group">
                        <label for="resumeId">${labels.select}:</label>
                        <select name="resumeId" id="resumeId" class="form-control" required>
                            ${optionsHtml}
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="message">${labels.message}:</label>
                        <textarea name="message" id="message" class="form-control" 
                                  placeholder="${labels.placeholder}"></textarea>
                    </div>

                    <button type="submit" class="btn-submit">${labels.send}</button>
                </form>
            </div>
        `;

        container.innerHTML = formHtml;
    });
});