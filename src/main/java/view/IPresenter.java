package view;

import model.service.ServiceType;
import view.components.panels.forms.EntryFormPanel;
import view.components.panels.table.TablePanel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IPresenter {

    boolean login(String hostname, int port, String login, String password, String role);

    void logout();

    List<String> getAvailableTablesTitles();

    Set<String> getReadOnlyTablesTitles();

    List<String> getAvailableQueriesNames();

    List<String> getAvailableServiceNames();

    void prepareTablePanel(String tableTitle, TablePanel panel);

    void prepareEntryForm(String tableTitle, EntryFormPanel panel);

    void prepareQueryPanel(String queryName, TablePanel panel);

    void prepareQueryFilterPanel(String queryName, EntryFormPanel panel);

    void prepareServiceForm(String serviceName, EntryFormPanel panel);

    boolean fillTablePanel(String tableName, TablePanel panel);

    boolean fillQueryPanel(String queryName, Map<String, Object> queryVariables, TablePanel panel);

    void refreshTablePanel(String tableName, TablePanel panel);

    String getIDColumnTitle(String tableName);

    void updateCellValue(String tableName, String columnName, int idValue, Object newValue);

    boolean insertRow(String tableName, Map<String, Object> values);

    void removeRow(String tableName, String idColumnName, int idValue);

    void createTables();

    void fillTables();

    void deleteTables();

    void executeServiceInsert(String serviceName, Map<String, Object> values);

    ServiceType getServiceType(String serviceName);

    boolean executeServiceDelete(String serviceName);

    void createUser(String username, String userPassword, String role);
}
