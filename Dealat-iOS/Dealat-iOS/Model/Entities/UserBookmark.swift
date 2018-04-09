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
    var query : String!
    var results_num : JSON!
    var created_at : String!
    var modified_at : String!
    
    // Mappable
    override func mapping(map: Map) {
        user_bookmark_id <- map["user_bookmark_id"]
        user_id <- map["user_id"]
        query <- map["query"]
        results_num <- map["results_num"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
    }
    
    
    func getName() -> String{
        return "Bookmark #\(self.user_bookmark_id.stringValue)"
    }
    
}

