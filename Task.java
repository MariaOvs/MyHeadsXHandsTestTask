/*
Задание:
На входе функция получает параметр n - натуральное число.
Необходимо сгенерировать n-массивов, заполнить их случайными числами, каждый массив имеет случайный размер.
Размеры массивов не должны совпадать.
Далее необходимо отсортировать массивы.
Массивы с четным порядковым номером отсортировать по возрастанию, с нечетным порядковым номером - по убыванию.
На выходе функция должна вернуть массив с отсортированными массивами.
 */
import java.util.*;

public class Task {
    private static final int n = 10;
    private static Random random;
    //диапазон для размера массива примем [1, 10], диапазон значений массивов [-1000, 1000]
    private static final int bound_value = 2000;
    private static final int bound_size = 10;
    private static List<Integer> array_sizes;

    //функция для создания требуемого массива массивов по параметру n
    private static Integer[][] taskFunction(){
        // Создание размеров массивов. Размеры - уникальные случайные положительные целые числа
        for (int i = 0; i < n; i++){
            int new_value = nextValue();
            if (new_value == -1){   //Случай, когда невозможно сгенерировать следующий уникальный размер массива. Так будет, если n > bound_size
                System.out.println("Невозможно сгенерировать следующий уникальный размер массива. Проверьте параметры системы");
                return new Integer[0][0];
            }
            array_sizes.add(new_value);//Найдено новое подходящее значение
        }

        //Создание n массивов чисел
        Integer[][] arrays = new Integer[n][];
        for (int i = 0; i < n; i++){
            arrays[i] = new Integer[array_sizes.get(i)];
            //Заполнение массивов случайными числами
            for (int j = 0; j < array_sizes.get(i); j++)
                arrays[i][j] = random.nextInt(bound_value+1)-bound_value/2;
            //Проводится сортиовка массивов (с четным порядковым номером - по возрастанию, с нечетным - по убыванию)
            if (i % 2 == 0)
                Arrays.sort(arrays[i]);
            else
                Arrays.sort(arrays[i], Collections.reverseOrder());
        }
        return arrays;
    }
    //Генерация нового уникального размера для массива
    private static int nextValue(){
        int count = 0;
        //Пытаемся найти подходящее уникальное значение
        int new_value = random.nextInt(bound_size) + 1;
        while ((count < 10) && (array_sizes.contains(new_value))) {
            new_value = random.nextInt(bound_size) + 1;
            count++;
        }
        if (!array_sizes.contains(new_value))
            return new_value;
        //Если несколько раз подряд сгенерировались повторяющиеся значения, будем искать их перебором
        return searchForValue();
    }
    //Поиск нового уникального значения.
    // Поиск осуществляется не повторяющимся генерированием случайных значений, а перебором в обе стороны от некоторого случайного значения
    private static int searchForValue(){
        int new_value = random.nextInt(bound_size)+1;
        int delta = 0;
        while ((new_value + delta <= bound_size) || (new_value - delta > 0)){
            if ((new_value - delta > 0) && (!array_sizes.contains(new_value - delta)))
                return new_value - delta;
            if ((new_value + delta <= bound_size) && (!array_sizes.contains(new_value + delta)))
                return new_value + delta;
            delta++;
        }
        //Если не нашли подходящее значение, значит, перебрали все значения в диапазоне, и все они уже использованы ранее
        return  -1;
    }
    public static void main(String[] args) {
        random = new Random();
        array_sizes = new ArrayList<Integer>();
        Integer[][] arrays = taskFunction();
        if (arrays.length == 0) // Если n < 1 или n > bound_size
            System.out.println("Полученный массив: []");
        else
            for (int i = 0; i <n; i ++)
                System.out.println("Массив №"+i + " (размер " + arrays[i].length + "): "+ Arrays.toString(arrays[i]));
    }
}

/*
Пример вывода программы:
Массив №0 (размер 3): [-711, -562, -285]
Массив №1 (размер 1): [-781]
Массив №2 (размер 4): [-368, -303, 837, 850]
Массив №3 (размер 10): [787, 733, 217, 31, -217, -226, -375, -518, -608, -692]
Массив №4 (размер 8): [-969, -577, -327, 98, 183, 290, 360, 400]
Массив №5 (размер 5): [847, 351, 58, -688, -965]
Массив №6 (размер 7): [-926, -556, 271, 414, 434, 673, 940]
Массив №7 (размер 9): [739, 493, 451, -36, -396, -465, -550, -602, -894]
Массив №8 (размер 2): [-849, 897]
Массив №9 (размер 6): [918, 852, 779, 248, -34, -776]
 */