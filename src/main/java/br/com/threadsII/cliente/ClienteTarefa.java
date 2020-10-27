package br.com.threadsII.cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClienteTarefa {

    public static void main(String[] args) throws IOException {

        final Socket socket = new Socket("localhost", 12345);
        System.out.println("Conexao estabelecida");

        Thread threadEnviaComando = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Envia comandos");
                    PrintStream saida = new PrintStream(socket.getOutputStream());
                    Scanner teclado = new Scanner(System.in);
                    while (teclado.hasNextLine()) {
                        String linha = teclado.nextLine();
                        if (linha.trim().equals("")) {
                            break;
                        }
                        saida.println(linha);
                    }
                    saida.close();
                    teclado.close();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });

        System.out.println("Recebendo dados do servidor");
        Thread threadRecebeResposta = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Scanner respostaServidor = new Scanner(socket.getInputStream());
                    while (respostaServidor.hasNextLine()) {
                        String linha = respostaServidor.nextLine();
                        System.out.println(linha);

                    }
                    respostaServidor.close();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });

        threadEnviaComando.start();
        threadRecebeResposta.start();

        try {
            threadEnviaComando.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        socket.close();
    }
}
