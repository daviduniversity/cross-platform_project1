import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public void createTask(String title, String description, LocalDateTime dueDate) {
        tasks.add(new Task(nextId++, title, description, dueDate, false));
        System.out.println("Завдання створено.");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список завдань порожній.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
        System.out.println("Завдання видалено.");
    }

    public void updateTask(int id, String newTitle, String newDesc, LocalDateTime newDueDate, boolean completed) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTitle(newTitle);
                task.setDescription(newDesc);
                task.setDueDate(newDueDate);
                task.setCompleted(completed);
                System.out.println("Завдання оновлено.");
                return;
            }
        }
        System.out.println("Завдання не знайдено.");
    }

    public void searchTasks(String keyword) {
        List<Task> result = tasks.stream()
                .filter(task -> task.getTitle().contains(keyword) || task.getDescription().contains(keyword))
                .collect(Collectors.toList());
        result.forEach(System.out::println);
    }

    public void sortTasksByDate() {
        tasks.stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .forEach(System.out::println);
    }

    public void sortTasksByTitle() {
        tasks.stream()
                .sorted(Comparator.comparing(Task::getTitle))
                .forEach(System.out::println);
    }

}
