package com.dba.assignment.command;

import com.dba.assignment.entity.User;
import com.dba.assignment.entity.UserCredentials;

import lombok.Data;

@Data
public class RegisterCommand {

private User user;
private UserCredentials userCredentials;
}
