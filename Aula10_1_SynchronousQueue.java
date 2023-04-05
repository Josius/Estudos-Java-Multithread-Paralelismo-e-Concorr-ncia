import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class Aula10_1_SynchronousQueue {

	private static final SynchronousQueue<String> FILA = new SynchronousQueue<>();
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();

		Runnable r1 = () -> {
			put();
			System.out.println("Entrou na fila");
		};
		
		Runnable r2 = () -> {
			String mensagem = take();
			System.out.println("Retirou da fila " + mensagem);
		};

		executor.execute(r1);
		executor.execute(r2);
		
		executor.shutdown();
	}

	private static String take() {
		try {
			
			return FILA.take();
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
			e.printStackTrace();
			return "Exceção ocorrida";
		}
	}
	private static void put() {
		try {
			
			FILA.put("Entrando na fila");
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}