import type {Skill} from "./Skill.ts"

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
    skills: Skill[] = new Array<Skill>

    constructor(name: string, email: string, linkedin: string, phone: string, date_of_birth: Date, cpf: string, region: string, cep: string, description: string, skills: Skill[]) {
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
