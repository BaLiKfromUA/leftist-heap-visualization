# Тестирование

## Предисловие
Все тесты описанные в этом разделе выполнены с помощью фреймворка [JUnit4](https://junit.org/junit4/). 

**Почему?**

Потому что это удобнее и даёт более правдивую информацию, 
т.к мой самописный тестер обрабатывает строки, а это очень дорого в Java. 

## Где файлы, Лебовски?
Чтобы не заставлять тестировщика разбираться с джавой, некоторые тестовые файлы я всетаки сделал(глянуть [тут](https://github.com/BaLiKfromUA/project_advance_1/tree/master/consoleApp/tests)).

В этих файлах лежат тесты для самой структуры(левосторонней кучи), чтобы тестер мог их запустить в консольном приложении.

Все остальные тесты(тесты парсера, команд, самого тестера) в файлах отсутствуют, но будут описаны.

## Тесты левосторонней кучи
Все тесты можно разделить на **5** групп по **размеру** и на **13** по **типу**.  
Начнем с разделения по **размеру**:
- **Small** - количество тестовых элементов **500**
- **Medium** - количество тестовых элементов **5000**
- **Big** - количество тестовых элементов **50000**
- **Large** - количество тестовых элементов **500000**
- **Extra-large** - количество тестовых элементов **5000000**

Для каждого размера выполняются **13** видов тестов, которые будут описаны ниже.

### 1) Compare with PriorityQueue test
Java предоставляет свою реализацию приоритетной очереди в пакете Collections. Насколько мне известно, под капотом
там бинарная куча. Так что можем сравнить результаты одних и тех же операций в нашей куче и куче джавы:)
Для этого я решил запустить цикл от **0** до **size-1**, где size зависит от типа теста по размеру.
На каждом шаге цикла мы добавляем случайный элемент в наши кучи и проверяем минимальный элемент. Как можно догадаться, он должен быть одинаков:)
После этого, мы пошагово опустошаем обе кучи и после каждого шага проверяем минимальный элемент в каждой куче.
Ну и по окончанию проверяем что обе кучи пусты. 

Исходный код теста представлен ниже:
```
    @Test
    @DisplayName("Compare with priority queue Test")
    public void insertCompareWithPriorityQueue() {
        PriorityQueue<Integer> javaPriorityQueue = new PriorityQueue<>();
        RANDOM.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; ++i) {
            final int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
            javaPriorityQueue.add(randomValue);
            heap.insert(randomValue);
            assertEquals("Minimums in heaps should be equal:", (int) javaPriorityQueue.peek(), heap.getMin());
        }

        for (int i = 0; i < size; ++i) {
            assertEquals("Minimums in heaps should be equal:", (int) javaPriorityQueue.peek(), heap.getMin());
            javaPriorityQueue.poll();
            heap.extractMin();
        }

        assertEquals("Heaps should be empty", heap.isEmpty(), javaPriorityQueue.isEmpty());
    }
    
```

**Данный тест показывает что наша самописная очередь идейно работает верно.**

### 2) Insert negative increasing numbers test
Данный тест являеться первым из семейства тестов с убывающими/возврастающими последовательностями.
Что мы делаем? Мы добавляем в кучу элементы начиная с **-(size-1)** до **0**. И после каждого добавления
проверяем минимальный элемент. Очевидно, что это всегда должен быть **-(size-1)**. После добавления всех элементов
мы опустошаем кучу в массив и проверяем что элементы в правильном порядке(по возрастанию).

Исходный код теста представлен ниже:
```
    @Test
    @DisplayName("Increasing negative elements test")
    public void insertNegativeIncreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = size-1; i >= 0; --i) {
            heap.insert(-i);
            expected.add(-i);
            assertEquals("Min should be -size:", -(size-1), heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
    
``` 

**Данный тест (как и следующий) показывает что куча правильно работает с отрицательными числами,
и правильно их возвращает.**
### 3) Insert decreasing negative numbers test
Похожий тест, но теперь числа вводяться в порядке от **0** до **-(size-1)**. И минимальным после каждого шага будет 
последний добавленный элемент.
После ввода, извлекаем все элементы из кучи и проверяем их порядок(должно быть по возрастанию).

Исходный код теста: 
```
    @Test
    @DisplayName("Decreasing negative elements test")
    public void insertNegativeDecreasing(){
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            heap.insert(-i);
            expected.add(-i);
            assertEquals("Min should be -index:", -i, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

```
### 4) Insert increasing positive numbers test
Вводим числа в порядке от **0** до **size-1**. На каждом шаге проверяем минимальный элемент(всегда должен быть 0).
После ввода, извлекаем все элементы из кучи и проверяем их порядок(должно быть по возрастанию).

Исходный код теста:
```
    @Test
    @DisplayName("Increasing positive elements test")
    public void insertIncreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            expected.add(i);
            assertEquals("Min should be 0:", 0, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
```

**Данный тест (как и следующий) показывает что куча правильно работает с положительными числами,
и правильно их возвращает.**

### 5) Insert decreasing positive numbers test
Похожий тест, но теперь числа вводяться в порядке от **size-1** до **0**. И минимальным после каждого шага будет 
последний добавленный элемент. После ввода, извлекаем все элементы из кучи и проверяем их порядок(должно быть по возрастанию).

Исходный код теста: 
```
    @Test
    @DisplayName("Decreasing positive elements test")
    public void insertDecreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = size; i > 0; --i) {
            heap.insert(i);
            expected.add(i);
            assertEquals("Min should be current index:", i, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

```

### 6) Insert zero test
Добавляем 0 **size** раз. 
После каждого раза проверяем ноль ли минимум:)
Так же потом извлекаем все нули и проверяем не потеряли ли что-то.

Исходный код теста:
```
    @Test
    @DisplayName("Zero Test")
    public void insertZero() {
        Integer[] expectedArray = new Integer[size];
        Arrays.fill(expectedArray, 0);

        for (int i = 0; i < size; ++i) {
            heap.insert(0);
            assertEquals("Min should be 0:", 0, heap.getMin());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
```
**Данный тест проверяет правильно ли работает наша куча с одинаковыми элементами**

### 7) Insert same random test
Такая же механика как и в **6** тесте, только вставляем один и тот же **случайный 
элемент**.

Исходный код теста:
```
    @Test
    @DisplayName("Same Random Test")
    public void insertOneRandomNumber() {
        RANDOM.setSeed(System.currentTimeMillis());
        final int randomNumber = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        Integer[] expectedArray = new Integer[size];
        Arrays.fill(expectedArray, randomNumber);

        for (int i = 0; i < size; ++i) {
            heap.insert(randomNumber);
            assertEquals("Min should be your random number:", randomNumber, heap.getMin());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
    
```

### 8) Insert-extract test
Добавляем в кучу элементы от **0** до **size-1**. После каждого добавления проверяем минимум и извлекаем его.
Минимум на каждом шаге должен быть последним добавленным элементом. Так же после каждого добавления и в конце теста
проверяем пуста ли куча.

Исходный код теста:
```
    @Test
    @DisplayName("Insert-extract test")
    public void insertExtract() {
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            assertEquals("Min should be current index:", i, heap.getMin());
            heap.extractMin();
            assertTrue("Heap should be empty:", heap.isEmpty());
        }

        assertEquals("Min should be -1:", -1, heap.getMin());
        assertTrue("Heap should be empty:", heap.isEmpty());
    }
```
**Данный тест проверяет правильно ли добавление и извлечение элемента взаимодействуют вместе.
Так же мы проверяем не осталось ли что-то в куче после удаления всех элементов**

### 9) Get min test
Добавляем случайный элемент в кучу и **size** раз проверяем минимум в куче. 

Исходный код теста:
```
    @Test
    @DisplayName("GetMin Test")
    public void getMinFromOneElementHeap() {
        RANDOM.setSeed(System.currentTimeMillis());
        final int randomNumber = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        heap.insert(randomNumber);
        for (int i = 0; i < size; ++i) {
            assertEquals("Min should be your random number:", randomNumber, heap.getMin());
        }
    }
```
**Данный тест проверяет отсутствие влияния вывода минимума на элементы в куче.**

### 10) Empty heap test
**А что если пусто?** 

Забыл сказать, что если куча пуста, то минимум будет возвращать число -1(в 
консоли выводит сообщение что куча пуста).
 
Так вот, попробуем **size** раз вывести минимум, а потом его извлечь. Упадет ли куча?

Исходный код теста:
```
    @Test
    @DisplayName("Empty Heap Test")
    public void extractFromEmptyHeap() {
        for (int i = 0; i < size; ++i) {
            assertEquals("Min should be -1", -1, heap.getMin());
            heap.extractMin();
        }
    }
```
**Данный тест проверяет устойчивость пустой кучи к запросам.**
### 11) Insert-extract increasing test
Добавляем в кучу числа от **0** до **size-1**. После каждого добавления проверяем минимум, добавляем 
элемент на единицу больше текущего, опять проверяем минимум, извлекаем его и опять проверяем новый минимум.
После этого опять извлекаем его.

Исходный код теста:
```
    @Test
    @DisplayName("Insert-Extract Increasing Test")
    public void insertExtractIncreasing() {
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            assertEquals("Min should be current index:", i, heap.getMin());
            heap.insert(i + 1);
            assertEquals("Min should be current index:", i, heap.getMin());
            heap.extractMin();
            assertEquals("Min should be current index+1:", i + 1, heap.getMin());
            heap.extractMin();
        }
    }

```
**Данный тест похож на 8, но тут комбинация немного сложнее и стоило проверить правильность ее выполнения.**

### 12) Insert-extract test with random values test
Почти аналогично с 11 тестом, но теперь мы каждый раз случайное число, а значит минимум не всегда будет на единицу меньше

Исходный код теста:
```
    @Test
    @DisplayName("Insert-Extract test with random values")
    public void insertExtractRandom() {
        int previousElement = -1;
        RANDOM.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; ++i) {
            assertEquals("Min should be previous random element:", previousElement, heap.getMin());
            heap.extractMin();
            previousElement = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
            heap.insert(previousElement);
        }
    }
```

### 13) Insert random test
Добавляем **size** случайных чисел в кучу. Потом извлекаем их и проверяем их порядок(должно быть по возрастанию)

Исходный код теста:
```
    @Test
    @DisplayName("Insert Random Test")
    public void insertRandom() {
        List<Integer> expected = new ArrayList<>();
        RANDOM.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; ++i) {
            final int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
            heap.insert(randomValue);
            expected.add(randomValue);
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }
```
**Данный тест проверяет правильность извлечения абсолютно случайных данных**

### А что по времени?
Спасибо разрабам JUnit за отличный функционал. Благодаря аннотации  **@Test(timeout = ...)**
мы можем ставить ограничение на выполнение теста.

Т.к. во всех тестах выполняется очень разное количество операций, то нужно было выбрать ограничение оптимальное для всех тестов. 
Для тестов размером от **500** до **50000** было выставлено ограничение в секунду для каждого теста.

Для тестов размером **500000** было выставлено ограничение в 2 секунды. Исключением был только тест со сравнением с очередью
(т.к. там 2 кучи,то поставил 2.5 секунды ограничения).

Для самых больших тестов(**5000000**) ограничение решил не ставить, т.к эти тесты очень сильно зависят от железа и настроек
виртуальной машины. Так что какое то оптимальное ограничение придумать сложно.

### Что по памяти?
Тут все сложно. Никакого адекватного инструмента для Memory Limit я не нашел. Да и учитывая специфику работы виртуальной
машины и сборщика мусора, очень трудно определить граничное значение. В Java с памятью весело:)

### Доказательства то где?!
Все зеленое, даже на моем слабом ноутбуке
![test result](https://github.com/BaLiKfromUA/project_advance_1/blob/master/docs/img/testResult.png)



## Тестирование консоли
Так как нам нужно парсить командную строку, то нужен удобный парсер. 
Для парсинга строки я выбрал свежий фреймворк [Picocli](https://picocli.info/), т.к он активно обновляется и функционал
с документацией радует.

Парсер файлов и тестов я написал сам, функционал минимально необходимый и оттестирован.

Т.к. парсер файлов и тестер являются частью консольного приложения, то перейдем к тестам самого приложения. Если ошибка в его частях,
то ошибка будет и в приложении.

Документацию к командам можно найти [тут](https://github.com/BaLiKfromUA/project_advance_1/blob/master/consoleApp/README.md), так что не буду описывать синтаксис.

### Правильные тесты
- Для начала, стоит протестировать команды **insert** и **extract min**. Делается это в следующих
двух тестах:
```
    @Test
    public void testOnlyInsert() {
        final String[] args = {"-i", "5", "-i", "6", "-i", "5", "-i", "1", "-i", "2", "-i", "3"};
        final String expectedOut = "Inserting 5\n" +
                "Inserting 6\n" +
                "Inserting 5\n" +
                "Inserting 1\n" +
                "Inserting 2\n" +
                "Inserting 3\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }

    @Test
    public void testInsertAndExtract() {
        final String[] args = {"-e", "min", "-i", "6", "-i", "5", "-i", "1", "-e", "min", "-e", "min"};
        final String expectedOut = "Heap is empty!\n" +
                "Inserting 6\n" +
                "Inserting 5\n" +
                "Inserting 1\n" +
                "Min number is 1\n" +
                "Min number is 5\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

- Проверим считывание команд с файла

Содержание файла **test_3.txt**:
```
-e min
-e min
-i 3
-e min
```
Исходный код теста:
```
    @Test
    public void testOnlyFile() {
        final String[] args = {"-f", "./src/main/resources/test_3.txt"};
        final String expectedOut = "Heap is empty!\n" +
                "Heap is empty!\n" +
                "Inserting 3\n" +
                "Min number is 3\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

- Проверка правильного пользовательского теста

Содержание файла **inputTest.txt**
```
1
2
3
min
extractMin
min
extractMin
min
extractMin
```
Содержание файла **outputTest.txt**
```
1
2
3
```
Исходный код теста:
```
   @Test
    public void testCorrect() {
        final String[] args = {"-t", "./src/main/resources/inputTest.txt,./src/main/resources/outputTest.txt"};
        final String expectedOut = Message.TEST_OK.getMessage() + "\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

### Неправильные тесты
- Ввод неправильных команд
```
    @Test
    public void testWrongCommands() {
        final String[] args = {"sanya", "sanya", "sanya", "-i", "1", "-e", "min", "-e", "min"};
        final String expectedOut = "Inserting 1\n" + "Min number is 1\n" + "Heap is empty!\n" + "Unknown command\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

- Ввод несуществующего файла
```
    @Test
    public void testFileNotFound() {
        final String[] args = {"-f", "where is this file?"};
        final String expectedOut = "File not found\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```
- Ввод неправильных параметров в команде
```
    @Test
    public void testWrongCommandParameterAtStart() {
        final String[] args = {"-i", "a", "-e", "min", "-e", "min", "-i,", "1"};
        final String expectedOut = "Wrong parameter\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
    
    @Test
    public void testWrongCommandParameterInEnd() {
        final String[] args = {"-i", "3", "-e", "min", "-i", "a", "-e", "aaa"};
        final String expectedOut = "Inserting 3\n" + "Min number is 3\n" + "Wrong parameter\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```
- Ввод неправильных команд в файле

Содержание файла **test_4.txt**
```
-i 1 -e min -e min balik
```

Исходный код теста:
```
    @Test
    public void testFileWrongCommands() {
        final String[] args = {"-f", "./src/main/resources/test_4.txt"};
        final String expectedOut = "Inserting 1\n" + "Min number is 1\n" + "Heap is empty!\n" + "Unknown command\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

- Не ввели команды

```
    @Test
    public void testEmptyCommands() {
        final String[] args = {};
        final String expectedOut = "There are no commands\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

- Не ввели файл(ы) для теста
```
   @Test
    public void testIncorrectOutputFile() {
        final String[] args = {"--test", "./src/main/resources/inputTest.txt,where is it?"};
        final String expectedOut = Message.OUTPUT_FILE_ERROR.getMessage() + "\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }

    @Test
    public void testIncorrectInputFile() {
        final String[] args = {"--test", "where is it?,./src/main/resources/outputTest.txt"};
        final String expectedOut = Message.INPUT_FILE_ERROR.getMessage() + "\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

- Невалидный тестовый файл

Содержание файла **validateTest.txt**
```
1
2
3.2
max
min
min
```
Исходный код теста:
```
    @Test
    public void testValidate() {
        final String[] args = {"--test", "./src/main/resources/validateTest.txt,./src/main/resources/outputTest.txt"};
        final String expectedOut = Message.INPUT_ERROR.getMessage() + "\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
```

**Как можно было увидеть в документации, некоторые команды нельзя использовать больше одного
раза или в комбинации с другими командами**

Это я тоже учел и проверил:
```
    @Test
    public void testCustomTesterWithOtherCommandAfter() {
        final String[] args = {"--test", "where is it?,./src/main/resources/outputTest.txt", "-i", "6"};
        final String expectedOut = Message.INPUT_FILE_ERROR.getMessage() + "\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }

    @Test
    public void testCustomTesterWithOtherCommandBefore() {
        final String[] args = {"-i", "6", "-e", "min", "--test", "where is it?,./src/main/resources/outputTest.txt"};
        final String expectedOut = Message.INPUT_FILE_ERROR.getMessage() + "\n";
        Main.main(args);
        assertEquals(expectedOut, systemOutRule.getLog());
    }
    
    @Test
    public void testOverwrittenCommand() {
       final String[] args = {"-f", "./src/main/resources/test_3.txt",
                    "-f", "./src/main/resources/test_3.txt"};
       final String expectedOut = "Heap is empty!\n" +
                    "Heap is empty!\n" +
                    "Inserting 3\n" +
                    "Min number is 3\n" +
                    "Option should be specified only once\n";
       Main.main(args);
       assertEquals(expectedOut, systemOutRule.getLog());
    }
```

### Доказательства то где?!
Все зеленое
![incorrect test result](https://github.com/BaLiKfromUA/project_advance_1/blob/master/docs/img/consoleTest.png)


## Extra test
Решил для удобства тестировщика прогнать все файлы в отдельном тесте:)

Для этого написал код,который по паттернам меняет имена файла и прогоняет все размеры(
кроме extra-large, т.к он в архиве
)


Исходный код теста:
```
    private final String OK = Message.TEST_OK.getMessage() + "\n";
    private final String inputPattern = "./tests/input/%s/%s%d.input";
    private final String outputPattern = "./tests/answers/%s/%s%d.answer";
    private final String commandPattern = "%s,%s";

    @Test
    public void runAll() {
        String[] names = {"increasingNegative","decreasingNegative","increasingPositive","decreasingPositive",
        "insertZero","insertSameRandom","insertExtract","minFromOne","empty","insertExtractIncreasing","insertExtractIncreasingRand",
        "insertRandom"};

        for (final String testName : names) {
            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.SMALL.getSizeName(), testName, TestPatterns.SMALL.getSize()),
                    String.format(outputPattern, TestPatterns.SMALL.getSizeName(), testName, TestPatterns.SMALL.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();

            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.MEDIUM.getSizeName(), testName, TestPatterns.MEDIUM.getSize()),
                    String.format(outputPattern, TestPatterns.MEDIUM.getSizeName(), testName, TestPatterns.MEDIUM.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();

            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.BIG.getSizeName(), testName, TestPatterns.BIG.getSize()),
                    String.format(outputPattern, TestPatterns.BIG.getSizeName(), testName, TestPatterns.BIG.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();

            Main.main(new String[]{"--test", String.format(commandPattern,
                    String.format(inputPattern, TestPatterns.LARGE.getSizeName(), testName, TestPatterns.LARGE.getSize()),
                    String.format(outputPattern, TestPatterns.LARGE.getSizeName(), testName, TestPatterns.LARGE.getSize()))});

            assertEquals(OK, systemOutRule.getLog());
            systemOutRule.clearLog();
        }
    }

```


### **Результат:**
![incorrect test result](https://github.com/BaLiKfromUA/project_advance_1/blob/master/docs/img/all-files.jpg)


## Итоги
Теперь прогоним все тесты.

Всего тестов - **98**

Пройдено - **98**

![incorrect test result](https://github.com/BaLiKfromUA/project_advance_1/blob/master/docs/img/all-tests.png)
