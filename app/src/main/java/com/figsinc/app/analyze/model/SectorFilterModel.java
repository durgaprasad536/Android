package com.figsinc.app.analyze.model;

/**
 * Created by Dinash on 17-12-2017.
 */

public class SectorFilterModel {

    private String description;

    private String name;

    private String name_jp;

    private String sub_industry_code;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getName_jp ()
    {
        return name_jp;
    }

    public void setName_jp (String name_jp)
    {
        this.name_jp = name_jp;
    }

    public String getSub_industry_code() {
        return sub_industry_code;
    }

    public void setSub_industry_code(String sub_industry_code) {
        this.sub_industry_code = sub_industry_code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+", name = "+name+", name_jp = "+name_jp+"]";
    }

}
