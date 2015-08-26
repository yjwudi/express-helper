package test;

public class User {
public String name;
public String password;
public User(User user){
	this.name=user.name;
	this.password=user.password;
}
public User(){
	
}
public void setName(String Name){
	this.name=Name;
}
public String getName(){
	return this.name;
}
public void setPassword(String Password){
	this.password=Password;
}
public String getPassword(){
	return this.password;
}
}
