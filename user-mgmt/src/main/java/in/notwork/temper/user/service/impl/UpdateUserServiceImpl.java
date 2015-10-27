package in.notwork.temper.user.service.impl;

import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.service.iface.UpdateUserService;
import in.notwork.temper.user.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rishabh.
 */
public class UpdateUserServiceImpl implements UpdateUserService {

    @Autowired
    UserDao userDao;

    @Autowired
    private UserServiceUtility userUtility;

    public User update(final User user) {
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

        return user;
    }

    public void delete(final User user) {
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDao.delete(userDbo);
    }

    public void expireCredentials(final User user) {
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDbo.setCredentialsExpired(true);
        userDao.update(userDbo);
    }

    public void enableAccount(final User user) {
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDbo.setAccountEnabled(true);
        userDao.update(userDbo);
    }

    public void disableAccount(final User user) {
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(user.getUsername());
        userDbo.setAccountEnabled(false);
        userDao.update(userDbo);
    }

}
