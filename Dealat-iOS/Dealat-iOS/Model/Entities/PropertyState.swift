//
//  PropertyState.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 5/24/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class PropertyState : BaseEntity {
    
    var property_state_id : JSON!
    var name : String!
        
    
    // Mappable
    override func mapping(map: Map) {
        property_state_id <- map["property_state_id"]
        name <- map["name"]
    }
}
