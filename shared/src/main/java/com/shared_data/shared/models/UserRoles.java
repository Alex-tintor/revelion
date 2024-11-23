package com.shared_data.shared.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("UserRoles")
public class UserRoles {

    @Id
    @Column("urId")
    private Long Id;
    
    @Column("urUserId")
    private Long userId;

    @Column("urRolId")
    private Long rolId;

    public UserRoles(Long userId,Long rolid){
        this.rolId = rolid;
        this.userId = userId;
    }
}
