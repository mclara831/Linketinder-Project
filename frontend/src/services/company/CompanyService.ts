import type {Company} from "../../models/Company.ts";
import {getObjects, setLoggedEntity, setObject,} from "../StorageService.ts";
import {clearForm, clearLoginInput} from "../form/FormCleaner.ts";
import {readCompanyForm} from "../form/FormReader.ts";
import {fillCompanyProfile} from "./CompanyDOMService.ts";
import {renderJobsByCompany} from "../job/JobService.ts";
import {isCnpjValid, isInvalid, isValid} from "../form/ValidationService.ts";
import {renderCandidates} from "../candidate/CandidateService.ts";

export function initializeCompanyModule() {
    setupAddCandidatesBtn();
    clearCompanyFormButton();
    setupCompanyLogin();
}

function setupAddCandidatesBtn() {
    document.querySelector("#cadastrar-empresa")
        ?.addEventListener("click", function (event) {
            event.preventDefault();

            let company: Company | null = readCompanyForm();
            if (company == null) {
                return
            }

            setObject<Company>("empresas", company);
            clearForm("#company-form")
        });
}

function clearCompanyFormButton() {
    document.querySelector("#clear-form-company")
        ?.addEventListener("click", function (event) {
            event.preventDefault();
            clearForm("#company-form")
        });
}


function setupCompanyLogin(): void {
    const submitBtn = document.querySelector("#login-btn-empresa") as HTMLButtonElement;
    const loginForm = document.querySelector(".login-empresa") as HTMLDivElement;
    const companyProfile = document.querySelector("#perfil-empresa") as HTMLDivElement;
    const cnpj = document.querySelector("#inputCnpj") as HTMLInputElement;

    submitBtn?.addEventListener("click", function (e) {
        e.preventDefault();

        const companies: Company[] = getObjects<Company>("empresas");
        let wasCompanyFound: boolean = false;

        isCnpjValid(cnpj.value) ? isInvalid(cnpj) : isValid(cnpj);

        companies.forEach((company) => {
            if (company.cnpj === cnpj.value) {
                companyProfile.style.display = "initial";
                loginForm.style.display = "none";
                wasCompanyFound = true;
                showCompanyProfile(company);
            }
        });

        if (!wasCompanyFound) {
            isInvalid(cnpj);
        }
        clearLoginInput("#inputCnpj");
    });
}

export function showCompanyProfile(company: Company): void {
    fillCompanyProfile(company)
    setLoggedEntity<Company>("empresaLogada", company);
    renderJobsByCompany()
    renderCandidates()
}