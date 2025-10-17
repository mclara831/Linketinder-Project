package org.acelerazg.services

import org.acelerazg.models.Address
import org.acelerazg.repositories.AddressRepository
import org.acelerazg.utils.Utils

class AddressService {

    AddressRepository addressRepository

    AddressService() {
        this(new AddressRepository())
    }

    AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository
    }

    String findById(String id) {
        return addressRepository.findEnderecoFromId(id)
    }

    String find(Address adress) {
        String result = addressRepository.findByAddress(adress)
        if (!result) {
            String id = Utils.generateUUID()
            adress.id = id
            addressRepository.create(adress)
            return id
        }
        return result
    }
}
