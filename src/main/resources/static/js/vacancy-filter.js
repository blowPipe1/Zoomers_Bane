document.addEventListener("DOMContentLoaded", function () {
    const STORAGE_KEY = 'vacancy_filter_settings';
    const sortForm = document.querySelector('.sort-form');
    const sortSelect = document.querySelector('select[name="sort"]');
    const dirSelect = document.querySelector('select[name="dir"]');

    const urlParams = new URLSearchParams(window.location.search);
    const hasParamsInUrl = urlParams.has('sort') || urlParams.has('dir');
    const savedSettings = JSON.parse(localStorage.getItem(STORAGE_KEY));

    if (!hasParamsInUrl && savedSettings) {
        const newUrl = new URL(window.location.href);
        newUrl.searchParams.set('sort', savedSettings.sort);
        newUrl.searchParams.set('dir', savedSettings.dir);
        window.location.replace(newUrl.toString());
        return;
    }

    if (sortForm) {
        sortForm.addEventListener('submit', function () {
            const settings = {
                sort: sortSelect.value,
                dir: dirSelect.value
            };
            localStorage.setItem(STORAGE_KEY, JSON.stringify(settings));
        });
    }
});