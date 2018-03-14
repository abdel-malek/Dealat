//
//  FilterParams.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation


class FilterParams{
    
    var searchText : String!
    var category : Cat!
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
        
}

class range_value {
    var min : String!
    var max : String!
}

