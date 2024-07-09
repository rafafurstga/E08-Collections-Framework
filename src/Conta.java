import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Comparable;

public abstract class Conta implements ITaxas{

    protected int numero;

    protected Cliente dono;

    protected double saldo;

    protected double limite;

    protected List<Operacao> operacoes = new ArrayList<Operacao>();

    protected int proximaOperacao;

    protected static int totalContas = 0;


    List<Operacao> fazOperacaoS(double valor){
        this.operacoes.add(new OperacaoSaque(valor));
        return this.operacoes;
    }
    List<Operacao> fazOperacaoD(double valor){
        this.operacoes.add(new OperacaoDeposito(valor));
        return this.operacoes;
    }

    public boolean sacar(double valor) {
        if (valor >= 0 && valor <= this.limite) {
            this.saldo -= valor;
            fazOperacaoS(valor);
            this.saldo-=this.operacoes.get(this.operacoes.size() - 1).calculaTaxas();
            return true;
        }

        return false;
    }

    public void depositar(double valor) {
        this.saldo += valor;
        fazOperacaoD(valor);
        this.saldo-=this.operacoes.get(this.operacoes.size() - 1).calculaTaxas();
    }

    public boolean transferir(Conta destino, double valor) {
        boolean valorSacado = this.sacar(valor);
        if (valorSacado) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public String toString() {
        String string = "===== Conta " + this.numero + " =====\n" + "Dono: " + this.dono.getNome() + "\n" + "Saldo: " + this.saldo + "\n" + "Limite: " + this.limite + "\n====================";
        return string;
    }

    //Flag 0 para caso (a), Flag 1 para caso (b)
    public void imprimirExtrato(int flag) {
        switch (flag) {
            case 0:
                Collections.sort(operacoes);
            break;
            case 1:
                List<Operacao> deposito = new ArrayList<Operacao>();
                List<Operacao> saque = new ArrayList<Operacao>();
                Collections.sort(operacoes);
                for (Operacao operacao : operacoes) {
                    if(operacao instanceof OperacaoDeposito){
                        deposito.add(operacao);
                    }else if(operacao instanceof OperacaoSaque){
                        saque.add(operacao);
                    }
                }
                deposito.addAll(saque);
                operacoes = deposito;
            break;

        }
        System.out.println("======= Extrato Conta " + this.numero + "======");
        for(Operacao atual : this.operacoes) {
            if (atual != null) {
                System.out.println(atual.toString());
            }
        }
        System.out.println("====================");
    }

    public void ExtratoDeTaxas(){
        System.out.println("=== Extrato de Taxas - Conta " + this.numero + "===");
        System.out.println("Manutenção da conta: " + calculaTaxas());
        System.out.println("Operações");
        double Total = 0;
        Total += calculaTaxas();
        for(Operacao atual : operacoes) {
            if (atual != null) {
                if(atual.calculaTaxas() != 0){
                    String tipo = "";
                    if(atual.tipo == 's'){
                        tipo = "Saque";
                    } else if(atual.tipo == 'd'){
                        tipo = "Deposito";
                    }
                    if(tipo != ""){
                        Total+=atual.calculaTaxas();
                        System.out.println(tipo + ": " + atual.calculaTaxas());
                    }
                }

            }
        }
        System.out.println("");
        System.out.println("Total: "+Total);
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getDono() {
        return dono;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public static int getTotalContas() {
        return Conta.totalContas;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public boolean setLimite(double limite) {
        if (limite < 0)
            limite = 0;

        this.limite = limite;
        return true;
    }

    public boolean equals(Conta conta){
        if(this.numero == conta.numero){
            return true;
        }else{
            return false;
        }
    }

    public double calculaTaxas(){
        return 0;
    }

    Conta(){

    }
}
