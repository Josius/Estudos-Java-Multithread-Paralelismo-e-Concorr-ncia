import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Aula09_2_ReentrantReadWriteLock {
	
	private static int contador = -1;

	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();

		Runnable r1 = () -> {
			Lock writeLock = lock.writeLock();
			writeLock.lock();
			String name = Thread.currentThread().getName();
			System.out.println(name + " - Escrevendo - contador: " + contador);
			contador++;
			System.out.println(name + " - Escrito - contador: " + contador);
			writeLock.unlock();
		};

		Runnable r2 = () -> {
			Lock readLock = lock.readLock();
			readLock.lock();
			System.out.println("Lendo - contador: " + contador);
			System.out.println("Lido - contador: " + contador);
			readLock.unlock();
		};

		for (int i = 0; i < 6; i++) {
			executor.execute(r1);
			executor.execute(r2);
		}

		executor.shutdown();
	}
}
