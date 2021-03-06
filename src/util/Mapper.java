package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * https://stackoverflow.com/questions/13582985/check-if-a-type-is-an-interface
 * https://stackoverflow.com/questions/6148798/get-generic-type-for-java-util-map-parameter
 * https://stackoverflow.com/questions/10945993/using-java-reflections-to-find-collections-element-type
 * https://stackoverflow.com/questions/1942644/get-generic-type-of-java-util-list
 * https://stackoverflow.com/questions/7495785/java-how-to-instantiate-a-class-from-string
 * https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Method.html
 */
public class Mapper {

  /**
   * This method is class to map a data class to a domain class. In order for
   * this to work the data and domain class must have the same getters and
   * setters. And the domain class must have a public no-args constructor.
   *
   * @param toBeMapped the object to be mapped, a data class
   * @param toData if the a domain class is mapped to a data class, if false the
   * reverse
   * @return the mapped Object
   */
  public static Object map(Object toBeMapped, boolean toData) {
    if (toBeMapped == null) {
      return null;
    }
    Object instanceOfClass = null;

    String mappedClassName;

    if (toData) {
      mappedClassName = toBeMapped.getClass().getName();
      mappedClassName = mappedClassName.replace("domain.elucidation.", "data.model.Data");
    } else {
      mappedClassName = toBeMapped.getClass().getName().replace("Data", "");
      if (mappedClassName.equals("data.model.User")
              || mappedClassName.equals("data.model.Role")) {
        mappedClassName = mappedClassName.replace("data.model", "domain.security");
      } else {
        mappedClassName = mappedClassName.replace("data.model", "domain.elucidation");
      }
    }

    try {

      //Create an instance of the class
      instanceOfClass = Class.forName(mappedClassName).newInstance();

      runThroughClass(instanceOfClass.getClass(), instanceOfClass, toBeMapped, toData);

    } catch (ClassNotFoundException | SecurityException | InstantiationException | NoSuchMethodException | InvocationTargetException
            | IllegalAccessException | IllegalArgumentException ex) {
      Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
    }

    return instanceOfClass;
  }

  /**
   * Runs through the interfaces a class implements
   *
   * @param c the class to run through
   * @param instanceOfClass a instance of the class
   * @param toBeMapped a object to be mapped
   * @param toData if the a domain class is mapped to a data class, if false the
   * reverse
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  private static void runThroughClass(Class c, Object instanceOfClass, Object toBeMapped, boolean toData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    for (Class i : c.getInterfaces()) {
      runThroughMethods(i, instanceOfClass, toBeMapped, toData);
    }
  }

  /**
   * Runs through the methods on a class
   *
   * @param c the class of the object
   * @param instanceOfClass a instance of the class
   * @param toBeMapped a object to be mapped
   * @param toData if the a domain class is mapped to a data class, if false the
   * reverse
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  private static void runThroughMethods(Class c, Object instanceOfClass, Object toBeMapped, boolean toData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    for (Method currentMethod : c.getMethods()) {
      //The name on the method
      String nameOnSetterMethod = null;

      if (currentMethod.getName().startsWith("can")) {
        nameOnSetterMethod = currentMethod.getName().replace("can", "setCan");
      } else if (currentMethod.getName().startsWith("is")) {
        nameOnSetterMethod = currentMethod.getName().replace("is", "set");
      } else if (currentMethod.getName().startsWith("get")) {
        nameOnSetterMethod = currentMethod.getName().replace("get", "set");
      } else {
        continue;
      }

      //The method with the name
      Method method = instanceOfClass.getClass().getMethod(nameOnSetterMethod, currentMethod.getReturnType());

      //The return type of the method
      Class returnTypeForMethod = currentMethod.getReturnType();

      if (Collection.class.isAssignableFrom(returnTypeForMethod)) {

        method.invoke(instanceOfClass, mapCollection(currentMethod, toBeMapped, instanceOfClass, toData));
      } else if (Map.class.isAssignableFrom(returnTypeForMethod)) {

        method.invoke(instanceOfClass, mapMap(currentMethod, toBeMapped, instanceOfClass, toData));

      } else {

        try {
          if (returnTypeForMethod.isInterface()) {
            //Invokes method
            method.invoke(instanceOfClass, map(currentMethod.invoke(toBeMapped), toData));
          } else {
            //Invokes method
            method.invoke(instanceOfClass, currentMethod.invoke(toBeMapped));
          }
        } catch (Exception ex) {
          System.out.println(method + " " + toBeMapped + " " + instanceOfClass);
          ex.printStackTrace();
        }
      }
    }
  }

  /**
   * Used when a method returns a collection
   *
   * @param currentMethod the method
   * @param toBeMapped the object to be mapped
   * @param instanceOfClass a instance of the class
   * @param toData if the a domain class is mapped to a data class, if false the
   * reverse
   * @return a collection with domain classes
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  private static Collection mapCollection(Method currentMethod, Object toBeMapped, Object instanceOfClass, boolean toData) throws IllegalAccessException, InvocationTargetException {
    Type genericFieldType = currentMethod.getGenericReturnType();

    if (!(genericFieldType instanceof ParameterizedType)) {
      throw new IllegalArgumentException("Input not a Collection");
    }

    ParameterizedType type = (ParameterizedType) genericFieldType;
    Type fieldArgTypes = type.getActualTypeArguments()[0];

    Class checkForInterface = (Class) fieldArgTypes;

    Collection tmpC = (Collection) currentMethod.invoke(toBeMapped);
    Collection retC = (Collection) currentMethod.invoke(instanceOfClass);

    if (checkForInterface.isInterface() && tmpC != null) {
      for (Object o : tmpC) {
        retC.add(map(o, toData));
      }
      return retC;
    } else {
      return tmpC;
    }
  }

  /**
   * Used when a method returns a map
   *
   * @param currentMethod the method
   * @param toBeMapped the object to be mapped
   * @param instanceOfMyClass a instance of the class
   * @param toData if the a domain class is mapped to a data class, if false the
   * reverse
   * @return a map with domain classes
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  private static Map mapMap(Method currentMethod, Object toBeMapped, Object instanceOfMyClass, boolean toData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Type genericFieldType = currentMethod.getGenericReturnType();

    if (!(genericFieldType instanceof ParameterizedType)) {
      throw new IllegalArgumentException("Input not a Map");
    }

    ParameterizedType type = (ParameterizedType) genericFieldType;
    Type key = type.getActualTypeArguments()[0];
    Class k = (Class) key;

    Type value = type.getActualTypeArguments()[1];
    Class v = (Class) value;

    Map tmpMap = (Map) currentMethod.invoke(toBeMapped);
    Map retMap = (Map) currentMethod.invoke(instanceOfMyClass);

    if (tmpMap != null) {
      for (Object o : tmpMap.keySet()) {
        retMap.put(
                (k.isInterface()) ? map(o, toData) : o,
                (v.isInterface()) ? map(tmpMap.get(o), toData) : tmpMap.get(o));
      }
    }
    return retMap;
  }
}
