//
//  BookmarkQuery.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 4/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class BookmarkQuery : BaseEntity {
    
    var category_name : String!
    var city_name : String!
    var location_name : String!
    var price_min : String!
    var price_max : String!
    
    var space_max : String!
    var space_min : String!
    var rooms_num_min : String!
    var rooms_num_max : String!
    var floor_min : String!
    var floor_max : String!
    var furniture_name : String!
    
    var education_name : String!
    var schedule_name : String!
    var salary_min : String!
    var salary_max : String!
    
    var type_name : String!
    var model_name : String!
    var kilometer_min : String!
    var kilometer_max : String!
    var automatic_name : String!
    var years_name : String!
    
    var size_min : String!
    var size_max : String!
    
    var state_name : String!

    
    // Mappable
    override func mapping(map: Map) {
        category_name <- map["category_name"]
        city_name <- map["city_name"]
        location_name <- map["location_name"]
        price_min <- map["price_min"]
        price_max <- map["price_max"]
        
        space_max <- map["space_max"]
        space_min <- map["space_min"]
        rooms_num_min <- map["rooms_num_min"]
        rooms_num_max <- map["rooms_num_max"]
        floor_min <- map["floor_min"]
        floor_max <- map["floor_max"]
        furniture_name <- map["furniture_name"]
        
        education_name <- map["education_name"]
        schedule_name <- map["schedule_name"]
        salary_min <- map["salary_min"]
        salary_max <- map["salary_max"]
        
        type_name <- map["type_name"]
        model_name <- map["model_name"]
        kilometer_min <- map["kilometer_min"]
        kilometer_max <- map["kilometer_max"]
        automatic_name <- map["automatic_name"]
        years_name <- map["years_name"]
        
        size_min <- map["size_min"]
        size_max <- map["size_max"]
        
        state_name <- map["state_name"]
    }
    
    
    func getStrings() -> (String,String){
        
        var keys = ""
        var values = ""
        
        keys += (isNotNull(category_name)) ? ("Category".localized + ":\n") : ""
        values += (isNotNull(category_name)) ? (category_name! + "\n") : ""
        
        keys += (isNotNull(city_name)) ? ("City".localized + ":\n") : ""
        values += (isNotNull(city_name)) ? (city_name! + "\n") : ""
        
        keys += (isNotNull(location_name)) ? ("Area".localized + ":\n") : ""
        values += (isNotNull(location_name)) ? (location_name! + "\n") : ""
        
        keys += (isNotNull(price_min)) ? ("Price".localized + " " + "From".localized + ":\n") : ""
        values += (isNotNull(price_min)) ? (price_min! + "\n") : ""
        
        keys += (isNotNull(price_max)) ? ("Price".localized + " " + "To".localized + ":\n") : ""
        values += (isNotNull(price_max)) ? (price_max! + "\n") : ""
        
        keys += (isNotNull(space_min)) ? ("Space".localized + " " + "From".localized + ":\n") : ""
        values += (isNotNull(space_min)) ? (space_min! + "\n") : ""
        
        keys += (isNotNull(space_max)) ? ("Space".localized + " " + "To".localized + ":\n") : ""
        values += (isNotNull(space_max)) ? (space_max! + "\n") : ""
        
        keys += (isNotNull(rooms_num_min)) ? ("Rooms".localized + " " + "From".localized + ":\n") : ""
        values += (isNotNull(rooms_num_min)) ? (rooms_num_min! + "\n") : ""
        
        keys += (isNotNull(rooms_num_max)) ? ("Rooms".localized + " " + "To".localized + ":\n") : ""
        values += (isNotNull(rooms_num_max)) ? (rooms_num_max! + "\n") : ""
        
        keys += (isNotNull(floor_min)) ? ("Floor".localized + " " + "From".localized + ":\n") : ""
        values += (isNotNull(floor_min)) ? (floor_min! + "\n") : ""
        
        keys += (isNotNull(floor_max)) ? ("Floor".localized + " " + "To".localized + ":\n") : ""
        values += (isNotNull(floor_max)) ? (floor_max! + "\n") : ""
        
        keys += (isNotNull(furniture_name)) ? ("Furniture".localized + ":\n") : ""
        values += (isNotNull(furniture_name)) ? (furniture_name! + "\n") : ""
        
        keys += (isNotNull(education_name)) ? ("Education".localized + ":\n") : ""
        values += (isNotNull(education_name)) ? (education_name! + "\n") : ""
        
        keys += (isNotNull(schedule_name)) ? ("Schedule".localized + ":\n") : ""
        values += (isNotNull(schedule_name)) ? (schedule_name! + "\n") : ""
        
        keys += (isNotNull(salary_min)) ? ("Salary".localized + " " + "From".localized + ":\n") : ""
        values += (isNotNull(salary_min)) ? (salary_min! + "\n") : ""
        
        keys += (isNotNull(salary_max)) ? ("Salary".localized + " " + "To".localized + ":\n") : ""
        values += (isNotNull(salary_max)) ? (salary_max! + "\n") : ""
        
        keys += (isNotNull(type_name)) ? ("TypeName".localized + ":\n") : ""
        values += (isNotNull(type_name)) ? (type_name! + "\n") : ""
        
        keys += (isNotNull(model_name)) ? ("TypeModelName".localized + ":\n") : ""
        values += (isNotNull(model_name)) ? (model_name! + "\n") : ""
        
        keys += (isNotNull(kilometer_min)) ? ("Kilometer".localized + " " + "From".localized + ":\n") : ""
        values += (isNotNull(kilometer_min)) ? (kilometer_min! + "\n") : ""
        
        keys += (isNotNull(kilometer_max)) ? ("Kilometer".localized + " " + "To".localized + ":\n") : ""
        values += (isNotNull(kilometer_max)) ? (kilometer_max! + "\n") : ""
        
        keys += (isNotNull(automatic_name)) ? ("Automatic".localized + ":\n") : ""
        values += (isNotNull(automatic_name)) ? (automatic_name! + "\n") : ""
        
        keys += (isNotNull(years_name)) ? ("Year".localized + ":\n") : ""
        values += (isNotNull(years_name)) ? (years_name! + "\n") : ""
        
        
        keys += (isNotNull(size_min)) ? ("Size".localized + " " + "From".localized + ":\n") : ""
        values += (isNotNull(size_min)) ? (size_min! + "\n") : ""
        
        keys += (isNotNull(size_max)) ? ("Size".localized + " " + "To".localized + ":\n") : ""
        values += (isNotNull(size_max)) ? (size_max! + "\n") : ""
        
        keys += (isNotNull(state_name)) ? ("State".localized + ":\n") : ""
        values += (isNotNull(state_name)) ? (state_name! + "\n") : ""
        
        if keys.count > 1{
            keys.removeLast(1)
            values.removeLast(1)
        }
        
//        keys = keys.dropLast()
//        values = values.dropLast()

        
        return (keys,values)
    }
    
    
    func isNotNull(_ s : String!) -> Bool{
        return (s != nil) && (!s.isEmpty)
    }

    
    
}
