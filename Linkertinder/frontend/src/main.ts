import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { Empresa } from "./models/Empresa";
import { salvarObjeto } from "./services/ArmazenamentoService";
import {
  adicionaEventosAManipulacaoCandidatos,

} from "./services/CandidatoService";
import {
  lerInfoFomularioEmpresa,
  limparFormularioEmpresa,
  loginEmpresa,
} from "./services/FormService";
import { carregarGraphic } from "./services/GraficoService";
import "./styles/style.css";
import {
  adicionaEventosCardVagas, carregarVagas
} from "./services/VagaService";
import { carregarCompetencias } from "./utils/Utils";

adicionaEventosAManipulacaoCandidatos()
carregarCompetencias();
adicionaEventosCardVagas();
carregarGraphic();
carregarVagas();
loginEmpresa();


document
  .querySelector("#cadastrar-empresa")
  ?.addEventListener("click", function (event) {
    event.preventDefault();
    var empresa = lerInfoFomularioEmpresa();
    if (empresa == null) {
      return;
    }
    salvarObjeto<Empresa>("empresas", empresa);
    // carregarCandidatos();
  });

document
  .querySelector("#clear-form-company")
  ?.addEventListener("click", function (event) {
    event.preventDefault();
    limparFormularioEmpresa();
  });
