package model.queries;

import model.Role;
import model.properties.PropertyType;
import model.properties.PropertiesBundle;

public class QueriesCTN {
    private final QueriesModel queriesModel = new QueriesModel();

    public QueriesCTN() {
        createQuery1();
        createQuery2();
        createQuery3();
        createQuery4_1();
        createQuery4_2();
        createQuery5();
        createQuery6();
        createQuery7();
        createQuery8();
        createQuery9();
        createQuery10();
        createQuery11();
        createQuery12();
        createQuery13_1();
        createQuery13_2();
    }

    private void createQuery1(){
        String queryName = "1. Перечень абонентов";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("CALLER_ID", "ID абонента", PropertyType.INTEGER);
        tableInfo.addProperty("SECOND_NAME", "Фамилия", PropertyType.STRING);
        tableInfo.addProperty("FIRST_NAME", "Имя", PropertyType.STRING);
        tableInfo.addProperty("MIDDLE_NAME", "Отчество", PropertyType.STRING);
        tableInfo.addProperty("IS_DEADHEAD", "Льготник", PropertyType.BOOLEAN);
        tableInfo.addProperty("AGE", "Возраст", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("is_deadhead", "Льготник", PropertyType.BOOLEAN);
        queryInfo.addProperty("lower_age", "Минимальный возраст", PropertyType.INTEGER);
        queryInfo.addProperty("upper_age", "Максимальный возраст", PropertyType.INTEGER);
        queryInfo.addProperty("second_name", "Группа фамилий", PropertyType.STR_ARRAY);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.ACCOUNTANT.toString(), queryName);
        queriesModel.grantAccess(Role.INSTALLER.toString(), queryName);
        queriesModel.grantAccess(Role.CTN_MANAGER.toString(), queryName);
        queriesModel.grantAccess(Role.CLIENT_MANAGER.toString(), queryName);
    }

    private void createQuery2() {
        String queryName = "2. Перечень свободных телефонных номеров";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("PHONE_NUMBER_ID", "ID номера", PropertyType.INTEGER);
        tableInfo.addProperty("PHONE_NUMBER", "Номер телефона", PropertyType.INTEGER);
        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.STRING);
        tableInfo.addProperty("ADDRESS_ID", "ID адреса", PropertyType.STRING);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("addr_id", "ID адреса", PropertyType.INTEGER);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.INSTALLER.toString(), queryName);

    }

    private void createQuery3(){
        String queryName = "3. Перечень должников";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("CALLER_ID", "ID Абонента", PropertyType.INTEGER);
        tableInfo.addProperty("REGION", "Район", PropertyType.STRING);
        tableInfo.addProperty("IS_BLOCKED", "Заблокирован", PropertyType.BOOLEAN);
        tableInfo.addProperty("SUBSCRIPTION_DEBT", "Долго по абонентской", PropertyType.DOUBLE);
        tableInfo.addProperty("LD_CALLS_DEBT", "Долг по межгороду", PropertyType.DOUBLE);
        tableInfo.addProperty("SUBSCRIPTION_DEBT_AGE", "Длительность долга по абоненской", PropertyType.INTEGER);
        tableInfo.addProperty("LONG_DISTANCE_DEBT_AGE", "Длительность долга по смежгороду", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("region", "Район", PropertyType.STRING);
        queryInfo.addProperty("subscription_debt_age_min_age", "Мин. длительность долга по абоненской", PropertyType.INTEGER);
        queryInfo.addProperty("long_distance_debt_min_age", "Мин. длительность долга по межгороду", PropertyType.INTEGER);
        queryInfo.addProperty("min_long_distance_debt", "Мин. сумма долга по межгороду", PropertyType.INTEGER);
        queryInfo.addProperty("min_subscription_debt", "Мин. сумма долга по абоненской", PropertyType.INTEGER);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.ACCOUNTANT.toString(), queryName);
    }

    private void createQuery4_1(){
        String queryName = "4.1 АТС с наибольшим числом должников";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("EXCHANGE_TYPE", "Тип АТС", PropertyType.STRING);
        tableInfo.addProperty("DEBTOR_COUNT", "Количество должников", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_type", "Тип АТС", PropertyType.STRING);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.ACCOUNTANT.toString(), queryName);
    }

    private void createQuery4_2(){
        String queryName = "4.2 АТС с наибольшей суммой задолжности";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("EXCHANGE_TYPE", "Тип АТС", PropertyType.STRING);
        tableInfo.addProperty("DEBT", "Сумма задолжностей", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_type", "Тип АТС", PropertyType.STRING);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.ACCOUNTANT.toString(), queryName);
    }

    private void createQuery5(){
        String queryName = "5. Перечень общественных телефонов";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("PUBLIC_PHONE_ID", "ID телефона", PropertyType.INTEGER);
        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("CITY", "Город", PropertyType.STRING);
        tableInfo.addProperty("REGION", "Район", PropertyType.STRING);
        tableInfo.addProperty("STREET", "Улица", PropertyType.STRING);
        tableInfo.addProperty("BUILDING_NUMBER", "Номер дома", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("region", "Район", PropertyType.STRING);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.INSTALLER.toString(), queryName);
    }

    private void createQuery6(){
        String queryName = "6. Процентное соотношение льготников";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("DEADHEAD_PERCENTAGE", "Процент льгоников", PropertyType.INTEGER);
        tableInfo.addProperty("NOT_DEADHEAD_PERCENTAGE", "Процент не льготников", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("region", "Район", PropertyType.STRING);
        queryInfo.addProperty("exchange_type", "Тип АТС", PropertyType.STRING);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.CLIENT_MANAGER.toString(), queryName);
    }

    private void createQuery7(){
        String queryName = "7. Перечень абонентов, имеющих параллельные телефоны";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("CALLER_ID", "ID Абонента", PropertyType.INTEGER);
        tableInfo.addProperty("SECOND_NAME", "Фаимилия", PropertyType.STRING);
        tableInfo.addProperty("FIRST_NAME", "Имя", PropertyType.STRING);
        tableInfo.addProperty("MIDDLE_NAME", "Отчество", PropertyType.STRING);
        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("EXCHANGE_TYPE", "Тип АТС", PropertyType.STRING);
        tableInfo.addProperty("IS_DEADHEAD", "Льготник", PropertyType.BOOLEAN);
        tableInfo.addProperty("REGION", "Район", PropertyType.STRING);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("is_deadhead", "Льготник", PropertyType.BOOLEAN);
        queryInfo.addProperty("region", "Район", PropertyType.STRING);
        queryInfo.addProperty("exchange_type", "Тип АТС", PropertyType.STRING);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.INSTALLER.toString(), queryName);
    }

    private void createQuery8(){
        String queryName = "8. Перечень телефонов";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("PHONE_ID", "ID телефона", PropertyType.INTEGER);
        tableInfo.addProperty("ADDRESS_ID", "ID адреса", PropertyType.INTEGER);
        tableInfo.addProperty("CITY", "Город", PropertyType.STRING);
        tableInfo.addProperty("REGION", "Район", PropertyType.STRING);
        tableInfo.addProperty("STREET", "Улица", PropertyType.STRING);
        tableInfo.addProperty("BUILDING_NUMBER", "Номер дома", PropertyType.INTEGER);
        tableInfo.addProperty("IS_BLOCKED", "Заблокирован", PropertyType.BOOLEAN);

        queryInfo.addProperty("address_id", "ID адреса", PropertyType.STRING);
        queryInfo.addProperty("street", "Улица", PropertyType.STRING);
        queryInfo.addProperty("is_blocked", "Заблокирован", PropertyType.BOOLEAN);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.INSTALLER.toString(), queryName);
    }

    private void createQuery9(){
        String queryName = "9. Город, с наибольшим количеством междугородных переговоров";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("CITY", "Город", PropertyType.STRING);
        tableInfo.addProperty("CALLS_COUNT", "Количетсво звонков", PropertyType.INTEGER);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.CTN_MANAGER.toString(), queryName);
    }

    private void createQuery10(){
        String queryName = "10. Информация об абонентах";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("PHONE_NUMBER_ID", "ID номера телефона", PropertyType.STRING);
        tableInfo.addProperty("TYPE_NAME", "Тип телефона", PropertyType.STRING);
        tableInfo.addProperty("SECOND_NAME", "Фамилия", PropertyType.STRING);
        tableInfo.addProperty("FIRST_NAME", "Имя", PropertyType.STRING);
        tableInfo.addProperty("MIDDLE_NAME", "Отчество", PropertyType.STRING);
        tableInfo.addProperty("AGE", "Возраст", PropertyType.INTEGER);

        queryInfo.addProperty("phone_number_id", "ID номера теелфона", PropertyType.ID);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.INSTALLER.toString(), queryName);
        queriesModel.grantAccess(Role.CLIENT_MANAGER.toString(), queryName);
    }

    private void createQuery11(){
        String queryName = "11. Cпаренные телефоны с возможностью их замены на обычные";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("PHONE_NUMBER_ID", "ID номера", PropertyType.INTEGER);
        tableInfo.addProperty("PHONE_NUMBER", "Номер телефона", PropertyType.STRING);
        tableInfo.addProperty("CALLER_ID", "ID абонента", PropertyType.INTEGER);
        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("ADDRESS_ID", "ID адреса", PropertyType.INTEGER);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.CLIENT_MANAGER.toString(), queryName);
    }

    private void createQuery12(){
        String queryName = "12. Получить перечень мало активных телефонов";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("SOURCE_PHONE_ID", "ID телефона", PropertyType.INTEGER);
        tableInfo.addProperty("PHONE_NUMBER", "Номер телефона", PropertyType.STRING);
        tableInfo.addProperty("CALLS_COUNT", "Кол-во звонков", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("start_date", "Начиная с даты", PropertyType.DATE);
        queryInfo.addProperty("end_date", "Заканчивая датой", PropertyType.DATE);
        queryInfo.addProperty("max_calls_count", "Максимальное кол-во звонков", PropertyType.INTEGER);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.CTN_MANAGER.toString(), queryName);
    }

    private void createQuery13_1(){
        String queryName = "13.1 Перечень должников для отключения";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("CALLER_ID", "ID Абонента", PropertyType.INTEGER);
        tableInfo.addProperty("SECOND_NAME", "Фамилия", PropertyType.STRING);
        tableInfo.addProperty("FIRST_NAME", "Имя", PropertyType.STRING);
        tableInfo.addProperty("MIDDLE_NAME", "Отчества", PropertyType.STRING);
        tableInfo.addProperty("SUBSCRIPTION_DEBT_AGE", "Длительность долга по абонентской", PropertyType.INTEGER);
        tableInfo.addProperty("LONG_DISTANCE_DEBT_AGE", "Длительность долга по межгороду", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("region", "Район", PropertyType.STRING);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.ACCOUNTANT.toString(), queryName);
    }

    private void createQuery13_2(){
        String queryName = "13.2 Перечень должников для уведомления об отключении";

        PropertiesBundle queryInfo = queriesModel.addQuery(queryName);
        PropertiesBundle tableInfo = queriesModel.getTable(queryName);

        tableInfo.addProperty("TELEPHONE_EXCHANGE_ID", "ID АТС", PropertyType.INTEGER);
        tableInfo.addProperty("CALLER_ID", "ID Абонента", PropertyType.INTEGER);
        tableInfo.addProperty("SECOND_NAME", "Фамилия", PropertyType.STRING);
        tableInfo.addProperty("FIRST_NAME", "Имя", PropertyType.STRING);
        tableInfo.addProperty("MIDDLE_NAME", "Отчества", PropertyType.STRING);
        tableInfo.addProperty("SUBSCRIPTION_DEBT_AGE", "Длительность долга по абонентской", PropertyType.INTEGER);
        tableInfo.addProperty("LONG_DISTANCE_DEBT_AGE", "Длительность долга по межгороду", PropertyType.INTEGER);

        queryInfo.addProperty("exchange_id", "ID АТС", PropertyType.INTEGER);
        queryInfo.addProperty("region", "Район", PropertyType.STRING);

        queriesModel.grantAccess(Role.ADMINISTRATOR.toString(), queryName);
        queriesModel.grantAccess(Role.ACCOUNTANT.toString(), queryName);
    }

    public QueriesModel getModel(){
        return queriesModel;
    }
}
