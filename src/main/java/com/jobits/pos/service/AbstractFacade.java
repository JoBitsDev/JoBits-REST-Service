/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobits.pos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobits.pos.authentication.Credentials;
import com.jobits.pos.authentication.TennantWrapper;
import com.jobits.pos.core.domain.models.Venta;
import com.jobits.pos.core.module.PosCoreModule;
import com.jobits.pos.persistence.repository.DatabaseRepository;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.jobits.db.pool.ConnectionPoolHandler;

/**
 *
 * @author Jorge
 */
public class AbstractFacade<T> {

    private Class<T> entityClass;

    public static HashMap<String, Credentials> tokens = new HashMap<>();
    public static HashMap<String, TennantWrapper> tennantTokens = new HashMap<>();

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static void setCurrentTennant(EntityManagerFactory emfTennant) {
    }

    public void create(T entity) {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().persist(entity);
        } else {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().getTransaction().commit();
        }

    }

    public void edit(T entity) {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().merge(entity);
        } else {
            getEntityManager().getTransaction().begin();
            getEntityManager().merge(entity);
            getEntityManager().getTransaction().commit();
        }
    }

    public void remove(T entity) {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().remove(getEntityManager().merge(entity));
        } else {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().getTransaction().commit();
        }
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();

    }

    public List findAll(Class entity) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entity));
        return getEntityManager().createQuery(cq).getResultList();

    }

    public static List findAll(EntityManager em, Class entity) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entity));
        return em.createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public Venta findVenta() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Venta.class));
        List<Venta> ventas = getEntityManager().createQuery(cq).getResultList();
        for (int i = ventas.size() - 1; i >= 0; i--) {
            if (ventas.get(i).getVentaTotal() == null) {
                return ventas.get(i);
            }
        }

        return null;
    }

    protected Response handleException(Exception ex) {
        if (ex instanceof JsonProcessingException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error en el Object Mapper. Contacte con soporte" + ((JsonProcessingException) ex).getMessage()).build();
        }
        System.out.println(ex.getStackTrace()[0]);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Contacte con soporte " + ex.getMessage()).build();
    }

    protected Response toJsonString(Response.Status status, Object o) {
        try {
            return Response.status(status).entity(new ObjectMapper().writeValueAsString(o)).build();
        } catch (JsonProcessingException ex) {
            return handleException(ex);
        }
    }

    protected String getToken(HttpServletRequest requestContext) {
        return requestContext.getHeader(HttpHeaders.AUTHORIZATION).substring("Bearer".length()).trim();
    }

    protected String getTennantToken(HttpServletRequest requestContext) {
        return requestContext.getHeader(HttpHeaders.LOCATION).substring("TennantId".length()).trim();
    }

    protected void startTransaction() {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
    }

    protected void commitTransaction() {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().commit();
        }
    }

    protected EntityManager getEntityManager() {
        return ConnectionPoolHandler.getConnectionPoolService(PosCoreModule.getInstance().getModuleName()).getCurrentConnection();
    }

    public static EntityManager getCurrentTennantConnection() {
        return ConnectionPoolHandler.getConnectionPoolService(PosCoreModule.getInstance().getModuleName()).getCurrentConnection();
    }

}
