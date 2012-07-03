//package de.twenty11.skysail.client;
//
//import java.util.Map;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.spi.PersistenceProvider;
//import javax.persistence.spi.PersistenceUnitInfo;
//
//public class SkysailPersistenceProvider implements PersistenceProvider {
//
//    @Override
//    public EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties) {
//        Configuration cfg = new Configuration();
//        Configuration configured = cfg.configure(persistenceUnitName, properties);
//        return configured != null ? configured.buildEntityManagerFactory() : null;
//    }
//
//    @Override
//    public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
//        throw new UnsupportedOperationException("not yet implemented");
//    }
//
//}
