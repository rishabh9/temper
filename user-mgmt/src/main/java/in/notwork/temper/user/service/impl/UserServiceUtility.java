package in.notwork.temper.user.service.impl;

import in.notwork.temper.user.service.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author rishabh.
 */
@Component("userUtility")
public class UserServiceUtility {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceUtility.class);

    @Autowired
    private RandomNumberGenerator randomNumberGenerator;

    public in.notwork.temper.user.db.dbo.objects.User encryptToDBO(User user) {
        LOG.debug("Encrypting password using random salt for {}", user.getUsername());

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
        userDbo.setSalt(((ByteSource) salt).getBytes());
        return userDbo;
    }
}
