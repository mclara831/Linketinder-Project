import type {Company} from "../models/Company.ts";
import {setLoggedCompany, setObject,} from "./StorageService.ts";
import {clearCompanyForm, lerInfoFomularioEmpresa, setupCompanyLogin,} from "./FormService";
import {fillCompanyProfile} from "./DOMService/CompanyDOMService.ts";
import {renderJobsByCompany} from "./JobService.ts";

export function initializeCompanyModule() {
    setupAddCandidatesBtn();
    clearCompanyFormButton();
    setupCompanyLogin();
}

function setupAddCandidatesBtn() {
    document.querySelector("#cadastrar-empresa")
        ?.addEventListener("click", function (event) {
            event.preventDefault();

            let company: Company | null = lerInfoFomularioEmpresa();
            if (company == null) {
                return
            }

            setObject<Company>("empresas", company);
            clearCompanyForm()
        });
}

function clearCompanyFormButton() {
    document.querySelector("#clear-form-company")
        ?.addEventListener("click", function (event) {
            event.preventDefault();
            clearCompanyForm();
        });
}

export function showCompanyProfile(company: Company): void {
    fillCompanyProfile(company)
    setLoggedCompany(company);
    renderJobsByCompany()
}