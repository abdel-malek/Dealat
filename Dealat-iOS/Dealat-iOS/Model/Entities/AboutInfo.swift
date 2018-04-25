//
//  AboutInfo.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 4/19/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class AboutInfo : BaseEntity {
    
    var about_us : String!
    
    var facebook_link : String!
    var twiter_link : String!
    var youtube_link : String!
    var linkedin_link : String!
    var instagram_link : String!
    
    var phone : String!
    var email : String!

    
    
    // Mappable
    override func mapping(map: Map) {
        about_us <- map["about_us"]
        
        facebook_link <- map["facebook_link"]
        twiter_link <- map["twiter_link"]
        youtube_link <- map["youtube_link"]
        linkedin_link <- map["linkedin_link"]
        instagram_link <- map["instagram_link"]


        phone <- map["phone"]
        email <- map["email"]
    }
    
    
    
    
}



