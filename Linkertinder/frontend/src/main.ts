import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { Empresa } from "./models/Empresa";
import { salvarObjeto } from "./services/ArmazenamentoService";
import {
  adicionarCandidato,
  carregarCandidatos,
} from "./services/CandidatoService";
import {
  lerInfoFomularioCandidato,
  lerInfoFomularioEmpresa,
  limparFormularioCandidato,
  limparFormularioEmpresa,
  loginCandidato,
  loginEmpresa,
} from "./services/FormService";
import { carregarGraphic } from "./services/GraficoService";
import {
  carregarCompetencias,
  carregarVagas,
} from "./services/LoaderService";
import "./styles/style.css";


carregarGraphic();
carregarCandidatos();
carregarVagas();
carregarCompetencias();
loginEmpresa()
loginCandidato()

document
  .querySelector("#cadastrar-candidato")
  ?.addEventListener("click", function (event) {
    event.preventDefault();
    var candidato = lerInfoFomularioCandidato();
    if (candidato === null) {
      return;
    }

    adicionarCandidato(candidato);
    limparFormularioCandidato();
    carregarCandidatos();
  });

document
  .querySelector("#cadastrar-empresa")
  ?.addEventListener("click", function (event) {
    event.preventDefault();
    var empresa = lerInfoFomularioEmpresa();
    if (empresa == null) {
      return;
    }
    salvarObjeto<Empresa>("empresas", empresa)
    carregarCandidatos();
  });

document
  .querySelector("#clear-form-candidate")
  ?.addEventListener("click", function (event) {
    event.preventDefault();
    limparFormularioCandidato();
  });

document
  .querySelector("#clear-form-company")
  ?.addEventListener("click", function (event) {
    event.preventDefault();
    limparFormularioEmpresa();
  });
