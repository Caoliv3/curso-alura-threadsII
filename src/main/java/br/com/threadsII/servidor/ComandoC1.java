package br.com.threadsII.servidor;

import java.io.PrintStream;

public class ComandoC1 implements Runnable{

    PrintStream saidaCliente;

    public ComandoC1(PrintStream saidaCliente) {

        this.saidaCliente = saidaCliente;
    }

    @Override
    public void run() {
        System.out.println("Ecexutando comando c1");
        try{
            Thread.sleep(20000);
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        saidaCliente.println("Comando executado com sucesso C1!");
    }
}
