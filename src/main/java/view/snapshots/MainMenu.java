package view.snapshots;

import view.components.panels.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu extends JPanel {
    private final MenuPanel menuPanel;

    private static final String SHOW_TABLES_BUTTON_NAME = "Таблицы";
    private static final String SHOW_QUERIES_BUTTON_NAME = "Запросы";
    private static final String SHOW_SERVICE_BUTTON_NAME = "Инструменты";
    private static final String CREATE_USER_BUTTON_NAME = "Пользователи";
    private static final String SERVER_CONF_BUTTON_NAME = "Настройки сервера";
    private static final String LOGOUT_BUTTON_NAME = "Выход";

    public MainMenu(List<Integer> columnProportion){
        this.menuPanel = new MenuPanel(columnProportion);

        setLayout(new BorderLayout());

        menuPanel.addSpace();
        menuPanel.addButton(SHOW_TABLES_BUTTON_NAME);
        menuPanel.addButton(SHOW_QUERIES_BUTTON_NAME);
        menuPanel.addButton(SHOW_SERVICE_BUTTON_NAME);
        menuPanel.addButton(CREATE_USER_BUTTON_NAME);
        menuPanel.addButton(SERVER_CONF_BUTTON_NAME);
        menuPanel.addButton(LOGOUT_BUTTON_NAME);
        menuPanel.addSpace();

        add(menuPanel, BorderLayout.CENTER);
    }

    public void setConfigEnabled(boolean isEnable){
        JButton button = menuPanel.getButton(SERVER_CONF_BUTTON_NAME);
        button.setEnabled(isEnable);
    }

    public void setRoleFormEnabled(boolean isEnabled){
        JButton button = menuPanel.getButton(CREATE_USER_BUTTON_NAME);
        button.setEnabled(isEnabled);
    }

    public void addLogoutButtonListener(ActionListener actionListener) {
        menuPanel.addButtonListener(LOGOUT_BUTTON_NAME, actionListener);
    }

    public void addShowTablesListener(ActionListener actionListener) {
        menuPanel.addButtonListener(SHOW_TABLES_BUTTON_NAME, actionListener);
    }

    public void addShowQueryListener(ActionListener actionListener) {
        menuPanel.addButtonListener(SHOW_QUERIES_BUTTON_NAME, actionListener);
    }

    public void addShowServiceListener(ActionListener actionListener) {
        menuPanel.addButtonListener(SHOW_SERVICE_BUTTON_NAME, actionListener);
    }

    public void addConfigButtonListener(ActionListener actionListener) {
        menuPanel.addButtonListener(SERVER_CONF_BUTTON_NAME, actionListener);
    }

    public void addRoleButtonListener(ActionListener actionListener) {
        menuPanel.addButtonListener(CREATE_USER_BUTTON_NAME, actionListener);
    }
}
