import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Aula07_1_CountDownLatch {
    
    private static volatile int contadorI = 0;
    private static CountDownLatch latch = new CountDownLatch(3);
    public static void main(String[] args) {
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        Runnable r1 = () -> {
            int contadorJ = new Random().nextInt(100);
            int contadorX = contadorI * contadorJ;
            System.out.println(contadorI + " x " + contadorJ + " = " + contadorX);
            latch.countDown();
        };

        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);

        while(true){
            await();
            contadorI = new Random().nextInt(100);
            latch = new CountDownLatch(3);
        }
    }

    private static void await(){

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
