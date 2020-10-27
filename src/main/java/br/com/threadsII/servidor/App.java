package br.com.threadsII.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private  ExecutorService threadPool;
    private  ServerSocket servidor;
    private volatile boolean rodando;
    private BlockingQueue<String> filaComandos;

    public App() throws IOException{

        System.out.println("INICIANDO SERVIDOR");
        this.servidor = new ServerSocket(12345);
        this.threadPool =  Executors.newCachedThreadPool(new FabricaThread());
        this.rodando = true;
        this.filaComandos = new ArrayBlockingQueue<>(2);
        inciarCosumidores();

    }

    private void inciarCosumidores() {

        int qtdConsumidores = 2;
        for (int i = 0; i < qtdConsumidores; i++){
            TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
            this.threadPool.execute(tarefa);

        }
    }

    public static void main( String[] args ) throws Exception {

        App servidor = new App();
        servidor.rodar();
        servidor.parar();
    }

    public void rodar() throws IOException {
        while (this.rodando) {
            try {
                Socket socket = servidor.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());
                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool,filaComandos, socket, this);
                threadPool.execute(distribuirTarefas);
            }catch(SocketException e){
                System.out.println("SocketException, esta rodando? " + this.rodando);
            }
        }
    }

    public  void parar() throws IOException{
        this.rodando = false;
        servidor.close();
        threadPool.shutdown();
    }
}
