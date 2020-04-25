package person;
        import com.github.javafaker.Faker;
        import javax.persistence.EntityManager;
        import javax.persistence.EntityManagerFactory;
        import javax.persistence.Persistence;
        import java.time.LocalDate;
        import java.time.ZoneId;
        import java.util.Date;
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();
        for (int i = 0; i < 1000; i++){
            Person user =randomPerson();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            System.out.println(user);
        }
        em.close();
        emf.close();
    }

    public static Person randomPerson(){
        Person person = new Person();
        Faker faker = new Faker();
        person.setAddress(
                new Address(
                        faker.address().country(),
                        faker.address().state(),
                        faker.address().city(),
                        faker.address().streetAddress(),
                        faker.address().zipCode()
                )
        );
        person.setProfession(
                faker.company().profession()
        );
        Date date = faker.date().birthday();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        person.setDob(
                localDate
        );
        person.setEmail(
                faker.internet().emailAddress()
        );
        person.setName(
                faker.name().fullName()
        );
        person.setGender(faker.options().option(Person.Gender.class));
        return person;
    }
}
