package advanced.balik.application.view;

public enum Action {
    INSERT("You have added an element equal to %d to the heap."),
    MIN("The minimum element of the heap is %d."),
    EXTRACT_MIN("A minimum element equal to %d was taken from the heap."),
    CLEAR("Heap was cleaned!"),
    EMPTY("Heap is empty!"),
    STEP_BACK("Step back to previous heap!"),
    ALREADY_IN_THE_HEAP("Element %d is already in the heap"),
    /**
     * ANIMATION ACTIONS
     **/
    INSERT_BEGIN("Create heap with one node, merge with existing heap."),
    MIN_ELEMENT_LEFT("Recursively merge right subtree of left heap with right heap."),
    MIN_ELEMENT_RIGHT("Min element is in right heap. " +
            "Swapping..."),
    LEFT_NULL("Merging right heap with empty heap, return right heap."),
    RIGHT_NULL("Merging left heap with empty heap, return left heap."),
    RECONNECTING_TREE("Reconnecting tree after merge."),
    RIGHT_SUBTREE_LARGER("Right subtree has NPL bigger than left subtree. " +
            "Swapping..."),
    LEFT_SUBTREE_LARGER("Left subtree has NPL bigger than right subtree." +
            "No swap required..."),
    LEFT_CHILD_IS_NULL("Left child of node %d is null, occupy his place..."),
    EXTRACT_BEGIN("Remove root element,leaving two subtrees");

    private final String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
