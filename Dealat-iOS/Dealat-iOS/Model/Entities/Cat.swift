//
//  Cat.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Cat : BaseEntity {
    
    var category_id : JSON!
    var category_name : String!
    var parent_id : JSON!
    var web_image : String!
    var mobile_image : String!
    var tamplate_name : String!
    var description : String!
    var children : [Cat] = [Cat]()

    
    // Mappable
    override func mapping(map: Map) {
        category_id <- map["category_id"]
        category_name <- map["category_name"]
        parent_id <- map["parent_id"]
        web_image <- map["web_image"]
        mobile_image <- map["mobile_image"]
        tamplate_name <- map["tamplate_name"]
        description <- map["description"]
        children <- map["children"]
    }
}

