import type { Company } from "../models/Company.ts";
import type { Vaga } from "../models/Vaga";

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

export function setLoggedCompany(empresa: Company): void {
  localStorage.setItem("empresaLogada", JSON.stringify(empresa));
}

export function getLoggedCompany(): Company | null {
  const company = localStorage.getItem("empresaLogada");
  return company ? JSON.parse(company) as Company : null;
}

export function selecionarVagaParaEdicao(vaga: Vaga): void {
  localStorage.setItem("vagaEmEdicao", JSON.stringify(vaga));
}

export function obterVagaEmEdicao(): Vaga  {
  const vaga : Vaga = JSON.parse(localStorage.getItem("vagaEmEdicao") || "[]") as Vaga;
  return vaga 
}

