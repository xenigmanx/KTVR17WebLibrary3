/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.History;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class HistoryFacade extends AbstractFacade<History> {

    @EJB BookFacade bookFacade;
    

    @PersistenceContext(unitName = "KTVR17WebLibrary3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }
    
    public List<History> findTakeBooks(){
        return em.createQuery("SELECT h FROM History h WHERE h.dateReturn=NULL")
                .getResultList();
    }
    
    public List<History> find(Book book){
        return em.createQuery("SELECT h FROM History h WHERE h.book = :book")
                .setParameter("book", book)
                .getResultList();
    }
 
    public List<History> findRange(Date fromDate, Date toDate) {
        try {
            return em.createQuery("SELECT h FROM History h WHERE h.dateBegin > : fromDate AND  h.dateBegin < :toDate")
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
        } catch (Exception e) {
            return null;
        }
        
    }

    public List<History> findByRange(Date fromDate, Date toDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
