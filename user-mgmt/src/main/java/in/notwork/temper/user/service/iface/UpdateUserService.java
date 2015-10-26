package in.notwork.temper.user.service.iface;

import in.notwork.temper.user.service.model.User;

import java.util.UUID;

/**
 * This service is used only to perform Updates or deletions on the User.
 *
 * @author rishabh.
 */
public interface UpdateUserService {

    User update(User user);

    void delete(User user);

    void delete(UUID uuid);
}
