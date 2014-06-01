/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sven
 */
@Stateless
@LocalBean
public class ProcessMessage {

    @PersistenceContext(unitName = "NewsApp-ejbPU")
    private EntityManager em;


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void process(NewsEntity object) {

        //NewsEntity ne = em.find(NewsEntity.class, new Long(1));
        NewsEntity news_res = null;

        try {
            Query q = em.createQuery("select i from NewsEntity i where i.title = ?1");
            q.setParameter(1, object.getTitle());
            news_res = (NewsEntity) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (news_res == null) {
            em.persist(object);
        } else {
            object.setId(news_res.getId());
            em.merge(object);
        }

        //object.setBody(object.getBody() + " - check");
    }
}
