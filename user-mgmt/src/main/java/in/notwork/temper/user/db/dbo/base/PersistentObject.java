package in.notwork.temper.user.db.dbo.base;

import in.notwork.temper.user.db.dbo.markers.Archivable;
import in.notwork.temper.user.db.dbo.markers.Auditable;
import in.notwork.temper.user.db.dbo.markers.Identity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author rishabh.
 */
@MappedSuperclass
public class PersistentObject extends BaseObject implements Identity<Long>, Archivable, Auditable {

    private Long id;
    private Date createTime;
    private Date modifiedTime;
    private String modifiedBy;
    private Boolean archived;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    public Long getId() {
        return id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, precision = 6)
    public Date getCreateTime() {
        return createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_time", nullable = false, precision = 6)
    public Date getModifiedTime() {
        return modifiedTime;
    }

    @Column(name = "modified_by", nullable = false, length = 45)
    public String getModifiedBy() {
        return modifiedBy;
    }

    @Column(name = "is_archived", nullable = false)
    public Boolean isArchived() {
        return archived;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentObject)) return false;
        PersistentObject persistentObject = (PersistentObject) o;
        return Objects.equals(id, persistentObject.id) &&
                Objects.equals(createTime, persistentObject.createTime) &&
                Objects.equals(modifiedTime, persistentObject.modifiedTime) &&
                Objects.equals(modifiedBy, persistentObject.modifiedBy) &&
                Objects.equals(archived, persistentObject.archived);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, modifiedTime, modifiedBy, archived);
    }
}
