//
//  Commecicial.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 2/26/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Commercial : BaseEntity {
    
    var commercial_ad_id : JSON!
    var title : String!
    var description : String!
    var category_id : JSON!
    var position : JSON!
    var image : String!
    var is_main : JSON!
    var ad_url : String!
    var category_name : String!
    var parent_category_name : String!
    
    // Mappable
    override func mapping(map: Map) {
        commercial_ad_id <- map["commercial_ad_id"]
        title <- map["title"]
        description <- map["description"]
        category_id <- map["category_id"]
        position <- map["position"]
        image <- map["image"]
        is_main <- map["is_main"]
        ad_url <- map["ad_url"]
        category_name <- map["category_name"]
        parent_category_name <- map["parent_category_name"]
    }
}



