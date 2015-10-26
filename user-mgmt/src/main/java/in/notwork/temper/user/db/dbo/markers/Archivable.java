package in.notwork.temper.user.db.dbo.markers;

/**
 * @author rishabh.
 */
public interface Archivable {

    Boolean isArchived();

    void setArchived(Boolean archived);
}
