package ru.job4j.generic;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TestStore {
    UserStore userStore;
    RoleStore roleStore;
    User user1 = new User("id-user-001");
    User user2 = new User("id-user-002");
    User user3 = new User("id-user-003");
    User user4 = new User("id-user-004");

    @Before
    public void setUp() {
        userStore = new UserStore(10) {
            {
                add(user1);
                add(user2);
                add(user3);
                add(user4);
            }
        };
        roleStore = new RoleStore(10) {
            {
                add(new Role("id-role-001"));
                add(new Role("id-role-002"));
                add(new Role("id-role-003"));
                add(new Role("id-role-004"));
            }
        };
    }

    @Test
    public void basicTestUserStore() {
        assertThat(userStore.replace("id-user-001", new User("id-005")), is(true));
        assertThat(userStore.replace("id-user-002", new User("id-006")), is(true));
        assertThat(userStore.replace("003", new User("id-user-003")), is(false));

        assertThat(userStore.findById("id-user-001"), Matchers.nullValue());
        assertThat(userStore.findById("id-user-002"), Matchers.nullValue());
        assertThat(userStore.findById("id-user-003"), is(user3));
        assertThat(userStore.findById("id-user-004"), is(user4));

        User user5 = userStore.findById("id-005");
        User user6 = userStore.findById("id-006");

        assertThat(user5.getId(), equalTo("id-005"));
        assertThat(user6.getId(), equalTo("id-006"));
    }

}


