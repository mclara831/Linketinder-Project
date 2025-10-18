import type {Company} from "../models/Company.ts";
import {loadSkillsFromObject, setText} from "../utils/Utils.ts";

export function fillCompanyProfile(company: Company): void {

    setText(".company-name", company.name);
    setText(".company-email", company.email);
    setText(".company-linkedin", company.linkedin);
    setText(".company-cnpj", company.cnpj);
    setText(".company-cep", company.cep);
    setText(".company-state", company.region);
    setText(".company-country", company.country);
    setText(".company-description", company.description);

    loadSkillsFromObject(".skills-list", company.skills);
}