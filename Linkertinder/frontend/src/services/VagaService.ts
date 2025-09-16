import type { Competencia } from "../models/Competencia";
import { Vaga } from "../models/Vaga";
import { obterCompetenciasSelecionadas } from "../utils/Utils";
import {
  obterEmpresaLogada,
  obterObjetos,
  obterVagaEmEdicao,
  salvarObjeto,
  salvarObjetos,
  selecionarVagaParaEdicao,
} from "./ArmazenamentoService";
import { limparFormularioVaga } from "./FormService";

export function adicionaEventosCardVagas() {
  cadastrarVaga()
  rendenrizarVagaPorEmpresa()
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
            <div class="card shadow-sm p-3 mb-5 bg-body rounded" style="width: 20rem;">
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
  const empresaAtual = obterEmpresaLogada();

  btnCadastrar.addEventListener("click", () => {
    const nome = (document.querySelector("#job-name") as HTMLInputElement)
      .value;
    const descricao = (
      document.querySelector("#description") as HTMLInputElement
    ).value;
    const dataPublicacao = (
      document.querySelector("[name='data-publicacao']") as HTMLInputElement
    ).value;

    const competencias: Competencia[] = obterCompetenciasSelecionadas();

    const vaga: Vaga = new Vaga(
      nome,
      descricao,
      empresaAtual,
      new Date(dataPublicacao),
      competencias
    );

    salvarObjeto<Vaga>("vagas", vaga);
    limparFormularioVaga();
    rendenrizarVagaPorEmpresa();
  });
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

  const collapse = document.querySelector<HTMLDivElement>("#collapseForm")!;
  collapse.className = "collapse show";

  const nomeInput = document.querySelector("#job-name") as HTMLInputElement;
  const descricaoInput = document.querySelector(
    "#description"
  ) as HTMLTextAreaElement;
  const dataPublicacaoInput = document.querySelector(
    "[name='data-publicacao']"
  ) as HTMLInputElement;
  const btnCadastrar = document.querySelector(
    "#cadastrar-vaga"
  ) as HTMLButtonElement;
  const btnsContainer = document.querySelector(".btns") as HTMLDivElement;

  nomeInput.value = vaga.nome;
  descricaoInput.value = vaga.descricao;
  dataPublicacaoInput.value = vaga.data_publicacao
    ? new Date(vaga.data_publicacao).toISOString().split("T")[0]
    : "";
  btnCadastrar.textContent = "Salvar alterações";

  if (!document.querySelector("#btn-voltar")) {
    const btnVoltar = document.createElement("button");
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
    let encontrada: Vaga | undefined = vagas.find((v) => v.id == vaga.id);

    if (encontrada) {
      const index: number = vagas.indexOf(encontrada);
      encontrada.nome = nomeInput.value;
      encontrada.descricao = descricaoInput.value;
      encontrada.data_publicacao = new Date(dataPublicacaoInput.value);
      vagas[index] = encontrada;
    }

    salvarObjetos<Vaga>("vagas", vagas);

    btnCadastrar.textContent = "Cadastrar";
    btnCadastrar.onclick = cadastrarVaga; // volta para o evento inicial
  };
  limparFormularioVaga()
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
                <div class="card shadow-sm p-3 mb-5 bg-body rounded" style="width: 20rem;">
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
  adicionaEventoDeletarVagas()
  adicionaEventoEditarVagas()
}
