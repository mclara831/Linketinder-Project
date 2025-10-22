import {
    isCepValid, isCnpjValid, isCPFValid,
    isEmailValid,
    isInvalid,
    isLinkedinValid,
    isPhoneValid,
    isTextValid,
    isValid
} from "./ValidationService.ts";

export function validateFields(name: HTMLInputElement, email: HTMLInputElement, linkedin: HTMLInputElement, phone: HTMLInputElement | null, cpf: HTMLInputElement | null, cnpj: HTMLInputElement | null, cep: HTMLInputElement, description: HTMLInputElement, region: HTMLInputElement, dateOfBirth: HTMLInputElement | null, country: HTMLInputElement | null): boolean {

    let isValidForm = true;

    const validate = (condition: boolean, element: HTMLInputElement | null) => {
        if (!element) return;
        if (!condition) {
            isInvalid(element);
            isValidForm = false;
        } else {
            isValid(element);
        }
    };

    validate(isTextValid(name.value), name);
    validate(isEmailValid(email.value), email);
    validate(isLinkedinValid(linkedin.value), linkedin);
    validate(isTextValid(description.value), description);
    validate(isTextValid(region.value), region);
    validate(isCepValid(cep.value), cep);

    if (phone) validate(isPhoneValid(phone.value), phone);
    if (cpf) validate(isCPFValid(cpf.value), cpf);
    if (cnpj) validate(isCnpjValid(cnpj.value), cnpj);
    if (country) validate(isTextValid(country.value), country);

    if (dateOfBirth) {
        const age = new Date().getFullYear() - new Date(dateOfBirth.value).getFullYear();
        validate(dateOfBirth.value !== "" && age >= 18, dateOfBirth);
    }

    return isValidForm;
}
