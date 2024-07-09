public class OperacaoDeposito extends Operacao{

    OperacaoDeposito(double valor){
        super(valor);
        this.setTipo('d');
    }

    public int compareTo(OperacaoDeposito operacao) {

        if (this.data.before(operacao.data)) {
            return -1;
        } else if (this.data.after(operacao.data)) {
            return 1;
        } else {
            return 0;
        }
    }

}
