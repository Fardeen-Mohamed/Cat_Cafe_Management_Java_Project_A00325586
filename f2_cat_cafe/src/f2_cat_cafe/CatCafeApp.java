package f2_cat_cafe;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatCafeApp {
    public static void main(String[] args) {
        CafeManager manager = new CafeManager();
        Scanner scanner = new Scanner(System.in);

        manager.addCat(new PersianCat("Fluffy", 3));
        manager.addCat(new SiameseCat("Luna", 2));
        manager.addMenuItem(new MenuItem("Coffee", 300, MenuCategory.DRINK));
        manager.addMenuItem(new MenuItem("Tea", 250, MenuCategory.DRINK));
        manager.addMenuItem(new MenuItem("Cake", 450, MenuCategory.SNACK));
        manager.addMenuItem(new MenuItem("Cookie", 200, MenuCategory.SNACK));

        int[] ratings = new int[5];

        System.out.println("o((>ω< ))o- Welcome to F2 Cat Cafe!");
        System.out.println("-------------------------------------");

        int choice = -1;
        while (choice != 0) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. View all cats");
            System.out.println("2. View menu");
            System.out.println("3. Make a booking");
            System.out.println("4. View all bookings");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> {
                    manager.printAllCats();
                    for (Cat cat : manager.getCats()) {
                        if (cat instanceof Pettable pettableCat) {
                            pettableCat.greet();
                        }
                    }
                    System.out.println(manager.getAllCatNames());
                }
                case 2 -> manager.printMenu();
                case 3 -> {
                    try {
                        System.out.print("Your name: ");
                        String name = scanner.nextLine();

                        System.out.print("Number of guests: ");
                        int guests = Integer.parseInt(scanner.nextLine());

                        List<Cat> cats = manager.getCats();
                        System.out.println("Choose up to 2 cats for your session (comma separated numbers):");
                        for (int i = 0; i < cats.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, cats.get(i).getName());
                        }
                        String[] chosen = scanner.nextLine().trim().split(",");
                        if (chosen.length > 2) {
                            System.out.println("You can only book up to 2 cats per session.");
                            continue;
                        }

                        // -- DRINK ORDERING --
                        int drinkTotal = 0;
                        String drinkSummary = "";
                        var drinks = manager.findMenuItemsByCategory(MenuCategory.DRINK);
                        if (!drinks.isEmpty()) {
                            System.out.println("Order drinks (enter quantity for each, 0 to skip):");
                            for (int i = 0; i < drinks.size(); i++) {
                                MenuItem drink = drinks.get(i);
                                System.out.print(drink.getName() + " (" + drink.getPrice() + " cents): ");
                                int qty = Integer.parseInt(scanner.nextLine());
                                if (qty > 0) {
                                    drinkTotal += qty * drink.getPrice();
                                    drinkSummary += qty + " x " + drink.getName() + " (" + (qty * drink.getPrice()) + " cents)\n";
                                }
                            }
                        }

                        // -- SNACK ORDERING --
                        int snackTotal = 0;
                        String snackSummary = "";
                        var snacks = manager.findMenuItemsByCategory(MenuCategory.SNACK);
                        if (!snacks.isEmpty()) {
                            System.out.println("Order snacks (enter quantity for each, 0 to skip):");
                            for (int i = 0; i < snacks.size(); i++) {
                                MenuItem snack = snacks.get(i);
                                System.out.print(snack.getName() + " (" + snack.getPrice() + " cents): ");
                                int qty = Integer.parseInt(scanner.nextLine());
                                if (qty > 0) {
                                    snackTotal += qty * snack.getPrice();
                                    snackSummary += qty + " x " + snack.getName() + " (" + (qty * snack.getPrice()) + " cents)\n";
                                }
                            }
                        }

                        int sessionPrice = guests * 1500;
                        int total = sessionPrice + drinkTotal + snackTotal;

                        System.out.println("----------- BILL SUMMARY -----------");
                        System.out.println("Name: " + name);
                        System.out.println("Guests: " + guests);
                        System.out.print("Cats: ");
                        for (int i = 0; i < chosen.length; i++) {
                            int idx = Integer.parseInt(chosen[i].trim()) - 1;
                            if (idx >= 0 && idx < cats.size()) {
                                System.out.print(cats.get(idx).getName());
                                if (i != chosen.length - 1) System.out.print(", ");
                            }
                        }
                        System.out.println();
                        if (!drinkSummary.isEmpty()) System.out.print("Drinks: \n" + drinkSummary);
                        if (!snackSummary.isEmpty()) System.out.print("Snacks: \n" + snackSummary);
                        System.out.println("Session: " + sessionPrice);
                        System.out.println("Total: " + total);
                        System.out.print("Confirm? (yes/no): ");
                        if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                            System.out.println("Booking cancelled.");
                            continue;
                        }

                        LocalDateTime time = LocalDateTime.now().plusDays(1).withHour(12).withMinute(0);
                        BookingReceipt receipt = manager.createBooking(name, time, guests);
                        System.out.println("\nBooking created:");
                        System.out.println(receipt.details());
                        System.out.println("Full bill: " + total);
                        System.out.println("o((>ω< ))o- Cats are waiting!");

                        // --- Ratings Array ---
                        System.out.print("Please rate your booking from 1 to 5: ");
                        int rating = Integer.parseInt(scanner.nextLine());
                        if (rating >= 1 && rating <= 5) {
                            ratings[rating - 1]++;
                            System.out.println("Thank you for rating us " + rating + " stars!");
                            if (rating == 1) {
                                System.out.println("Sorry for your experience—cat is sad :( (=①︿①=)");
                                System.out.println("Thank you for visiting F2 Cat Cafe.");
                            }
                   
                        } else {
                            System.out.println("Rating not counted.");
                        }


                    } catch (BookingException | NumberFormatException e) {
                        System.out.println("Booking failed: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("--- Existing Bookings ---");
                    var bookings = manager.getAllBookings();
                    if (bookings.isEmpty()) System.out.println("No bookings yet.");
                    else bookings.forEach(System.out::println);
                }
                case 0 -> System.out.println("Thank you for visiting F2 Cat Cafe! o((>ω< ))o");
                default -> System.out.println("Invalid option, try again.");
            }
        }
        scanner.close();
    }
}

// ======= CafeManager, Cat(s), MenuItem, etc. =======
class CafeManager {
    private final List<Cat> cats = new ArrayList<>();
    private final List<MenuItem> menuItems = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    public void addCat(Cat cat) { cats.add(cat); }
    public void addMenuItem(MenuItem item) { menuItems.add(item); }
    public BookingReceipt createBooking(String name, LocalDateTime time, int guests) throws BookingException {
        Booking booking = new Booking(name, time, guests);
        bookings.add(booking);
        return new BookingReceipt(name, guests * 1500, booking.getDetails());
    }
    public List<Cat> getCats() { return new ArrayList<>(cats); }
    public List<MenuItem> findMenuItemsByCategory(MenuCategory cat) {
        return menuItems.stream().filter(i -> i.getCategory() == cat).toList();
    }
    public void printAllCats() { cats.forEach(System.out::println); }
    public void printMenu() { menuItems.forEach(System.out::println); }
    public List<Booking> getAllBookings() { return bookings; }
    // StringBuilder demo
    public String getAllCatNames() {
        StringBuilder sb = new StringBuilder();
        for (Cat cat : cats) {
            sb.append(cat.getName()).append(", ");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}

sealed class Cat permits PersianCat, SiameseCat {
    private final String name;
    private final CatBreed breed;
    private final int age;
    public Cat(String name) { this(name, CatBreed.UNKNOWN, 0); }
    public Cat(String name, CatBreed breed, int age) {
        this.name = name; this.breed = breed; this.age = age;
    }
    public String getName() { return name; }
    public CatBreed getBreed() { return breed; }
    public int getAge() { return age; }
    public void play() { System.out.println(name + " is playing!"); }
    @Override public String toString() { return "Cat(" + name + ", " + breed + ", age " + age + ")"; }
}

final class PersianCat extends Cat implements Pettable {
    public PersianCat(String name, int age) { super(name, CatBreed.PERSIAN, age); }
    public void play() { System.out.println(getName() + " (Persian) loves naps."); }
    public void pet() { System.out.println("Petting Persian cat " + getName()); }
}

final class SiameseCat extends Cat implements Pettable {
    public SiameseCat(String name, int age) { super(name, CatBreed.SIAMESE, age); }
    public void play() { System.out.println(getName() + " (Siamese) jumps!"); }
    public void pet() { System.out.println("Petting Siamese cat " + getName()); }
}

interface Pettable {
    void pet();
    default void greet() { System.out.println("Meow!"); }
    static void info() { System.out.println("Cats like gentle pets."); }
    private String getGreeting() {
        return "Meow!";
    }
}

interface Orderable {
    String getName(); int getPrice();
}

enum CatBreed { PERSIAN, SIAMESE, MAINE_COON, UNKNOWN }
enum MenuCategory { DRINK, SNACK }

class MenuItem implements Orderable {
    private final String name;
    private final int price;
    private final MenuCategory category;
    public MenuItem(String name, int price, MenuCategory category) {
        this.name = name; this.price = price; this.category = category;
    }
    public MenuItem(String name, MenuCategory cat) { this(name, 100, cat); }
    public void addAddOns(String... addOns) {
        System.out.print("Add-ons: ");
        for (String a : addOns) System.out.print(a + " ");
        System.out.println();
    }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public MenuCategory getCategory() { return category; }
    @Override public String toString() { return name + " (" + category + ", " + price + " cents)"; }
}

class Booking {
    private final String customerName;
    private final LocalDateTime bookingTime;
    private final int numberOfGuests;
    public Booking(String name, LocalDateTime time, int guests) throws BookingException {
        if (guests <= 0) throw new BookingException("Guests must be positive.");
        this.customerName = name;
        this.bookingTime = LocalDateTime.from(time);
        this.numberOfGuests = guests;
    }
    public String getDetails() { return "Booking for " + customerName + " at " + bookingTime + " for " + numberOfGuests + " guests"; }
    @Override public String toString() { return getDetails(); }
}
class BookingException extends Exception { public BookingException(String msg) { super(msg); } }
record BookingReceipt(String customerName, int totalPrice, String details) {}
