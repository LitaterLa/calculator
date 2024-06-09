import java.util.*;

class Main {
    public static void main(String[] args) { // объявление основного метода
        Scanner scanner = new Scanner(System.in); // объявление объекта scanner
        String input = scanner.nextLine(); // сохранение ввода в переменную input
        try { // обработка исключения
            System.out.println(calc(input));
            scanner.close();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage()); // вывод сообщения исключения для конкретного случая
            scanner.close();

        }

    }

    public static String calc(String input) throws Exception {
        String[] userInput = input.split(" "); // разделяем по пробелам
        if (userInput.length != 3) { // проверка что 3 элемента массива
            throw new Exception("Некорректный ввод"); // добавление сообщения для текущего случая
        }

        RomanNumerals roman = new RomanNumerals(); // класс
        int num1, num2;
        boolean isRoman = roman.isRoman(userInput[0]) && roman.isRoman(userInput[2]); // проверка римские ли циферки
                                                                                      // ввели

        if (isRoman) {
            num1 = roman.romanToArabic(userInput[0]); // ниже работаю с этим методом, здесь просто применяю относительно
                                                      // первого введенного элемента
            num2 = roman.romanToArabic(userInput[2]);
        } else {
            num1 = Integer.parseInt(userInput[0]); // строка в интеджер
            num2 = Integer.parseInt(userInput[2]);
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10");
        }

        int result;
        switch (userInput[1]) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new Exception("Некорректная операция");
        }

        if (isRoman) {
            if (result < 1) {
                throw new Exception("Результат не может быть меньше I");
            }
            return roman.arabicToRoman(result); // функция объявлена ниже и переводит ар в рим
        } else {
            return String.valueOf(result);
        }
    }
}

class RomanNumerals {
    private static final Map<Character, Integer> romanMap = new HashMap<>(); // map - таблица для хранения ключ-значения

    static {
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
    }

    public boolean isRoman(String s) {
        return s.matches("^(M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3}))$"); // регулярное выражение проверяет по паттерну является ли текст римскими
                                          // цифрами
    }

    public int romanToArabic(String roman) { // метод преобразования римских цифр вublic int romanToArabic(String roman)
                                             // { // функция преобразования римских цифр в арабские
        int result = 0; // здесь храним итог
        for (int i = 0; i < roman.length(); i++) {
            if (i > 0 && romanMap.get(roman.charAt(i)) > romanMap.get(roman.charAt(i - 1))) {
                // смотрю каждую римскую циферку относительоно арабской, нп обработка IV (чтобы
                // выразить 4 нужно меньшее первое вычесть из большего второго)
                result += romanMap.get(roman.charAt(i)) - 2 * romanMap.get(roman.charAt(i - 1));
            } else {
                result += romanMap.get(roman.charAt(i));
            }
        }
        return result;
        // пример - roman = "IX" Делю на I X, беру I (i при этом = 0, первый элемент,
        // result для него 1 (1 - 2*0), дальше беру Х (его i = 1 => 10 - 2*1=8, then i
        // sum 8+1 - have 9 in the end)

    }

    public String arabicToRoman(int number) { // метод преобразования араб в римские
        StringBuilder result = new StringBuilder();
        List<Integer> values = Arrays.asList(100, 90, 50, 40, 10, 9, 5, 4, 1); // список арабских
        List<String> symbols = Arrays.asList("C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"); // список римских
        for (int i = 0; i < values.size(); i++) { // сайз метод кол-ва элементов в упоряд коллекции
            while (number >= values.get(i)) { // работает пока number >= value (например беру 14 - 10(первый элемент
                                              // списка) = 4 значит первый символ результата Х, крутимся дальше и 4 и 9
                                              // не совпали, 4 и 5 не совпали, крутимся до IV, теперь 0 цикл закрыт
                number -= values.get(i); //
                result.append(symbols.get(i)); // добавляет к рез-ту римскую циферку из symbols
            }
        }
        return result.toString(); // возврат к римским
    }
}

