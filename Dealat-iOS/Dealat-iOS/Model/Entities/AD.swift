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
    var city_id : JSON!
    var location_id : JSON!
    var category_id : JSON!
    var show_period : JSON!
    var publish_date : String!
    var is_featured : JSON!
    var status : JSON!
    var reject_note : String!
    var price : JSON!
    var main_image : String!
    var main_video : String!
    var is_negotiable : JSON!
    var title : String! = ""
    var description : String! = ""
    var created_at : String!
    var modified_at: String!
    var category_name : String!
    var parent_category_name : String!
    var tamplate_id : JSON!
    var location_name: String!
    var city_name : String!
    var parent_location: String!
    var seller_name : String!
    var seller_id : JSON! = 1
    var seller_phone : String!
    var whatsup_number : String!
    var images = [IMG]()
    var is_favorite : JSON!
    var expiry_date : String!
    var expired_after : JSON!
    var ad_visible_phone : JSON!
    var is_admin : JSON!
    var views_num : JSON!
    
    var vehicle = Vehicle()
    var property = Property()
    var mobile = Mobile()
    var electronic = Electronic()
    var fashion = Fashion()
    var kids = Kids()
    var sport = Sport()
    var job = Job()
    var industry = Industry()

    // 1 Vehicles
    class Vehicle : BaseEntity{
        var manufacture_date : String!
        var is_automatic : JSON!
        var is_new : JSON!
        var kilometer : JSON!
        var type_name : String!
        var type_model_name : String!
        var type_id : JSON!
        var type_model_id : JSON!
        var engine_capacity : String!
    }

    
    // 2 Properties
    class Property : BaseEntity{
        var property_state_id : JSON!
        var property_state_name : String!
        var rooms_num : String!
        var floor : String!
        var with_furniture : JSON!
        var space : String!
        var floors_number : String!
    }
    
    // 3 Mobiles
    class Mobile : BaseEntity{
        var is_new : JSON!
        var type_id : JSON!
        var type_name : String!
    }
    
    // 4 Electronic
    class Electronic : BaseEntity{
        var is_new : JSON!
        var type_name : String!
        var size : String!
        var type_id : JSON!

    }
    
    // 5 Fashion
    class Fashion : BaseEntity{
        var is_new : JSON!
    }


    // 6 Kids
    class Kids : BaseEntity{
        var is_new : JSON!
    }
    
    // 7 Sport
    class Sport : BaseEntity{
        var is_new : JSON!
    }

    
    // 8 Job
    class Job{
        var education_name : String!
        var schedule_name : String!
        var experience : String!
        var salary : String!
        var schedule_id : JSON!
        var education_id : JSON!
        var certificate_id : JSON!
        var certificate_name : String!
        var gender : JSON!
    }
    
    // 9 Industry
    class Industry : BaseEntity{
        var is_new : JSON!
    }

    
    // Mappable
    override func mapping(map: Map) {
        
        ad_id <- map["ad_id"]
        user_id <- map["user_id"]
        city_id <- map["city_id"]
        location_id <- map["location_id"]
        category_id <- map["category_id"]
        show_period <- map["show_period"]
        publish_date <- map["publish_date"]
        is_featured <- map["is_featured"]
        status <- map["status"]
        reject_note <- map["reject_note"]
        price <- map["price"]
        main_image <- map["main_image"]
        main_video <- map["main_video"]
        is_negotiable <- map["is_negotiable"]
        title <- map["title"]
        description <- map["description"]
        created_at <- map["created_at"]
        modified_at <- map["modified_at"]
        category_name <- map["category_name"]
        parent_category_name <- map["parent_category_name"]
        tamplate_id <- map["tamplate_id"]
        location_name <- map["location_name"]
        city_name <- map["city_name"]
        parent_location <- map["parent_location"]
        seller_name <- map["seller_name"]
        seller_id <- map["seller_id"]
        seller_phone <- map["seller_phone"]
        whatsup_number <- map["whatsup_number"]
        images <- map["images"]
        is_favorite <- map["is_favorite"]
        expiry_date <- map["expiry_date"]
        expired_after <- map["expired_after"]
        ad_visible_phone <- map["ad_visible_phone"]
        is_admin <- map["is_admin"]
        views_num <- map["views_num"]
        
        // 1 Vehicle
        vehicle.manufacture_date <- map["manufacture_date"]
        vehicle.is_automatic <- map["is_automatic"]
        vehicle.is_new <- map["is_new"]
        vehicle.kilometer <- map["kilometer"]
        vehicle.type_name <- map["type_name"]
        vehicle.type_model_name <- map["type_model_name"]
        vehicle.type_id <- map["type_id"]
        vehicle.type_model_id <- map["type_model_id"]
        vehicle.engine_capacity <- map["engine_capacity"]
        
        
        // 2 property
        property.property_state_id <- map["property_state_id"]
        property.property_state_name <- map["property_state_name"]
        property.rooms_num <- map["rooms_num"]
        property.floor <- map["floor"]
        property.with_furniture <- map["with_furniture"]
        property.space <- map["space"]
        property.floors_number <- map["floors_number"]
        
        // 3 Mobile
        mobile.is_new <- map["is_new"]
        mobile.type_name <- map["type_name"]
        mobile.type_id <- map["type_id"]

        // 4 Electronic
        electronic.is_new <- map["is_new"]
        electronic.type_name <- map["type_name"]
        electronic.size <- map["size"]
        electronic.type_id <- map["type_id"]

        // 5 Fashion
        fashion.is_new <- map["is_new"]

        // 6 Kids
        kids.is_new <- map["is_new"]
        
        // 7 Sport
        sport.is_new <- map["is_new"]
        
        // 8 job
        job.education_name <- map["education_name"]
        job.schedule_name <- map["schedule_name"]
        job.experience <- map["experience"]
        job.salary <- map["salary"]
        job.certificate_id <- map["certificate_id"]
        job.certificate_name <- map["certificate_name"]
        job.gender <- map["gender"]

        
        // 6 industry
        industry.is_new <- map["is_new"]

    }
    
    
    func getStatus() -> (String,UIImage?) {
        switch self.status.intValue {
        case 1:
            return ("Pending".localized, #imageLiteral(resourceName: "pending"))
        case 2:
            return ("Accepted".localized,#imageLiteral(resourceName: "checked_copy"))
        case 3:
            return ("Expired".localized,#imageLiteral(resourceName: "expired_copy"))
        case 4:
            return ("Hidden".localized,#imageLiteral(resourceName: "hidden"))
        case 5:
            return ("Rejected".localized,#imageLiteral(resourceName: "exclamation-mark_copy"))
        case 6:
            return ("Deleted".localized,nil)
        default:
            return ("",nil)
        }
    }
    
}
