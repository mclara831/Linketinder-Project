import type { Competencia } from "./Competencia"

export class Company {
    name: string
    email: string
    linkedin: string
    cnpj: string
    region: string
    cep: string
    country: string
    description: string
    skills: Competencia[] = new Array<Competencia>
    

    constructor(name: string, email: string, linkedin: string, cnpj: string, region: string, cep: string, country: string, description: string, skills: Competencia[]) {
        this.name = name
        this.email = email
        this.linkedin = linkedin
        this.cnpj = cnpj
        this.region = region
        this.cep = cep
        this.country = country
        this.description = description
        this.skills = skills
    }

}
