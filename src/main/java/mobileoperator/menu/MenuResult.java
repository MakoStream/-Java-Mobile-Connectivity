package mobileoperator.menu;

public class MenuResult {

    public enum Type {
        STAY,
        BACK,
        NEXT,
        EXIT
    }

    private final Type type;
    private final Menu nextMenu;

    private MenuResult(
            Type type,
            Menu nextMenu
    ) {
        this.type = type;
        this.nextMenu = nextMenu;
    }

    public static MenuResult stay() {
        return new MenuResult(
                Type.STAY,
                null
        );
    }

    public static MenuResult back() {
        return new MenuResult(
                Type.BACK,
                null
        );
    }

    public static MenuResult next(Menu menu) {

        if (menu == null) {
            throw new IllegalArgumentException(
                    "Наступне меню не може бути null."
            );
        }

        return new MenuResult(
                Type.NEXT,
                menu
        );
    }

    public static MenuResult exit() {
        return new MenuResult(
                Type.EXIT,
                null
        );
    }

    public Type getType() {
        return type;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }
}