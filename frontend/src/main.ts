import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {initializeCandidateModule} from "./services/candidate/CandidateService.ts";
import {ChartJsRenderer, renderGraphic} from "./provider/implementation/GraphicService.ts";
import "./styles/style.css";
import {initializeJobModule, renderJobs} from "./services/job/JobService.ts";
import {loadSkills} from "./utils/Utils";
import {initializeCompanyModule} from "./services/company/CompanyService.ts";

loadSkills();
renderJobs()
initializeCandidateModule()
initializeCompanyModule();
renderGraphic(new ChartJsRenderer());
initializeCompanyModule();
initializeJobModule();

