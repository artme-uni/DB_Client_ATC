package view.snapshots;

import view.components.panels.ButtonsPanel;
import view.components.panels.forms.EntryFormPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RoleForm extends JPanel {
    private final EntryFormPanel entryForm;
    private final ButtonsPanel buttonsPanel;

    private static final String BACK_BUTTON_NAME = "Назад";
    private static final String CREATE_BUTTON_NAME = "Создать";

    private static final String USERNAME_FIELD_NAME = "Имя пользователя";
    private static final String USER_PASSWORD_FIELD_NAME = "Пароль";
    private static final String ROLES_FIELD_NAME = "Роль";
    private static final String[] ROLES = {"Администратор", "Установщик телефонов",
            "Бухгалтер", "Клиент-менеджер", "Следящий за АТС"};

    public RoleForm(List<Integer> columnProportion) {
        setLayout(new BorderLayout());

        entryForm = new EntryFormPanel(columnProportion);
        buttonsPanel = new ButtonsPanel();

        entryForm.addClearRow();
        entryForm.addTextField(USERNAME_FIELD_NAME);
        entryForm.addTextField(USER_PASSWORD_FIELD_NAME);
        entryForm.addDropDownField(ROLES_FIELD_NAME, ROLES);
        entryForm.addClearRow();

        buttonsPanel.addButton(BACK_BUTTON_NAME);
        buttonsPanel.addButton(CREATE_BUTTON_NAME);

        add(entryForm.getPanelContent(), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void addBackButtonListener(ActionListener actionListener){
        buttonsPanel.addButtonListener(BACK_BUTTON_NAME, actionListener);
    }

    public void addCreateButtonListener(ActionListener actionListener){
        buttonsPanel.addButtonListener(CREATE_BUTTON_NAME, actionListener);
    }

    public String getUsername(){
        return entryForm.getString(USERNAME_FIELD_NAME);
    }

    public String getPassword(){
        return entryForm.getString(USER_PASSWORD_FIELD_NAME);
    }

    public String getRole(){
        return entryForm.getString(ROLES_FIELD_NAME);
    }
}
