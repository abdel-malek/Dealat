//
//  Model.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Model : BaseEntity {
    
    var type_id : JSON!
    var type_model_id : String!
    var name : String!
    
    // Mappable
    override func mapping(map: Map) {
        type_model_id <- map["type_model_id"]
        type_id <- map["type_id"]
        name <- map["name"]
    }
}

