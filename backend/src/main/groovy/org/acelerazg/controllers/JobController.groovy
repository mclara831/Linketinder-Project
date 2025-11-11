package org.acelerazg.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.acelerazg.models.DTO.job.JobResponseDTO
import org.acelerazg.models.DTO.job.JobRequestDTO
import org.acelerazg.repositories.AddressRepository
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.JobRepository
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection
import org.acelerazg.repositories.db.connection.PostgresConnection
import org.acelerazg.repositories.db.factory.RepositoryFactory
import org.acelerazg.services.address.AddressService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.company.CompanyService
import org.acelerazg.services.job.JobService
import org.acelerazg.services.mappers.CompanyMapper
import org.acelerazg.services.mappers.JobMapper
import org.acelerazg.services.skill.CompanySkillService
import org.acelerazg.services.skill.JobSkillService

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/jobs/*")
class JobController extends HttpServlet {

    private JobService jobService
    private JobMapper jobMapper
    private ObjectMapper objectMapper

    @Override
    void init() throws ServletException {
        DatabaseConnection database = PostgresConnection.getInstance()

        RepositoryFactory factory = new RepositoryFactory(database)

        AddressRepository addressRepository = factory.createAddressRepository()
        JobRepository jobRepository = factory.createJobRepository()
        SkillRepository skillRepository = factory.createSkillRepository()

        JobSkillService jobSkillService = new JobSkillService(skillRepository)
        IAddressService addressService = new AddressService(addressRepository)
        CompanyService companyService = new CompanyService(new CompanyRepository(database),
                                                            addressService, new CompanySkillService(skillRepository), new CompanyMapper())
        jobMapper = new JobMapper()

        jobService = new JobService(
                jobRepository,
                addressService,
                jobSkillService,
                companyService,
                jobMapper
        )

        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String cnpj = req.getParameter("cnpj")

            if (cnpj) {
                List<JobResponseDTO> job = jobService.findJobFromACompany(cnpj)

                if (job != null) {
                    resp.setStatus(HttpServletResponse.SC_OK)
                    objectMapper.writeValue(resp.writer, job)
                }
            } else {

                List<JobResponseDTO> jobs = jobService.findAll()
                objectMapper.writeValue(resp.getWriter(), jobs)
            }

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "It wasn't possible to find jobs!", e)
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            resp.setContentType("application/json")
            resp.setCharacterEncoding("UTF-8")

            StringBuilder body = new StringBuilder()
            String line
            BufferedReader reader = req.getReader()
            while ((line = reader.readLine()) != null) {
                body.append(line)
            }

            JobRequestDTO newJob = objectMapper.readValue(body.toString(), JobRequestDTO.class)
            JobResponseDTO response = jobService.create(newJob)

            resp.setStatus(HttpServletResponse.SC_CREATED)
            objectMapper.writeValue(resp.getWriter(), response)

        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to create job!", e)
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String id = req.getPathInfo()

            StringBuilder body = new StringBuilder()
            String line
            BufferedReader reader = req.getReader()
            while ((line = reader.readLine()) != null) {
                body.append(line)
            }

            JobRequestDTO newJob = objectMapper.readValue(body.toString(), JobRequestDTO.class)
            JobResponseDTO jobDTO = jobService.update(id.substring(1), newJob)

            resp.setStatus(HttpServletResponse.SC_OK)
            objectMapper.writeValue(resp.getWriter(), jobDTO)
        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to update job!", e)
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String id = req.getPathInfo()

            jobService.deleteById(id.substring(1))
            resp.setStatus(HttpServletResponse.SC_OK)
        } catch (Exception e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to delete job!", e)
        }
    }

    private void sendError(HttpServletResponse resp, int statusCode, String message, Exception e) throws IOException {
        resp.setStatus(statusCode)
        objectMapper.writeValue(resp.getWriter(), Map.of(
                "error", message,
                "details", e.getMessage()
        ))
    }
}
