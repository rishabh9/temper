package in.notwork.temper.user.db.dao.iface;

import in.notwork.temper.user.db.dbo.objects.User;

/**
 * @author rishabh.
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Find user by its username.
     *
     * @param username The username of the User.
     * @return User.
     */
    User getUserByUsername(String username);
}
