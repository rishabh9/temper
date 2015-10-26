package in.notwork.temper.user.db.dbo.objects;

import in.notwork.temper.user.db.dbo.base.PersistentObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author rishabh.
 */
@Entity
@Table(name = "privilege")
public class Privilege extends PersistentObject {

    private String name;
    private String description;

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege)) return false;
        if (!super.equals(o)) return false;
        Privilege privilege = (Privilege) o;
        return Objects.equals(name, privilege.name) &&
                Objects.equals(description, privilege.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }
}
