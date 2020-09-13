package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

public class Function {

    public final boolean staticFunction;
    public final Visibility visibility;
    public final String name;
    public final String[] parameters;
    public final String returnTypeName;

    public Function(String name) {
        this(name, Visibility.EMPTY_VISIBILITY);
    }

    public Function(String name, Visibility visibility) {
        this(name, visibility, "");
    }

    public Function(String name, Visibility visibility, String returnType) {
        this(name, visibility, returnType, null);
    }

    public Function(String name, Visibility visibility, String returnType, String[] parameters) {
        this(name, visibility, returnType, parameters, false);
    }

    public Function(String name, Visibility visibility, String returnType, String[] parameters, boolean staticFunction) {
        this.name = name;
        this.visibility = visibility;
        this.returnTypeName = returnType;
        this.staticFunction = staticFunction;
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function = (Function) o;
        return Objects.equals(name, function.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.visibility != Visibility.EMPTY_VISIBILITY) {
            stringBuilder.append(this.visibility.getCharacter());
            stringBuilder.append(" ");
        }
        if (this.staticFunction)
            stringBuilder.append("{static} ");
        stringBuilder.append(this.name);
        if(this.parameters != null){
            StringBuilder sb = new StringBuilder();
            Arrays.stream(parameters).forEach(p -> {
                sb.append(p);
                sb.append(", ");
            });
            replaceIfOnlyOneParameter(sb);
            stringBuilder.append(replaceIfOnlyOneParameter(sb));
        }
        if (!StringUtils.isEmpty(this.returnTypeName)) {
            stringBuilder.append(": ");
            stringBuilder.append(this.returnTypeName);
        }
        return stringBuilder.toString();
    }

    private String replaceIfOnlyOneParameter(StringBuilder sb){
        if(sb.lastIndexOf(", ") == sb.indexOf(", ")){
            int init = sb.indexOf(", ");
            sb.replace(init, init +1, "");
        }
        return sb.toString();
    }
}