import {Candidate} from "../../models/Candidate.ts";
import {Company} from "../../models/Company.ts";
import {validateFields} from "./FormValidator.ts";
import {getSelectedSkills} from "../../utils/Utils.ts";

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
