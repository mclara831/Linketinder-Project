import org.acelerazg.cli.UI
import org.acelerazg.models.Candidato
import org.acelerazg.models.Empresa

static void main(String[] args) {

    List<Candidato> candidatos = new ArrayList<>()
    List<Empresa> empresas = new ArrayList<>()

    UI.cadastraPessoas(candidatos, empresas)

    Scanner sc = new Scanner(System.in)
    boolean continuar = true
    int opcao

    while (continuar) {
        UI.menu()
        opcao = sc.nextInt()

        switch (opcao) {
            case 1:
                empresas.each {it -> println(it.toString())}
                break
            case 2:
                def empresa = UI.cadastrarEmpresa()
                empresas.add(empresa)
                break
            case 3:
                candidatos.each {it -> println(it.toString())}
                break
            case 4:
                def candidato = UI.cadastrarCandidato()
                candidatos.add(candidato)
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