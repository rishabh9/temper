package in.notwork.temper.user.service.iface;

import in.notwork.temper.user.service.model.User;

/**
 * This service is used only to perform Updates or deletions on the User.
 *
 * @author rishabh.
 */
public interface UpdateUserService {

    User update(User user);

    void delete(User user);

    void expireCredentials(User user);

    void enableAccount(User user);

    void disableAccount(User user);

}
