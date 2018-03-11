//
//  User.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import SwiftyJSON

class User : NSObject, NSCoding{
    
    static let KEY_USER_ME : String = "me"
    
    var user_id : Int!
    var email : String!
    var password : String!
    var name : String!
    var phone : String!
    var city_id : Int!
    var is_active : Int!
    var lang : String!
    var username : String!
    var server_key : String!
    var os : Int!
    var account_type : Int!
    var personal_image : String!
    
    
    //MARK: Local data
    var statues_key: String? = USER_STATUES.NEW_USER.rawValue
    
    enum USER_STATUES : String {
        case NEW_USER = "new"
        case PENDING_CODE = "pending_code"
        case USER_REGISTERED = "registered"
    }
    
    //MARK: chache current user
    static func getCurrentUser() -> User
    {
        if let archivedData = UserDefaults.standard.object(forKey: KEY_USER_ME) as? Data
        {
            let me: User = (NSKeyedUnarchiver.unarchiveObject(with: archivedData) as? User)!
            return me
        }
        return User()
    }
    
    static func saveMe(me : User)
    {
        let archivedObject = NSKeyedArchiver.archivedData(withRootObject: me)
        UserDefaults.standard.set(archivedObject, forKey: KEY_USER_ME)
        UserDefaults.standard.synchronize()
        
        if let c = me.city_id{
            Provider.setCity(c)
        }
    }
    
    static func clearMe()
    {
        UserDefaults.standard.removeObject(forKey: KEY_USER_ME)
    }
    
    
    override init(){
        
    }
    
    required init(coder decoder: NSCoder) {
        self.user_id = decoder.decodeObject(forKey: "user_id") as? Int
        self.statues_key = decoder.decodeObject(forKey: "statues_key") as? String
        
        self.email = decoder.decodeObject(forKey: "email") as? String
        self.password = decoder.decodeObject(forKey: "password") as? String
        self.name = decoder.decodeObject(forKey: "name") as? String
        self.phone = decoder.decodeObject(forKey: "phone") as? String
        self.city_id = decoder.decodeObject(forKey: "city_id") as? Int
        self.is_active = decoder.decodeObject(forKey: "is_active") as? Int
        self.lang = decoder.decodeObject(forKey: "lang") as? String
        self.username = decoder.decodeObject(forKey: "username") as? String
        self.server_key = decoder.decodeObject(forKey: "server_key") as? String
        self.os = decoder.decodeObject(forKey: "os") as? Int
        self.account_type = decoder.decodeObject(forKey: "account_type") as? Int
        self.personal_image = decoder.decodeObject(forKey: "personal_image") as? String
    }
    
    
    func encode(with coder: NSCoder) {
        coder.encode(user_id, forKey: "user_id")
        coder.encode(statues_key, forKey: "statues_key")
        
        coder.encode(email, forKey: "email")
        coder.encode(password, forKey: "password")
        coder.encode(name, forKey: "name")
        coder.encode(phone, forKey: "phone")
        coder.encode(city_id, forKey: "city_id")
        coder.encode(is_active, forKey: "is_active")
        coder.encode(lang, forKey: "lang")
        coder.encode(username, forKey: "username")
        coder.encode(server_key, forKey: "server_key")
        coder.encode(os, forKey: "os")
        coder.encode(account_type, forKey: "account_type")
        coder.encode(personal_image, forKey: "personal_image")
    }
    
    
    static func isRegistered() -> Bool{
        return (User.getCurrentUser().statues_key == User.USER_STATUES.USER_REGISTERED.rawValue)
    }
    
    
    static func getObject(_ dic : [String : Any]) -> User{
        let me = User.getCurrentUser()
        me.user_id = dic["user_id"] as? Int
        me.email =  dic["email"] as? String
        me.password =  dic["password"] as? String
        me.name =  dic["name"] as? String
        me.phone =  dic["phone"] as? String
        me.city_id =  dic["city_id"] as? Int
        me.is_active =  dic["is_active"] as? Int
        me.lang =  dic["lang"] as? String
        me.username =  dic["username"] as? String
        me.server_key =  dic["server_key"] as? String
        me.os =  dic["os"] as? Int
        me.account_type =  dic["account_type"] as? Int
        me.personal_image = dic["personal_image"] as? String
        
        return me
    }
    
}

