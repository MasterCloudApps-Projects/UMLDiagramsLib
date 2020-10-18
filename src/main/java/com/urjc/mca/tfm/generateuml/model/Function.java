package com.urjc.mca.tfm.generateuml.model;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

public class Function {

    private boolean staticFunction;
    private Visibility visibility;
    private String name;
    private String[] parameters;
    private String returnTypeName;

    public Function(String name) {
        this.setVisibility(Visibility.EMPTY_VISIBILITY);
        this.name = name;
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
        if (this.getVisibility() != Visibility.EMPTY_VISIBILITY) {
            stringBuilder.append(this.getVisibility().getCharacter());
            stringBuilder.append(" ");
        }
        if (this.isStaticFunction())
            stringBuilder.append("{static} ");
        stringBuilder.append(this.name);
        if(this.getParameters() != null){
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            Arrays.stream(getParameters()).forEach(p -> {
                sb.append(p);
                sb.append(", ");
            });
            stringBuilder.append(replaceLastComma(sb));
            stringBuilder.append(")");
        }
        if (!StringUtils.isEmpty(this.getReturnTypeName())) {
            stringBuilder.append(": ");
            stringBuilder.append(this.getReturnTypeName());
        }
        return stringBuilder.toString();
    }

    private String replaceLastComma(StringBuilder sb){
            int init = sb.lastIndexOf(", ");
            sb.replace(init, init +2, "");
        return sb.toString();
    }

    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public void setStaticFunction(boolean staticFunction) {
        this.staticFunction = staticFunction;
    }

    public boolean isStaticFunction() {
        return staticFunction;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getReturnTypeName() {
        return returnTypeName;
    }
}