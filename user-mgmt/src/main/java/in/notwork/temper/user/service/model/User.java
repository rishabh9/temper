package in.notwork.temper.user.service.model;

import java.util.Objects;
import java.util.UUID;

/**
 * @author rishabh.
 */
public class User extends BaseModel {

    private String username;
    private String password;
    private boolean credentialsExpired;
    private boolean accountEnabled;
    private UUID uuid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(credentialsExpired, user.credentialsExpired) &&
                Objects.equals(accountEnabled, user.accountEnabled) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(uuid, user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, credentialsExpired, accountEnabled, uuid);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", credentialsExpired=").append(credentialsExpired);
        sb.append(", accountEnabled=").append(accountEnabled);
        sb.append(", uuid=").append(uuid);
        sb.append('}');
        return sb.toString();
    }
}
