package org.zent.oracledb.enums;

public enum EntityStatus {
    ACTIVE(1),
    INACTIVE(0);

    public final Integer value;

    private EntityStatus(Integer value) {
        this.value = value;
    }
}

