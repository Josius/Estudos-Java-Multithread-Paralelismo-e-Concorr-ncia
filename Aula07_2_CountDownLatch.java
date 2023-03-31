import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Aula07_2_CountDownLatch {
    
    private static volatile int contadorI = 0;
    private static CountDownLatch latch = new CountDownLatch(3);
    public static void main(String[] args) {
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

        Runnable r1 = () -> {
            int contadorJ = new Random().nextInt(100);
            int contadorX = contadorI * contadorJ;
            System.out.println(contadorI + " x " + contadorJ + " = " + contadorX);
            latch.countDown();
        };
        Runnable r2 = () -> {
            await();
            contadorI = new Random().nextInt(100);
        };
        Runnable r3 = () -> {
            await();
            latch = new CountDownLatch(3);
        };
        Runnable r4 = () -> {
            await();
            System.out.println("Terminou! " + "Bora reiniciar " + " o começo!");
        };


        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);
// Assim que acabar de executar qualquer uma das tarefas abaixo, depois de 1 segundo será agendado para ser executado novamente
        executor.scheduleWithFixedDelay(r2, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r3, 0, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(r4, 0, 1, TimeUnit.SECONDS);

    }

    private static void await(){

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
