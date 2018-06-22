package org.wso2.sample.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "miles")
@XmlAccessorType(XmlAccessType.NONE)
public class FlyerMiles {
    @XmlElement
    private long milesFlown;

    @XmlElement
    private String customerId;

    public long getMilesFlown() {
        return milesFlown;
    }

    public void setMilesFlown(long milesFlown) {
        this.milesFlown = milesFlown;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
