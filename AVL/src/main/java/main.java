import java.io.IOException;
import java.util.Scanner;
public class main {
    public static void main(String[] args) throws IOException {
        AVLTree tree = new AVLTree();
        Read.Read_ip r = new Read.Read_ip();
        r.Read_ip1(tree);
        Scanner scanner = new Scanner(System.in);

        // print menu






        boolean quit = false;

        int menuItem;

        do {

            System.out.println("-------- MENU --------");
            System.out.println("1. Display the full index");
            System.out.println("2. Search for a URL");
            System.out.println("3. Search for an IP address");
            System.out.println("4. Quit\n----------------------");

            menuItem = scanner.nextInt();

            switch (menuItem) {

                case 1:
                    tree.Disply();
                    System.out.println();
                    break;
                case 2:
                        System.out.println("Please enter the URL :");
                        String s = scanner.next();
                    tree.isInTree(s);
                    break;

                case 3:

                    System.out.println("Please enter the IP :");
                    String s2 = scanner.next();
                    tree.isInTreeip(s2);
                    break;



                case 4:



                    quit = true;

                    break;

                default:
                    System.out.println("Invalid choice.");

            }

        } while (!quit);

        System.out.println("Bye-bye!");

    }
}


