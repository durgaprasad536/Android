package com.figsinc.app.learn.Model;

/**
 * Created by Dinash on 17-12-2017.
 */

public class RegionFilterModel {
    private String description_jp;

    private String description;

    private String pk;

    public String getDescription_jp ()
    {
        return description_jp;
    }

    public void setDescription_jp (String description_jp)
    {
        this.description_jp = description_jp;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getPk ()
    {
        return pk;
    }

    public void setPk (String pk)
    {
        this.pk = pk;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description_jp = "+description_jp+", description = "+description+", pk = "+pk+"]";
    }

}
