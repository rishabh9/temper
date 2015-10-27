package in.notwork.temper.user.db.dao.impl;

import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.db.dbo.objects.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

    public User getUserByUsername(String username) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        if (isEntityArchivable()) {
            // Don't include archived records.
            criteria.add(Restrictions.eq("archived", false));
        }
        User user = (User) criteria.add(Restrictions.eq("username", username))
                .uniqueResult();
        return user;
    }
}
