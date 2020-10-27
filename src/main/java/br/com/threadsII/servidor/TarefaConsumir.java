package br.com.threadsII.servidor;

import java.nio.file.FileAlreadyExistsException;
import java.util.concurrent.BlockingQueue;

public class TarefaConsumir implements Runnable{

    private BlockingQueue<String> filaComandos;

    public TarefaConsumir(BlockingQueue<String> filaComandos){

        this.filaComandos = filaComandos;

    }

    @Override
    public void run() {

        try{

            String comando = null;
            while ((comando = filaComandos.take()) != null){
                System.out.println("Consumindo comando c3 " + comando + " - " + Thread.currentThread().getName());
                Thread.sleep(5000);
            }

        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
