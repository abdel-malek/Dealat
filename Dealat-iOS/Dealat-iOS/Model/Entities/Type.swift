//
//  Type.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Type : BaseEntity {
    
    var type_id : JSON!
    var category_id : JSON!
    var tamplate_id : JSON!
    var name : String!
    var full_type_name : String!
    var models = [Model]()
    
    // Mappable
    override func mapping(map: Map) {
        type_id <- map["type_id"]
        category_id <- map["category_id"]
        tamplate_id <- map["tamplate_id"]
        name <- map["name"]
        full_type_name <- map["full_type_name"]
        models <- map["models"]
    }
}


