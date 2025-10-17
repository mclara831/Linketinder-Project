package org.acelerazg.models

class Address {
    String id
    String country
    String region
    String cep

    Address(String id, String country, String region, String cep) {
        this.id = id
        this.country = country
        this.region = region
        this.cep = cep
    }

    Address(String country, String region, String cep) {
        this.id = id
        this.country = country
        this.region = region
        this.cep = cep
    }

    @Override
    String toString() {
        return "\tEndereco: " + cep + ", " + region + ", " + country
    }
}
