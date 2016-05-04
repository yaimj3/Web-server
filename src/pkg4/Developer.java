/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4;

import java.math.BigDecimal;

/**
 *
 * @author minyo
 */
class Developer {

    private int age;
    private String name;
    private int id;
    
    public Developer(String str,int a) {
       this.name = str;
       this.age = a;
    }
    
    public int getAge()
    {
    return this.age;
    }
    
    public String getName()
    {
    return this.name;
    }
    
    public int getID()
    {
    return this.id;
    }
}
