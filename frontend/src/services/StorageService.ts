import type {Job} from "../models/Job.ts";

export function getObjects<T>(key: string): T[] {
    return JSON.parse(localStorage.getItem(key) || "[]") as T[];
}

export function setObject<T>(key: string, obj: T): void {
    const objects = getObjects<T>(key);
    objects.push(obj);
    localStorage.setItem(key, JSON.stringify(objects));
}

export function setObjects<T>(key: string, obj: T[]): void {
    localStorage.setItem(key, JSON.stringify(obj));
}

export function setLoggedEntity<T>(key: string, obj: T): void {
    localStorage.setItem(key, JSON.stringify(obj));
}

export function getLoggedEntity<T>(key: string): T | null {
    const entity = localStorage.getItem(key);
    return entity ? JSON.parse(entity) as T : null;
}

export function setJobInEdition(vaga: Job): void {
    localStorage.setItem("vagaEmEdicao", JSON.stringify(vaga));
}

export function getJobInEdition(): Job {
    return JSON.parse(localStorage.getItem("vagaEmEdicao") || "[]") as Job;
}