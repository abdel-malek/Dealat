//
//  CustomResponse.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import Foundation
import SwiftyJSON
import ObjectMapper
import AlamofireObjectMapper
import Alamofire

class CustomResponse : BaseEntity {
    
    var status : Bool = false
    var data : JSON!
    var message : String = ""
    var currency_en : String = ""
    var currency_ar : String = ""

    
    var description : String{
        return "CustomResponse: { status: \(status), data: \(data!) , message : \(message) }"
    }
    
    override func mapping(map: Map) {
        status <- map["status"]
        data <- map["data"]
        message <- map["message"]
        
        currency_en <- map["currency_en"]
        currency_ar <- map["currency_ar"]
        
    }
    
    
}

