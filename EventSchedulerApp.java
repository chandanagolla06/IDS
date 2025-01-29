import java.util.*;

class Event {
    private String description;
    private int startTime; 
    private int endTime;   

    public Event(String description, int startTime, int endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Event: " + description + ", Start: " + startTime + ", End: " + endTime;
    }
}

class EventScheduler {
    private List<Event> events;

    public EventScheduler() {
        events = new ArrayList<>();
    }

    public boolean addEvent(String description, int startTime, int endTime) {
        Event newEvent = new Event(description, startTime, endTime);

        for (Event event : events) {
            if (isConflict(newEvent, event)) {
                System.out.println("Conflict detected with: " + event);
                suggestAlternativeSlots(newEvent);
                return false;
            }
        }

        events.add(newEvent);
        System.out.println("Event added successfully: " + newEvent);
        return true;
    }

    private boolean isConflict(Event event1, Event event2) {
        return !(event1.getEndTime() <= event2.getStartTime() || event1.getStartTime() >= event2.getEndTime());
    }

    private void suggestAlternativeSlots(Event newEvent) {
        System.out.println("Suggested alternative time slots:");

        events.sort(Comparator.comparingInt(Event::getEndTime));
        int previousEndTime = 0;

        for (Event event : events) {
            if (newEvent.getEndTime() <= event.getStartTime() && newEvent.getStartTime() >= previousEndTime) {
                System.out.println("Start: " + previousEndTime + ", End: " + event.getStartTime());
            }
            previousEndTime = event.getEndTime();
        }

        if (newEvent.getStartTime() >= previousEndTime) {
            System.out.println("Start: " + previousEndTime + ", End: 2400");
        }
    }

    public void viewSchedule() {
        if (events.isEmpty()) {
            System.out.println("No events scheduled.");
        } else {
            System.out.println("Scheduled Events:");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }
}

public class EventSchedulerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventScheduler scheduler = new EventScheduler();

        while (true) {
            System.out.println("\nEvent Scheduler Menu:");
            System.out.println("1. Add Event");
            System.out.println("2. View Schedule");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter event description: ");
                    scanner.nextLine(); // Consume newline
                    String description = scanner.nextLine();
                    System.out.print("Enter start time (e.g., 900 for 9:00 AM): ");
                    int startTime = scanner.nextInt();
                    System.out.print("Enter end time (e.g., 1030 for 10:30 AM): ");
                    int endTime = scanner.nextInt();

                    if (startTime >= endTime || startTime < 0 || endTime > 2400) {
                        System.out.println("Invalid time range. Please try again.");
                    } else {
                        scheduler.addEvent(description, startTime, endTime);
                    }
                    break;
                case 2:
                    scheduler.viewSchedule();
                    break;
                case 3:
                    System.out.println("Exiting Event Scheduler. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
