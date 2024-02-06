import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String fileName = "Activities.txt";
        List<MonitoredData> list = null;

        try {
            list = Files.lines(Paths.get(fileName))
                    .map(line -> line.split("\t\t"))
                    .map(stringVector -> new MonitoredData(stringVector[0], stringVector[1], stringVector[2]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("a)Objects correctly pulled from file:");
        list.forEach(System.out::println);

        long distinctDaysCount = list.stream().map(MonitoredData::getDay).distinct().count();
        System.out.println("b)Distinct days count = " + distinctDaysCount);

        List<Map<String, Integer>>  totalActivityAppearanceList = list.stream()
                .collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingInt(e -> 1)))
                .entrySet()
                .stream()
                .map(entry -> {
                    Map<String, Integer> map = new HashMap<>();
                    map.put(entry.getKey(), entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
        System.out.println("c)Activity appearance count:");
        totalActivityAppearanceList.forEach(System.out::println);

        Duration totalSleepDuration = list.stream()
                .filter(activity -> activity.getActivity().equals("Sleeping"))
                .map(activity -> Duration.between(activity.getStartTime(), activity.getEndTime()))
                .reduce(Duration.ZERO, Duration::plus);
        System.out.println("d)Total duration of sleep activity(hours) = " + totalSleepDuration.toHours());

        List<Map<Integer, Map<String, Integer>>> list2 = list.stream()
                .collect(Collectors.groupingBy(
                        MonitoredData::getDay,
                        Collectors.toMap(
                                MonitoredData::getActivity,
                                MonitoredData::computeDuration,
                                Math::max)))
                .entrySet().stream()
                .map(entry -> Collections.singletonMap(entry.getKey(), entry.getValue())).toList();
        System.out.println("e)Count how many times each activity has appeared for each day");
        list2.forEach(System.out::println);

        long countToileting = list.stream()
                .filter(activity -> activity.getActivity().equals("Toileting"))
                .filter(activity -> activity.getDay() % 2 != 0)
                .count();
        System.out.println("f)Count of many times toileting appeared on odd days: " + countToileting);


        List<Map<String, LocalTime>> activityDurations = list.stream()
                .collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingInt(activity -> (int) Duration.between(activity.getStartTime(), activity.getEndTime()).toHours())))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, LocalTime> map = new HashMap<>();
                    map.put(entry.getKey(), LocalTime.ofSecondOfDay(entry.getValue()));
                    return map;
                })
                .collect(Collectors.toList());
        System.out.println("g)For each activity compute the entire duration over the monitoring period");
        activityDurations.forEach(System.out::println);
        System.out.println();

        Map<Integer, Integer> toiletingDurationsByDay = list.stream()
                .filter(activity -> activity.getActivity().equals("Toileting"))
                .collect(Collectors.groupingBy(activity -> activity.getStartTime().getDayOfMonth(),
                        Collectors.summingInt(activity -> (int) Duration.between(activity.getStartTime(), activity.getEndTime()).toMinutes())));
        System.out.println("h)Compute the total duration of the Toileting activity for each day from the monitored period");
        System.out.println(toiletingDurationsByDay);

        List<MonitoredData> finalList = list;
        List<String> filteredActivities = list.stream()
                .filter(activity -> {
                    long count = finalList.stream()
                            .filter(a -> a.getActivity().equals(activity.getActivity()))
                            .filter(a -> Duration.between(a.getStartTime(), a.getEndTime()).toMinutes() < 5)
                            .count();
                    long totalCount = finalList.stream()
                            .filter(a -> a.getActivity().equals(activity.getActivity()))
                            .count();
                    return (count / (double) totalCount) > 0.9;
                })
                .map(MonitoredData::getActivity)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("i)Filter the activities that have more than 90% of the monitoring records with duration less\n" +
                "than 5 minutes");
        filteredActivities.forEach(System.out::println);


        List<MonitoredData> list1 = null;
        list1 = list.stream().filter(MonitoredData::respectSleep).collect(Collectors.toList());
        System.out.println("j)Filter the days in which sleeping is more than 6 hours");
        list1.forEach(System.out::println);

        Map<Integer, Map<String, Integer>> activityDurationsByDay = list.stream()
                .collect(Collectors.groupingBy(activity -> activity.getStartTime().getDayOfMonth(),
                        Collectors.groupingBy(MonitoredData::getActivity,
                                Collectors.summingInt(activity -> (int) Duration.between(activity.getStartTime(), activity.getEndTime()).toMinutes()))));
        System.out.println("k)Compute the duration of each activity for each day from the monitored period");
        System.out.println(activityDurationsByDay);

    }
}
// Code from the previous lab

// Example from board
        /*
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        List<Integer> numbersList = Arrays.asList(numbers);

        Integer sum = numbersList.stream().filter(x->x%2==1).map(y->y*y).reduce(0, Integer::sum);
        System.out.println("Sum(calculated via lambda expressions) = " + sum);
         */

//        final int MAX_NUMBERS = 100;
//
//        List<Integer> numbersList = new ArrayList<Integer>();
//        Random random = new Random();
//        for(int i = 0; i < MAX_NUMBERS; ++i) {
//            numbersList.add(random.nextInt(MAX_NUMBERS / 10));
//        }
//
//        // Frequency map computation
//        Map<Integer, Integer> frequencyMap = numbersList.stream()
//                .collect(HashMap::new, (map, number) -> map.merge(number, 1, Integer::sum), HashMap::putAll);
//
//        // Frequency map of only odd numbers
//        Map<Integer, Integer> oddFrequencyMap = numbersList.stream().filter(x -> x%2==1).
//                collect(HashMap::new, (map, number) -> map.merge(number, 1, Integer::sum), HashMap::putAll);
//
//
//
//        // Print the input numbersList
//        int spaceIndex = 0;
//        for(Integer number : numbersList) {
//            System.out.print(number + " ");
//            if(spaceIndex % 80 == 0) {
//                spaceIndex = 0;
//                System.out.println();
//            }
//            ++spaceIndex;
//        }
//        System.out.println();
//
//        // Print the frequencyMap
//        System.out.println("Frequency map:");
//        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
//            System.out.println("Number: " + entry.getKey() + ", Frequency: " + entry.getValue());
//        }
//
//        // Print the oddFrequencyMap
//        System.out.println("Odd frequency map:");
//        for (Map.Entry<Integer, Integer> entry : oddFrequencyMap.entrySet()) {
//            System.out.println("Number: " + entry.getKey() + ", Frequency: " + entry.getValue());
//        }

