package in.notwork.temper.user.db.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import in.notwork.temper.user.db.dao.dbsetup.CommonOperations;
import in.notwork.temper.user.db.dao.iface.UserDao;
import in.notwork.temper.user.db.dbo.objects.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.util.Date;
import java.util.UUID;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;

/**
 * @author rishabh.
 */
public class UserDaoTest extends BaseDaoTest {

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserDaoTest.class);

    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    @Qualifier("unitTestDataSource")
    private DataSource dataSource;

    @Autowired
    private UserDao userDao;

    @Before
    public void prepareForTests() throws Exception {
        Operation operation = sequenceOf(
                CommonOperations.DELETE_ALL,
                CommonOperations.INSERT_REF_USER_DATA
        );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        //dbSetup.launch();
        // use the tracker to launch the DbSetup.
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    @Test(expected = DataAccessException.class)
    public void testGetUserInvalid() throws Exception {
        dbSetupTracker.skipNextLaunch();
        // should throw DataAccessException
        userDao.get(9123447L);
    }

    @Test(expected = DataAccessException.class)
    public void testGetUserInvalidIds() throws Exception {
        dbSetupTracker.skipNextLaunch();
        userDao.get(-1123984672347862834L);
        userDao.get(999999999999999999L);
    }

    @Test
    public void testGetValidUserById() {
        dbSetupTracker.skipNextLaunch();
        long id = 1;
        Assert.assertNotNull(userDao.get(id));
    }

    @Test(expected = DataAccessException.class)
    public void testGetValidArchivedUserById() {
        dbSetupTracker.skipNextLaunch();
        long id = 2;
        userDao.get(id);
    }

    @Test
    public void testGetUserCount() {
        dbSetupTracker.skipNextLaunch();
        Assert.assertEquals(1, userDao.count());
    }

    @Test
    public void testGetAll() {
        dbSetupTracker.skipNextLaunch();
        Assert.assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void testExists() {
        dbSetupTracker.skipNextLaunch();
        Assert.assertEquals(true, userDao.exists(1L));
    }

    @Test
    public void testExistsArchivedObject() {
        dbSetupTracker.skipNextLaunch();
        Assert.assertEquals(false, userDao.exists(2L));
    }

    @Test
    public void testDelete() {
        userDao.delete(1L);
        Assert.assertEquals(false, userDao.exists(1L));
    }

    @Test
    public void testDeleteArchivedObjects() {
        userDao.delete(2L);
        Assert.assertEquals(false, userDao.exists(2L));
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("username3");
        user.setPassword("password3");
        user.setSalt("salt".getBytes());
        user.setAccountEnabled(true);
        user.setCredentialsExpired(false);
        user.setArchived(false);
        user.setUuid(UUID.randomUUID());

        user.setModifiedBy("db-unit-tests");
        Assert.assertNull(user.getId());
        user = userDao.create(user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("username3");
        user.setPassword("password3");
        user.setSalt("salt".getBytes());
        user.setAccountEnabled(true);
        user.setCredentialsExpired(false);
        user.setArchived(false);
        user.setUuid(UUID.randomUUID());

        user.setModifiedBy("db-unit-tests");
        Assert.assertNull(user.getId());
        user = userDao.create(user);
        Assert.assertNotNull(user.getId());

        Date modTime = user.getModifiedTime();

        Assert.assertTrue(!user.isCredentialsExpired());
        user.setCredentialsExpired(true);
        user = userDao.update(user);
        Assert.assertTrue(user.isCredentialsExpired());

        Assert.assertTrue(modTime.before(user.getModifiedTime()));
    }
}
