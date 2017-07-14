package com.example.bhargava.cse;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Bhargava on 5/18/2017.
 */
@IgnoreExtraProperties
public class Question implements Serializable{
    String ques;
    String A,B,C,D,E;
    int F,G;
    public  Question(){}

    public void setQue(String ques)
    {
        this.ques=ques;
    }

    public String getQues(){return ques;}

    public void setA(String A)
    {
        this.A=A;
    }

    public void setB(String B)
    {
        this.B=B;
    }
    public void setC(String C)
    {
        this.C=C;
    }
    public void setD(String D)
    {
        this.D=D;
    }
    public void setE(String E)
    {
        this.E=E;
    }

    public void setF(int F)
    {
        this.F=F;
    }

    public void setG(int G)
    {
        this.G=G;
    }

    public String getA(){return A;}

    public String getB(){return B;}

    public String getC(){return C;}

    public String getD(){return D;}

    public String getE(){return E;}

    public int getF(){return F;}

    public int getG(){return G;}


}
