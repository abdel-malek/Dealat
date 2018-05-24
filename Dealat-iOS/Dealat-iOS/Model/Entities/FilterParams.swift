//
//  FilterParams.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import SwiftyJSON


class FilterParams{
    
    var searchText : String!
    var category : Cat!
    var city : City!
    var location : Location!
    
    var type_id : Type!
    var type_model_id : [Model]!
    
    var schedule_id : [Schedule]!
    var education_id : [Education]!
    var certificate_id : [Certificate]!
    var propertyStates : [PropertyState]!
    var manufacture_date : [String]!

    
    var is_new : Int!
    var is_automatic : Int!
    var with_furniture : Int!
    var gender : Int!

    
    var price = range_value()
    var size = range_value()
    var kilometer = range_value()
    var rooms_num = range_value()
    var floors_number = range_value()
    var space = range_value()
    var floor = range_value()
    var salary = range_value()
    var engine_capacity = range_value()
    
    static var shared = FilterParams()
    
    static func getParams(_ filter : FilterParams) -> [String : Any]{
        var params : [String : Any] = [:]
        
        if let x = filter.searchText{
            params["query"] = x
        }
        if let x = filter.category{
            params["category_id"] = x.category_id.intValue
            params["category_name"] = x.category_name
        }
        
        if let x = filter.city{
            params["city_id"] = x.city_id.intValue
            params["city_name"] = x.city_name
        }
        
        if let x = filter.location{
            params["location_id"] = x.location_id.intValue
            params["location_name"] = x.location_name
        }
        
        if let x = filter.type_id{
            params["type_id"] = x.type_id.intValue
            params["type_name"] = x.name
        }
        
        
        if let x = filter.type_model_id{
            params["type_model_id"] = JSON(x.map({$0.type_model_id.intValue})).rawString()
            params["model_name"] = JSON(x.map({$0.name})).rawString()
        }
        
        if let x = filter.schedule_id{
            params["schedule_id"] = JSON(x.map({$0.schedule_id.intValue})).rawString()
            params["schedule_name"] = JSON(x.map({$0.name})).rawString()
        }
        
        if let x = filter.education_id{
            params["education_id"] = JSON(x.map({$0.education_id.intValue})).rawString()
            params["education_name"] = JSON(x.map({$0.name})).rawString()
        }
        
        if let x = filter.certificate_id{
            params["certificate_id"] = JSON(x.map({$0.certificate_id.intValue})).rawString()
            params["certificate_name"] = JSON(x.map({$0.name})).rawString()
        }

        
        
        if let x = filter.manufacture_date{
            params["manufacture_date"] = JSON(x).rawString()
            params["years_name"] = JSON(x).rawString()
        }
        
        
        if let x = filter.gender{
            params["gender"] = JSON(x).rawString()
            params["gender_name"] = (x == 1) ? "Male".localized : "Famale".localized
        }

        
        if let x = filter.searchText{
            params["query"] = x
        }
        
        if let x = filter.is_new{
            params["is_new"] = x
            params["state_name"] = (x == 1) ? "new".localized : "old".localized
         }
        
        if let x = filter.is_automatic{
            params["is_automatic"] = x
            params["automatic_name"] = (x == 1) ? "new".localized : "old".localized
        }
        
        if let x = filter.with_furniture{
            params["with_furniture"] = x
            params["furniture_name"] = (x == 1) ? "yes".localized : "no".localized
        }
        
        if let x = filter.price.min{
            params["price_min"] = x
        }
        if let x = filter.price.max{
            params["price_max"] = x
        }
        if let x = filter.size.min{
            params["size_min"] = x
        }
        if let x = filter.size.max{
            params["size_max"] = x
        }
        if let x = filter.kilometer.min{
            params["kilometer_min"] = x
        }
        if let x = filter.kilometer.max{
            params["kilometer_max"] = x
        }
        if let x = filter.rooms_num.min{
            params["rooms_num_min"] = x
        }
        if let x = filter.rooms_num.max{
            params["rooms_num_max"] = x
        }
        if let x = filter.floors_number.min{
            params["floors_number_min"] = x
        }
        if let x = filter.floors_number.max{
            params["floors_number_max"] = x
        }

        if let x = filter.space.min{
            params["space_min"] = x
        }
        if let x = filter.space.max{
            params["space_max"] = x
        }
        if let x = filter.floor.min{
            params["floor_min"] = x
        }
        if let x = filter.floor.max{
            params["floor_max"] = x
        }
        if let x = filter.salary.min{
            params["salary_min"] = x
        }
        if let x = filter.salary.max{
            params["salary_max"] = x
        }
        
        if let x = filter.engine_capacity.min{
            params["engine_capacity_min"] = x
        }
        if let x = filter.engine_capacity.max{
            params["engine_capacity_max"] = x
        }

        
        return params
    }
        
}

class range_value {
    var min : String!
    var max : String!
}

