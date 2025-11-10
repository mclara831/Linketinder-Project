package org.acelerazg.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.acelerazg.models.Company
import org.acelerazg.models.DTO.CompanyDTO
import org.acelerazg.repositories.AddressRepository
import org.acelerazg.repositories.CompanyRepository
import org.acelerazg.repositories.SkillRepository
import org.acelerazg.repositories.db.connection.DatabaseConnection
import org.acelerazg.repositories.db.connection.PostgresConnection
import org.acelerazg.repositories.db.factory.RepositoryFactory
import org.acelerazg.services.address.AddressService
import org.acelerazg.services.address.IAddressService
import org.acelerazg.services.company.CompanyService
import org.acelerazg.services.mappers.CompanyMapper
import org.acelerazg.services.skill.CompanySkillService

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/companies")
class CompanyController extends HttpServlet {

    private CompanyService companyService
    private CompanyMapper companyMapper
    private ObjectMapper objectMapper

    @Override
    void init() throws ServletException {
        DatabaseConnection database = PostgresConnection.getInstance()

        RepositoryFactory factory = new RepositoryFactory(database)

        AddressRepository addressRepository = factory.createAddressRepository()
        CompanyRepository companyRepository = factory.createCompanyRepository()
        SkillRepository skillRepository = factory.createSkillRepository()

        CompanySkillService companySkillService = new CompanySkillService(skillRepository)
        IAddressService addressService = new AddressService(addressRepository)
        companyMapper = new CompanyMapper()

        companyService = new CompanyService(
                companyRepository,
                addressService,
                companySkillService,
                companyMapper
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
                Company company = companyService.findByCnpj(cnpj)

                if (company != null) {
                    resp.setStatus(HttpServletResponse.SC_OK)
                    objectMapper.writeValue(resp.writer, company)
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
                }
            } else {

                List<CompanyDTO> companies = companyService.findAll()
                objectMapper.writeValue(resp.getWriter(), companies)
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to find companies!\n[ERROR]: ${e.getMessage()}")
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

            CompanyDTO newCompany = objectMapper.readValue(body.toString(), CompanyDTO.class)
            CompanyDTO response = companyService.create(newCompany)

            resp.setStatus(HttpServletResponse.SC_CREATED)
            objectMapper.writeValue(resp.getWriter(), response)

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to create candidate!\n[ERROR]: ${e.getMessage()}")
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String cnpj = req.getParameter("cnpj")

            StringBuilder body = new StringBuilder()
            String line
            BufferedReader reader = req.getReader()
            while ((line = reader.readLine()) != null) {
                body.append(line)
            }

            CompanyDTO newCompany = objectMapper.readValue(body.toString(), CompanyDTO.class)

            CompanyDTO companyDTO = companyService.updateByCnpj(cnpj, newCompany)

            resp.setStatus(HttpServletResponse.SC_OK)
            objectMapper.writeValue(resp.getWriter(), companyDTO)
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to update company!\n[ERROR]: ${e.getMessage()}")
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)

        try {
            String cnpj = req.getParameter("cnpj")

            companyService.deleteByCnpj(cnpj)
            resp.setStatus(HttpServletResponse.SC_OK)
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "It wasn't possible to delete the company!\n[ERROR]: ${e.getMessage()}")
        }
    }
}
