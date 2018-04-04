//
//  ReportMessage.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 4/4/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class ReportMessage : BaseEntity {
    
    var report_message_id : JSON!
    var msg : String!
    
    // Mappable
    override func mapping(map: Map) {
        report_message_id <- map["report_message_id"]
        msg <- map["msg"]
    }
}
