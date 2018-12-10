package com.zhangwei.authority;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResourceEntity {
    private String id;
    private String sysCode;
    private String typeCode;
    private String typeName;
    private String resourceCode;
    private String resourceName;
    private String uri;
    private String isMenu;
    private String isTiming;
    private String menuOrder;
    private String parentCode;
    private String description;
    private String state;
    private String enable;

}
