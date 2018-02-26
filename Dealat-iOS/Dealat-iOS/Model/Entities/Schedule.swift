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
    
    var schedual_id : JSON!
    var name : String!
    
    // Mappable
    override func mapping(map: Map) {
        schedual_id <- map["schedual_id"]
        name <- map["name"]
    }
}
