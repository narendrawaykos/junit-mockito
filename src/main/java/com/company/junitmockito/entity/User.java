package com.company.junitmockito.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long id;
    private String name;
    private String userId;
    private Date dob;
}
