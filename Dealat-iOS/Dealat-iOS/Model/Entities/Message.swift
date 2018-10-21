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
import AFDateHelper

class Message : BaseEntity {
    
    var message_id : JSON!
    var text : String!
    var to_seller : JSON!
    var chat_session_id : JSON!
    var created_at : String!
    var modified_at : String!
    
    var isNew : Bool = false
    var timeStamp : TimeInterval!

    
    //    var date : Date!
    //    var dateFull : Date!
    
    // Mappable
    override func mapping(map: Map) {
        message_id <- map["message_id"]
        text <- map["text"]
        to_seller <- map["to_seller"]
        chat_session_id <- map["chat_session_id"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
        
        timeStamp <- map["timeStamp"]
        isNew <- map["isNew"]
        
        //        date <- map["date"]
        //        dateFull <- map["dateFull"]
        
    }
    
    func  getDateOnlyString() -> String{
        if let created_at = self.created_at{
             let cc = created_at.components(separatedBy: " ")
        
            if let dateString = cc.first{
                return dateString
            }
        }
        return Date().toString(format: DateFormatType.isoDate)
    }
    
    func getDate() -> Date{
        if let created_at = self.created_at{
            if let date  = Date.init(fromString: created_at, format: DateFormatType.custom("yyyy-MM-dd HH:mm:ss")){
                
                return  Date.init(fromString: date.toString(format: DateFormatType.isoDate), format: DateFormatType.isoDate)!
            }
        }
        
        print("FALSEEEEEEEE")
        return Date()
    }
    
    func getDateFull() -> Date{
        if let created_at = self.created_at{
            if let date  = Date.init(fromString: created_at, format: DateFormatType.custom("yyyy-MM-dd HH:mm:ss")){
                return  date
            }
        }
        
        print("FALSEEEEEEEE")
        return Date()
    }
    
    
}

