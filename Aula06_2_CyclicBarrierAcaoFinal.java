import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Aula06_2_CyclicBarrierAcaoFinal {

    private static BlockingQueue<Double> resultados = new LinkedBlockingQueue<>();
    public static void main(String[] args) {
        
        Runnable finalizacao = () -> {
            System.out.println("Somando tudo");
            double resultadoFinal = 0;
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            
            System.out.println("Processo finalizado -  Resultado final: " + resultadoFinal);
        };

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, finalizacao);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            resultados.add(432d*3d);
            await(cyclicBarrier);
            System.out.println("Terminei - r1");
        };
        Runnable r2 = () -> {
            resultados.add(Math.pow(3d, 14d));    
            await(cyclicBarrier);
            System.out.println("Terminei - r2");
        };
        Runnable r3 = () -> {
            resultados.add(45d*127d/12d);
            await(cyclicBarrier);
            System.out.println("Terminei - r3");
        };

        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);

        executor.shutdown();
    }

    private static void await(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
