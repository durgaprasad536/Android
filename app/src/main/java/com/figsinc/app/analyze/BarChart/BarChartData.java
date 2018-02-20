package com.figsinc.app.analyze.BarChart;

/**
 * Created by 461883 on 9/23/17.
 */

public class BarChartData {

    String value;
    String label;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public  interface  rajesh{

    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    enum Color implements  rajesh
    {
        RED(""), GREEN(""), BLUE("");
         Color(String raj){

        }

    }

}
