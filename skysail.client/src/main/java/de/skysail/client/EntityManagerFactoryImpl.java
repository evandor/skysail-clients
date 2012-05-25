package de.skysail.client;

import java.util.Map;

import javax.persistence.EntityManager;

public class EntityManagerFactoryImpl implements javax.persistence.EntityManagerFactory {

    @Override
    public EntityManager createEntityManager() {
        return new EntityManagerImpl();
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return new EntityManagerImpl();
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

}
