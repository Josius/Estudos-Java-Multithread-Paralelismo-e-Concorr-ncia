import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aula11_2_ProdutorConsumidor {

	private static final BlockingQueue<Integer> FILA = new LinkedBlockingQueue<>(5);
	private static volatile boolean produzindo = true;
	private static volatile boolean consumindo = true;
	private static final Lock LOCK = new ReentrantLock();

	public static void main(String[] args) {

		Thread produtor = new Thread(() -> {

			while (true) {
				simulaProcessamento();
				if (produzindo) {
					LOCK.lock();
					System.out.println("Produzindo");
					int numero = new Random().nextInt(1000);
					FILA.add(numero);
					
					// se houver atingido o tamanho máximo da FILA, para de produzir
					if (FILA.size() == 5) {
						System.out.println("Pausando produtor");
						produzindo = false;
					}
					// se houver ao menos 1 elemento na FILA, consumidor volta a consumir
					if (FILA.size() == 1) {
						System.out.println("Iniciando consumidor");
						consumindo = true;
					}
					LOCK.unlock();
				} else {
					System.out.println("!!!Produtor Dormindo");
				}
			}
		});
		Thread consumidor = new Thread(() -> {
			
			while (true) {
				try {
					simulaProcessamento();
					if (consumindo) {
						LOCK.lock();
						System.out.println("Consumindo");
						Optional<Integer> numero = FILA.stream().findFirst();
						numero.ifPresent(n -> {
							FILA.remove(n);
						});
						// se a FILA estiver vazia, para de consumir
						if (FILA.size() == 0) {
							System.out.println("Pausando consumidor");
							consumindo = false;
						}
						// se ainda houver espaço para produzir, produza
						if (FILA.size() == 4) {
							System.out.println("Iniciando produtor");
							produzindo = true;
						}
						LOCK.unlock();
					} else {
						System.out.println("???Consumidor Dormindo");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
		Aula11Janela.monitore(() -> String.valueOf(FILA.size()));
		
		produtor.start();
		consumidor.start();
		
	}
	
	private static void simulaProcessamento() {
		int tempo = new Random().nextInt(10);
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}
