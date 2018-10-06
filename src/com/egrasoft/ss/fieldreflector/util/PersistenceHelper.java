package com.egrasoft.ss.fieldreflector.util;

import java.util.List;
import java.util.Map;

public class PersistenceHelper {
    private List<Class<?>> classes;
    private Map<Class<?>, Object> instanceMap;
    private Class<?> currentClass;

    public List<Class<?>> getClasses() {
        return classes;
    }

    public void setClasses(List<Class<?>> classes) {
        this.classes = classes;
    }

    public Map<Class<?>, Object> getInstanceMap() {
        return instanceMap;
    }

    public void setInstanceMap(Map<Class<?>, Object> instanceMap) {
        this.instanceMap = instanceMap;
    }

    public Class<?> getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Class<?> currentClass) {
        this.currentClass = currentClass;
    }
}
