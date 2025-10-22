package services

import org.acelerazg.models.Address
import org.acelerazg.repositories.AddressRepository
import org.acelerazg.services.address.AddressService
import spock.lang.Specification

class AddressServiceTest extends Specification {

    def addressRepository = Mock(AddressRepository)
    def addressService = new AddressService(addressRepository)


    def "find existing adress"() {
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        addressRepository.findByAddress(_ as Address) >> "mock-adress-id"

        when:
        String result = addressService.find(address)

        then:
        result.size() == 36
        1 * addressRepository.findByAddress(address)
    }

    def "find existing adress"() {
        Address address = new Address("Brasil", "Sao Paulo", "12.345-67")
        addressRepository.findByAddress(_ as Address) >> null

        when:
        String result = addressService.find(address)

        then:
        result.size() == 36
        1 * addressRepository.create(address)
    }
}
