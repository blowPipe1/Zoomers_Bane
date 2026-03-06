document.addEventListener("DOMContentLoaded", function () {
    const STORAGE_KEY = 'vacancy_filter_settings';
    const sortForm = document.querySelector('.sort-form');

    const nameInput = document.querySelector('input[name="name"]');
    const sortSelect = document.querySelector('select[name="sort"]');
    const dirSelect = document.querySelector('select[name="dir"]');

    const urlParams = new URLSearchParams(window.location.search);
    const hasParamsInUrl = urlParams.has('sort') || urlParams.has('dir') || urlParams.get('name');
    const savedSettings = JSON.parse(localStorage.getItem(STORAGE_KEY));

    if (!hasParamsInUrl && savedSettings) {
        const newUrl = new URL(window.location.href);
        if (savedSettings.name) newUrl.searchParams.set('name', savedSettings.name);
        newUrl.searchParams.set('sort', savedSettings.sort || 'id');
        newUrl.searchParams.set('dir', savedSettings.dir || 'asc');

        if (newUrl.search !== window.location.search) {
            window.location.replace(newUrl.toString());
            return;
        }
    }

    function saveVacancySettings() {
        const settings = {
            name: nameInput ? nameInput.value : '',
            sort: sortSelect ? sortSelect.value : 'id',
            dir: dirSelect ? dirSelect.value : 'asc'
        };
        localStorage.setItem(STORAGE_KEY, JSON.stringify(settings));
    }

    if (sortForm) {
        sortForm.addEventListener('change', saveVacancySettings);
        sortForm.addEventListener('submit', saveVacancySettings);
    }
});

function resetVacancyFilters() {
    localStorage.removeItem('vacancy_filter_settings');
    window.location.href = window.location.pathname;
}