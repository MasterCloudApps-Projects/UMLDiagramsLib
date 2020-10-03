package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

public class Function {

    public boolean staticFunction;
    public  Visibility visibility;
    public  String name;
    public  String[] parameters;
    public  String returnTypeName;

    public Function(String name) {
//        this(name, Visibility.EMPTY_VISIBILITY);
        this.name = name;
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

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
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
            sb.append("(");
            Arrays.stream(parameters).forEach(p -> {
                sb.append(p);
                sb.append(", ");
            });
            stringBuilder.append(replaceLastComma(sb));
            stringBuilder.append(")");
        }
        if (!StringUtils.isEmpty(this.returnTypeName)) {
            stringBuilder.append(": ");
            stringBuilder.append(this.returnTypeName);
        }
        return stringBuilder.toString();
    }

    private String replaceLastComma(StringBuilder sb){
            int init = sb.lastIndexOf(", ");
            sb.replace(init, init +2, "");
        return sb.toString();
    }
}