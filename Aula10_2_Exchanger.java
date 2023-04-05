import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Aula10_2_Exchanger {

	private static final Exchanger<String> EXCHANGER = new Exchanger<>();

	public static void main(String[] args) {

		ExecutorService executor = Executors.newCachedThreadPool();

		Runnable r1 = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " Beba água!");
			String mensagem = "Beba água!";
			String retorno = exchange(mensagem);
			System.out.println(name + " - " + retorno);
		};
		
		Runnable r2 = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " Obregadu");
			String mensagem = "Obregadu";
			String retorno = exchange(mensagem);
			System.out.println(name + " - " + retorno);
		};

		executor.execute(r1);
		executor.execute(r2);

		executor.shutdown();
	}

	private static String exchange(String mensagem) {
		try {
			return EXCHANGER.exchange(mensagem);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			return "Deu exception";
		}
	}

	
}
