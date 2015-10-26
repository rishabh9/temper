package in.notwork.temper.user.service.iface;

import in.notwork.temper.user.service.model.User;

/**
 * This service is only used to create an user.
 *
 * @author rishabh.
 */
public interface CreateUserService {

    User create(User user);
}
