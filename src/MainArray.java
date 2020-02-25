import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainArray {
    private final static ArrayStorage arrayStorage = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(arrayStorage.size());
                    break;
                case "save":
                    arrayStorage.save(new Resume(uuid));
                    printAll();
                    break;
                case "delete":
                    arrayStorage.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(arrayStorage.get(uuid));
                    break;
                case "clear":
                    arrayStorage.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll() {
        System.out.println("----------------------------");
        if (arrayStorage.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : arrayStorage.getAll()) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
