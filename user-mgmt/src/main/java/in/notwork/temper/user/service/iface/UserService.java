package in.notwork.temper.user.service.iface;

import in.notwork.temper.user.service.model.User;

/**
 * This service is used to perform all types of retrievals for a user.
 *
 * @author rishabh.
 */
public interface UserService {

    /**
     * Find user by username
     *
     * @param username username of the user.
     * @return User.
     */
    User findUserWithUsername(String username);
}
