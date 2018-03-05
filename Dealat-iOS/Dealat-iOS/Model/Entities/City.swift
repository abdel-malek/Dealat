//
//  City.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class City : BaseEntity {
    
    var city_id : JSON!
    var city_name : String!
    
    
    // Mappable
    override func mapping(map: Map) {
        city_id <- map["city_id"]
        city_name <- map["city_name"]
        
    }
    
}


