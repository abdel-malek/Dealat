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
    var manufacture_date : [String]!
    
    var is_new : Int!
    var is_automatic : Int!
    var with_furniture : Int!
    
    var price = range_value()
    var size = range_value()
    var kilometer = range_value()
    var rooms_num = range_value()
    var space = range_value()
    var floor = range_value()
    var salary = range_value()
    
    static var shared = FilterParams()
    
    static func getParams(_ filter : FilterParams) -> [String : Any]{
        var params : [String : Any] = [:]
        
        if let x = filter.searchText{
            params["query"] = x
        }
        if let x = filter.category{
            params["category_id"] = x.category_id.intValue
        }
        if let x = filter.location{
            params["location_id"] = x.location_id.intValue
        }
        
        if let x = filter.type_id{
            params["type_id"] = x.type_id.intValue
        }
        
        if let x = filter.type_id{
            params["type_id"] = x.type_id.intValue
        }
        
        if let x = filter.type_model_id{
            params["type_model_id"] = JSON(x.map({$0.type_model_id.intValue})).rawString()
        }
        
        if let x = filter.schedule_id{
            params["schedule_id"] = JSON(x.map({$0.schedual_id.intValue})).rawString()
        }
        
        if let x = filter.education_id{
            params["education_id"] = JSON(x.map({$0.education_id.intValue})).rawString()
        }
        
        if let x = filter.manufacture_date{
            params["manufacture_date"] = JSON(x).rawString()
        }
        
        if let x = filter.searchText{
            params["query"] = x
        }

        
        if let x = filter.is_new{
            params["is_new"] = x
        }
        
        if let x = filter.is_automatic{
            params["is_automatic"] = x
        }
        
        if let x = filter.with_furniture{
            params["with_furniture"] = x
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
        if let x = filter.space.min{
            params["space_min"] = x
        }
        if let x = filter.space.max{
            params["space_num_max"] = x
        }
        if let x = filter.floor.min{
            params["floor_min"] = x
        }
        if let x = filter.floor.max{
            params["floor_num_max"] = x
        }
        if let x = filter.salary.min{
            params["salary_min"] = x
        }
        if let x = filter.salary.max{
            params["salary_num_max"] = x
        }
        
        return params
    }
        
}

class range_value {
    var min : String!
    var max : String!
}

