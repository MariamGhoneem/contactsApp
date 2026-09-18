package utils;

import models.Contact;
import net.datafaker.Faker;


public class ContactDataGenerator {

    private static final String[] MOBILE_PREFIXES = {"10", "11", "12", "15"};

    private static final Faker faker = new Faker();

    public static Contact generateValidContact() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phoneNumber = generateEgyptianMobileNumber();
        return new Contact(firstName, lastName, phoneNumber);
    }

    private static String generateEgyptianMobileNumber() {
        String prefix = MOBILE_PREFIXES[faker.random().nextInt(MOBILE_PREFIXES.length)];
        String subscriberNumber = faker.number().digits(8);
        return prefix + subscriberNumber;
    }
}
