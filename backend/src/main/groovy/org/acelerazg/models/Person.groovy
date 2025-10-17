package org.acelerazg.models

abstract class Person {

    String id
    String name
    String email
    String linkedin
    String addressId
    String description
    String password

    Person(String name, String email, String linkedin, String description, String password) {
        this.name = name
        this.email = email
        this.linkedin = linkedin
        this.addressId = addressId
        this.description = description
        this.password = password
    }

    Person(String name, String email, String linkedin, String addressId, String description, String password) {
        this.name = name
        this.email = email
        this.linkedin = linkedin
        this.addressId = addressId
        this.description = description
        this.password = password
    }

    Person(String id, String name, String email, String linkedin, String addressId, String description, String password) {
        this.id = id
        this.name = name
        this.email = email
        this.linkedin = linkedin
        this.addressId = addressId
        this.description = description
        this.password = password
    }

    @Override
    String toString() {
        return "  name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", linkedin='" + linkedin + '\'' +
                ", addressId='" + addressId + '\'' +
                ", description='" + description + '\'' +
                ", password='" + password + '\''
    }
}

