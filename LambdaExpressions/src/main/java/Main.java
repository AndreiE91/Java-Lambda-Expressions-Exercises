import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

        System.out.println("First part:");
        list.forEach(System.out::println);

        List<MonitoredData> list1 = null;
        list1 = list.stream().filter(MonitoredData::respectSleep).collect(Collectors.toList());

        System.out.println("Second part:");
        list1.forEach(System.out::println);

        List<Map<Integer, Map<String, Integer>>> list2 = list.stream()
                .collect(Collectors.groupingBy(
                        MonitoredData::getDay,
                        Collectors.toMap(
                                MonitoredData::getActivity,
                                MonitoredData::computeDuration,
                                Math::max)))
                .entrySet().stream()
                .map(entry -> Collections.singletonMap(entry.getKey(), entry.getValue())).toList();

        System.out.println("Third part:");
        list2.forEach(System.out::println);
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

