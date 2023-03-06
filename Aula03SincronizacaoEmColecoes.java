import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aula03SincronizacaoEmColecoes {

    private static List<String> lista = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        
        // Sincronizar coleções
        lista = Collections.synchronizedList(lista);
        MeuRunnableAula03 runnableAula03 = new MeuRunnableAula03();
        Thread threadUm = new Thread(runnableAula03);
        Thread threadDois = new Thread(runnableAula03);
        Thread threadTres = new Thread(runnableAula03);
        threadUm.start();
        threadDois.start();
        threadTres.start();
        Thread.sleep(500);
        System.out.println(lista);
    }   
    
    public static class MeuRunnableAula03 implements Runnable{

        @Override
        public void run() {
            lista.add("Hello there!");
            String nameThread =  Thread.currentThread().getName();
            System.out.println(nameThread + " inseriu na lista!");
        }
    }
}