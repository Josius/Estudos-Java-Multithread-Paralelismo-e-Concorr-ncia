# **Estudos Java Multithread Paralelismo e Concorrência**
## **Fonte de estudos: [RinaldoDev - Multithread, Paralelismo e Concorrência](https://www.youtube.com/playlist?list=PLuYctAHjg89YNXAXhgUt6ogMyPphlTVQG)**
### **Execução Monothread**
Execução seqüêncial das linhas do código. Ou seja, se tivermos duas execuções de programas diferentes, um processo suportará somente um programa em seu espaço de endereçamento.
### **Execução Multithread**
Execução em paralelo das linhas do código. Neste caso, com o mesmo exemplo acima, ocorrerá a execução de parte do código do programa 1, depois de um tempo executará parte do código do programa 2, deixando o programa 1 em suspensão, em seguida o cenário troca para o programa 2 em suspensão e execução de parte do programa 1, e assim sucessivamente até que todas as execuções seja concluídas.
### **Paralelismo**
Execução de vários trechos de código no mesmo instante. Ou seja, são trechos de código executando em núcleos diferentes do processador. Paralelismo só pode ocorrer em processadores com dois núcleos ou mais.
### **Concorrência**
Várias execuções de código concorrendo pelo mesmo recurso. O recurso pode ser uma variável, um acesso ao banco de dados, uma chamada ao SO, um acesso a uma API externa, um acesso a uma lista ou mapa, ou até mesmo um acesso a um string builder,  então, se houver dois processos diferentes tentando acessar esse recurso ao mesmo tempo, ocorre a concorrência.
### **Aplicações Web**
Spring, Quarkus, JBoss, Wildfly, etc.

Muitas vezes, esses frameworks possuem formas próprias implementadas para lidar com multithread, paralelismo e concorrência. Precisa verificar como eles tratam esses assuntos.

### **Quando usar?**
- Processos batch/em lote - grande carga de dados com um processamento pesado e depois gerar um resultado; oriunda de um banco de dados, arquivo, requisição, etc.; paralelismo é usado para processar essa grande quantidade de dados
- Aplicações que executam no cliente - mobile, desktop; 

## **Aula 01 - Múltiplas Threads em Java - Thread e Runnable**
### **Descobrindo a thread atual**
Um getName() sobre um objeto.
### **Criando um objeto que representa uma nova thread**
**Forma mais simples:** instânciando um objeto Thread passando uma classe que implementa a interface Runnable. Essa classe precisa implementar o método run() da interface Runnable.

Entretanto, dessa forma não criamos exatamente uma nova thread, o que fazemos é criar um objeto de thread e mandamos executar na thread main.

**Método start():** o método start instrui a JVM a chamar o método run dessa thread assim que for possível. Ou seja, passa a decisão de quando chamar o método run para a JVM, ao invés de mandar para a thread main. Com isso a execução do método run será executado em uma thread diferente da main.

**Implementando Runnable com uma função lambda:** instânciamos um objeto Thread e ao invés de passar uma classe que implementa a interface Runnable, passamos uma função lambda com o que queremos fazer.

**Exception - IllegalThreadStateException:** Não podemos mandar executar a mesma thread mais de uma vez pois gerará a exception em negrito. A thread tem um sistema de controle interno o qual gerencia ela própria para saber se está executando, esperando, se foi interrompida, se está parada, etc. Esses vários estados da thread é um controle interno que impede iniciar a mesma thread mais de uma vez. Para executar o mesmo tipo de processo que uma thread executa mais de uma vez, precisamos criar uma nova thread.

**Várias Threads:** Como visto acima, não podemos chamar o método start mais de uma vez para uma thread, mas podemos executar o mesmo Runnable mais de uma vez. Ou seja, criamos threads diferentes que usam a mesma implementação de Runnable. Há alguns problemas de concorrência, mas mesmo assim, pode-se instânciar um Runnable e implementar o mesmo Runnable para threads diferentes.

Quando criamos várias threads, estamos criando novas linhas de execução paralelas dentro do programa. As threads podem executar em diversas ordens.

## **Aula 02 - Concorrência em Java - Sincronizar Recursos**
### **Concorrência**
No exemplo, temos uma variável global que é acessada por 5 threads diferentes, todas elas estão com a mesma instância de Runnable e executando todas elas, ou seja, o método run está sendo executado em paralelo por 5 threads diferentes, 5 vezes. E cada thread está incrementando a variável global.

Pensemos agora que a variável global é um recurso compartilhado sendo acessado por várias threads. Como resultado, não temos garantia de qual thread executará 1º, resultando em valores diferentes e iguais para cada nova execução desse programa.

Um dos resultados possíveis:
```
Thread-0: 1
Thread-1: 1
Thread-2: 2
Thread-4: 4
Thread-3: 3
```

### **Sincronização**
#### **syncronized no método run**
Com a palavra reservada *_syncronized_* evitamos a saída desordenada demonstrada acima. A ordem da saída pode não ser organizada, mas o resultado será organizado:
```
Thread-0: 0
Thread-4: 1
Thread-3: 2
Thread-2: 3
Thread-1: 4
```
Essa palavra reservada faz com que somente uma thread execute o método run dentro da instância de Runnable, ou seja, será executada uma de cada vez. Independente de qual thread chegar 1º para executar o método run, as outros que chegarem depois só serão executadas após a finalização da anterior. 

Neste caso, não temos mais o paralelismo, mas conseguimos resolver o problema de concorrência sobre o recurso.

#### **syncronized em um bloco de código**
Podemos criar um bloco de código com a palavra reservada syncronized passando a palavra reservada this, e dentro do bloco de código colocamos o código que queremos executar sincronizado. Fora do bloco também podemos alocar mais código, se necessário. Desta forma executará igual ao modo descrito acima, a diferença é que quando usamos um bloco sincronizado, precisamos informar em qual objeto faremos a sincronia (palavra reservada this). Isso porque, podemos ter vários blocos diferentes sincronizados, mas não queremos que a sincronia se dê no mesmo objeto. 

Exemplo de saída de dois blocos sincronizando o mesmo objeto:
```
Thread-0-Sincronizado em bloco: 0
Thread-0-Sincronizado em bloco: 1
Thread-4-Sincronizado em bloco: 2
Thread-4-Sincronizado em bloco: 3
Thread-3-Sincronizado em bloco: 4
Thread-3-Sincronizado em bloco: 5
Thread-2-Sincronizado em bloco: 6
Thread-2-Sincronizado em bloco: 7
Thread-1-Sincronizado em bloco: 8
Thread-1-Sincronizado em bloco: 9
```

Ou seja, voltamos para a concorrência. Para resolver isso, criamos dois objetos estáticos que servirão para regular essa sincronia:

```
Thread-0-Sincronizado em bloco: 0
Thread-4-Sincronizado em bloco: 2
Thread-3-Sincronizado em bloco: 3
Thread-2-Sincronizado em bloco: 4
Thread-0-Sincronizado em bloco: 1
Thread-1-Sincronizado em bloco: 5
Thread-2-Sincronizado em bloco: 6
Thread-3-Sincronizado em bloco: 7
Thread-4-Sincronizado em bloco: 8
Thread-1-Sincronizado em bloco: 9
```
#### **syncronized em um método estático**
Podemos alocar a palavra reservada syncronized na assinatura do método estático, funcionará normalmente. Mas se queremos sincronizar um bloco de código dentro do método estático, não podemos usar a palavra this, pois dentro de trecho estáticos não existe objeto para referenciar com this, não existe instância de objeto. Para resolver isso temos duas saídas:

1ª - Criar objeto estático (como acima) para servirem de lock.

2ª - Ou podemos fazer o lock na própria classe (nome_da_classe.class)

Mas dessa forma, estamos sincronizando toda a classe e, basicamente, somente uma thread pode executar o bloco de código na JVM inteira.
#### **Desvantagens de sincronização**
**syncronized no método run:** o método só é executado em uma thread de cada vez. Isson acaba com o paralelismo. Se o objetivo é usar da vantagem de criar threads e paralelismo, é melhor não usar esse método, pois é mais vantajoso usar os métodos convencionais. Ou mesmo usar um for e jogar o método run dentro dele, do que criar várias threads e sincronizá-las.
#### **Exemplo mais próximo do real**
No código de exemplo, isolamos o uso do bloco syncronized apenas para quando existe a concorrência. No caso, quando será acessada a variável global. Se o recurso está sendo concorrido, usamos o syncronized, do contrário, o restante do código não precisa de syncronized.
> código que não ocorre a concorrência:
```java
public void run() {
            int j;
```
> recurso que ocorre a concorrência (variavelGlobal):
```java
            synchronized(this){
                variavelGlobal++;
                j = variavelGlobal * 2;
            }
```            
> código que não ocorre a concorrência:
```java
            double jElevadoA10 = Math.pow(j, 100);
            double sqrt = Math.sqrt(jElevadoA10);
            System.out.println(sqrt);
        }
```

## **Aula 03 - Concorrência em Java - Não usar Syncronized com List ou Map - Parte 1**
### **Método .sleep()**
> `Thread.sleep(milissegundos);`

Temos a thread principal, que no caso é a main e se criarmos várias threads, teremos a thread main mais essas outras threads criadas. Entretanto, pode ocorrer que todas elas ainda estão sendo processadas e o programa continue o código, ou seja, a thread main continue a execução, resultando em uma saída sem todos os resultados das outras threads. Neste caso, usamos o método .sleep() para que a thread main aguarde um determinado tempo, permitindo assim que as outras threads sejam concluídas.

Porém, quando usamos List, mesmo usando o método .sleep(), pode ocorrer de que o resultado de todas as threads sejam adicionadas na List e a saída ainda esteja faltando algum resultado. Isso é um erro que ocorre na List, sendo uma Collection a não ser usada com Threads. Para resolver isso sincronizamos coleções.

### **Sincronizando coleções**
Sobreescrevendo a lista com uma versão sincronizada:
```java
List<String> lista = new ArrayList<>();
lista = Collections.synchronizedList(lista);
```
ou podemos usar:
```java
List<String> lista = Collections.synchronizedList(new ArrayList<>());
```
Com isso, a lista será acessada em algumas operações por apenas uma única thread.

Também temos várias outras versões de syncronized:
- synchronizedList
- synchronizedCollection
- synchronizedMap
- synchronizedSet

## **Aula 03 - Concorrência em Java - Coleções para concorrência - Parte 2**
### **Coleções para concorrência**
**CopyOnWriteArrayList**

Outra forma de sincronizar a lista:
> `private static List<String> lista = new CopyOnWriteArrayList<>();`

Classe CopyOnWriteArrayList<>(); é thread safe. É uma classe segura  para cenários multithread, com várias threads acessando a coleção, sem causar um efeito colateral. Entretanto, essa é uma classe pesada, pois sempre que estivermos modificando a lista, a classe faz uma cópia do array inteiro, com todos os elementos da lista. Isso não é performático, mas é uma classe boa pois é thread safe. Se houver muita alteração na lista, como .add() ou .remove(), é melhor não usar essa classe. É ideal para leitura, como .get() e .indexOf().

**ConcurrentHashMap**

Semelhante ao problema descrito no início da aula, com Map nós temos o mesmo problema, então para contornar isso, usamos a classe ConcurrentHashMap:
> `private static Map<Integer, String> mapa = new ConcurrentHashMap<>();`

Ela é thread safe e se comporta da forma ideal para não gerar erros. Sua desvantagem é que é um pouco mais lenta pois ela é sincronizada internamente.

**BlockingQueue e LinkedBlockingQueue**

> `private static BlockingQueue<String> fila = new LinkedBlockingQueue<>();`

Uma boa alternativa em comparação com a solução para lista, pois essas classes são thread safe. Um outro ponto é com relação ao uso da lista. Se acaso estivermos usando operações que inserem e retiram elementos do coleção constantemente, usar uma fila pode ser uma solução mais interessante, ao invés de usar uma lista. Afinal, uma fila adiciona elementos ao final da fila e remove elementos do início da fila. Também podemos limitar o tamanho da fila.

**LinkedBlockingDeque**

Também temos a classe LinkedBlockingDeque, uma fila que permite adicionar ou remover elementos tanto no final, quanto no início da fila. Classe thread safe.

## **Aula 03 - Concorrência em Java - Classes Atômicas - Parte 3**
### **Classes Atômicas**

Com a classe **_AtomicInteger_**, não precisamos sincronizar o método run, pois ela executa métodos atômicos, ou seja, os seus métodos executaram de uma vez só, não há como uma outra atrapalhar a 1ª thread durante sua execução. Não é uma operação sincronizada, mas opera como. Temos outras classes atômicas:
- AtomicLong
- AtomicDoble
- AtomicBoolean
- AtomicReference

## **Aula 04 - Volatile e Yield**
### **Método .yield()**
> `Thread.yield();`
- método que avisa ao processador que não há trabalho a ser feito naquele momento, logo o processador pausa a execução da thread e diponibiliza o tempo de processador dessa thread para outra. Depois o processador pode retornar para essa thread que verificar se há a ser feito. A thread não para, somente pausa.

### **Método .getState()**
> `thread.getState();`
- método que retorna o estado atual da thread. Possíveis estados:
    - NEW - ainda não iniciou seu estado
    - RUNNABLE - está executando na JVM
    - BLOCKED - está esperando um monitor lock
    - WAITING - esperando indefinidamente outra thread executar uma ação
    - TIMED_WAITING - esperando por um tempo determinado outra thread executar uma ação
    - TERMINATED - uma que terminou sua execução
### **Enum State.< Estado >**
> `State.TERMINATED;`
- enum que retorna algum dos 6 estados citados acima. 
- é interessante usar o .getState() junto com State.< Estado > para fazer alguma verificação booleana

### [Explicação da execução do código da classe Aula04YieldEVolatileParteDois sem a palavra reservada volatile - até 10:35](https://youtu.be/4bH-XilmJoI?list=PLuYctAHjg89YNXAXhgUt6ogMyPphlTVQG&t=542)

### **Palavra reservada volatile**
- indica para o programa não confiar no que está no cachê local dentro do processador. Escreva e leia esse dado sempre direto da memória ram. Não use o cachê local para acessar esse valor. Isso fará que ao ler a variável, sempre será lido o valor mais recente
- em geral não precisamos usar essa palavra reservada
- se usarmos ela, estaremos deixando ler o valor que está em cachê no processador para acessar a memória ram, logo, o programa se torna mais lento
- mas podemos também verificar que podemos estar perdendo um pouco de performance, mas ao mesmo tempo, como no exemplo, estamos usando paralelismo, o que incrementa a performance muito mais que a perca por usar volatile

### [Explicação da palavra reservada volatile - até 16:20](https://youtu.be/4bH-XilmJoI?list=PLuYctAHjg89YNXAXhgUt6ogMyPphlTVQG&t=680)

## **Aula 05 - Executores - Parte Um**
### **SingleThread com Runnable**
- semelhante a criar um Thread; instanciamos um objeto, passamos um runnable e executamos o executor
- precisamos chamar o método .shutdown(), do contrário o programa continua executando
- porém, ao chamar prematuramente o .shutdown(), ele pode não terminar de executar a tarefa
- para evitar isso, chamamos o .awaitTermination(timeout, unit) para esperar um determinado tempo antes de chamar o .shutdown()
- o .shutdown() também indica para não receber novas tarefas

Modo elegante de usar:
~~~java
ExecutorService executor = null;
    try {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(new Tarefa());
        executor.execute(new Tarefa());
        executor.execute(new Tarefa());
        executor.awaitTermination(5, TimeUnit.SECONDS);
    } catch (Exception e) {
        throw e;
    }finally{
        if (executor != null){ 
            executor.shutdown();
        }
    }
~~~

Uma grande diferença entre Thread e Executor é que para executar várias tarefas precisamos criar várias threads, com executor podemos usar somente um para várias tarefas.

- há também o método .shutdownNow(), o qual faz uma parada sem esperar que as tarefas sejam terminadas
- também podemos usar o método .submit() ao invés do .execute(). Este método tem um retorno do tipo Future<?>, o qual possuí vários funcionalidades que podem ser usadas

~~~java
ExecutorService executor = null;
    try {
        
        executor = Executors.newSingleThreadExecutor();
        
        executor.execute(new Tarefa());
        executor.execute(new Tarefa());
        executor.execute(new Tarefa());
        Future<?> future = executor.submit(new Tarefa());
        
        System.out.println(future.isDone());
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println(future.isDone());
        
    } catch (Exception e) {
        
        throw e;
    }finally{
        
        if (executor != null){ 
            
            executor.shutdownNow();
        }
    }
~~~

### **SingleThread com Callable**
- não tem o método .run() e sim o método .call()
- método .call() retorna alguma coisa, seja String, Integer ou qualquer outra classe necessária
- no método .run() não temos acesso a qualquer valor executado pela tarefa, já com .call() nós temos
- quando chamamos o método .get() ele espera até que a tarefa finalize para ter algum retorno, logo, se fizermos a chamada desse método, não faz tanto sentido usar .shutdown() e .awaitTermination() após o .get(), com exceção do .shutdown() que houver no bloco finally
- também podemos usar o .get(1, TimeUnit.SECONDS) com timeout
- em produção é ideal usar um timeout, pois não sabemos o quão grande é uma tarefa e em quanto tempo ela será executada

~~~java
ExecutorService executor = null;
    try {

        executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(new Tarefa());

        System.out.println(future.isDone());
        System.out.println(future.get(2, TimeUnit.SECONDS));
        System.out.println(future.isDone());
    } catch (Exception e) {
        
        throw e;
    } finally {
        
        if (executor != null) {
            executor.shutdownNow();
        }
    }
~~~

## **Aula 05 - Executores - Parte Dois**
### **Multithread com Fixed**
- executor que usa um conjunto de threads numa quantidade pré-determinada, numa quantidade fixa de threads
- se houver mais tarefas que a quantidade de threads, a tarefa ficará esperando alguma thread liberar, não necessariamente será a thread que finalizou a tarefa 1º
- indicada para várias tarefas
```java
ExecutorService executor = null;

try {
    executor = Executors.newFixedThreadPool(4);
    Future<String> future_1 = executor.submit(new Tarefa());
    Future<String> future_2 = executor.submit(new Tarefa());
    Future<String> future_3 = executor.submit(new Tarefa());
    Future<String> future_4 = executor.submit(new Tarefa());
    Future<String> future_5 = executor.submit(new Tarefa());
    System.out.println(future_1.get());
    System.out.println(future_2.get());
    System.out.println(future_3.get());
    System.out.println(future_4.get());
    System.out.println(future_5.get());
    executor.shutdown();
} catch (Exception e) {
    throw e;
} finally {
    if(executor != null){
        executor.shutdownNow();
    }
}
```

### **Multithread com Cached**
- não precisamos passar o tamanho do pool que usaremos
- ele cria uma thread nova sempre que precisar
- se houver alguma thread que não está sendo usada, ele utilizará essa thread
- por exemplo, foram criadas 3 threads para 3 tarefas, as duas 1ªs tarefas foram executadas cada uma numa thread, na execução da 3ª tarefa, a 1ª thread vagou, logo quem vai executar a 3ª tarefa é a 1ª thread ao invés da 3ª thread
- threads não utilizadas por mais de 60 segundos são destruídas
- ele não tem um limite para criar thread, portanto, precisa tomar muito cuidado, pois se houver muitas tarefas, várias threads serão criadas, o que pode virar um problema
- indicada para poucas tarefas
```java
ExecutorService executor = null;

try {
    
    executor = Executors.newFixedThreadPool(4);
    Future<String> future_1 = executor.submit(new Tarefa());
    System.out.println(future_1.get()); // forçando a terminar a 1ª tarefa antes de executar as outras
    Future<String> future_2 = executor.submit(new Tarefa());
    Future<String> future_3 = executor.submit(new Tarefa());
    System.out.println(future_2.get());
    System.out.println(future_3.get());
    executor.shutdown();
} catch (Exception e) {
    throw e;
} finally {
    if(executor != null){
        executor.shutdownNow();
    }
}
```
### **Multithread com InvokeAll**
- se você tem uma lista, invokeAll() pode executar essa lista de tarefas
```java
List<Tarefa> lista = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    lista.add(new Tarefa());
}

List<Future<String>> list = executor.invokeAll(lista);

for (Future<String> future : list) {
    System.out.println(future.get());
}
```
```java
List<Tarefa> lista = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    lista.add(new Tarefa());
}

List<Future<String>> list = executor.invokeAll(lista);

for (Future<String> future : list) {
    System.out.println(future.get());
}
```
### **Multithread com InvokeAny**
- recebe lista como o método acima
- retorna somente uma tarefa da lista, provavelmente a 1ª que terminar a execução
- retorna String ao invés de um Future
```java
List<Tarefa> lista = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    lista.add(new Tarefa());
}

String invokeAny = executor.invokeAny(lista);

System.out.println(invokeAny);
executor.shutdown();
```
## **Aula 05 - Executores - Parte Três**
### **Scheduled - Agendamento de Executores**
Agendamos em quando o Executor será exercida.

Determinamos quantas threads usaremos:
> ``ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);``

Passamos a tarefa, o intervalo de tempo decorrido antes de ser executar a tarefa e a unidade de tempo usada:
> ``ScheduledFuture<String> future = executor.schedule(new Tarefa(), 2, TimeUnit.SECONDS);``

Por fim, um shutdown para finalizar:
> `executor.shutdown();`

O mesmo pode ser usado para uma tarefa que use Runnable ao invés de Callable:
```java
ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

System.out.println("1º " + System.currentTimeMillis());
executor.schedule(new TarefaComRunnable(), 2, TimeUnit.SECONDS);

executor.shutdown();
```

### **FixedRate**
Primeiro, passamos a tarefa, depois o tempo que será esperado para executar a 1ª vez, o intervalo de tempo em que a terefa será esperará para ser executada diversas vezes, e a unidade de tempo:
> ``executor.scheduleAtFixedRate(new TarefaComRunnable(), 0, 1, TimeUnit.SECONDS);``

**NOTA:** se chamarmos o método *.shutdown()*, a tarefa não será executa de diversas vezes com relação ao período passado.

**NOTA 2:** se houver um *.sleep()* na tarefa, o que ocorrerá é o seguinte:
- se o tempo passado para o método *.sleep()* for maior que o período passado para *.scheduleAtFixedRate()*, o programa não esperará; no caso, acabou uma tarefa, ele executa outra, e assim sucessivamente
- se o tempo passado para o método *.sleep()* for menor que o período passado para *.scheduleAtFixedRate()*, o programa executará a tarefa com base no período passado no *.scheduleAtFixedRate()*
  
### **FixedDelay**
Diferete do FixedRate, o FixedDelay sempre terá um intervalo determinado pelo período informado entre as tarefas.

## **Aula 06 - Cyclic Barrier**
### **Para que serve? Quando usar?**
- várias threads executando em paralelo
- enquanto elas estão em execução, se houver um momento onde uma deva esperar pela outra

**Exemplo:**
Temos 3 threads executando tarefas distintas, mas num certo ponto durante a execução dessas threads, precisamos forçar que essas threads esperem umas pelas outras. Então, digamos a thread 2 chega em determinado ponto e aguarda outra thread chegar, em seguida a thread 1 chega no mesmo ponto que a thread 2 e as duas esperam a thread 3 chegar no mesmo ponto que elas. Quando a última thread chegar no ponto determinado, podemos continuar a execução, encerrar o programa, ou qualquer outra coisa relevante para alcançar o objetivo. Neste caso, estamos sincronizando as threads.

O valor numérico entre parênteses é o número de participantes/threads que serão barrados pelo CyclicBarrier:
> `CyclicBarrier cyclicBarrier = new CyclicBarrier(3);`

### **Número de participantes**
```java
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
```
No código de exemplo, a 1ª thread que chegar no await ficará esperando as outras chegarem até completarem a quantidade de participantes do cyclicBarrier.

Talvez usar um try-catch nestes casos:
- se houver menos participantes do que indicado na instanciação da classe, o programa fica esperando chegar alguma thread.
- se houver mais participantes que o informado, o programa fica em execução, mas não retorna o resultado. 

### **Ação final**
Após todas as threads alcançarem o ponto em comum, queremos que seja executada uma ação única sobre todas as threads e seus resultados.

>`CyclicBarrier cyclicBarrier = new CyclicBarrier(3, acaoFinal);`

Podemos passar a ação final na instanciação da classe CyclicBarrier. Ela recebe um Runnable, logo, podemos usar uma função lambda. 

### **Cíclico**
Por que cíclico? Pq pode executar a mesma ação várias vezes. Ele não encerra após as threads chamarem o *.await()*. 

Podemos ter uma tarefa que não é finalização, e sim sumarização. Ver código: *Aula06_3_CyclicBarrierCiclico.java*.

## **Aula 07 - Count Down Latch**
### **Para que serve? Quando usar?**
Quando houver múltiplas threads executando, sejam tarefas iguais ou distintas, e queremos que após uma certa quantidade de vezes que essas threads forem executadas, queremos executar um ou várias outras coisas. Um exemplo simples, temos uma execução acontecendo inúmeras vezes, e queremos que algo acontece a cada 10 execuções dessa thread, usamos Count Down Latch.

### **Explicação do Exemplo**
Queremos que a cada 3 execuções de *r1*, o valor do *contadorI* mude.

Exemplo:
```java
public class Aula07_1_CountDownLatch {
    
    private static volatile int contadorI = 0;
    public static void main(String[] args) {
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        Runnable r1 = () -> {
            int contadorJ = new Random().nextInt(100);
            int contadorX = contadorI * contadorJ;
            System.out.println(contadorI + " x " + contadorJ + " = " + contadorX);
        };

        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);

        while(true){
            sleep();
            contadorI = new Random().nextInt(100);
        }
    }

    private static void sleep(){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
### **Usando somente uma chamada para o método *.await()***
- toda vez que r1 executar vamos chamar o *countdown()*
- ao invés de chamar o métod *sleep()*,vamos chamar o *await()*.
- ele fica parado até que *latch.down()*, encontrado em r1, execute 3 vezes
- sua desvantagem é que ele não é reutilizável, ou seja, no exemplo, ele só imprimirá no terminal o mesmo valor 3 vezes; após isso, todas as saídas são aleatórias.
- isso acontece porque ele não reinicia a contagem regressiva; para isso, criar um novo objeto dessa classe, por exemplo, dentro do while

[Modo mais simples](./Aula07_1_CountDownLatch.java)

### **Usando vários *.await()***
Podemos ter várias tarefas chamando o countdown e chamando o await:

[Usando vários *.await()*](./Aula07_2_CountDownLatch.java)

## **Aula 08 - Semáforos**
### **Para que serve?**
Temos algumas ferramentas em java para lidar com a concorrência entre multiplas threads, seja limitando ou fazendo algum controle. 

O semáforo é mais uma ferramenta para ajudar no controle dessa concorrência entre paralelismo.

### **Quando usar?**
Usaremos quando soubermos a quantidade de threads que possam executar um trecho de código ao mesmo tempo. É uma barreira que permite ou não que uma thread continue seu processamento baseada na quantidade de threads que você permite que prossigam.

### **Criando um semáforo**
> ``Semaphore SEMAFORO = new Semaphore(permits:0);``

Dentro do contrutor passamos um valor numérico referente a quantas threads podem passar pelo semáforo ao mesmo tempo. 

### **Método .acquire()**
Quando a thread chama o método *.acquire()* ela tentará passar pelo semáforo. Se a quantidade permitida não estiver completa, a thread passará, do contrário terá que aguardar para poder passar no semáforo.

### **Método .release()**
Precisamos chamar esse método após a execução da thread para liberar o semáforo, do contrário o semáforo não é liberado e não passa mais nenhuma thread por ele.

### **Método .tryAcquire()**
Passamos para ele quanto tempo queremos que ele fique esperando. Então, a thread tenta adquirir uma vaga no semáforo em um tempo máximo determinado.

## **Aula 09 - Locks**
### **Para que serve?**
Em situações em que temos um recurso que está sendo acessado ou modificado por mais de uma thread ao mesmo tempo, este é um recurso compartilhado, e queremos evitar que várias dessas threads consigam alterar/modificar/ler/acessar esse recurso ao mesmo tempo, usaremos os *Locks*.
- **recurso:** uma variável, um acesso ao BD, chamada externa via rede, acessar um arquivo

### **ReentrantLock**
Bom, no exemplo do código sem o *lock*, a saída pode ser repetida. Já com o *lock*, ao usarmos o método *__.lock()__* no começo da thread e o método *__.unlock()__* no fim da thread, a saída não será repetida. 

### **Como funciona o lock**
- a 1ª thread que for executar obterá o *__.lock()__* e as threads seguintes não
- as que chegarem depois ficarão esperando a thread com o *lock* terminar de executar
- quando a 1ª thread terminar de executar, ela chamará o *__.unlock()__* e liberará o *lock* para a próxima thread na fila
- *lock* é semelhante ao semáforo mas com uma vaga só

### **Porque usar o lock ao invés do Syncronized?**
- muito mais flexível
- podemos chamar o *__.lock()__* e o *__.unlock()__* em linhas diferentes
- *syncronized* tem um bloco, o qual não permite que escolhamos onde inicia e onde termina
- podemos até chamar o *__.unlock()__* em uma outra classe/método, só precisando passar como parâmetro
- podemos chamar o *__.lock()__* várias vezes, entretanto, precisamos chamar a mesma quantidade de vezes o *__.unlock()__*

### **Outros métodos**
- **tryLock()** - retorna um boolean para saber se conseguimos pegar o *lock* ou não
- **tryLock(time, unit)** - determinamos o tempo decorrido para pegar o *lock*, se conseguiu retorna true, do contrário retorna false

### **ReentrantReadWriteLock**
- usamos *__.writeLock()__* e *__.readLock()__*.
- podemos chamar o *__.writeLock().lock()__* várias vezes, entretanto, precisamos chamar a mesma quantidade de vezes o *__.writeLock().unlock()__*

**.writeLock()** - durante a execução da thread de escrita, ninguém atrapalhará ela, ou seja, ninguém entrará no meio da execução da thread e alterará algo durante a execução da thread que chamou o *__.writeLock()__*. Isso acontece pq quando uma thread pega o *writeLock*, nenhuma outra thread conseguirá pegar *writeLock* e nem o *readLock*. Lock de escrita é exclusivo.

**.readLock()** - já o *.readLock* não ocorre o mesmo que o *.writeLock*. Ele não bloqueia um outro lock de leitura, desta forma, podemos ter um milhão de threads obtendo o lock de leitura, todas elas ao mesmo tempo. Ele só garante que enquanto uma thread estiver lendo, nenhuma outra thread irá escrever até o fim da leitura. Lock de leitura não é exclusivo.