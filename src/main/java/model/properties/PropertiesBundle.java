package model.properties;

import java.util.*;
import java.util.stream.Collectors;

public class PropertiesBundle {
    private final String bundleName;
    private final HashMap<String, PropertyInfo> properties = new HashMap<>();
    private final List<PropertyInfo> orderedProperties = new ArrayList<>();

    private String idPropertyName;
    private Map<String, String> extraTitles = new HashMap<>();

    public PropertiesBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getBundleName() {
        return bundleName;
    }

    public PropertyInfo addProperty(String name, PropertyType type){
        return createProperty(name, name, type);
    }

    public PropertyInfo addProperty(String name, String title, PropertyType type){
        return createProperty(name, title, type);
    }

    public Map<String, String> getTitles(){
        Map<String, String> propertiesTitles = new HashMap<>();
        for(PropertyInfo prop : properties.values()){
            propertiesTitles.put(prop.getPropertyName(), prop.getPropertyTitle());
        }
        propertiesTitles.putAll(extraTitles);

        return propertiesTitles;
    }

    private PropertyInfo createProperty(String propertyName, String propertyTitle, PropertyType propertyType){
        String preparedName = prepareName(propertyName);

        checkIsIDProperty(preparedName, propertyType);

        PropertyInfo propertyInfo = new PropertyInfo(preparedName, propertyTitle, propertyType);
        properties.put(preparedName, propertyInfo);
        orderedProperties.add(propertyInfo);

        return propertyInfo;
    }

    private void checkIsIDProperty(String preparedName, PropertyType type){
        if(type == PropertyType.ID){
            if(idPropertyName != null){
                throw new IllegalArgumentException("Property with type ID already exists");
            }
            idPropertyName = preparedName;
        }
    }

    public PropertyInfo getProperty(String columnName){
        String name = prepareName(columnName);

        PropertyInfo propertyInfo = properties.get(name);
        if(propertyInfo == null){
            throw new NoSuchElementException("Property with name " + columnName + " does not exist");
        }
        return propertyInfo;
    }

    public List<String> getPropertyNames(){
        return orderedProperties.stream().map(PropertyInfo::getPropertyName).collect(Collectors.toList());
    }

    public PropertyType getPropertyType(String columnName){
        PropertyInfo propertyInfo = getProperty(columnName);
        return propertyInfo.getPropertyType();
    }

    public String getPropertyTitle(String columnName) {
        PropertyInfo propertyInfo = getProperty(columnName);
        return propertyInfo.getPropertyTitle();
    }

    public String getIDPropertyName(){
        return idPropertyName;
    }

    private String prepareName(String name){
        return name.toLowerCase(Locale.US);
    }

    public void addTitle(String propertyName, String propertyTitle){
        extraTitles.put(prepareName(propertyName), propertyTitle);
    }
}
