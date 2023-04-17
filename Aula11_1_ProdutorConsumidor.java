import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Aula11_1_ProdutorConsumidor {

	private static final List<Integer> LISTA = new ArrayList<>(5);
	private static boolean produzindo = true;
	private static boolean consumindo = true;

	public static void main(String[] args) {
		
		Thread produtor = new Thread(() -> {

			while(true){
				simulaProcessamento();
				if (produzindo) {
					System.out.println("Produzindo");
					int numero = new Random().nextInt(1000);
					LISTA.add(numero);
				
				// se houver atingido o tamanho máximo da lista, para de produzir
					if (LISTA.size() == 5) {
						System.out.println("Pausando produtor");
						produzindo = false;
					}
					// se houver ao menos 1 elemento na lista, consumidor volta a consumir
					if (LISTA.size() == 1) {
						System.out.println("Iniciando consumidor");
						consumindo = true;
					}
				}else {
					System.out.println("!!!Produtor Dormindo");
				}
			} 
		});
		Thread consumidor = new Thread(() -> {
			
			while(true){
				simulaProcessamento();
				if (consumindo) {
					System.out.println("Consumindo");
					Optional<Integer> numero = LISTA.stream().findFirst();
					numero.ifPresent(n -> {
						LISTA.remove(n);
					});
					// se a lista estiver vazia, para de consumir
					if (LISTA.size() == 0) {
						System.out.println("Pausando consumidor");
						consumindo = false;
					}
					// se ainda houver espaço para produzir, produza
					if (LISTA.size() == 4) {
						System.out.println("Iniciando produtor");
						produzindo = true;
					}
				}else {
					System.out.println("???Consumidor Dormindo");
				}
			}
			
		});

		// Janelas.m
		produtor.start();
		consumidor.start();

	}

	private static void simulaProcessamento(){
		int tempo = new Random().nextInt(10);
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}
