document.addEventListener("DOMContentLoaded", function () {
    const STORAGE_KEY = 'resume_filter_settings';
    const sortForm = document.querySelector('.sort-form');
    const nameInput = document.querySelector('input[name="name"]');
    const sortSelect = document.querySelector('select[name="sort"]');
    const dirSelect = document.querySelector('select[name="dir"]');

    const urlParams = new URLSearchParams(window.location.search);
    const hasFiltersInUrl = urlParams.has('sort') || urlParams.has('dir') || urlParams.has('name');
    const savedSettings = JSON.parse(localStorage.getItem(STORAGE_KEY));

    if (!hasFiltersInUrl && savedSettings) {
        const newUrl = new URL(window.location.href);
        if (savedSettings.name) newUrl.searchParams.set('name', savedSettings.name);
        newUrl.searchParams.set('sort', savedSettings.sort || 'id');
        newUrl.searchParams.set('dir', savedSettings.dir || 'asc');
        window.location.replace(newUrl.toString());
        return;
    }

    if (sortForm) {
        sortForm.addEventListener('submit', function () {
            const settings = {
                name: nameInput ? nameInput.value : '',
                sort: sortSelect.value,
                dir: dirSelect.value
            };
            localStorage.setItem(STORAGE_KEY, JSON.stringify(settings));
        });
    }
});

function resetResumeFilters() {
    localStorage.removeItem('resume_filter_settings');
    window.location.href = window.location.pathname;
}