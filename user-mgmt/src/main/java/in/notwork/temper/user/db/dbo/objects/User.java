package in.notwork.temper.user.db.dbo.objects;

import in.notwork.temper.user.db.dbo.base.PersistentObject;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author rishabh.
 */
@Entity
@Table(name = "user")
public class User extends PersistentObject {

    private String username;
    private String password;
    private byte[] salt;
    private boolean credentialsExpired;
    private boolean accountEnabled;
    private UUID uuid;
    private Set<Role> roles;

    @Column(name = "username", nullable = false, length = 255)
    public String getUsername() {
        return username;
    }

    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    @Column(name = "salt", nullable = false, length = 60)
    public byte[] getSalt() {
        return salt;
    }


    @Column(name = "credentials_expired", nullable = false)
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    @Column(name = "account_enabled", nullable = false)
    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "user_has_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }

    @Column(name = "uuid", nullable = true)
    @Type(type = "uuid-char")
    public UUID getUuid() {
        return uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(credentialsExpired, user.credentialsExpired) &&
                Objects.equals(accountEnabled, user.accountEnabled) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(uuid, user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password,
                credentialsExpired, accountEnabled, uuid);
    }
}
