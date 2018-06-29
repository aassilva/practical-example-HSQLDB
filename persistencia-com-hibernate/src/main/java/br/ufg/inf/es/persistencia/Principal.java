package br.ufg.inf.es.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Principal {

    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Agenda");
        EntityManager em = emf.createEntityManager();

        Agenda agenda = new Agenda();
        agenda.setNome("João Nascimento");
        agenda.setTelefone("(62) 9999-9999");
        em.getTransaction().begin();
        em.persist(agenda);
        em.getTransaction().commit();

        List<Agenda> lista = em.createQuery("FROM Agenda", Agenda.class).getResultList();

        for (Agenda agendas : lista) {
            System.out.println(agendas.getId());
            System.out.println(agendas.getNome());
            System.out.println(agendas.getTelefone());
        }

        em.close();
        emf.close();
    }
}
