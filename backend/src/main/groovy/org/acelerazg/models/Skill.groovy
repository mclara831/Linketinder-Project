package org.acelerazg.models

class Skill {
    String id
    String name

    Skill(String name) {
        this.name = name
    }

    Skill(String id, String name) {
        this.id = id
        this.name = name
    }

    @Override
    String toString() {
        return "nome = " + name
    }
}
