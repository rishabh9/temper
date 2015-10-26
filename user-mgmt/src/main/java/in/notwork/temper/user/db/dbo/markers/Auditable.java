package in.notwork.temper.user.db.dbo.markers;

import java.util.Date;

/**
 * @author rishabh.
 */
public interface Auditable {


    Date getCreateTime();

    void setCreateTime(Date createTime);

    Date getModifiedTime();

    void setModifiedTime(Date modifiedTime);

    String getModifiedBy();

    void setModifiedBy(String modifiedBy);
}
