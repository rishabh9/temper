package in.notwork.temper.user.db.dao.iface;

import java.io.Serializable;
import java.util.List;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * @author rishabh.
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * Get an object by id (primary key).
     *
     * @param id The primary key (id) of the object.
     * @return The object with given id.
     */
    T get(PK id);

    /**
     * Get all objects from db.
     *
     * @return all objects from database.
     */
    List<T> getAll();

    /**
     * Check if an object exists.
     *
     * @param id The primary key (id) of the object.
     * @return TRUE if object is available in database, else FALSE.
     */
    boolean exists(PK id);

    /**
     * Create and persist an object.
     *
     * @param object The object to be created and persisted.
     * @return The created object.
     */
    T create(T object);

    /**
     * Update an object.
     *
     * @param object the object to be updated.
     * @return The updated object.
     */
    T update(T object);

    /**
     * Delete an object.
     * If object of instance Archivable, then mark it as archived only.
     *
     * @param object The object to be deleted/archived.
     */
    void delete(T object);

    /**
     * Delete an object given its id.
     * If object of instance Archivable, then mark it as archived only.
     *
     * @param id The primary key (id) of the object to be deleted/archived.
     */
    void delete(PK id);

    /**
     * @return The total count of rows in the table.
     * If the object is instanceof Archivable, then only count of non archived rows is returned.
     */
    long count();

    /**
     * Paginates within the list of objects in the db.
     * Paging logic should be maintained at the service layer, ensuring the recordsPerPage count is maintained
     * correctly.
     *
     * @param pageNumber     Page number starting from 1.
     * @param recordsPerPage Number of records required per pageNumber.
     * @return a sub-list of records from db.
     */
    List<T> paginate(int pageNumber, int recordsPerPage);
}
