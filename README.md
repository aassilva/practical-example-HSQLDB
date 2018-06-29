# HSQLDB ou HYPERSQL

## Primeiros Passos:

1. Acessse o site e baixe a última versão do HSQLDB
   * http://hsqldb.org/
   
2. Execute a ferramenta
   * Vá ao diretório raiz onde o arquivo foi descompactado;
   * Abrar a ferramenta gráfica HSQL Database Manager para que assim possamos manipular o banco de dados execultando o comando abaixo:
   ```
   java -cp ./lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing
   ```
   * Caso o Java não esteja no classpath deve-se apontar para o diretório onde ele está instalado;
   ```
   "C:\Program Files (x86)\Java\jre6\bin\java.exe" -cp ./lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing
   ```
   
   * Após a execução dos comandos acima a ferramenta gráfica HSQL Database Manager já estara em execução.
   
3. Criando uma base de dados
   * Digite o nome da base de dados no campo “Setting Name”;
   * Escolhar o tipo de dados no campo “Type”;
   * O caminho do banco de dados no sistema de arquivo na "URL" após “jdbc:hsqldb:file:”;
   * Nome e senha de usuário.
   
4. Criando a tabela
   * Execute comando SQL pela ferramenta gráfica
   ```
        CREATE TABLE Contato (
        id IDENTITY PRIMARY KEY,
        nome VARCHAR(50)
        telefone VARCHAR(20)
        );
    ```
    
 ## Usando JPA/Hibernate + HSQLDB com gerenciador de pacotes maven:
 
 1. Crie um projeto em sua IDE;
 2. Adicione o gerenciador de pacote maven ao seu projeto, ou crie um projeto maven.
 3. Modificar arquivo pom.xml
    * Adicionando a dependência hsqldb e hibernate-entitymanager   
     ```
        <dependencies>
		      <dependency>
			        <groupId>org.hibernate</groupId>
			        <artifactId>hibernate-entitymanager</artifactId>
			        <version>4.3.7.Final</version>
		      </dependency>
		      <dependency>
			        <groupId>org.hsqldb</groupId>
			        <artifactId>hsqldb</artifactId>
			        <version>2.3.2</version>
		      </dependency>
	      </dependencies>
    ```
 4. Criar arquivo src/main/resources/META-INF/persistence.xml
    * Configure o endereço do banco, usuário, senha, dialeto etc.
    ```
       <persistence xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
        version="2.0">

      <persistence-unit name="minha-persistence-unit" transaction-type="RESOURCE_LOCAL">
      <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
      <properties>
          <property name="javax.persistence.jdbc.driver"   value="org.hsqldb.jdbcDriver"               />
          <property name="javax.persistence.jdbc.url"      value="jdbc:hsqldb:file:<endereço do banco>"    />
          <property name="javax.persistence.jdbc.user"     value="sa"                                  />
          <property name="javax.persistence.jdbc.password" value=""                                    />

          <property name="hibernate.hbm2ddl.auto"          value="update"                              />
          <property name="hibernate.show_sql"              value="true"                                />
          <property name="hibernate.format_sql"            value="false"                               />
          <property name="hibernate.dialect"               value="org.hibernate.dialect.HSQLDialect"   />
      </properties>
      </persistence-unit>

      </persistence>
  

5. Crie as classes e seus abributos veja exemplo no código.
