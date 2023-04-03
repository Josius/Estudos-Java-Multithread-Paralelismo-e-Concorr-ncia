import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Aula08_2_Semaforo {
	
	private static final Semaphore SEMAFORO = new Semaphore(15);

	private static final AtomicInteger QUANTIDADE = new AtomicInteger(0);
    public static void main(String[] args) {
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(501);

        Runnable r1 = () -> {
            String name = Thread.currentThread().getName();
            int usuario = new Random().nextInt(10000);

            boolean conseguiu = false;
			
			QUANTIDADE.incrementAndGet();
			while(!conseguiu){
				conseguiu = tryAcquire();
			}
			QUANTIDADE.decrementAndGet();

            System.out.println("Usuário " + usuario + " usou a thread " + name);
            sleep();

            SEMAFORO.release();
        };

		// r2 exibe quantas tarefas ainda estão esperando para serem executadas
		Runnable r2 = () -> {
			System.out.println(QUANTIDADE.get());
		};

        for (int i = 0; i < 500; i++) {
            executor.execute(r1);
        }
		
		executor.scheduleWithFixedDelay(r2, 0, 100, TimeUnit.MILLISECONDS);
    }

    private static void sleep() {
        // espera de 1 a 6 segundos
        try {
            int tempoEspera = new Random().nextInt(6);
            tempoEspera++;
            Thread.sleep(1000 * tempoEspera);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static boolean tryAcquire() {
        try {
            return SEMAFORO.tryAcquire(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
			return false;
        }
    }
}
