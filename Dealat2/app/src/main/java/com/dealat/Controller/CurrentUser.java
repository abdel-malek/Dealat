package com.dealat.Controller;


import com.dealat.Model.User;

public interface CurrentUser {

	void Save(User customer) ;
	Boolean IsLogged() ;
	User Get();
}
