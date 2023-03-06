import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Aula03SincronizacaoEmColecoesParteDois {
    
    private static List<String> lista = new CopyOnWriteArrayList<>();
    private static Map<Integer, String> mapa = new ConcurrentHashMap<>();
    private static BlockingQueue<String> fila = new LinkedBlockingQueue<>();
    
    public static void main(String[] args) throws InterruptedException {
        
        // Coleções para concorrência
        MeuRunnableAula03Lista runnableAula03Lista = new MeuRunnableAula03Lista();
        MeuRunnableAula03Mapa runnableAula03Mapa = new MeuRunnableAula03Mapa();
        MeuRunnableAula03Fila runnableAula03Fila = new MeuRunnableAula03Fila();

        Thread threadUm = new Thread(runnableAula03Lista);
        Thread threadDois = new Thread(runnableAula03Lista);
        Thread threadTres = new Thread(runnableAula03Lista);
        Thread threadQuatro = new Thread(runnableAula03Mapa);
        Thread threadCinco = new Thread(runnableAula03Mapa);
        Thread threadSeis = new Thread(runnableAula03Mapa);
        Thread threadSete = new Thread(runnableAula03Fila);
        Thread threadOito = new Thread(runnableAula03Fila);
        Thread threadNove = new Thread(runnableAula03Fila);
        
        threadUm.start();
        threadDois.start();
        threadTres.start();
        threadQuatro.start();
        threadCinco.start();
        threadSeis.start();
        threadSete.start();
        threadOito.start();
        threadNove.start();
        
        Thread.sleep(500);
        
        System.out.println(lista);
        System.out.println(mapa);
        System.out.println(fila);
    }   
    
    public static class MeuRunnableAula03Lista implements Runnable{

        @Override
        public void run() {
            lista.add("Hello there!");
            String nameThread =  Thread.currentThread().getName();
            System.out.println(nameThread + " inseriu na lista!");
        }
    }
    public static class MeuRunnableAula03Mapa implements Runnable{

        @Override
        public void run() {
            mapa.put(new Random().nextInt(3), "General");
            String nameThread =  Thread.currentThread().getName();
            System.out.println(nameThread + " inseriu no mapa!");
        }
    }
    public static class MeuRunnableAula03Fila implements Runnable{

        @Override
        public void run() {
            fila.add("Kenobi!");
            String nameThread =  Thread.currentThread().getName();
            System.out.println(nameThread + " inseriu na fila!");
        }
    }
}
