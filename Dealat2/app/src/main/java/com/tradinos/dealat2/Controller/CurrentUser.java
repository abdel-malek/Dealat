package com.tradinos.dealat2.Controller;


import com.tradinos.dealat2.Model.User;

public interface CurrentUser {

	void Save(User customer) ;
	Boolean IsLogged() ;
	User Get();
	void SignOut();
}
