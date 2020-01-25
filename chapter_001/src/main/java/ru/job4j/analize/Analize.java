package ru.job4j.analize;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Analize {


    public static Info diff(List<User> previous, List<User> current) {
        Info info = new Info();
        Map<Integer, User> prev = previous.stream().collect(Collectors.toMap(u -> u.id, u -> u, (id1, id2) -> id2));
        Map<Integer, User> curr = current.stream().collect(Collectors.toMap(u -> u.id, u -> u, (id1, id2) -> id2));
        for (User pUser : prev.values()) {
            User cUser = curr.get(pUser.id);
            if (cUser == null) {
                info.deleted++;
            } else {
                if (!Objects.equals(pUser.name, cUser.name)) {
                    info.changed++;
                }
                curr.remove(pUser.id);
            }
        }
        info.added = curr.size();
        return info;
    }

    public static class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class Info {
        int added;
        int changed;
        int deleted;
    }
}
