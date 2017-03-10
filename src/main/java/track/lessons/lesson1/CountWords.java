package track.lessons.lesson1;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

/**
 * Задание 1: Реализовать два метода
 *
 * Формат файла: текстовый, на каждой его строке есть (или/или)
 * - целое число (int)
 * - текстовая строка
 * - пустая строка (пробелы)
 *
 *
 * Пример файла - words.txt в корне проекта
 *
 * ******************************************************************************************
 *  Пожалуйста, не меняйте сигнатуры методов! (название, аргументы, возвращаемое значение)
 *
 *  Можно дописывать новый код - вспомогательные методы, конструкторы, поля
 *
 * ******************************************************************************************
 *
 */
public class CountWords {

    /**
     * Метод на вход принимает объект File, изначально сумма = 0
     * Нужно пройти по всем строкам файла, и если в строке стоит целое число,
     * то надо добавить это число к сумме
     * @param file - файл с данными
     * @return - целое число - сумма всех чисел из файла
     */

    public long countNumbers(File file) throws Exception {
      //  String path = "C:\\Users\\V\\track17-spring\\words.txt";
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8  );
        Iterator iterator = lines.iterator();
        int sum = 0;
        while (iterator.hasNext()) {
            String element = (String) iterator.next();
            boolean isElementInteger = isInteger(element);
            if (isElementInteger) {
                int num;
                num = Integer.parseInt(element);
                sum = sum + num;
            }
        }
        return sum;
    }


    public boolean isInteger(String str) {
        int size = str.length();

        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return size > 0;
    }


    /**
     * Метод на вход принимает объект File, изначально результат= ""
     * Нужно пройти по всем строкам файла, и если в строка не пустая и не число
     * то надо присоединить ее к результату через пробел
     * @param file - файл с данными
     * @return - результирующая строка
     */
    public String concatWords(File file) throws Exception {
       // String path = "C:\\Users\\V\\track17-spring\\words.txt";
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        Iterator iterator = lines.iterator();
        StringBuilder result = new StringBuilder("");
        while (iterator.hasNext()) {

            String element = (String) iterator.next();
            if (element.equals("")) {
                continue;
            }

            boolean isElementInteger = isInteger(element);

            if (!isElementInteger) {
                result.append(element);
                if (iterator.hasNext()) {
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }

}
