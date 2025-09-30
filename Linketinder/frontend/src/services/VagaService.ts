import type { Competencia } from "../models/Competencia";
import { Vaga } from "../models/Vaga";
import {
  obterCompetenciasSelecionadas,
  selecionarCompetencias,
} from "../utils/Utils";
import {
  obterEmpresaLogada,
  obterObjetos,
  obterVagaEmEdicao,
  salvarObjeto,
  salvarObjetos,
  selecionarVagaParaEdicao,
} from "./ArmazenamentoService";
import { limparFormularioVaga } from "./FormService";
import { invalido, validaTextoMaior, valido } from "./ValidacaoService";

export function adicionaEventosCardVagas() {
  cadastrarVaga();
  rendenrizarVagaPorEmpresa();
}

export function carregarVagas() {
  var card_list = document.querySelector(".jobs") as HTMLDivElement;
  if (card_list) {
    card_list.innerHTML = "";
  }

  var vagas: Vaga[] = obterObjetos<Vaga>("vagas");

  vagas.forEach((vaga) => {
    var li: HTMLElement = document.createElement("li");
    li.innerHTML = `
            <div class="card shadow-sm p-3 mb-5 bg-body rounded" style="width: 19rem;">
              <div class="card-body">
                <h5 class="card-title">${vaga.nome}</h5>
                <p class="card-text">${vaga.descricao}</p>
              </div>
              <div class="card-body">
                <h6>Competências requiridas:</h6>
                <p class="competencies-required">${vaga.competencias}</p>
              </div>
              <div class="card-body" style="display: none;">
                <a href="#" class="card-link">Card link</a>
                <a href="#" class="card-link">Another link</a>
              </div>
            </div>
            `;
    card_list?.appendChild(li);
  });
}

function cadastrarVaga(): void {
  const btnCadastrar = document.querySelector(
    "#cadastrar-vaga"
  ) as HTMLButtonElement;
  
  btnCadastrar.onclick = () => {
    const empresaAtual = obterEmpresaLogada();
    const nome = document.querySelector("#job-name") as HTMLInputElement;
    const descricao = document.querySelector(
      "#description"
    ) as HTMLInputElement;
    const dataPublicacao = document.querySelector(
      "[name='data-publicacao']"
    ) as HTMLInputElement;

    const competencias: Competencia[] = obterCompetenciasSelecionadas();

    let camposValidos: boolean = true;

    if (!validaTextoMaior(nome.value)) {
      invalido(nome);
      camposValidos = false;
    } else {
      valido(nome);
    }

    if (!dataPublicacao.value) {
      invalido(dataPublicacao);
      camposValidos = false;
    } else {
      valido(dataPublicacao);
    }

    if (!validaTextoMaior(descricao.value)) {
      invalido(descricao);
      camposValidos = false;
    } else {
      valido(descricao);
    }

    if (!camposValidos) {
      return;
    }

    const vaga: Vaga = new Vaga(
      nome.value,
      descricao.value,
      empresaAtual,
      new Date(dataPublicacao.value),
      competencias
    );

    salvarObjeto<Vaga>("vagas", vaga);
    limparFormularioVaga();
    rendenrizarVagaPorEmpresa();
  };
}

function adicionaEventoEditarVagas(): void {
  const editar_btn =
    document.querySelectorAll<HTMLButtonElement>(".editar-vaga");

  const vagas = obterObjetos<Vaga>("vagas");

  editar_btn.forEach((vaga) => {
    vaga.addEventListener("click", function (event) {
      event.preventDefault();
      const v: Vaga | undefined = vagas.find((v) => v.id == vaga.dataset.id);
      if (v) {
        selecionarVagaParaEdicao(v);
      }
      editarVaga();
    });
  });
}

function adicionaEventoDeletarVagas() {
  const deletar_btn =
    document.querySelectorAll<HTMLButtonElement>(".deletar-vaga");

  const vagas = obterObjetos<Vaga>("vagas");

  deletar_btn.forEach((btn) => {
    btn.addEventListener("click", function (event) {
      event.preventDefault();
      const v: Vaga | undefined = vagas.find((v) => v.id == btn.dataset.id);
      if (v) {
        selecionarVagaParaEdicao(v);
      }
      deletarVaga();
    });
  });
}

function editarVaga(): void {
  const vaga = obterVagaEmEdicao();
  console.log(vaga);

  const collapse = document.querySelector<HTMLDivElement>("#collapseForm")!;
  collapse.className = "collapse show";

  let nomeInput = document.querySelector("#job-name") as HTMLInputElement;
  let descricaoInput = document.querySelector(
    "#description"
  ) as HTMLInputElement;
  let dataPublicacaoInput = document.querySelector(
    "[name='data-publicacao']"
  ) as HTMLInputElement;
  let btnCadastrar = document.querySelector(
    "#cadastrar-vaga"
  ) as HTMLButtonElement;
  const btnsContainer = document.querySelector(".btns") as HTMLDivElement;
  selecionarCompetencias(vaga.competencias);

  nomeInput.value = vaga.nome;
  descricaoInput.value = vaga.descricao;
  dataPublicacaoInput.value = vaga.data_publicacao
    ? new Date(vaga.data_publicacao).toISOString().split("T")[0]
    : "";
  selecionarCompetencias(vaga.competencias)
  btnCadastrar.textContent = "Salvar alterações";

  const btnVoltar = document.createElement("button");
  if (!document.querySelector("#btn-voltar")) {
    btnVoltar.id = "btn-voltar";
    btnVoltar.className = "btn btn-secondary ms-2";
    btnVoltar.textContent = "Voltar";

    btnVoltar.addEventListener("click", () => {
      nomeInput.value = "";
      descricaoInput.value = "";
      dataPublicacaoInput.value = "";

      btnCadastrar.textContent = "Cadastrar";
      btnCadastrar.onclick = cadastrarVaga;
      btnVoltar.remove();
      limparFormularioVaga();
    });

    btnsContainer.appendChild(btnVoltar);
  }

  btnCadastrar.onclick = () => {
    const vagas: Vaga[] = obterObjetos("vagas");

    let camposValidos: boolean = true;

    if (!validaTextoMaior(nomeInput.value)) {
      invalido(nomeInput);
      camposValidos = false;
    } else {
      valido(nomeInput);
    }

    if (!dataPublicacaoInput.value) {
      invalido(dataPublicacaoInput);
      camposValidos = false;
    } else {
      valido(dataPublicacaoInput);
    }

    if (!validaTextoMaior(descricaoInput.value)) {
      invalido(descricaoInput);
      camposValidos = false;
    } else {
      valido(descricaoInput);
    }

    if (!camposValidos) {
      return;
    }

    let encontrada: Vaga | undefined = vagas.find((v) => v.id == vaga.id);

    if (encontrada) {
      const index: number = vagas.indexOf(encontrada);
      encontrada.nome = nomeInput.value;
      encontrada.descricao = descricaoInput.value;
      encontrada.data_publicacao = new Date(dataPublicacaoInput.value);
      const competencias: Competencia[] = obterCompetenciasSelecionadas();
      encontrada.competencias = competencias
      vagas[index] = encontrada;
      salvarObjetos<Vaga>("vagas", vagas);
    }

    btnCadastrar.textContent = "Cadastrar";
    btnCadastrar.onclick = cadastrarVaga;
    btnVoltar.remove();
    rendenrizarVagaPorEmpresa();
    limparFormularioVaga();
  };
}

function deletarVaga(): void {
  const vaga = obterVagaEmEdicao();

  let vagas: Vaga[] = obterObjetos("vagas");
  vagas = vagas.filter((v) => v.id !== vaga.id);
  salvarObjetos<Vaga>("vagas", vagas);

  rendenrizarVagaPorEmpresa();
}

export function rendenrizarVagaPorEmpresa() {
  const empresaAtual = obterEmpresaLogada();
  var card_list = document.querySelector(".jobs-company") as HTMLDivElement;
  if (card_list) {
    card_list.innerHTML = "";
  }

  var vagas: Vaga[] = obterObjetos<Vaga>("vagas");

  vagas.forEach((vaga) => {
    if (vaga.empresa?.cnpj == empresaAtual?.cnpj) {
      var li: HTMLElement = document.createElement("li");
      li.innerHTML = `
                <div class="card shadow-sm p-3 mb-5 bg-body rounded" style="width: 19rem;">
                  <div class="card-body">
                    <h5 class="card-title">${vaga.nome}</h5>
                    <p class="card-text">${vaga.descricao}</p>
                  </div>
                  <div class="card-body">
                    <h6>Competências requiridas:</h6>
                    <p class="competencies-required">${vaga.competencias}</p>
                  </div>
                  <div class="d-flex gap-2">
                    <button class="btn btn-sm btn-outline-primary editar-vaga" data-id="${vaga.id}">
                        <i class="fa fa-pencil"></i> Editar
                    </button>
                    <button class="btn btn-sm btn-outline-danger deletar-vaga" data-id="${vaga.id}">
                        <i class="fa fa-trash"></i> Deletar
                     </button>
                 </div>
                </div>
                `;
      card_list?.appendChild(li);
    }
  });
  adicionaEventoDeletarVagas();
  adicionaEventoEditarVagas();
}
