package in.notwork.temper.user.service.iface;

import in.notwork.temper.user.service.model.User;

import java.util.UUID;

/**
 * This service is used to perform all types of retrievals for a user.
 *
 * @author rishabh.
 */
public interface UserService {

    User get(UUID uuid);
}
