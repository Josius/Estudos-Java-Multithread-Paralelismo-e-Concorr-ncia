# **Estudos-Java-Multithread-Paralelismo-e-Concorrência**
### **Execução Monothread**
Execução seqüêncial das linhas do código. Ou seja, se tivermos duas execuções de programas diferentes, um processo suportará somente um programa em seu espaço de endereçamento.
### **Execução Multithread**
Execução em paralelo das linhas do código. Neste caso, com o mesmo exemplo acima, ocorrerá a execução de parte do código do programa 1, depois de um tempo executará parte do código do programa 2, deixando o programa 1 em suspensão, em seguida o cenário troca para o programa 2 em suspensão e execução de parte do programa 1, e assim sucessivamente até que todas as execuções seja concluídas.
### **Paralelismo**
Execução de vários trechos de código no mesmo instante. Ou seja, são trechos de código executando em núcleos diferentes do processador. Paralelismo só pode ocorrer em processadores com dois núcleos ou mais.
### **Concorrência**
Várias execuções de código concorrendo pelo mesmo recurso. O recurso pode ser uma variável, um acesso ao banco de dados, uma chamada ao SO, então, se houver dois processos diferentes tentando acessar esse recurso ao mesmo tempo, ocorre a concorrência.
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