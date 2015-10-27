package in.notwork.temper.user.db.dao.impl;

import in.notwork.temper.user.db.dao.iface.GenericDao;
import in.notwork.temper.user.db.dbo.markers.Archivable;
import in.notwork.temper.user.db.dbo.markers.Auditable;
import org.hibernate.Criteria;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author rishabh.
 */
@Repository("genericDao")
@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    /*
     * PMD Fix
     */
    private static final String UNCHECKED = "unchecked";

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(GenericDaoImpl.class);

    /**
     * The Type of object we need to persist.
     */
    private final Class<T> persistentClass;

    /**
     * The SessionFactory used by the DAO.
     */
    @Resource
    private SessionFactory sessionFactory;

    /**
     * Getter for SessionFactory.
     *
     * @return SessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Setter for SessionFactory.
     *
     * @param sessionFactory
     */
    @Autowired
    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public GenericDaoImpl(final Class<T> persistentClass, final SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public T get(final PK id) {
        LOG.debug("DB_GET: Type: '{}', Id: '{}'", this.persistentClass, id);

        final Session session = getSession();
        final IdentifierLoadAccess byId = session.byId(this.persistentClass);
        final T entity = (T) byId.load(id);

        if (entity == null) {
            LOG.warn("Object not found! Type: '{}', Id: '{}'", this.persistentClass, id);
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        if (isEntityArchivable()) {
            final Archivable archivable = (Archivable) entity;
            if (archivable.isArchived()) {
                LOG.warn("Unable to retrieve archived object! Type: '{}', Id: '{}'", this.persistentClass, id);
                throw new ObjectRetrievalFailureException(this.persistentClass, id, "Unable to retrieve archived object!", null);
            }
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public List<T> getAll() {
        LOG.debug("DB_GET ALL: Type: '{}'", this.persistentClass);

        final Session session = getSession();
        final Criteria criteria = session.createCriteria(this.persistentClass);
        if (isEntityArchivable()) {
            criteria.add(Restrictions.eq("archived", false));
        }
        return criteria.list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public boolean exists(final PK id) {
        LOG.debug("DB_EXISTS: Type: '{}', Id: '{}'", this.persistentClass, id);

        final IdentifierLoadAccess byId = getSession().byId(this.persistentClass);
        final T entity = (T) byId.load(id);
        boolean isArchived = false;
        if (isEntityArchivable()) {
            final Archivable archivable = (Archivable) entity;
            isArchived = archivable.isArchived();
        }
        return entity != null && !isArchived;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public T create(final T object) {
        LOG.debug("DB_CREATE: Type: '{}'", this.persistentClass);

        if (isEntityAuditable()) {
            updateAuditDetails(object, true);
        }

        return (T) getSession().merge(object);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public T update(final T object) {
        LOG.debug("DB_UPDATE: Type: '{}'", this.persistentClass);

        if (isEntityAuditable()) {
            updateAuditDetails(object, false);
        }

        return (T) getSession().merge(object);
    }

    private void updateAuditDetails(T object, boolean addCreateTime) {
        Date date = new Date(System.currentTimeMillis());
        final Auditable auditable = (Auditable) object;
        if (addCreateTime) {
            auditable.setCreateTime(date);
        }
        auditable.setModifiedTime(date);
    }

    /**
     * {@inheritDoc}
     */
    public void delete(final T object) {
        LOG.debug("DB_DELETE: Type: '{}'", this.persistentClass);

        if (isEntityArchivable()) {
            markAsArchived(object);
            update(object);
        } else {
            getSession().delete(object);
        }
    }

    private void markAsArchived(final T object) {
        LOG.debug("DB_ARCHIVING instead of DB_DELETE '{}'", this.persistentClass);

        final Archivable archivable = (Archivable) object;
        archivable.setArchived(true);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public void delete(final PK id) {
        LOG.debug("DB_DELETE: Type: '{}', Id: '{}'", this.persistentClass, id);

        final Session session = getSession();
        final IdentifierLoadAccess byId = session.byId(this.persistentClass);
        final T object = (T) byId.load(id);
        delete(object);
    }

    /**
     * {@inheritDoc}
     */
    public long count() {
        LOG.debug("DB_COUNT: Type: '{}'", this.persistentClass);

        final Session session = getSession();
        final Criteria criteriaCount = session.createCriteria(this.persistentClass);
        if (isEntityArchivable()) {
            criteriaCount.add(Restrictions.eq("archived", false));
        }
        criteriaCount.setProjection(Projections.rowCount());
        return (Long) criteriaCount.uniqueResult();
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public List<T> paginate(final int pageNumber, final int recordsPerPage) {
        LOG.debug("DB_PAGINATE: Type: '{}', PageNumber: '{}', RecordsPerPage: '{}'",
                this.persistentClass, pageNumber, recordsPerPage);

        final Session session = getSession();
        final Criteria criteria = session.createCriteria(this.persistentClass);
        criteria.setFirstResult((pageNumber - 1) * recordsPerPage);
        criteria.setMaxResults(recordsPerPage);
        if (isEntityArchivable()) {
            criteria.add(Restrictions.eq("archived", false));
        }
        return criteria.list();
    }

    protected boolean isEntityArchivable() {
        return Archivable.class.isAssignableFrom(this.persistentClass);
    }

    protected boolean isEntityAuditable() {
        return Auditable.class.isAssignableFrom(this.persistentClass);
    }
}
