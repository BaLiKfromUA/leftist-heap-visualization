package advanced.balik.application.graph;

/**
 * Перечисление константных типов.
 * <br>Инкапсулирует CSS-классы, используемые приложением динамически.
 * <br>Константы должны соответствовать СSS-классам из файла "layout.css".
 *
 * @author BaLiK
 */
public enum Style {

    /**
     * Стиль кнопок управления анимацией - пуск/пауза/стоп.
     */
    ANIMATION_BUTTON("animation-btn"),

    /**
     * Базовый стиль ребра дерева.
     */
    VERTEX("vertex"),

    /**
     * Базовый стиль ячейки дерева.
     */
    CELL_STYLE("cell"),

    /**
     * Стиль выбранной ячейки дерева.
     */
    CELL_SELECTED_STYLE("cell-selected");

    /**
     * Имя СSS-класса из layout.css
     */
    private final String styleClass;

    /**
     * Базовый конструктор
     */
    Style(String style) {
        styleClass = style;
    }

    /**
     * Геттер для {@link Style#styleClass}.
     *
     * @return {@link Style#styleClass}
     */
    public String getStyleClass() {
        return styleClass;
    }
}