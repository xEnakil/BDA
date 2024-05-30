import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        // Task 2
        List<String> strings = List.of("123", "abc", "456", "78.9", "789");
        List<Long> longs = filterAndCastToLong(strings);
        System.out.println(longs);

        // Task 3
        List<String> sentences = List.of(
                "Java streams are powerful",
                "Streams allow functional programming in Java",
                "Functional programming is powerful"
        );
        List<String> longs2 = collectWords(sentences);
        System.out.println(longs2);

        // Task 4
        List<Double> randomDoubles = generateRandomDouble(50);
        System.out.println(randomDoubles);
    }

    public static List<Long> filterAndCastToLong(List<String> strings) {
        return strings.stream()
                .filter(str -> {
                    try {
                        Long.parseLong(str);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .map(Long::parseLong)
                .toList();
    }

    public static List<String> collectWords(List<String> strings) {
        return strings.stream()
                .flatMap(str -> Stream.of(str.split(" ")))
                .toList();
    }

        public static List<Double> generateRandomDouble(int size) {
            Random random = new Random();
            return Stream.generate(random::nextDouble)
                    .limit(size)
                    .collect(Collectors.toList());
        }
}