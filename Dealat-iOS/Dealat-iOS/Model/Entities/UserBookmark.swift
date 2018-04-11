//
//  UserBookmark.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/18/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class UserBookmark : BaseEntity {
    
    var user_bookmark_id : JSON!
    var user_id : JSON!
    var queryString : String!
    var results_num : JSON!
    var created_at : String!
    var modified_at : String!
    
    var query : BookmarkQuery!

    
    // Mappable
    override func mapping(map: Map) {
        user_bookmark_id <- map["user_bookmark_id"]
        user_id <- map["user_id"]
        queryString <- map["query"]
        results_num <- map["results_num"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
        
        
        if queryString != nil{
            
            let data = queryString!.data(using: .utf8)!
            do {
                if let jsonArray = try JSONSerialization.jsonObject(with: data, options : .allowFragments) as? Dictionary<String,Any>
                {
                    
                    if let j = JSON(jsonArray).dictionaryObject{
                        if let obj = BookmarkQuery(JSON : j){
                            self.query = obj
                        }
                    }
                    
                } else {
                    print("bad json")
                }
            } catch let error as NSError {
                print(error)
            }
        }
        
        
    }
    
    
    func getName() -> String{
        return "Bookmark #\(self.user_bookmark_id.stringValue)"
    }
    
    
    
}

