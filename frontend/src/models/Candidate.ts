import type {Competencia} from "./Competencia"

export class Candidate {
    name: string
    email: string
    linkedin: string
    phone: string
    date_of_birth: Date
    cpf: string
    region: string
    cep: string
    description: string
    skills: Competencia[] = new Array<Competencia>

    constructor(name: string, email: string, linkedin: string, phone: string, date_of_birth: Date, cpf: string, region: string, cep: string, description: string, skills: Competencia[]) {
        this.name = name
        this.email = email
        this.linkedin = linkedin
        this.phone = phone
        this.date_of_birth = date_of_birth
        this.cpf = cpf
        this.region = region
        this.cep = cep
        this.description = description
        this.skills = skills
    }
}
