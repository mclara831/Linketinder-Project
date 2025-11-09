package org.acelerazg.services.address

import org.acelerazg.models.Address
import org.acelerazg.repositories.AddressRepository
import org.acelerazg.utils.Utils

class AddressService implements IAddressService{

    AddressRepository addressRepository

    AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository
    }

    @Override
    String findById(String id) {
        return addressRepository.findById(id).toString()
    }

    @Override
    String find(Address address) {
        String result = addressRepository.findByAddress(address)
        if (result) return result

        String id = Utils.generateUUID()
        address.id = id
        addressRepository.create(address)
        return id
    }

    @Override
    Address formatAddress(String address) {
        List<String> ad = address.split(", ")
        return new Address(ad.get(0), ad.get(1), ad.get(2))
    }

}
