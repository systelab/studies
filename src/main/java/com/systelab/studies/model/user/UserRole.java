package com.systelab.studies.model.user;

import javax.xml.bind.annotation.XmlEnumValue;

public enum UserRole {
    @XmlEnumValue("USER") USER,
    @XmlEnumValue("ADMIN") ADMIN
}