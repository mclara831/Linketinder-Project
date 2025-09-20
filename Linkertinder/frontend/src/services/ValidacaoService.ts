export function validaCPF(cpf: string): boolean {
  const regex = RegExp(/\d{3}\.\d{3}\.\d{3}-\d{2}/g);
  return regex.test(cpf);
}

export function validaCNPJ(cnpj: string): boolean {
  const regex = RegExp(/\d{2}\.\d{3}\.\d{3}\/0001-\d{2}/g);
  return regex.test(cnpj);
}

export function validaCEP(cep: string): boolean {
  const regex = RegExp(/\d{2}\.\d{3}\-\d{3}/g);
  return regex.test(cep);
}

export function validaEmail(cep: string): boolean {
  const regex = RegExp(/\S+@\w+\.\w{2,6}(\.\w{2})?/g);
  return regex.test(cep);
}

export function validaLinkedin(linkedin: string): boolean {
  const regex = RegExp(/(http:\/\/)?(www\.)?linkedin\.com\/in\/\w+/g);
  return regex.test(linkedin);
}

export function validaTexto(texto: string): boolean {
  const regex = RegExp(/\w{3,}/g);
  return regex.test(texto.trim());
}

export function invalido(elemento: HTMLInputElement): void {
  elemento.classList.add("is-invalid");
}

export function valido(elemento: HTMLInputElement): void {
  elemento.classList.remove("is-invalid");
}
