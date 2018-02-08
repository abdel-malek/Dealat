//
//  AD.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class AD : BaseEntity {
    
    var ad_id : JSON!
    var user_id : JSON!
    var location_id : JSON!
    var category_id : JSON!
    var show_period : JSON!
    var publish_date : String!
    var is_featured : JSON!
    var status : JSON!
    var price : JSON!
    var main_image : String!
    var main_vedio : String!
    var title : String!
    var description : String!
    var created_at : String!
    var modified_at: String!
    var category_name : String!
    var location_name: String!
    var parent_location: String!
    
    // Mappable
    override func mapping(map: Map) {
        
        ad_id <- map["ad_id"]
        user_id <- map["user_id"]
        location_id <- map["location_id"]
        category_id <- map["category_id"]
        show_period <- map["show_period"]
        publish_date <- map["publish_date"]
        is_featured <- map["is_featured"]
        status <- map["status"]
        price <- map["price"]
        main_image <- map["main_image"]
        main_vedio <- map["main_vedio"]
        title <- map["title"]
        description <- map["description"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
        category_name <- map["category_name"]
        location_name <- map["location_name"]
        parent_location <- map["parent_location"]
    }
    
}
