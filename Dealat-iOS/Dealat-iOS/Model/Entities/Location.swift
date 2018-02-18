//
//  Location.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class location : BaseEntity {
    
    var location_id : JSON2!
    var location_name : String!
    var city_id : JSON!
    var city_name : String!

    
    // Mappable
    override func mapping(map: Map) {
        location_id <- map["location_id"]
        location_name <- map["location_name"]
        city_id <- map["city_id"]
        city_name <- map["city_name"]

    }
}

