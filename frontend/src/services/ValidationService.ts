export function isCPFValid(cpf: string): boolean {
    const regex = RegExp(/\d{3}\.\d{3}\.\d{3}-\d{2}/g);
    return regex.test(cpf);
}

export function isCnpjValid(cnpj: string): boolean {
    const regex = RegExp(/\d{2}\.\d{3}\.\d{3}\/0001-\d{2}/g);
    return regex.test(cnpj);
}

export function isCepValid(cep: string): boolean {
    const regex = RegExp(/\d{2}\.\d{3}\-\d{2}/g);
    return regex.test(cep);
}

export function isEmailValid(cep: string): boolean {
    const regex = RegExp(/\S+@\w+\.\w{2,6}(\.\w{2})?/g);
    return regex.test(cep);
}

export function isLinkedinValid(linkedin: string): boolean {
    const regex = RegExp(/(https:\/\/)?(www\.)?linkedin\.com\/in\/\w+/g);
    return regex.test(linkedin);
}

export function isPhoneValid(phone: string): boolean {
    const regex = RegExp(/\+\d{1,3}\s?\(?\d{2,3}\)?\s?\d{4,5}-\d{4}/g);
    return regex.test(phone);
}

export function isTextValid(text: string): boolean {
    const regex = RegExp(/\w{3,}/g);
    return regex.test(text.trim());
}

export function isBiggerTextValid(text: string): boolean {
    const regex = RegExp(/\w{10,}/g);
    return regex.test(text.trim());
}

export function isInvalid(element: HTMLInputElement): void {
    element.classList.add("is-invalid");
}

export function isValid(element: HTMLInputElement): void {
    element.classList.remove("is-invalid");
}