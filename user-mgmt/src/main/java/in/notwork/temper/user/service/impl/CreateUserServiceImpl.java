package in.notwork.temper.user.service.impl;

import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.service.iface.CreateUserService;
import in.notwork.temper.user.service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rishabh.
 */
@Service("createUserService")
public class CreateUserServiceImpl implements CreateUserService {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(CreateUserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserServiceUtility userUtility;

    /**
     * {@inheritDoc}
     */
    public User create(final User user) {
        LOG.debug("Creating user with username - {}", user.getUsername());
        in.notwork.temper.user.db.dbo.objects.User userDbo = userUtility.encryptToDBO(user);

        // Enable the account on creation, by default.
        userDbo.setCredentialsExpired(false);
        userDbo.setAccountEnabled(true);

        // Create user.
        userDbo = userDao.create(userDbo);

        // Assign the UUID created for the new user.
        user.setUuid(userDbo.getUuid());
        LOG.debug("User created with username - {}", user.getUsername());
        return user;
    }

}
