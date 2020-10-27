package br.com.threadsII.excecao;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorDeTestes {

    private AtomicBoolean estaRodando = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        ServidorDeTestes servidor = new ServidorDeTestes();
        servidor.rodar();
        servidor.alterandoAtributo();
    }

    private void rodar() {

       Thread thread =  new Thread(new Runnable() {
                public void run() {

                        System.out.println("Servidor começando, estaRodando = " + estaRodando);

                        while (!estaRodando.get()) {
                        }

                        if (estaRodando.get()) {
                            throw new RuntimeException("Deu erro na thread ...");
                        }

                        System.out.println("Servidor rodando, estaRodando = " + estaRodando);

                        while (estaRodando.get()) {
                        }

                        System.out.println("Servidor terminando, estaRodando = " + estaRodando);

                }
            });

       thread.setUncaughtExceptionHandler(new TratadorExcecao());
       thread.start();
    }

    private void alterandoAtributo() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando = true");
        estaRodando.set(true);

        Thread.sleep(5000);
        System.out.println("Main alterando estaRodando = false");
        estaRodando.set(false);
    }
}