import {Candidate} from "../models/Candidate.ts";
import {Company} from "../models/Company.ts";
import {getSelectedSkills} from "../utils/Utils";
import {
    isCepValid, isCnpjValid, isCPFValid, isEmailValid, isInvalid, isLinkedinValid, isPhoneValid, isTextValid, isValid,
} from "./ValidationService.ts";

export function readCandidateForm(): Candidate | null {
    const form = document.querySelector("#candidate-form") as HTMLInputElement;

    let name = form.querySelector('[name="name"]') as HTMLInputElement;
    let email = form.querySelector('[name="email"]') as HTMLInputElement;
    let linkedin = form.querySelector('[name="linkedin"]') as HTMLInputElement;
    let phone = form.querySelector('[name="telefone"]') as HTMLInputElement;
    let cpf = form.querySelector('[name="cpf"]') as HTMLInputElement;
    let dataOfBirth = form.querySelector('[name="data-nascimento"]') as HTMLInputElement;
    let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
    let description = form.querySelector('[name="description"]') as HTMLInputElement;
    let skills = getSelectedSkills();
    let region = form.querySelector('[name="estado"]') as HTMLInputElement;

    const result = validateFields(name, email, linkedin, phone, cpf, null, cep, description, region, dataOfBirth, null);

    if (!result) return null;

    const [ano, mes, dia] = dataOfBirth.value.split("-").map(Number);

    return new Candidate(name.value, email.value, linkedin.value, phone.value, new Date(ano, mes - 1, dia), cpf.value, region.value, cep.value, description.value, skills);
}

export function readCompanyForm(): Company | null {

    const form = document.querySelector("#company-form") as HTMLInputElement;

    let name = form.querySelector('[name="name"]') as HTMLInputElement;
    let email = form.querySelector('[name="email"]') as HTMLInputElement;
    let linkedin = form.querySelector('[name="linkedin"]') as HTMLInputElement;
    let cnpj = form.querySelector('[name="cnpj"]') as HTMLInputElement;
    let region = form.querySelector('[name="estado"]') as HTMLInputElement;
    let cep = form.querySelector('[name="cep"]') as HTMLInputElement;
    let country = form.querySelector('[name="pais"]') as HTMLInputElement;
    let description = form.querySelector('[name="description"]') as HTMLInputElement;
    let selectedSkills = getSelectedSkills();

    const result: boolean = validateFields(name, email, linkedin, null, null, cnpj, cep, description, region, null, country);
    if (!result) return null;

    return new Company(name.value, email.value, linkedin.value, cnpj.value, region.value, cep.value, country.value, description.value, selectedSkills);
}

function validateFields(name: HTMLInputElement, email: HTMLInputElement, linkedin: HTMLInputElement, phone: HTMLInputElement | null, cpf: HTMLInputElement | null, cnpj: HTMLInputElement | null, cep: HTMLInputElement, description: HTMLInputElement, region: HTMLInputElement, dateOfBirth: HTMLInputElement | null, country: HTMLInputElement | null): boolean {

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
