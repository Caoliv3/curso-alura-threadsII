package br.com.threadsII.servidor;

import java.util.LinkedList;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class TesteFila {

    public static void main(String[] args) throws InterruptedException{

        BlockingQueue<String> filaComandos = new ArrayBlockingQueue<String>(3);

//        filaComandos.offer("c1");
//        filaComandos.offer("c2");
//        filaComandos.offer("c3");

        filaComandos.put("c1");
        filaComandos.put("c2");
        filaComandos.put("c3");
        filaComandos.put("C4");


//        System.out.println(filaComandos.peek());
//        System.out.println(filaComandos.poll());
//        System.out.println(filaComandos.poll());

        System.out.println(filaComandos.take());
        System.out.println(filaComandos.take());
        System.out.println(filaComandos.take());
        System.out.println(filaComandos.take());

        System.out.println(filaComandos.size());
    }
}
