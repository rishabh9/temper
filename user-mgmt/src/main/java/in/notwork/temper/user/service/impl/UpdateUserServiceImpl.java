package in.notwork.temper.user.service.impl;

import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.service.iface.UpdateUserService;
import in.notwork.temper.user.service.model.User;
import in.notwork.temper.user.service.utilities.UserServiceUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rishabh.
 */
@Service("updateUserService")
public class UpdateUserServiceImpl implements UpdateUserService {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(UpdateUserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    private UserServiceUtility userUtility;

    public User updatePassword(final User user) {
        LOG.debug("Updating password for user - {}", user.getUsername());
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        in.notwork.temper.user.db.dbo.objects.User encryptedDbo = userUtility.encryptToDBO(user);

        // Can only update the password and nothing else.
        // Copy encrypted password onto attached/persisted object.
        userDbo.setPassword(encryptedDbo.getPassword());
        userDbo.setSalt(encryptedDbo.getSalt());
        // Since we are updating the password, by default the expired-credentials flag should be unset.
        userDbo.setCredentialsExpired(false);

        userDbo = userDao.update(userDbo);

        user.setUsername(userDbo.getUsername());
        user.setUuid(userDbo.getUuid());
        user.setAccountEnabled(userDbo.isAccountEnabled());
        user.setCredentialsExpired(userDbo.isCredentialsExpired());

        LOG.debug("Password updated for user - {}", user.getUsername());
        return user;
    }

    public void delete(final User user) {
        LOG.debug("Attempting to delete user - {}", user.getUsername());
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDao.delete(userDbo);
        LOG.debug("Deleted user - {}", user.getUsername());
    }

    public void expireCredentials(final User user) {
        LOG.debug("Expiring the credentials for user - {}", user.getUsername());
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDbo.setCredentialsExpired(true);
        userDao.update(userDbo);
        LOG.debug("Credentials marked as expired for user - {}", user.getUsername());
    }

    public void enableAccount(final User user) {
        LOG.debug("Enabling account for user - {}", user.getUsername());
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDbo.setAccountEnabled(true);
        userDao.update(userDbo);
        LOG.debug("Account enabled for user - {}", user.getUsername());
    }

    public void disableAccount(final User user) {
        LOG.debug("Disabling account for for user - {}", user.getUsername());
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDbo.setAccountEnabled(false);
        userDao.update(userDbo);
        LOG.debug("Account disabled for user - {}", user.getUsername());
    }

}
