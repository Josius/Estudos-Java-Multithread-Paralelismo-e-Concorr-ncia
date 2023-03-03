# Estudos-Java-Multithread-Paralelismo-e-Concorrência
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