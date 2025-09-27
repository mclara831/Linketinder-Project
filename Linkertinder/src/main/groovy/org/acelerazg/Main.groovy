package org.acelerazg

import org.acelerazg.cli.UI
import org.acelerazg.services.PessoaService

static void main(String[] args) {

    UI ui = new UI()
    PessoaService service = new PessoaService()

    Scanner sc = new Scanner(System.in)
    boolean continuar = true
    int opcao

    while (continuar) {
        ui.menu()
        opcao = sc.nextInt()

        switch (opcao) {
            case 1:
                service.empresas.each { it -> println(it.toString()) }
                break
            case 2:
                def empresa = ui.cadastrarEmpresa()
                service.empresas.add(empresa)
                break
            case 3:
                service.candidatos.each { it -> println(it.toString()) }
                break
            case 4:
                def candidato = ui.cadastrarCandidato()
                service.candidatos.add(candidato)
                break
            case 0:
                println("Obrigado por utilizar o Linketinder!")
                continuar = false
                break
            default:
                println("Opção inválida!")
                break
        }
    }

}