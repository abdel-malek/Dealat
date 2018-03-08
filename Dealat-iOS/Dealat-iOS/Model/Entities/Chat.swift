//
//  Chat.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/8/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Chat : BaseEntity {
    
    var chat_session_id : JSON!
    var ad_id : JSON!
    var ad_title : String!
    var user_id : JSON!
    var seller_id : JSON!
    var user_seen : JSON!
    var seller_seen : JSON!
    var user_name : String!
    var seller_name : String!
    var created_at : String!
    var modified_at : String!
    
    // Mappable
    override func mapping(map: Map) {
        chat_session_id <- map["chat_session_id"]
        ad_id <- map["ad_id"]
        ad_title <- map["ad_title"]
        user_id <- map["user_id"]
        seller_id <- map["seller_id"]
        user_seen <- map["user_seen"]
        seller_seen <- map["seller_seen"]
        user_name <- map["user_name"]
        seller_name <- map["seller_name"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
    }
}


