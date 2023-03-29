import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Aula06_3_CyclicBarrierCiclico {

    private static BlockingQueue<Double> resultados = new LinkedBlockingQueue<>();
    private static ExecutorService executor;
    private static Runnable r1;
    private static Runnable r2;
    private static Runnable r3;
    private static double resultadoFinal = 0;

    public static void main(String[] args) {

        Runnable sumarizacao = () -> {
            System.out.println("Somando tudo");
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();
            resultadoFinal += resultados.poll();

            System.out.println("Processo finalizado -  Resultado final: " + resultadoFinal);
            System.out.println("----------------------------");
            restart();
        };

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, sumarizacao);

        executor = Executors.newFixedThreadPool(3);

        r1 = () -> {
            resultados.add(432d * 3d);
            await(cyclicBarrier);
            System.out.println("Terminei - r1");
        };
        r2 = () -> {
            resultados.add(Math.pow(3d, 14d));
            await(cyclicBarrier);
            System.out.println("Terminei - r2");
        };
        r3 = () -> {
            resultados.add(45d * 127d / 12d);
            await(cyclicBarrier);
            System.out.println("Terminei - r3");
        };

        restart();
        // executor.shutdown();
    }

    private static void await(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static void restart() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);
    }
}
