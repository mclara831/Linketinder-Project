package org.acelerazg.services.address

import org.acelerazg.models.Address

interface IAddressService {
    String findById(String id)
    String find(Address address)
}
