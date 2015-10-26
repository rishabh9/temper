package in.notwork.temper.user.db.dbo.markers;

/**
 * @author rishabh.
 */
public interface Identity<T> {

    T getId();

    void setId(T id);
}
