import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Aula06_1_CyclicBarrierNumParticipantes {
    public static void main(String[] args) {
        
        // Objetivo -> resolver a seguinte equação:
        // (432*3) + (3^14) + (45*127/12) = ?
        // vamos colocar uma thread para cada parte da conta
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable r1 = () -> {
            System.out.println(432d*3d);
            await(cyclicBarrier);
            System.out.println("Terminei - r1");
        };
        Runnable r2 = () -> {
            System.out.println(Math.pow(3d, 14d));    
            await(cyclicBarrier);
            System.out.println("Terminei - r2");
        };
        Runnable r3 = () -> {
            System.out.println(45d*127d/12d);
            await(cyclicBarrier);
            System.out.println("Terminei - r3");
        };
        // Runnable r4 = () -> {
        //     System.out.println(45d*127d/6d);
        //     await(cyclicBarrier);
        //     System.out.println("Terminei - r4");
        // };

        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);
        // executor.submit(r4);

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
