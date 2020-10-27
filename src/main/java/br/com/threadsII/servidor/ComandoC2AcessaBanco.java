package br.com.threadsII.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String> {

    PrintStream saidaCliente;

    public ComandoC2AcessaBanco(PrintStream saidaCliente) {

        this.saidaCliente = saidaCliente;

    }

    @Override
    public String call() throws Exception{
        System.out.println("Servidor recebeu comando c2 - Banco");
        saidaCliente.println("procesando comando c2 - Banco");

        Thread.sleep(15000);

        int numero = new Random().nextInt(100) + 1;

        System.out.println("Servidor finalizou comando c2 - Banco");

        return Integer.toString(numero);
    }
}
