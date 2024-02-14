package lk.ijse.hibernate_crud.util;

public class CustomerIdGenerator {
    private static final String PREFIX = "C";
    private static final int INITIAL_ID = 1;
    private static int nextId = INITIAL_ID;

    public static String generateCustomerId() {
        String paddedId = String.format("%03d", nextId);
        String customerId = PREFIX + paddedId;
        nextId++;
        return customerId;
    }

    public static void reset() {
        nextId = INITIAL_ID;
    }
}
