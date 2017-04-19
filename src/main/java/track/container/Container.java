package track.container;

import org.omg.CORBA.Object;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           

public class Container {
    private List<Bean> beans;

    /**
     * Если не получается считать конфиг, то бросьте исключение
     * @throws InvalidConfigurationException - неверный конфиг
     */
    Map<String, String> idNameToClass;
    Map<String, Object> classNameToObject;

    public Container(List<Bean> beans) throws InvalidConfigurationException {
        this.beans = beans;
        boolean flag = init(beans);
        if(flag == false){
            throw new InvalidConfigurationException("initialization failed");
        }
    }

    public Container(String pathToConfig) throws InvalidConfigurationException {
        JsonConfigReader reader = new JsonConfigReader();
        File config = new File(pathToConfig);
        try {
            beans = reader.parseBeans(config);
        }catch (IOException e){
            throw  new InvalidConfigurationException("We can't read config");
        }
        boolean flag = init(beans);
        if(flag == false){
            throw new  InvalidConfigurationException("Initialization failed!");
        }

    }

    public boolean init(List<Bean> beans){
        classNameToObject = new HashMap<>();
        for(Bean bean : beans){
            if (getObjectByClassName(bean.getClassName())==null){
                return false;
            }
            else {
                Object object = getObjectByClassName((bean.getClassName()));
                setProperties(bean, object);
                classNameToObject.put(bean.getClassName(), object);
            }
        }
        return true;
    }

    public void initializeidNameToClass(List<Bean> beans){
        idNameToClass = new HashMap<>();
        for(Bean bean : beans){
            idNameToClass.put(bean.getId(), bean.getClassName());
        }
    }

    public Object getObjectByClassName(String className){
        try{
            Class c = Class.forName(className);
            Class[] paramTypes = new Class[] {};
            Constructor aConstrct = c.getConstructor(paramTypes);
            Object obj = (Object) aConstrct.newInstance();
            return obj;
        }
        catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setProperties(Bean bean, org.omg.CORBA.Object object){
        Collection<Property> properties = bean.getProperties().values();
        for(Property property : properties){
            if(property.getType() == ValueType.REF){
                Object refObject = getObjectByClassName(idNameToClass.get(property.getValue()));
                if(refObject.equals(null)){
                    return false;
                }
                applySetters(object, property.getName(), refObject);
            }
            else {
                try {
                    applySetters(object, property.getName(), Integer.parseInt(property.getName()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean applySetters(Object object, String fieldName, Object setValue){
        try {
            Class c = object.getClass();
            String firstLetter = Character.toString(fieldName.charAt(0));
            String methodName = "set".concat(firstLetter.toUpperCase()).concat(fieldName.substring(1));
            Class[] paramTypes = new Class[] { Object.class };
            Method method = c.getMethod(methodName, paramTypes);
            method.invoke(object,setValue );
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean applySetters(Object object, String fieldName, int setValue){
        try{
            Class c = object.getClass();
            String firstLetter = Character.toString(fieldName.charAt(0));
            String methodName = "set".concat(firstLetter.toUpperCase()).concat(fieldName.substring(1));
            Class[] paramTypes = new Class[] { int.class };
            Method method = c.getMethod(methodName, paramTypes);
            method.invoke(object,setValue );
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }




    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getByName("carBean")
     */
    public Object getById(String id) {
        if (idNameToClass.containsKey(id)) {
            return getByClass(idNameToClass.get(id));
        }
        return null;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        if (classNameToObject.containsKey(className)) {
            return classNameToObject.get(className);
        }
        return null;
    }

}