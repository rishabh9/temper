package in.notwork.temper.user.service.impl;

import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.service.iface.CreateUserService;
import in.notwork.temper.user.service.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rishabh.
 */
@Service("createUserService")
public class CreateUserServiceImpl implements CreateUserService {

    @Autowired
    private RandomNumberGenerator randomNumberGenerator;

    @Autowired
    private UserDao userDao;

    public User create(User user) {

        String plainTextPassword = user.getPassword();

        // We'll use a Random Number Generator to generate salts.
        Object salt = randomNumberGenerator.nextBytes();

        // Now hash the plain-text password with the random salt and multiple
        // iterations and then Base64-encode the value (requires less space than Hex)
        String hashedPasswordBase64 = new Sha256Hash(plainTextPassword, salt, 1024).toBase64();

        in.notwork.temper.user.db.dbo.objects.User userDbo = new in.notwork.temper.user.db.dbo.objects.User();

        // Copy the credentials
        userDbo.setUsername(user.getUsername());
        userDbo.setPassword(hashedPasswordBase64);

        // Enable the account on creation.
        userDbo.setAccountLocked(false);
        userDbo.setCredentialsExpired(false);
        userDbo.setAccountExpired(false);
        userDbo.setAccountEnabled(true);

        // Create user.
        userDbo = userDao.create(userDbo);

        // Blank out the password
        user.setPassword("");
        // Assign the UUID created for the new user.
        user.setUuid(userDbo.getUuid());

        return user;
    }
}
