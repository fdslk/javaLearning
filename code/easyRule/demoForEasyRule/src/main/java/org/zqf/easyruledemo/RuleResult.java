package org.zqf.easyruledemo;

public class RuleResult {

    private String classType = "";


    private String resultType = "";

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public String getResultType() {
        return resultType;
    }
}
