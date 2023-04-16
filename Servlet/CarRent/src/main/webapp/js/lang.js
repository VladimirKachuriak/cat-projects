function setLang(lang) {
    document.cookie = "lang=" + lang + "; path=/; max-age=" + (10 * 365 * 24 * 60 * 60);
    location.reload();
}