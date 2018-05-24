//
//  Certificate.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 5/10/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Certificate : BaseEntity {
    
    var certificate_id : JSON!
    var name : String!
    
    // Mappable
    override func mapping(map: Map) {
        certificate_id <- map["certificate_id"]
        name <- map["name"]
    }
}

