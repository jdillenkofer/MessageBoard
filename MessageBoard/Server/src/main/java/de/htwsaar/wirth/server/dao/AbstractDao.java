package de.htwsaar.wirth.server.dao;

import org.hibernate.Session;
import org.hibernate.mapping.KeyValue;
import org.hibernate.query.Query;
import org.hibernate.query.QueryParameter;

import java.util.List;

/**
 * Created by Marius on 08.02.17
 */
public abstract class AbstractDao<T> {

    public boolean save(T obj) {
        try {
            Session session = PersistenceManager.getSession();
            session.beginTransaction();
            session.saveOrUpdate(obj);
            session.getTransaction().commit();
            session.close();

            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(T obj){
        boolean success = true;
        try {
            Session session = PersistenceManager.getSession();
            session.beginTransaction();

            session.delete(obj);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            success = false;
        }
        return success;
    }

    @SuppressWarnings("all")
    public List<T> query(String query) {
        Session session = PersistenceManager.getSession();
        session.beginTransaction();

        List<T> result = session.createQuery(query).list();

        session.getTransaction().commit();
        session.close();

        return result;
    }

    public List<T> query(String query, List<DatabaseQueryParameter> parameterList) {
        Session session = PersistenceManager.getSession();
        session.beginTransaction();

        Query queryObj = session.createQuery(query);
        for(int i=0; i<parameterList.size();i++){
            queryObj.setParameter(parameterList.get(i).getKey(), parameterList.get(i).getValue());
        }
        List<T> result = queryObj.list();
        session.getTransaction().commit();
        session.close();

        return result;
    }
}
