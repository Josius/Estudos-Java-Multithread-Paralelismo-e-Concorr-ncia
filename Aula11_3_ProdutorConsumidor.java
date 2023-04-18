import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Aula11_3_ProdutorConsumidor {

	private static final BlockingQueue<Integer> FILA = new LinkedBlockingQueue<>(5);

	public static void main(String[] args) {

		Runnable produtor = () -> {

			// simulaProcessamento();
			simulaProcessamentoLento();
			System.out.println("Produzindo");
			int numero = new Random().nextInt(1000);
			try {
				FILA.put(numero);
				System.out.println(numero);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}

		};
		Runnable consumidor = () -> {
			
			simulaProcessamento();
			// simulaProcessamentoLento();
			System.out.println("Consumindo");
			try {
				Integer take = FILA.take();
				System.out.println(take);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		};

		Aula11Janela.monitore(() -> String.valueOf(FILA.size()));

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

		executor.scheduleWithFixedDelay(produtor, 0, 10, TimeUnit.MILLISECONDS);
		executor.scheduleWithFixedDelay(consumidor, 0, 10, TimeUnit.MILLISECONDS);

	}

	private static void simulaProcessamento() {
		int tempo = new Random().nextInt(40);
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
	
	private static void simulaProcessamentoLento() {
		int tempo = new Random().nextInt(400);
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}
