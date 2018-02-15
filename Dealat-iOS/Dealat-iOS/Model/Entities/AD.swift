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
    var is_negotiable : JSON!
    var title : String!
    var description : String!
    var created_at : String!
    var modified_at: String!
    var category_name : String!
    var parent_category_name : String!
    var tamplate_id : JSON!
    var location_name: String!
    var parent_location: String!
    var seller_name : String!
    var images = [IMG]()
    
    var property = Property()
    var job = Job()

    
    // 2 Properties
    class Property : BaseEntity{
        var state : String!
        var rooms_num : String!
        var floor : String!
        var with_furniture : String!
        var space : String!
    }
    
    // 8 Job
    class Job{
        var education_name : String!
        var schedule_name : String!
    }
    
    
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
        is_negotiable <- map["is_negotiable"]
        title <- map["title"]
        description <- map["description"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
        category_name <- map["category_name"]
        parent_category_name <- map["parent_category_name"]
        tamplate_id <- map["tamplate_id"]
        location_name <- map["location_name"]
        parent_location <- map["parent_location"]
        seller_name <- map["seller_name"]
        images <- map["images"]
        
        // 2 property
        property.state <- map["state"]
        property.rooms_num <- map["rooms_num"]
        property.floor <- map["floor"]
        property.with_furniture <- map["with_furniture"]
        property.space <- map["space"]
        
        // 8 job
        job.education_name <- map["education_name"]
        job.schedule_name <- map["schedule_name"]

    }
    
}
