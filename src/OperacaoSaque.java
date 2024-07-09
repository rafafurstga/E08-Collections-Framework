public class OperacaoSaque extends Operacao{

    public double calculaTaxas(){return 0.05;}

    public OperacaoSaque(double valor) {
        super(valor);
        this.setTipo('s');
    }

    public int compareTo(OperacaoSaque operacao) {

        if (this.data.before(operacao.data)) {
            return -1;
        } else if (this.data.after(operacao.data)) {
            return 1;
        } else {
            return 0;
        }
    }
}
