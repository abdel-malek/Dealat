//
//  Period.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 4/4/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Period : BaseEntity {
    
    var show_period_id : JSON!
    var name : String!
    var days : JSON!
    
    // Mappable
    override func mapping(map: Map) {
        show_period_id <- map["show_period_id"]
        name <- map["name"]
        days <- map["days"]
    }
}



