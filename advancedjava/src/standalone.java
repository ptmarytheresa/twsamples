//Compact Source Files and Instance Main Methods (Finalized in Java 25)

/*
No explicit class declaration is required.
The main() method can be an instance method.
No need to declare public static void main(String[] args) for this form.
Java implicitly provides the class structure needed to launch the program.
*/
void main() {

    String restaurantName = "Ocean View Restaurant";

    System.out.println("Welcome to " + restaurantName);

    displayMenu();
}

void displayMenu() {

    System.out.println("Today's Menu");
    System.out.println("1. Paneer Tikka - Rs. 250");
    System.out.println("2. Veg Biryani - Rs. 300");
    System.out.println("3. Fresh Juice - Rs. 120");
}


