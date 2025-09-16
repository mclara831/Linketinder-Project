import type { Empresa } from "../models/Empresa";
import type { Vaga } from "../models/Vaga";

export function obterObjetos<T>(chave: string): T[] {
  return JSON.parse(localStorage.getItem(chave) || "[]") as T[];
}

export function salvarObjeto<T>(chave: string, obj: T): void {
  const lista = obterObjetos<T>(chave);
  lista.push(obj);
  localStorage.setItem(chave, JSON.stringify(lista));
}

export function salvarObjetos<T>(chave: string, obj: T[]): void {
  localStorage.setItem(chave, JSON.stringify(obj));
}

export function definirEmpresaLogada(empresa: Empresa): void {
  localStorage.setItem("empresaLogada", JSON.stringify(empresa));
}

export function obterEmpresaLogada(): Empresa | null {
  const empresa = localStorage.getItem("empresaLogada");
  return empresa ? JSON.parse(empresa) as Empresa : null;
}

export function selecionarVagaParaEdicao(vaga: Vaga): void {
  localStorage.setItem("vagaEmEdicao", JSON.stringify(vaga));
}

export function obterVagaEmEdicao(): Vaga  {
  const vaga : Vaga = JSON.parse(localStorage.getItem("vagaEmEdicao") || "[]") as Vaga;
  return vaga 
}

