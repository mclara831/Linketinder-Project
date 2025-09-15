export function obterObjetos<T>(chave: string): T[] {
  return JSON.parse(localStorage.getItem(chave) || "[]") as T[];
}

export function salvarObjeto<T>(chave: string, obj: T): void {
  const lista = obterObjetos<T>(chave);
  lista.push(obj);
  localStorage.setItem(chave, JSON.stringify(lista));
}