document.addEventListener("DOMContentLoaded", function () {
    const STORAGE_KEY = 'resume_filter_settings';
    const sortForm = document.querySelector('.sort-form');

    const urlParams = new URLSearchParams(window.location.search);
    const hasFiltersInUrl = urlParams.has('sort') || urlParams.has('dir') || urlParams.has('name') || urlParams.has('category');
    const savedSettings = JSON.parse(localStorage.getItem(STORAGE_KEY));

    if (!hasFiltersInUrl && savedSettings) {
        const newUrl = new URL(window.location.href);
        if (savedSettings.name) newUrl.searchParams.set('name', savedSettings.name);
        if (savedSettings.category) newUrl.searchParams.set('category', savedSettings.category);
        newUrl.searchParams.set('sort', savedSettings.sort || 'id');
        newUrl.searchParams.set('dir', savedSettings.dir || 'asc');

        window.location.replace(newUrl.toString());
        return;
    }

    function saveToStorage() {
        const settings = {
            name: document.querySelector('input[name="name"]')?.value || '',
            category: document.querySelector('select[name="category"]')?.value || '',
            sort: document.querySelector('select[name="sort"]')?.value || 'id',
            dir: document.querySelector('select[name="dir"]')?.value || 'asc'
        };
        localStorage.setItem(STORAGE_KEY, JSON.stringify(settings));
    }

    if (sortForm) {
        sortForm.addEventListener('change', saveToStorage);
        sortForm.addEventListener('submit', saveToStorage);
    }
});

function resetResumeFilters() {
    localStorage.removeItem('resume_filter_settings');
    window.location.href = window.location.pathname;
}