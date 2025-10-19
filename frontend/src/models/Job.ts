import type {Skill} from "./Skill.ts"
import {Company} from "./Company.ts"
import {v4 as uuidv4} from "uuid";

export class Job {
    id: string
    name: string
    description: string
    company: Company | null
    publication_date: Date
    skills: Skill[] = new Array<Skill>;

    constructor(name: string, description: string, company: Company | null, publication_date: Date, skills: Skill[]) {
        this.id = uuidv4()
        this.name = name
        this.description = description
        this.company = company
        this.publication_date = publication_date
        this.skills = skills
    }
}
