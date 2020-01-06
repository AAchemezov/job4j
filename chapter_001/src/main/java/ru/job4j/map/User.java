package ru.job4j.map;

import java.util.*;

public class User {
    String name;
    int children;
    Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

//    @Override
//    public int hashCode() {
//        int result = 1;
//        result = 31 * result + children;
//        result = 31 * result + (name == null ? 0 : name.hashCode());
//        result = 31 * result + (birthday == null ? 0 : birthday.hashCode());
//        return result;
//    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children
                && Objects.equals(name, user.name)
                && Objects.equals(birthday, user.birthday);
    }

    public static void main(String[] args) {
        User user1 = new User("Иван", 0, new GregorianCalendar(1999, Calendar.JANUARY, 25));
        User user2 = new User(user1.name, user1.children, user1.birthday);

        Map<User, Object> hashMap = new HashMap<>() {
            {
                put(user1, "User1");
                put(user2, "User2");
            }
        };
        System.out.println("user1.hashCode(): " + user1.hashCode());
        System.out.println("user2.hashCode(): " + user2.hashCode());
        System.out.println("user1.equals(user2): " + user1.equals(user2));
        System.out.println("user2.equals(user1): " + user2.equals(user1));
        System.out.println(hashMap);
    }
}
