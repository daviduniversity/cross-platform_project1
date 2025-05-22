package tasklist;

import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;

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

    public void searchByTitle(String keyword) {
        tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .forEach(System.out::println);
    }

    public void searchByDescription(String keyword) {
        tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .forEach(System.out::println);
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

    public void markTaskAsCompleted(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(true);
                System.out.println("Завдання позначено як виконане.");
                return;
            }
        }
        System.out.println("Завдання не знайдено.");
    }

    public void saveToFile(String filename) {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            gson.toJson(tasks, writer);
            System.out.println("Дані збережено у файл: " + filename);
        } catch (IOException e) {
            System.out.println("Помилка при збереженні: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);

        // ✅ перевірка на існування та порожність
        if (!file.exists() || file.length() < 5) { // < 5 — захист від обірваних "["
            System.out.println("Файл не знайдено або пошкоджений. Починаємо з порожнього списку.");
            tasks = new ArrayList<>();
            return;
        }

        try (Reader reader = new FileReader(file)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            Type taskListType = new TypeToken<List<Task>>() {}.getType();
            tasks = gson.fromJson(reader, taskListType);
            if (tasks == null) tasks = new ArrayList<>();
            nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
            System.out.println("Дані завантажено з файлу.");
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
            tasks = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Файл пошкоджено або має неправильний формат. Починаємо з порожнього списку.");
            tasks = new ArrayList<>();
        }
    }



}
