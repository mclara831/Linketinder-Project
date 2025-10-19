import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {initializeCandidateModule,} from "./services/CandidateService.ts";
import {renderGraphic} from "./services/GraphicService.ts";
import "./styles/style.css";
import {initializeJobModule, renderJobs} from "./services/JobService.ts";
import {loadSkills} from "./utils/Utils";
import {initializeCompanyModule} from "./services/CompanyService.ts";

loadSkills();
renderJobs()
initializeCandidateModule()
initializeCompanyModule();
renderGraphic();
initializeCompanyModule();
initializeJobModule();

