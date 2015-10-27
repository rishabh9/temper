package in.notwork.temper.user.db.dao.dbsetup;

import com.ninja_squad.dbsetup.operation.Operation;

import java.util.Date;
import java.util.UUID;

import static com.ninja_squad.dbsetup.Operations.*;

/**
 * @author rishabh.
 */
public class CommonOperations {

    public static final Operation DELETE_ALL = deleteAllFrom(
            "role_has_privilege",
            "user_has_role",
            "privilege",
            "role",
            "user"
    );

    public static final Operation INSERT_REF_USER_DATA = sequenceOf(
            insertInto("privilege")
                    .columns(
                            "id", "name", "description",
                            "create_time", "modified_time",
                            "modified_by", "is_archived"
                    )
                    .values(
                            1, "priv1", "priv1",
                            new Date(System.currentTimeMillis()),
                            new Date(System.currentTimeMillis()),
                            "db-unit-tests", false
                    )
                    .values(
                            2, "priv2", "priv2",
                            new Date(System.currentTimeMillis()),
                            new Date(System.currentTimeMillis()),
                            "db-unit-tests", false
                    )
                    .build(),
            insertInto("role")
                    .columns(
                            "id", "name", "description",
                            "create_time", "modified_time",
                            "modified_by", "is_archived"
                    )
                    .values(
                            1, "role1", "role1",
                            new Date(System.currentTimeMillis()),
                            new Date(System.currentTimeMillis()),
                            "db-unit-tests", false
                    )
                    .values(
                            2, "role2", "role2",
                            new Date(System.currentTimeMillis()),
                            new Date(System.currentTimeMillis()),
                            "db-unit-tests", false
                    )
                    .build(),
            insertInto("role_has_privilege")
                    .columns("role_id", "privilege_id")
                    .values(1, 1)
                    .values(2, 2)
                    .build(),
            insertInto("user")
                    .columns(
                            "id", "username", "password", "salt",
                            "credentials_expired", "account_enabled",
                            "create_time", "modified_time", "modified_by",
                            "is_archived", "uuid"
                    )
                    .values(
                            1, "admin1", "password1", "salt".getBytes(),
                            false, true,
                            new Date(System.currentTimeMillis()),
                            new Date(System.currentTimeMillis()),
                            "db-unit-tests", false, UUID.randomUUID().toString()
                    )
                    .values(
                            2, "admin2", "password2", "salt".getBytes(),
                            false, true,
                            new Date(System.currentTimeMillis()),
                            new Date(System.currentTimeMillis()),
                            "db-unit-tests", true, UUID.randomUUID().toString()
                    )
                    .build(),
            insertInto("user_has_role")
                    .columns("user_id", "role_id")
                    .values(1, 1)
                    .values(2, 2)
                    .build()
    );
}
