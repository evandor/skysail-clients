package de.skysail.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

public class EntityManagerImpl implements EntityManager {

    @Override
    public void persist(Object entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> T merge(T entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(Object entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFlushMode(FlushModeType flushMode) {
        // TODO Auto-generated method stub

    }

    @Override
    public FlushModeType getFlushMode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void lock(Object entity, LockModeType lockMode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void refresh(Object entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(Object entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Query createQuery(String qlString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Query createNamedQuery(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Query createNativeQuery(String sqlString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Query createNativeQuery(String sqlString, Class resultClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void joinTransaction() {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getDelegate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isOpen() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public EntityTransaction getTransaction() {
        // TODO Auto-generated method stub
        return null;
    }

}
