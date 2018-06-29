package br.ufg.inf.es.persistencia;

import java.util.ArrayList;

public class JavaHyperSql {

    public static void main(String[] args) {
        ContatoDAO cttDAO = new ContatoDAO();
        Contato cttUsr = new Contato();

        cttUsr.setNome("TESTE NOVO");
        cttUsr.setTelefone("(62)9999-9999");

        cttDAO.inserirContato(cttUsr);

        ArrayList<Contato> Contatos = cttDAO.listarTodosContato();
        for(Contato contato : Contatos)
            System.out.println(contato.toString());


    }
}
