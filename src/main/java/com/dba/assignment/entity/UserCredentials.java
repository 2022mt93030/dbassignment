package com.dba.assignment.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_credential")
public class UserCredentials {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="CREATE_OR_UPDATE_DATE")
    private Date createOrUpdatedAt;
	
	@Column(name="EMAIL")
	private String emailId;
	
	@OneToOne(cascade = CascadeType.ALL)    
    @JoinColumn(name = "EMP_ID", referencedColumnName = "id")
	private Employee emp;
    
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "Role_id", referencedColumnName = "id")
//	private Role role;
}
