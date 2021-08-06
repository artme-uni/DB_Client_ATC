package model;

public enum Role {
    ADMINISTRATOR ("Администратор"),
    USER ("Пользователь"),
    ACCOUNTANT ("Бухгалтер"),
    INSTALLER("Установщик телефонов"),
    CLIENT_MANAGER ("Клиент-менеджер"),
    CTN_MANAGER ("Следящий за АТС");

    private final String roleLabel;

    Role(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    @Override
    public String toString() {
        return roleLabel;
    }
}
