/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ResourceBundle;

/**
 *
 * @author Melnikov
 */
public class PageReturner {
    private final static ResourceBundle pageName = ResourceBundle.getBundle("properties.pageAlias");
    public static String getPage(String key){
        return pageName.getString(key);
    }
}
