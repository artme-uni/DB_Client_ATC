package presenter;

import connectivity.ConnectionProperties;
import connectivity.DataAccessObject;
import model.database.DatabaseCTN;
import model.database.DatabaseModel;
import model.properties.PropertiesBundle;
import model.properties.PropertyType;
import model.queries.QueriesCTN;
import model.queries.QueriesModel;
import model.service.ServiceCTN;
import model.service.ServiceInfo;
import model.service.ServiceModel;
import model.service.ServiceType;
import model.support.SupportElementsCTN;
import model.support.SupportElementsModel;
import view.IPresenter;
import view.components.panels.forms.EntryFormPanel;
import view.components.panels.table.TablePanel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Presenter implements IPresenter {
    private final ArrayList<ErrorHandler> errorHandlers = new ArrayList<>();
    private DataAccessObject dao;
    private String clientRole;

    private final DatabaseModel databaseModel;
    private final QueriesModel queriesModel;
    private final SupportElementsModel supportElementsModel;
    private final ServiceModel serviceModel;

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_TIME_FORMAT_SQL = "dd-MM-yyyy hh:mi:ss";
    private static final String DATE_TIME_FORMAT_JAVA = "dd-MM-yyyy hh:mm:ss";

    private final TitleConverter titleConverter = new TitleConverter();

    private static final String TABLE_NAMESPACE = "Table namespace";

    public Presenter() {
        this.databaseModel = new DatabaseCTN().getModel();
        this.queriesModel = new QueriesCTN().getModel();
        this.supportElementsModel = new SupportElementsCTN().getModel();
        this.serviceModel = new ServiceCTN().getModel();

        titleConverter.addTitles(TABLE_NAMESPACE, databaseModel.getTablesTitles());
        initializeTableTitles();
        initializeQueriesTitles();
        initializeServiceTitles();
    }

    private void initializeTableTitles() {
        List<String> names = databaseModel.getAvailableTables();

        for (String tableName : names) {
            PropertiesBundle tableProperties = databaseModel.getTable(tableName);
            titleConverter.addTitles(tableName, tableProperties.getTitles());
        }
    }

    private void initializeQueriesTitles() {
        List<String> names = queriesModel.getAvailableQueriesNames();

        for (String queryName : names) {
            PropertiesBundle queryProperties = queriesModel.getQuery(queryName);
            titleConverter.addTitles(queryName, queryProperties.getTitles());
        }
    }

    private void initializeServiceTitles() {
        List<String> names = serviceModel.getAvailableServicesNames();

        for (String serviceName : names) {
            PropertiesBundle service = serviceModel.getServiceBundle(serviceName);
            titleConverter.addTitles(serviceName, service.getTitles());
        }
    }

    @Override
    public boolean login(String hostname, int port, String login, String password, String role) {
        ConnectionProperties properties = new ConnectionProperties(hostname, port, login, password);
        setClientRole(role);

        try {
            this.dao = new DataAccessObject(properties);
        } catch (SQLException exception) {
            showMessage("Cannot connect to " + hostname + ":" + port, exception.getMessage());
            return false;
        } catch (IOException ioException) {
            showMessage("Cannot create dao :{}", ioException.getMessage());
            ioException.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<String> getAvailableTablesTitles() {
        List<String> names = databaseModel.getAvailableTables(clientRole);
        return titleConverter.getTitles(TABLE_NAMESPACE, names);
    }

    @Override
    public Set<String> getReadOnlyTablesTitles() {
        Set<String> names = databaseModel.getReadAccessedTables(clientRole);
        return titleConverter.getTitles(TABLE_NAMESPACE, names);
    }

    @Override
    public List<String> getAvailableQueriesNames() {
        return queriesModel.getAccessedQueriesNames(clientRole);
    }

    @Override
    public List<String> getAvailableServiceNames() {
        return serviceModel.getAccessedServiceNames(clientRole);
    }

    @Override
    public void logout() {
        dao.closeConnection();
    }

    @Override
    public void prepareTablePanel(String tableTitle, TablePanel panel) {
        String name = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        PropertiesBundle tableInfo = databaseModel.getTable(name);
        prepareTable(tableInfo, panel);
    }

    @Override
    public void prepareQueryPanel(String queryName, TablePanel panel) {
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);
        prepareTable(tableInfo, panel);
    }

    private void prepareTable(PropertiesBundle tableInfo, TablePanel panel) {
        for (String name : tableInfo.getPropertyNames()) {

            String title = tableInfo.getPropertyTitle(name);
            PropertyType columnType = tableInfo.getPropertyType(name);

            panel.addColumn(title);

            switch (columnType) {
                case DOUBLE:
                    panel.setDoubleType(title);
                    break;
                case ID:
                    panel.setIntegerType(title);
                    panel.setColumnEditable(title, false);
                    break;
                case REFERENCE:
                case INTEGER:
                    panel.setIntegerType(title);
                    break;
                case BOOLEAN:
                case DATE:
                case STRING:
                case TIME:
                case STR_ARRAY:
            }
        }
    }

    @Override
    public boolean fillTablePanel(String tableTitle, TablePanel panel) {
        String tableName = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        PropertiesBundle tableInfo = databaseModel.getTable(tableName);
        List<String> columnNames = tableInfo.getPropertyNames();

        try {
            ResultSet resultSet = dao.getTableData(tableName, columnNames);
            fillTable(resultSet, panel, tableInfo);
            return true;
        } catch (SQLException exception) {
            showMessage("Cannot get data from table " + tableName, exception.getMessage());
            return false;
        }
    }

    private void fillTable(ResultSet resultSet, TablePanel tableData, PropertiesBundle tableInfo) throws SQLException {
        List<String> columnNames = tableInfo.getPropertyNames();

        while (resultSet.next()) {
            List<Object> values = new ArrayList<>();

            for (String name : columnNames) {
                PropertyType columnType = tableInfo.getPropertyType(name);
                values.add(prepareDataToShow(resultSet, name, columnType));
            }

            tableData.addRow(values);
        }
    }

    @Override
    public boolean fillQueryPanel(String queryName, Map<String, Object> queryVariables, TablePanel panel) {
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);
        PropertiesBundle queryInfo = queriesModel.getQuery(queryName);

        Map<String, String> stringQueryVariables = new HashMap<>();

        List<String> usedVariables = new ArrayList<>();

        try {
            for (Map.Entry<String, Object> entry : queryVariables.entrySet()) {
                String propertyName = titleConverter.getName(queryName, entry.getKey());
                usedVariables.add(propertyName);
                PropertyType propertyType = queryInfo.getPropertyType(propertyName);
                stringQueryVariables.put(propertyName, prepareDataToSend(entry.getValue(), propertyType));
            }
        } catch (ParseException e) {
            showMessage("Use specified date format " + DATE_FORMAT + " and time format " + DATE_TIME_FORMAT_JAVA, e.getMessage());
            return false;
        }

        try {
            List<String> unusedVariables = queriesModel.getQuery(queryName).getPropertyNames();
            unusedVariables.removeAll(usedVariables);

            ResultSet resultSet = dao.getQueryResult(queryName, stringQueryVariables, unusedVariables);
            fillTable(resultSet, panel, tableInfo);
            return true;
        } catch (SQLException exception) {
            showMessage("Cannot execute " + queryName, exception.getMessage());
            return false;
        }
    }

    @Override
    public void refreshTablePanel(String tableTitle, TablePanel panel) {
        String tableName = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        panel.clearTable();
        fillTablePanel(tableName, panel);
    }

    @Override
    public void prepareQueryFilterPanel(String queryName, EntryFormPanel panel) {
        PropertiesBundle queryInfo = queriesModel.getQuery(queryName);
        prepareForm(queryName, queryInfo, panel);
    }

    @Override
    public void prepareServiceForm(String serviceName, EntryFormPanel panel) {
        PropertiesBundle serviceInfo = serviceModel.getServiceBundle(serviceName);
        prepareForm(serviceName, serviceInfo, panel);
    }

    @Override
    public void prepareEntryForm(String tableTitle, EntryFormPanel panel) {
        String name = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        PropertiesBundle tableInfo = databaseModel.getTable(name);
        prepareForm(name, tableInfo, panel);
    }

    private void prepareForm(String metaName, PropertiesBundle info, EntryFormPanel panel) {
        List<String> columnNames = info.getPropertyNames();

        for (String columnName : columnNames) {
            String columnTitle = titleConverter.getTitle(metaName, columnName);
            PropertyType columnType = info.getPropertyType(columnName);

            switch (columnType) {
                case BOOLEAN:
                    panel.addBooleanField(columnTitle);
                    break;
                case ID:
                case INTEGER:
                case REFERENCE:
                    panel.addNumericField(columnTitle, false);
                    break;
                case DOUBLE:
                    panel.addNumericField(columnTitle, true);
                    break;
                case STRING:
                case DATE:
                case TIME:
                case STR_ARRAY:
                    panel.addTextField(columnTitle);
                    break;
            }
        }
    }

    @Override
    public String getIDColumnTitle(String tableTitle) {
        String tableName = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        return titleConverter.getTitle(tableName, getIDColumnName(tableName));
    }

    public String getIDColumnName(String tableName) {
        PropertiesBundle tableInfo = databaseModel.getTable(tableName);
        return tableInfo.getIDPropertyName();
    }

    @Override
    public void updateCellValue(String tableTitle, String columnTitle, int idValue, Object newValue) {
        String tableName = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        String columnName = titleConverter.getName(tableName, columnTitle);
        try {
            String idColumnName = titleConverter.getName(tableName, getIDColumnTitle(tableName));
            PropertiesBundle tableInfo = databaseModel.getTable(tableName);
            PropertyType columnType = tableInfo.getPropertyType(columnName);
            String preparedNewValue = prepareDataToSend(newValue, columnType);

            dao.updateCellValue(tableName, columnName, idColumnName, idValue, preparedNewValue);

        } catch (SQLException exception) {
            showMessage("Cannot update value in table " + tableName, exception.getMessage());
        } catch (ParseException exception) {
            showMessage("Date doesnt match format: " + DATE_FORMAT, exception.getMessage());
        }
    }

    @Override
    public boolean insertRow(String tableTitle, Map<String, Object> values) {
        String tableName = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        try {
            insertRowWithoutCommit(tableName, values);
            dao.commit();
            return true;
        } catch (SQLException exception) {
            showMessage("Cannot insert new row in table " + tableName, exception.getMessage());
            dao.rollback();
        } catch (ParseException exception) {
            showMessage("Date doesnt match format: " + DATE_FORMAT, exception.getMessage());
            dao.rollback();
        }

        return false;
    }

    private void insertRowWithoutCommit(String tableName, Map<String, Object> values) throws SQLException, ParseException {
        PropertiesBundle tableInfo = databaseModel.getTable(tableName);
        Map<String, String> preparedValues = new HashMap<>();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            Object value = entry.getValue();
            String columnName = titleConverter.getName(tableName, entry.getKey());

            PropertyType columnType = tableInfo.getPropertyType(columnName);
            String preparedValue = prepareDataToSend(value, columnType);

            preparedValues.put(columnName, preparedValue);
        }
        dao.insertRowWithoutCommit(tableName, preparedValues);
    }

    @Override
    public void removeRow(String tableTitle, String idColumnTitle, int idValue) {
        String tableName = titleConverter.getName(TABLE_NAMESPACE, tableTitle);
        String idColumnName = titleConverter.getName(tableName, idColumnTitle);
        try {
            dao.deleteRow(tableName, idColumnName, idValue);
        } catch (SQLException exception) {
            showMessage("Cannot delete row in table " + tableName, exception.getMessage());
        }
    }

    @Override
    public void createTables() {
        List<String> tables = databaseModel.getAvailableTables();
        List<String> supportElements = supportElementsModel.getSupportElementsNames();
        try {
            dao.createTables(tables);
            dao.createSupportElements(supportElements);
        } catch (SQLException exception) {
            showMessage("Cannot create tables on server", exception.getMessage());
        }
    }

    @Override
    public void fillTables() {
        List<String> tables = databaseModel.getAvailableTables();
        try {
            dao.fillTables(tables);
        } catch (SQLException exception) {
            showMessage("Cannot fill tables on server", exception.getMessage());
        }
    }

    @Override
    public void deleteTables() {
        List<String> tables = databaseModel.getAvailableTables();
        try {
            dao.deleteTables(tables);
        } catch (SQLException exception) {
            showMessage("Cannot delete tables on server", exception.getMessage());
        }

    }

    @Override
    public void executeServiceInsert(String serviceName, Map<String, Object> values) {
        ServiceInfo serviceInfo = serviceModel.getServiceInfo(serviceName);

        List<String> independentTables = serviceInfo.getIndependentTables();
        try {
            for (String tableName : independentTables) {
                startInsertChain(serviceName, tableName, values, serviceInfo);
            }
            dao.commit();
        } catch (SQLException exception) {
            showMessage("Cannot execute service inserts", exception.getMessage());
            dao.rollback();
        } catch (ParseException exception) {
            showMessage("Date doesnt match format: " + DATE_FORMAT, exception.getMessage());
            dao.rollback();
        }
    }

    @Override
    public ServiceType getServiceType(String serviceName) {
        return serviceModel.getServiceInfo(serviceName).getServiceType();
    }

    @Override
    public boolean executeServiceDelete(String serviceName) {
        ServiceInfo serviceInfo = serviceModel.getServiceInfo(serviceName);

        List<List<String>> deleteTablesChains = new ArrayList<>();

        List<String> independentTables = serviceInfo.getIndependentTables();
        for (String tableName : independentTables) {
            List<String> chain = new ArrayList<>();
            deleteTablesChains.add(chain);

            fillDeleteTablesChain(serviceInfo, tableName, chain);
            System.out.println(chain);
        }

        return true;
    }

    @Override
    public void createUser(String username, String userPassword, String role) {
        try {
            dao.createUser(username, userPassword);
            dao.setRole(username, getRoleName(role));
        } catch (SQLException exception) {
            showMessage("Cannot create user", exception.getMessage());
        }
    }

    private String getRoleName(String role) {
        switch (role) {
            case "Администратор":
                return "ctn_administrator";
            case "Установщик телефонов":
                return "installer";
            case "Бухгалтер":
                return "accountant";
            case "Клиент-менеджер":
                return "client_manager";
            case "Следящий за АТС":
                return "ctn_manager";
            default:
                throw new IllegalArgumentException();
        }
    }

    private void fillDeleteTablesChain(ServiceInfo serviceInfo, String tableName, List<String> chain) {
        List<String> dependentTables = serviceInfo.getDependentTables(tableName);
        for (String dependentTableName : dependentTables) {
            chain.add(dependentTableName);
            fillDeleteTablesChain(serviceInfo, tableName, chain);
        }
    }

    private void startInsertChain(String serviceName, String tableName, Map<String, Object> values, ServiceInfo serviceInfo) throws SQLException, ParseException {
        List<String> fields = serviceInfo.getTableFields(tableName);
        Map<String, Object> fieldsValues = values.entrySet().stream()
                .filter(entry -> fields.contains(titleConverter.getName(serviceName, entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        insertRowWithoutCommit(tableName, fieldsValues);

        List<String> dependentTables = serviceInfo.getDependentTables(tableName);
        if (dependentTables != null && !dependentTables.isEmpty()) {
            String idColumnName = getIDColumnName(tableName);
            int lastInsertedID = dao.getLastInsertedID(tableName, idColumnName);

            values.put(titleConverter.getTitle(tableName, idColumnName), (double) lastInsertedID);

            for (String dependentTable : dependentTables) {
                startInsertChain(serviceName, dependentTable, values, serviceInfo);
            }
        }
    }

    private String prepareDataToSend(Object data, PropertyType type) throws ParseException {
        switch (type) {
            case STRING:
                return "'" + data + "'";
            case BOOLEAN:
                boolean isTrue = (Boolean) data;
                return isTrue ? "1" : "0";
            case DATE:
                DateFormat format = new SimpleDateFormat(DATE_FORMAT);
                Date date = format.parse(data.toString());
                return "TO_DATE('" + format.format(date) + "', '" + DATE_FORMAT + "')";
            case TIME:
                DateFormat timeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_JAVA);
                Date dateTime = timeFormat.parse(data.toString());
                return "TO_DATE('" + timeFormat.format(dateTime) + "', '" + DATE_TIME_FORMAT_SQL + "')";
            case ID:
            case INTEGER:
            case REFERENCE:
                Double doubleValue = (Double) data;
                return Integer.toString(doubleValue.intValue());
            case DOUBLE:
                return data.toString();
            case STR_ARRAY:
                return processStringArr((String) data);
            default:
                throw new IllegalArgumentException("Not expected case");
        }
    }

    private String processStringArr(String data) {
        StringBuilder result = new StringBuilder();
        String[] valuesArr = data.split(",");
        for (String val : valuesArr) {
            val = val.replace(" ", "");
            if (result.toString().equals("")) {
                result.append("'").append(val).append("'");
                continue;
            }
            result.append(", '").append(val).append("'");
        }
        return result.toString();
    }

    private Object prepareDataToShow(ResultSet resultSet, String columnName, PropertyType type) throws SQLException {

        Object result = null;
        switch (type) {
            case BOOLEAN:
                int value = resultSet.getInt(columnName);
                result = value > 0;
                break;
            case ID:
            case REFERENCE:
                result = resultSet.getInt(columnName);
                break;
            case DOUBLE:
                result = resultSet.getDouble(columnName);
                break;
            case DATE:
                DateFormat format = new SimpleDateFormat(DATE_FORMAT);
                Date date = resultSet.getDate(columnName);
                if (date != null) {
                    result = format.format(date);
                }
                break;
            case TIME:
                DateFormat timeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_JAVA);
                Date dateTime = resultSet.getDate(columnName);
                if (dateTime != null) {
                    result = timeFormat.format(dateTime);
                }
                break;
            case STRING:
            case INTEGER:
            case STR_ARRAY:
                result = resultSet.getString(columnName);
                break;
        }

        if (result == null) {
            return "-";
        }
        return result;
    }

    public void addErrorHandler(ErrorHandler handler) {
        errorHandlers.add(handler);
    }

    public void showMessage(String title, String message) {

        for (ErrorHandler handler : errorHandlers) {
            handler.showMsg(title + "\n" + message);
        }
    }

    public void setClientRole(String role) {
        clientRole = role;
    }
}
