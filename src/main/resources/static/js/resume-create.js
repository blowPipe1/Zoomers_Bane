document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('resumeForm');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        try {
            const formData = new FormData(form);

            const resumeData = {
                name: formData.get('name') || "N/A",
                applicantEmail: "zaglushka",
                salary: parseFloat(formData.get('salary')) || 0,
                category: formData.get('category') || "Default",
                isActive: true,
                education: [],
                workExperience: [],
                contactInfo: []
            };

            document.querySelectorAll('.education-entry').forEach(el => {
                resumeData.education.push({
                    institution: el.querySelector('.inst')?.value || "N/A",
                    degree:      el.querySelector('.degree')?.value || "N/A",
                    program:     el.querySelector('.program')?.value || "N/A",
                    startDate:   el.querySelector('.sDate')?.value || null,
                    endDate:     el.querySelector('.eDate')?.value || null
                });
            });

            document.querySelectorAll('.work-entry').forEach(el => {
                resumeData.workExperience.push({
                    companyName:      el.querySelector('.company')?.value || "N/A",
                    years:            parseInt(el.querySelector('.years')?.value) || 0,
                    position:         el.querySelector('.pos')?.value || "N/A",
                    responsibilities: el.querySelector('.resp')?.value || "N/A"
                });
            });

            document.querySelectorAll('.contact-entry').forEach(el => {
                resumeData.contactInfo.push({
                    type:  el.querySelector('.cType')?.value,
                    value: el.querySelector('.cValue')?.value,
                    resume: "Main Contact"
                });
            });


            const csrfToken = document.querySelector('input[name="_csrf"]')?.value;

            const response = await fetch('/api/resumes/create-resume', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                },
                body: JSON.stringify(resumeData)
            });

            if (response.ok) {
                const data = await response.json();
                window.location.href = data.redirectUrl;
            } else {
                const errors = await response.json();
                console.error("error:", errors);
            }

        } catch (err) {
            console.error("script erroe:", err);
        }
    });
});

function addEducation() {
    const html = `
        <div class="education-entry border-bottom mb-4 pb-3">
            <div class="form-group">
                <label>${i18n.institution}</label>
                <input type="text" class="inst">
            </div>
            <div class="form-group">
                <label>${i18n.degree}</label>
                <input type="text" class="degree">
            </div>
            <div class="form-group">
                <label>${i18n.program}</label>
                <input type="text" class="program">
            </div>
            <div class="form-group">
                <label>${i18n.startDate}</label>
                <input type="date" class="sDate">
            </div>
            <div class="form-group">
                <label>${i18n.endDate}</label>
                <input type="date" class="eDate">
            </div>
            <button type="button" class="btn-remove" onclick="this.parentElement.remove()">${i18n.deleteBtn}</button>
        </div>`;
    document.getElementById('education-container').insertAdjacentHTML('beforeend', html);
}

function addExperience() {
    const html = `
        <div class="work-entry border-bottom mb-4 pb-3">
            <div class="form-group">
                <label>${i18n.company}</label>
                <input type="text" class="company">
            </div>
            <div class="form-group">
                <label>${i18n.experience}</label>
                <input type="number" class="years">
            </div>
            <div class="form-group">
                <label>${i18n.position}</label>
                <input type="text" class="pos">
            </div>
            <div class="form-group">
                <label>${i18n.responsibilities}</label>
                <input type="text" class="resp">
            </div>
            <button type="button" class="btn-remove" onclick="this.parentElement.remove()">${i18n.deleteBtn}</button>
        </div>`;
    document.getElementById('experience-container').insertAdjacentHTML('beforeend', html);
}

function addContact() {
    const optionsHtml = contactTypes.map(type =>
        `<option value="${type.value}">${type.label}</option>`
    ).join('');

    const html = `
        <div class="contact-entry border-bottom mb-4 pb-3">
            <div class="form-group">
                <label>${i18n.type}</label>
                <select class="cType">
                    ${optionsHtml}
                </select>
            </div>
            <div class="form-group">
                <label>${i18n.value}</label>
                <input type="text" class="cValue">
            </div>
            <button type="button" class="btn-remove" onclick="this.parentElement.remove()">${i18n.deleteBtn}</button>
        </div>`;
    document.getElementById('contacts-container').insertAdjacentHTML('beforeend', html);
}