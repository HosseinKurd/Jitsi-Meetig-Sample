package modularization.libraries.uicomponents.callbacks;

public interface GlobalClickCallback<T> {

    default void onClick() {
    }

    default void onClick(T t) {
    }

    default void onOptionClicked(T t) {
    }

}
