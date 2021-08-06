package model.database;

import model.Role;
import model.properties.PropertiesBundle;
import model.properties.PropertyType;

public class DatabaseCTN {
    private final DatabaseModel databaseModel = new DatabaseModel();

    public DatabaseCTN() {
        createTelephoneExchanges();
        createCityExchanges();
        createInstitutionalExchanges();
        createDepartmentalExchanges();
        createConnectionPrices();
        createLongDistanceCallPrices();
        createPhoneTypes();
        createSubscriptionFees();
        createPhoneNumbers();
        createClients();
        createCallers();
        createAddress();
        createPhones();
        createBalances();
        createPublicPhones();
        createLongDistanceCalls();
        createConnectionRequests();
        createInstallingPossibilities();
    }

    private void createTelephoneExchanges() {
        String tableName = "Telephone_exchanges";
        PropertiesBundle table = databaseModel.addTable(tableName, "АТС");
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.ID);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.USER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CLIENT_MANAGER.toString(), tableName);
    }

    private void createCityExchanges() {
        String tableName = "City_exchanges";

        PropertiesBundle table = databaseModel.addTable(tableName, "Городские АТС");
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.ID);
        table.addProperty("City_name", "Название города", PropertyType.STRING);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.USER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CLIENT_MANAGER.toString(), tableName);
    }

    private void createInstitutionalExchanges() {
        String tableName = "Institutional_exchanges";

        PropertiesBundle table = databaseModel.addTable(tableName, "Учрежденческие АТС");
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.ID);
        table.addProperty("Institution_name", "Название учреждения", PropertyType.STRING);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CLIENT_MANAGER.toString(), tableName);

    }

    private void createDepartmentalExchanges() {
        String tableName = "Departmental_exchanges";

        PropertiesBundle table = databaseModel.addTable(tableName, "Ведомственные АТС");
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.ID);
        table.addProperty("Department_name", "Название ведомства", PropertyType.STRING);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CLIENT_MANAGER.toString(), tableName);
    }

    private void createConnectionPrices() {
        String tableName = "Connection_prices";

        PropertiesBundle table = databaseModel.addTable(tableName, "Цены на подключение");
        table.addProperty("Connection_price_ID", "ID цены", PropertyType.ID);
        table.addProperty("Price_name", "Название цены", PropertyType.STRING);
        table.addProperty("Price", "Цена", PropertyType.DOUBLE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.ACCOUNTANT.toString(), tableName);
    }

    private void createLongDistanceCallPrices() {
        String tableName = "Long_distance_call_prices";

        PropertiesBundle table = databaseModel.addTable(tableName, "Цены на междугородние звонки");
        table.addProperty("Long_distance_call_price_ID","ID цены", PropertyType.ID);
        table.addProperty("Price_name", "Название цены", PropertyType.STRING);
        table.addProperty("Price_per_minute", "Цена за минуту",PropertyType.DOUBLE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.ACCOUNTANT.toString(), tableName);
    }

    private void createPhoneTypes() {
        String tableName = "Phone_types";

        PropertiesBundle table = databaseModel.addTable(tableName, "Типы телефонов");
        table.addProperty("Phone_type_ID", "ID типа", PropertyType.ID);
        table.addProperty("Type_name", "Название типа", PropertyType.STRING);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CTN_MANAGER.toString(), tableName);
    }

    private void createSubscriptionFees() {
        String tableName = "Subscription_fees";

        PropertiesBundle table = databaseModel.addTable(tableName, "Стоимость абонентской платы");
        table.addProperty("Subscription_fee_ID", "ID стоимости", PropertyType.ID);
        table.addProperty("Phone_number_type_ID", "Тип телефона", PropertyType.REFERENCE);
        table.addProperty("Is_deadhead", "Льготник", PropertyType.BOOLEAN);
        table.addProperty("Has_long_distance_calls","Имеет выход на междгород", PropertyType.BOOLEAN);
        table.addProperty("Subscription_fee", "Стоимость", PropertyType.DOUBLE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.ACCOUNTANT.toString(), tableName);
    }

    private void createPhoneNumbers() {
        String tableName = "Phone_numbers";

        PropertiesBundle table = databaseModel.addTable(tableName, "Номера телефонов");
        table.addProperty("Phone_number_ID", "ID номера", PropertyType.ID);
        table.addProperty("Phone_number", "Номер", PropertyType.STRING);
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.REFERENCE);
        table.addProperty("Is_free", "Свободен", PropertyType.BOOLEAN);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CTN_MANAGER.toString(), tableName);
    }

    private void createClients() {
        String tableName = "Clients";

        PropertiesBundle table = databaseModel.addTable(tableName, "Клиенты");
        table.addProperty("Client_ID", "ID клиента", PropertyType.ID);
        table.addProperty("Second_name", "Фамилия", PropertyType.STRING);
        table.addProperty("First_name", "Имя", PropertyType.STRING);
        table.addProperty("Middle_name", "Отчество", PropertyType.STRING);
        table.addProperty("Gender", "Пол", PropertyType.STRING);
        table.addProperty("Birth_date", "Дата рождения", PropertyType.DATE);
        table.addProperty("Is_deadhead", "Льготник", PropertyType.BOOLEAN);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.CLIENT_MANAGER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
    }


    private void createCallers() {
        String tableName = "Callers";

        PropertiesBundle table = databaseModel.addTable(tableName, "Абоненты");
        table.addProperty("Caller_ID", "ID абонента", PropertyType.ID);
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.REFERENCE);
        table.addProperty("Client_ID", "ID клиента", PropertyType.REFERENCE);
        table.addProperty("Is_blocked", "Заблокирован", PropertyType.BOOLEAN);
        table.addProperty("Has_long_distance_calls", "Имеет выход на межгород", PropertyType.BOOLEAN);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CLIENT_MANAGER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);
    }

    private void createAddress() {
        String tableName = "Address";

        PropertiesBundle table = databaseModel.addTable(tableName, "Адреса");
        table.addProperty("Address_ID", "ID адреса", PropertyType.ID);
        table.addProperty("Zip_code", "Индекс", PropertyType.INTEGER);
        table.addProperty("City", "Город", PropertyType.STRING);
        table.addProperty("Region", "Район", PropertyType.STRING);
        table.addProperty("Street", "Улица", PropertyType.STRING);
        table.addProperty("Building_number", "Номер дома", PropertyType.INTEGER);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.CLIENT_MANAGER.toString(), tableName);
    }

    private void createPhones() {
        String tableName = "Phones";

        PropertiesBundle table = databaseModel.addTable(tableName, "Установленные телефоны");
        table.addProperty("Phone_ID", "ID телефона", PropertyType.ID);
        table.addProperty("Phone_number_ID", "ID номера", PropertyType.REFERENCE);
        table.addProperty("Phone_type_ID", "ID типа телефона", PropertyType.REFERENCE);
        table.addProperty("Caller_ID", "ID абонента", PropertyType.REFERENCE);
        table.addProperty("Address_ID", "ID адреса", PropertyType.REFERENCE);
        table.addProperty("Apartment_number", "Номер квартиры", PropertyType.INTEGER);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);
    }

    private void createBalances() {
        String tableName = "Balances";

        PropertiesBundle table = databaseModel.addTable(tableName, "Балансы абонентов");
        table.addProperty("Caller_ID","ID абонента", PropertyType.ID);
        table.addProperty("Long_distance_calls_debt", "Долг по межгороду", PropertyType.DOUBLE);
        table.addProperty("Subscription_debt", "Долго по абонентской плате", PropertyType.DOUBLE);
        table.addProperty("Penalty_interest", "Пеня", PropertyType.DOUBLE);
        table.addProperty("Long_dist_debt_date", "Дата долга по межгороду", PropertyType.DATE);
        table.addProperty("Subscription_debt_date", "Дата долга по абонентской", PropertyType.DATE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.ACCOUNTANT.toString(), tableName);
    }

    private void createPublicPhones() {
        String tableName = "Public_phones";

        PropertiesBundle table = databaseModel.addTable(tableName, "Общественные телефоны");
        table.addProperty("Public_phone_ID", "ID телефона", PropertyType.ID);
        table.addProperty("Address_ID", "ID адреса", PropertyType.REFERENCE);
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.REFERENCE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.INSTALLER.toString(), tableName);
    }

    private void createLongDistanceCalls() {
        String tableName = "Long_distance_calls";

        PropertiesBundle table = databaseModel.addTable(tableName, "Междугородние звонки");
        table.addProperty("Long_distance_call_ID", "ID Звонка", PropertyType.ID);
        table.addProperty("Source_phone_ID", "ID исходящего телефона", PropertyType.REFERENCE);
        table.addProperty("Destination_phone_ID", "ID входящего телефона", PropertyType.REFERENCE);
        table.addProperty("Start_date", "Дата начала", PropertyType.TIME);
        table.addProperty("End_date", "Дата оконачания", PropertyType.TIME);
        table.addProperty("Call_price", "Стоимость звонка", PropertyType.DOUBLE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.ACCOUNTANT.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CTN_MANAGER.toString(), tableName);
    }

    private void createConnectionRequests() {
        String tableName = "Connection_requests";

        PropertiesBundle table = databaseModel.addTable(tableName, "Заявки на подключение");
        table.addProperty("Connection_request_ID", "ID заявки", PropertyType.ID);
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.REFERENCE);
        table.addProperty("Address_ID", "ID адреса", PropertyType.REFERENCE);
        table.addProperty("Apartment_number", "Номер квартиры", PropertyType.INTEGER);
        table.addProperty("Client_ID", "ID клиента", PropertyType.REFERENCE);
        table.addProperty("Request_date", "Дата создания", PropertyType.DATE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantReadOnlyAccess(Role.INSTALLER.toString(), tableName);
        databaseModel.grantWriteAccess(Role.CLIENT_MANAGER.toString(), tableName);
    }

    private void createInstallingPossibilities() {
        String tableName = "Installing_possibilities";

        PropertiesBundle table = databaseModel.addTable(tableName, "Возможность установки");
        table.addProperty("Installing_possibility_ID", "ID возможности", PropertyType.ID);
        table.addProperty("Address_ID", "ID адреса", PropertyType.REFERENCE);
        table.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.REFERENCE);

        databaseModel.grantWriteAccess(Role.ADMINISTRATOR.toString(), tableName);
        databaseModel.grantReadOnlyAccess(Role.CTN_MANAGER.toString(), tableName);

        databaseModel.grantWriteAccess(Role.INSTALLER.toString(), tableName);
    }

    public DatabaseModel getModel(){
        return databaseModel;
    }


}
