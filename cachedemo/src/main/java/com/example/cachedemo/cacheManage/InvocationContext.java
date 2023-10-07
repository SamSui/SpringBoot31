package com.example.cachedemo.cacheManage;

import java.util.Arrays;

public class InvocationContext {

    public static final String TEMPLATE = "%s.%s(%s)";

    private final Class<?> targetClass;
    private final String targetMethod;
    private final Object[] args;

    public InvocationContext(Class<?> targetClass, String targetMethod, Object[] args) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.args = args;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        InvocationContext other = (InvocationContext) obj;
        if (!Arrays.equals(args, other.args)) {
            return false;
        }
        if (targetMethod == null) {
            if (other.targetMethod != null) {
                return false;
            }
        } else if (!targetMethod.equals(other.targetMethod)) {
            return false;
        }
        if (targetClass == null) {
            if (other.targetClass != null) {
                return false;
            }
        } else if (!targetClass.equals(other.targetClass)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(args);
        result = prime * result + ((targetMethod == null) ? 0 : targetMethod.hashCode());
        result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format(TEMPLATE, targetClass.getName(), targetMethod, Arrays.toString(args));
    }
}
