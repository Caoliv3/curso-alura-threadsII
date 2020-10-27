package br.com.threadsII.servidor;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaResultadoFutures implements Callable<Void> {

    private Future<String> futureWS;
    private Future<String> futureBanco;
    private PrintStream saidaCliente;

    public JuntaResultadoFutures(Future<String> futureBanco, Future<String> futureWS, PrintStream saidaCliente) {

        this.futureWS = futureWS;
        this.futureBanco = futureBanco;
        this.saidaCliente = saidaCliente;

    }

    @Override
    public Void call()  {

        System.out.println("Aguardando resultados da futere Ws  e Banco");

        try {
            String numeroAleatorio = this.futureWS.get(20, TimeUnit.SECONDS);
            String numeroAleatorio1 = this.futureBanco.get(20, TimeUnit.SECONDS);

            this.saidaCliente.println("Resultado comando c2 " + numeroAleatorio + " - " + numeroAleatorio1);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("timeout: Cancelando execucao do comando c2");
           this.saidaCliente.println("Timeout na execucao do comando c2 ");
           this.futureBanco.cancel(true);
           this.futureWS.cancel(true);
        }

        System.out.println("Finalizou JuntaResultadoFutures");
        return null;
    }
}
