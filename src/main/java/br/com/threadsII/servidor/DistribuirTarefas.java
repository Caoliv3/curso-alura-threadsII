package br.com.threadsII.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable{

    private Socket socket;
    private App servidor;
    private ExecutorService threadPool;
    private BlockingQueue<String> filaComandos;

    public DistribuirTarefas(ExecutorService threadPool, BlockingQueue<String> filaComandos, Socket socket, App servidor) {
        this.socket = socket;
        this.servidor = servidor;
        this.threadPool = threadPool;
        this.filaComandos = filaComandos;
    }

    @Override
    public void run() {

        try {
        System.out.println("Distribuir tarefas para " + socket);
        Scanner entradaClietne = new Scanner(socket.getInputStream());

            PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

            while (entradaClietne.hasNextLine()){
            String comando = entradaClietne.nextLine();
            System.out.println("comando recebido " + comando);
           switch (comando){
               case "c1":{
                   saidaCliente.println("Confirmacao comando c1");
                   ComandoC1 c1 = new ComandoC1(saidaCliente);
                   this.threadPool.execute(c1);
                   break;
               }
               case "c2":{
                   saidaCliente.println("Confirmacao comando c2");
                   ComandoC2ChamaWS c2WS = new ComandoC2ChamaWS(saidaCliente);
                   this.threadPool.submit(c2WS);
                   ComandoC2AcessaBanco c2Banco = new ComandoC2AcessaBanco(saidaCliente);
                   Future<String> futureWS = this.threadPool.submit(c2WS);
                   Future<String> futureBanco = this.threadPool.submit(c2Banco);

                   this.threadPool.submit(new JuntaResultadoFutures(futureBanco, futureWS, saidaCliente));

                   break;
               }case "12": {
                   saidaCliente.println("Desligando o servidor");
                   servidor.parar();
                   break;
               } case "c3": {
                   this.filaComandos.put(comando);
                   saidaCliente.println("Comando c3 adicionado na fila");
                   break;
               }
               default:{
                   saidaCliente.println("Comando nao encontrado");
               }
           }
           
//            System.out.println("Comando " + comando);
        }
            saidaCliente.close();
            entradaClietne.close();
            Thread.sleep(20000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
