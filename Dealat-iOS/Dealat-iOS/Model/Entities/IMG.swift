//
//  IMG.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/12/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class IMG : BaseEntity {
    
    var ad_image_id : JSON!
    var ad_id : JSON!
    var image : String!
    var isVideo : Bool = false
    
    // Mappable
    override func mapping(map: Map) {
        ad_image_id <- map["ad_image_id"]
        ad_id <- map["ad_id"]
        image <- map["image"]
        isVideo <- map["isVideo"]
    }
}


