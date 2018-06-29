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

## Conexão bruta

1. Crie um projeto java;
2. Crie uma pasta chamada lib;
3. Coloque o arquivo .jar na basta lib;
4. Faça o arquivo ser reconhecido como biblioteca;
   * Depende da IDE
   * Adicione Libraries
5. Crie a classe de conexão
```
public class CNXJDBC {

	private String usuario = "SA";
	private String senha = "";
	private String PathBase = "endereço da base de dados";
	private String URL = "jdbc:hsqldb:file:" + PathBase + ";";

	public Connection conectar() {
		try {
			return DriverManager.getConnection(URL, usuario, senha);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
```
6. Crie as classes de operaçes/serviços.
```
public class ContatoDAO {
	private final String SQL_INSERE_CONTATO = "INSERT INTO USUARIOS(NOME, TELEFONE) VALUES ( ?, ?);";
	private final String SQL_SELECIONA_USUARIO = "SELECT * FROM USUARIOS";

	private PreparedStatement pst = null;

	public void inserirContato(Contato contato) {
		try (	Connection conn = new CNXJDBC().conectar(); 
				PreparedStatement pst = conn.prepareStatement(SQL_INSERE_CONTATO);) {
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getTelefone());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao executar o Statment " + e.toString());
		}
	}
	
public ArrayList<Contato> listarTodosContato() {
		ArrayList<Contato> contatos = new ArrayList<Contatos>();

		Contato contato;
		try (	Connection conn = new CNXJDBC().conectar();
				PreparedStatement pst = conn.prepareStatement(SQL_SELECIONA_CONTATO);
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				contato = new Contato();
				contato.setId(rs.getInt("ID"));
				contato.setNome(rs.getString("NOME"));
				contato.setTelefone(rs.getString("TELEFONE"));
				contatos.add(contato);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao executar o Statement" + e.toString());
		}

		return contatos;
	}
}
```
7. Crie a classe principal:
```
public class JavaHyperSql {

	public static void main(String[] args) {
		ContatoDAO cttDAO = new ContatoDAO();
		Contato cttUsr = new Contato();
		
		cttUsr.setNome("TESTE NOVO");
		cttUsr.setEMail("teste_novo@tr.tr");
		
		cttDAO.inserirUContato(cttUsr);
		
		ArrayList<Contato> Contatos = cttDAO.listarTodosContatos();
		for(COntato contato : Contatos)
			System.out.println(contato.toString());
		
		
	}

}
```
