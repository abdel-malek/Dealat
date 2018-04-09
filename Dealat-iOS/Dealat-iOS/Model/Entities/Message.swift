//
//  Message.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/8/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Message : BaseEntity {
    
    var message_id : JSON!
    var text : String!
    var to_seller : JSON!
    var chat_session_id : JSON!
    var created_at : String!
    var modified_at : String!
    
    // Mappable
    override func mapping(map: Map) {
        message_id <- map["message_id"]
        text <- map["text"]
        to_seller <- map["to_seller"]
        chat_session_id <- map["chat_session_id"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
    }
}

