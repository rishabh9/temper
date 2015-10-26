package in.notwork.temper.user.db.dao.impl;

import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.db.dbo.objects.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author rishabh.
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    /**
     * {@inheritDoc}
     */
    public UserDaoImpl() {
        super(User.class);
    }

}
