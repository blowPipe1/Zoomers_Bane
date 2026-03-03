document.addEventListener("DOMContentLoaded", function () {
    const STORAGE_KEY = 'vacancy_filter_settings';
    const sortForm = document.querySelector('.sort-form');
    const nameInput = document.querySelector('input[name="name"]');
    const sortSelect = document.querySelector('select[name="sort"]');
    const dirSelect = document.querySelector('select[name="dir"]');

    const urlParams = new URLSearchParams(window.location.search);
    const hasParamsInUrl = urlParams.has('sort') || urlParams.has('dir') || urlParams.has('name');
    const savedSettings = JSON.parse(localStorage.getItem(STORAGE_KEY));

    if (!hasParamsInUrl && savedSettings) {
        const newUrl = new URL(window.location.href);
        if (savedSettings.name) newUrl.searchParams.set('name', savedSettings.name);
        newUrl.searchParams.set('sort', savedSettings.sort);
        newUrl.searchParams.set('dir', savedSettings.dir);
        window.location.replace(newUrl.toString());
        return;
    }

    if (sortForm) {
        sortForm.addEventListener('submit', function () {
            const settings = {
                name: nameInput.value,
                sort: sortSelect.value,
                dir: dirSelect.value
            };
            localStorage.setItem(STORAGE_KEY, JSON.stringify(settings));
        });
    }
});