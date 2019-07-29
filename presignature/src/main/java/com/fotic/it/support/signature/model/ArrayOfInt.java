
package com.fotic.it.support.signature.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInt", propOrder = {
        "_int"
})
public class ArrayOfInt {

    @XmlElement(namespace = "http://xfire.webservice.com/SignatureByRulesWebService", name = "int", type = Integer.class)
    protected List<Integer> _int;


    public List<Integer> getInt() {
        if (_int == null) {
            _int = new ArrayList<>();
        }
        return this._int;
    }

    public int[] getArray() {
        int[] array;
        if (_int == null) {
            array = new int[0];
        } else {
            int size = _int.size();
            array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = _int.get(i);
            }
        }
        return array;
    }

    public void set_int(List<Integer> _int) {
        this._int = _int;
    }
}
