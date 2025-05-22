import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        boolean running = true;

        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Створити завдання");
            System.out.println("2. Показати всі завдання");
            System.out.println("3. Видалити завдання");
            System.out.println("4. Оновити завдання");
            System.out.println("5. Пошук завдань");
            System.out.println("6. Сортувати за датою");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Заголовок: ");
                    String title = scanner.nextLine();
                    System.out.print("Опис: ");
                    String desc = scanner.nextLine();
                    System.out.print("Рік місяць день година хв (через пробіл): ");
                    int y = scanner.nextInt(), m = scanner.nextInt(), d = scanner.nextInt(), h = scanner.nextInt(), min = scanner.nextInt();
                    scanner.nextLine();
                    manager.createTask(title, desc, LocalDateTime.of(y, m, d, h, min));
                }
                case 2 -> manager.listTasks();
                case 3 -> {
                    System.out.print("ID для видалення: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    manager.deleteTask(id);
                }
                case 4 -> {
                    System.out.print("ID завдання: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Новий заголовок: ");
                    String title = scanner.nextLine();
                    System.out.print("Новий опис: ");
                    String desc = scanner.nextLine();
                    System.out.print("Нова дата (рік місяць день година хв): ");
                    int y = scanner.nextInt(), m = scanner.nextInt(), d = scanner.nextInt(), h = scanner.nextInt(), min = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Виконано? (true/false): ");
                    boolean done = Boolean.parseBoolean(scanner.nextLine());
                    manager.updateTask(id, title, desc, LocalDateTime.of(y, m, d, h, min), done);
                }
                case 5 -> {
                    System.out.print("Ключове слово: ");
                    String keyword = scanner.nextLine();
                    manager.searchTasks(keyword);
                }
                case 6 -> {
                    System.out.println("Сортувати за:");
                    System.out.println("1. Дата");
                    System.out.println("2. Назва");
                    int sortChoice = Integer.parseInt(scanner.nextLine());

                    switch (sortChoice) {
                        case 1 -> manager.sortTasksByDate();
                        case 2 -> manager.sortTasksByTitle();
                        default -> System.out.println("Невірний вибір сортування.");
                    }
                }
                case 0 -> running = false;
                default -> System.out.println("Невірна опція.");
            }
        }

        System.out.println("Завершення роботи.");
    }
}
