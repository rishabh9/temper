package in.notwork.temper.user.service.utilities;

import in.notwork.temper.user.db.dbo.objects.Privilege;
import in.notwork.temper.user.db.dbo.objects.Role;
import in.notwork.temper.user.service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rishabh.
 */
@Component("userBeanUtil")
public class UserBeanUtil {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserBeanUtil.class);

    /**
     * Bean copy user content - From User Model over to User DBO.
     *
     * @param model Source User Model.
     * @param dbo   Destination User DBO.
     */
    public void copyModelToDbo(final User model, final in.notwork.temper.user.db.dbo.objects.User dbo) {
        dbo.setUsername(model.getUsername());
        dbo.setPassword(model.getPassword());
        dbo.setUuid(model.getUuid());
        dbo.setCredentialsExpired(model.isCredentialsExpired());
        dbo.setAccountEnabled(model.isAccountEnabled());
    }

    /**
     * Bean copy user content - From User DBO to User Model.
     *
     * @param dbo   Source User DBO.
     * @param model Destination User Model.
     */
    public void copyDboToModel(final in.notwork.temper.user.db.dbo.objects.User dbo, final User model) {
        model.setUsername(dbo.getUsername());
        model.setPassword(dbo.getPassword());
        model.setUuid(dbo.getUuid());
        model.setCredentialsExpired(dbo.isCredentialsExpired());
        model.setAccountEnabled(dbo.isAccountEnabled());
        model.setRoles(getRolesFromDbo(dbo.getRoles()));
    }

    private Set<in.notwork.temper.user.service.model.Role> getRolesFromDbo(Set<Role> roles) {
        Set<in.notwork.temper.user.service.model.Role> mRoleSet =
                new HashSet<in.notwork.temper.user.service.model.Role>();
        for (Role role : roles) {
            in.notwork.temper.user.service.model.Role mRole = new in.notwork.temper.user.service.model.Role();
            mRole.setName(role.getName());
            mRole.setPrivileges(getPrivilegesFromDbo(role.getPrivileges()));
            mRoleSet.add(mRole);
        }
        return mRoleSet;
    }

    private Set<in.notwork.temper.user.service.model.Privilege> getPrivilegesFromDbo(Set<Privilege> privileges) {
        Set<in.notwork.temper.user.service.model.Privilege> mPrivilegeSet =
                new HashSet<in.notwork.temper.user.service.model.Privilege>();
        for (Privilege privilege : privileges) {
            in.notwork.temper.user.service.model.Privilege mPrivilege = new in.notwork.temper.user.service.model.Privilege();
            mPrivilege.setName(privilege.getName());
            mPrivilegeSet.add(mPrivilege);
        }
        return mPrivilegeSet;
    }

    /**
     * Bean copy user content - From one User Model to another.
     *
     * @param source      Source User Model.
     * @param destination Destination User Model.
     */
    public void copyModelToModel(final User source, final User destination) {
        destination.setUsername(source.getUsername());
        destination.setPassword(source.getPassword());
        destination.setUuid(source.getUuid());
        destination.setCredentialsExpired(source.isCredentialsExpired());
        destination.setAccountEnabled(source.isAccountEnabled());
    }

    /**
     * Bean copy user content - From one User DBO to another.
     *
     * @param source      Source User DBO.
     * @param destination Destination User DBO.
     */
    public void copyDboToDbo(final in.notwork.temper.user.db.dbo.objects.User source,
                             final in.notwork.temper.user.db.dbo.objects.User destination) {
        destination.setUsername(source.getUsername());
        destination.setPassword(source.getPassword());
        destination.setUuid(source.getUuid());
        destination.setCredentialsExpired(source.isCredentialsExpired());
        destination.setAccountEnabled(source.isAccountEnabled());
    }
}
