package user;


import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        User user1 = User.builder()
                .username("gergomarcellturi")
                .password("OX3YA0")
                .name("Turi Gergő Marcell")
                .email("gergo.marcell.turi@gmail.com")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1999-03-16"))
                .enabled(true)
                .build();

        User user2 = User.builder()
                .username("007")
                .password("jb")
                .name("James Bond")
                .email("jamesbond@secretmail.com")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1920-11-11"))
                .enabled(true)
                .build();

        User user3 = User.builder()
                .username("username")
                .password("password")
                .name("name")
                .email("email")
                .gender(User.Gender.MALE)
                .dob(LocalDate.parse("1970-01-01"))
                .enabled(true)
                .build();


        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);

            Logger.trace("CREATING TABLE");
            dao.createTable();

            Logger.trace("INSERTING DATA");
            dao.insert(user1);
            dao.insert(user2);
            dao.insert(user3);

            Logger.trace("LISTING DATA");
            dao.list().stream().forEach(System.out::println);

            Logger.trace("FIND BY ID");
            Logger.info(dao.findById(3).get().toString());;

            Logger.trace("FIND BY USERNAME");
            Logger.info(dao.findByUsername("gergomarcellturi").get().toString());

            Logger.trace("DELETING USER");
            dao.delete(dao.findById(3).get());

            Logger.trace("LISTING DATA");
            dao.list().stream().forEach(System.out::println);

        }

//        try (Handle handle = jdbi.open()) {
//            LegoSetDao dao = handle.attach(LegoSetDao.class);
//            dao.createTable();
//            dao.insertLegoSet(new LegoSet("60073", 2015, 233));
//            dao.insertLegoSet(new LegoSet("75211", 2018, 519));
//            dao.insertLegoSet(new LegoSet("21034", 2017, 468));
//            dao.listLegoSets().stream().forEach(System.out::println);
//        }





    }


}
