//
//  Scheldule.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//


import Foundation
import ObjectMapper
import SwiftyJSON

class Schedule : BaseEntity {
    
    var schedule_id : JSON!
    var name : String!
    
//    schedule_id
    
    
    // Mappable
    override func mapping(map: Map) {
        schedule_id <- map["schedule_id"]
        name <- map["name"]
    }
}
