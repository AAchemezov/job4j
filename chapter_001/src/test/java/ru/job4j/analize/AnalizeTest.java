package ru.job4j.analize;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.analize.Analize.Info;
import ru.job4j.analize.Analize.User;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AnalizeTest {
    ArrayList<Analize.User> prevs = new ArrayList<>();
    ArrayList<Analize.User> curr = new ArrayList<>();

    @Before
    public void before() {
        prevs.add(new User(1, "User1"));
        prevs.add(new User(2, null));
        prevs.add(new User(3, "User3"));
        prevs.add(new User(4, "User4"));
        prevs.add(new User(5, "User5"));
        prevs.add(new User(6, "User6"));
        prevs.add(new User(7, "User7"));

        curr.add(new User(1, "User1"));
        curr.add(new User(2, "User2"));
        curr.add(new User(3, null));
        curr.add(new User(4, "User3"));
        curr.add(new User(5, "User5"));
        curr.add(new User(8, "User8"));
    }

    @Test
    public void whenFirstPreviousThenCurrent() {
        Info info = Analize.diff(prevs, curr);
        assertThat(info.added, is(1));
        assertThat(info.changed, is(3));
        assertThat(info.deleted, is(2));
    }

    @Test
    public void whenFirstCurrentThenPrevious() {
        Info info = Analize.diff(curr, prevs);
        assertThat(info.added, is(2));
        assertThat(info.changed, is(3));
        assertThat(info.deleted, is(1));
    }

    @Test
    public void whenFirstCurrentThenCurrent() {
        Info info = Analize.diff(curr, curr);
        assertThat(info.added, is(0));
        assertThat(info.changed, is(0));
        assertThat(info.deleted, is(0));
    }

    @Test
    public void whenFirstPreviousThenPrevious() {
        Info info = Analize.diff(prevs, prevs);
        assertThat(info.added, is(0));
        assertThat(info.changed, is(0));
        assertThat(info.deleted, is(0));
    }

    @Test
    public void whenFirstPreviousThenNewArray() {
        Info info = Analize.diff(prevs, new ArrayList<>());
        assertThat(info.added, is(0));
        assertThat(info.changed, is(0));
        assertThat(info.deleted, is(7));
    }

    @Test
    public void whenFirstNewArrayThenPrevious() {
        Info info = Analize.diff(new ArrayList<>(), prevs);
        assertThat(info.added, is(7));
        assertThat(info.changed, is(0));
        assertThat(info.deleted, is(0));
    }

    @Test
    public void whenFirstPreviousThenAllUsers() {
        final ArrayList<User> allUsers = new ArrayList<>();
        allUsers.addAll(curr);
        allUsers.addAll(prevs);
        Info info = Analize.diff(prevs, allUsers);
        assertThat(info.added, is(1));
        assertThat(info.changed, is(0));
        assertThat(info.deleted, is(0));
    }

}
