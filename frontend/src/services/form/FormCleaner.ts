export function clearForm(formSelector: string): void {
    const form = document.querySelector(`${formSelector}`) as HTMLInputElement;
    if (!form) return;

    form.querySelectorAll("input, textarea").forEach(el => {
        if (el instanceof HTMLInputElement && (el.type === "checkbox" || el.type === "radio")) {
            el.checked = false;
        } else {
            (el as HTMLInputElement | HTMLTextAreaElement).value = "";
        }
        el.classList.remove("is-invalid", "is-valid");
    });
}

export function clearLoginInput(id: string) {
    (document.querySelector(id) as HTMLInputElement).value = "";
}