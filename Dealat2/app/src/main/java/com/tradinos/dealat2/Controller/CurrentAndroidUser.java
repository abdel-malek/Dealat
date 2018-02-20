package com.tradinos.dealat2.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tradinos.dealat2.Model.User;


public class CurrentAndroidUser implements CurrentUser {

	Context context ; 
	public CurrentAndroidUser(Context context) {
		this.context = context ; 
	}
	
	public static CurrentAndroidUser getInstance(Context  context) {
		return new CurrentAndroidUser(context);
	}
	
	@Override
	public void Save( User customer) {
		SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		if(customer.getId()!= null){
			editor.putString("id", customer.getId());
			editor.putString("name", customer.getName());
			editor.putInt("role_id", customer.getRole());
			editor.putString("username", customer.getUsername());
			editor.putString("password", customer.getPassword());
			editor.putInt("country", customer.getCountry());
		}

		editor.commit();
	}

	@Override
	public Boolean IsLogged() {
		SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		return !(preferences == null || preferences.getString("id", "").equals(""));
	}

	@Override
	public User Get() {
		SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		if (preferences == null || preferences.getString("id", "").equals(""))
			return null;
		else {
			User user = new User();
			user.setId(preferences.getString("id", ""));
			user.setName(preferences.getString("name", ""));
			user.setRole(preferences.getInt("role_id", -1));
			user.setUsername(preferences.getString("username", ""));
			user.setPassword(preferences.getString("password", ""));
			user.setCountry(preferences.getInt("country", -1));

			return user;
		}
	}

	@Override
	public void SignOut() {
		SharedPreferences mSharedPreference = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		Editor editor = mSharedPreference.edit();
		editor.clear();
		editor.commit();
	}

	
}
