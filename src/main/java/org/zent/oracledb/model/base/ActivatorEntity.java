package org.zent.oracledb.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.zent.oracledb.enums.EntityStatus;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class ActivatorEntity extends BaseEntity {
    @Column(columnDefinition = "SMALLINT default 0")
    @Enumerated(EnumType.ORDINAL)
    protected EntityStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date activatedDate;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date deactivatedDate;
}
