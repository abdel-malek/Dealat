//
//  User.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation

class User : NSObject, NSCoding{
    
    static let KEY_USER_ME : String = "me"
    
    var user_id : Int!

    
    //MARK: Local data
    var statues_key: String? = USER_STATUES.NEW_USER.rawValue
    
    enum USER_STATUES : String {
        case NEW_USER = "new"
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
    }
    
    
    func encode(with coder: NSCoder) {
        coder.encode(user_id, forKey: "user_id")
        coder.encode(statues_key, forKey: "statues_key")
    }
    
    
    static func isRegistered() -> Bool{
        return (User.getCurrentUser().statues_key == User.USER_STATUES.USER_REGISTERED.rawValue)
    }
    
}

