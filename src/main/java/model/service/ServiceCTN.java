package model.service;

import model.Role;
import model.properties.PropertiesBundle;
import model.properties.PropertyType;

import java.util.Arrays;
import java.util.Collections;

public class ServiceCTN {
    private final ServiceModel serviceModel = new ServiceModel();
    public ServiceCTN() {
        addCaller();
        addCityExchange();
        addInstitutionalExchange();
        addDepartmentalExchange();
        addInstallingPossibility();
    }

    private void addCaller(){
        String serviceName = "Добавить абонента";
        PropertiesBundle serviceBundle = serviceModel.addService(serviceName);
        ServiceInfo serviceInfo = serviceModel.getServiceInfo(serviceName);
        serviceInfo.setServiceType(ServiceType.INSERT);

        serviceInfo.addTableFields("Clients",
                Arrays.asList("Second_name", "First_name", "Middle_name", "Gender", "Birth_date", "Is_deadhead"));

        serviceBundle.addProperty("Second_name", "Фамилия", PropertyType.STRING);
        serviceBundle.addProperty("First_name", "Имя", PropertyType.STRING);
        serviceBundle.addProperty("Middle_name", "Отчество", PropertyType.STRING);
        serviceBundle.addProperty("Gender", "Пол", PropertyType.STRING);
        serviceBundle.addProperty("Birth_date", "Дата рождения", PropertyType.DATE);
        serviceBundle.addProperty("Is_deadhead", "Льготник", PropertyType.BOOLEAN);

        serviceInfo.addTableFields("Callers",
                Arrays.asList("Telephone_exchange_ID", "Client_ID", "Has_long_distance_calls"));

        serviceBundle.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.REFERENCE);
        serviceBundle.addProperty("Has_long_distance_calls", "Имеет выход на межгород", PropertyType.BOOLEAN);

        serviceInfo.addTableFields("Balances",
                Collections.singletonList("Caller_ID"));

        serviceBundle.addTitle("Caller_ID", "ID абонента");
        serviceBundle.addTitle("Client_ID", "ID клиента");

        serviceInfo.addTableDependency("Clients", "Callers");
        serviceInfo.addTableDependency("Callers", "Balances");

        serviceModel.grantAccess(Role.ADMINISTRATOR.toString(), serviceName);
        serviceModel.grantAccess(Role.CLIENT_MANAGER.toString(), serviceName);
    }

    private void addInstitutionalExchange(){
        String serviceName = "Создать учрежденческую АТС";
        PropertiesBundle serviceBundle = serviceModel.addService(serviceName);
        ServiceInfo serviceInfo = serviceModel.getServiceInfo(serviceName);
        serviceInfo.setServiceType(ServiceType.INSERT);

        serviceInfo.addTableFields("Telephone_exchanges", Collections.singletonList("Telephone_exchange_ID"));
        serviceBundle.addTitle("Telephone_exchange_ID", "ID АТС");

        serviceInfo.addTableFields("Institutional_exchanges",
                Arrays.asList("Telephone_exchange_ID", "Institution_name"));

        serviceBundle.addProperty("Institution_name", "Название учреждения", PropertyType.STRING);

        serviceInfo.addTableDependency("Telephone_exchanges", "Institutional_exchanges");

        serviceModel.grantAccess(Role.ADMINISTRATOR.toString(), serviceName);
        serviceModel.grantAccess(Role.CTN_MANAGER.toString(), serviceName);
    }

    private void addDepartmentalExchange(){
        String serviceName = "Создать ведомственную АТС";
        PropertiesBundle serviceBundle = serviceModel.addService(serviceName);
        ServiceInfo serviceInfo = serviceModel.getServiceInfo(serviceName);
        serviceInfo.setServiceType(ServiceType.INSERT);

        serviceInfo.addTableFields("Telephone_exchanges", Collections.singletonList("Telephone_exchange_ID"));
        serviceBundle.addTitle("Telephone_exchange_ID", "ID АТС");

        serviceInfo.addTableFields("Departmental_exchanges",
                Arrays.asList("Telephone_exchange_ID", "Department_name"));

        serviceBundle.addProperty("Department_name", "Название ведомства", PropertyType.STRING);

        serviceInfo.addTableDependency("Telephone_exchanges", "Departmental_exchanges");

        serviceModel.grantAccess(Role.ADMINISTRATOR.toString(), serviceName);
        serviceModel.grantAccess(Role.CTN_MANAGER.toString(), serviceName);

    }

    private void addCityExchange(){
        String serviceName = "Создать городскую АТС";
        PropertiesBundle serviceBundle = serviceModel.addService(serviceName);
        ServiceInfo serviceInfo = serviceModel.getServiceInfo(serviceName);
        serviceInfo.setServiceType(ServiceType.INSERT);

        serviceInfo.addTableFields("Telephone_exchanges", Collections.singletonList("Telephone_exchange_ID"));
        serviceBundle.addTitle("Telephone_exchange_ID", "ID АТС");

        serviceInfo.addTableFields("City_exchanges",
                Arrays.asList("Telephone_exchange_ID", "City_name"));

        serviceBundle.addProperty("City_name", "Название города", PropertyType.STRING);

        serviceInfo.addTableDependency("Telephone_exchanges", "City_exchanges");

        serviceModel.grantAccess(Role.ADMINISTRATOR.toString(), serviceName);
        serviceModel.grantAccess(Role.CTN_MANAGER.toString(), serviceName);
    }

    private void addInstallingPossibility(){
        String serviceName = "Подключить дом к АТС";
        PropertiesBundle serviceBundle = serviceModel.addService(serviceName);
        ServiceInfo serviceInfo = serviceModel.getServiceInfo(serviceName);
        serviceInfo.setServiceType(ServiceType.INSERT);

        serviceInfo.addTableFields("Address",
                Arrays.asList("Zip_code", "City", "Region", "Street", "Building_number"));

        serviceBundle.addProperty("Zip_code", "Индекс", PropertyType.INTEGER);
        serviceBundle.addProperty("City", "Город", PropertyType.STRING);
        serviceBundle.addProperty("Region", "Район", PropertyType.STRING);
        serviceBundle.addProperty("Street", "Улица", PropertyType.STRING);
        serviceBundle.addProperty("Building_number", "Номер дома", PropertyType.INTEGER);

        serviceInfo.addTableFields("Installing_possibilities",
                Arrays.asList("Address_ID", "Telephone_exchange_ID"));

        serviceBundle.addProperty("Telephone_exchange_ID", "ID АТС", PropertyType.REFERENCE);
        serviceBundle.addTitle("Address_ID", "ID адреса");

        serviceInfo.addTableDependency("Address", "Installing_possibilities");

        serviceModel.grantAccess(Role.ADMINISTRATOR.toString(), serviceName);
        serviceModel.grantAccess(Role.INSTALLER.toString(), serviceName);
    }

//    private void delete

    public ServiceModel getModel() {
        return serviceModel;
    }
}
