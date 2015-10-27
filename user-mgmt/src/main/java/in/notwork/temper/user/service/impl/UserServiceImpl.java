package in.notwork.temper.user.service.impl;

import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.service.iface.UserService;
import in.notwork.temper.user.service.model.User;
import in.notwork.temper.user.service.utilities.UserBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rishabh.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserBeanUtil userBeanUtil;

    /**
     * {@inheritDoc}
     */
    public User findUserWithUsername(String username) {
        LOG.debug("Finding user having username - {}", username);
        in.notwork.temper.user.db.dbo.objects.User userDbo = userDao.getUserByUsername(username);
        User user = new User();
        userBeanUtil.copyDboToModel(userDbo, user);
        return user;
    }
}
